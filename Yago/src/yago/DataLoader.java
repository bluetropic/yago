package yago;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class DataLoader {
	public String loadTableHead(String headFile) {
		String head = "CarID";
		int n = 0;
		try {
			FileReader fr = new FileReader(headFile);
			BufferedReader reader = new BufferedReader(fr);
			String line;

			while ((line = reader.readLine()) != null) {
				head = head + "\t" + n + ":" + line;
				n++;
			}
			reader.close();
			fr.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return head;

	}

	public void loadEntityTable(String typeOfFile, Map<String, ArrayList<Integer>> typeCat,
			Map<String, ArrayList<String>> typePaths, Map<String, Entity> entityTable) {
		String id1 = "", id2 = "", left = "", right = "";
		try {
			FileReader fr = new FileReader(typeOfFile);
			BufferedReader reader = new BufferedReader(fr);
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] arr = line.split("\t");
				left = arr[1].trim();
				right = arr[3].trim();

				String id;
				id = arr[0].replace("<", "");
				id = arr[0].replace(">", "");
				String[] idArr = id.split("_");
				if (idArr.length == 4) {
					id1 = idArr[1];
					id2 = idArr[3];
				} else {
					id1 = id;
					id2 = id;
				}

				if (typeCat.containsKey(right)) {

					if (entityTable.containsKey(id1)) {
						for (int i : typeCat.get(right)) {
							Type t = new Type(i, id2, right, typePaths.get(id2));
							entityTable.get(id1).addType(t);
						}
					} else {
						Entity e = new Entity(id1, left);
						for (int i : typeCat.get(right)) {
							Type t = new Type(i, id2, right, typePaths.get(id2));
							e.addType(t);
						}
						entityTable.put(id1, e);
						// System.out.println(e.getId()+" "+e.getName());
					}
				}
			}
			fr.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getEntityDictionary(String typeOfFile, Set<String> typeSet, Map<String, String> carEntityDictionary) {
		FileReader fr;
		String line;
		String id1 = "", id2 = "", left = "", right = "";
		try {
			fr = new FileReader(typeOfFile);
			BufferedReader reader = new BufferedReader(fr);
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {

				String[] arr = line.split("\t");
				left = arr[1].trim();
				right = arr[3].trim();

				String id;
				id = arr[0].replace("<", "");
				id = arr[0].replace(">", "");
				String[] idArr = id.split("_");
				if (idArr.length == 4) {
					id1 = idArr[1];
					id2 = idArr[3];
				} else {
					id1 = id;
					id2 = id;
				}

				if (typeSet.contains(id2)) {
					carEntityDictionary.put(id1, left);
				}
			}
			reader.close();
			fr.close();
			System.out.println("Vehical Taxonomy has been loaded!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File Not FOUND!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File open ERROR!");
		}
	}

	public void getCatTypeDictionary(String taxonomyFile, Set<String> typeSet, Map<String, String> typeDictionary) {
		FileReader fr;
		String line;
		String id1 = "", id2 = "", left = "", right = "";
		try {
			fr = new FileReader(taxonomyFile);
			BufferedReader reader = new BufferedReader(fr);
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {

				String[] arr = line.split("\t");
				left = arr[1].trim();
				right = arr[3].trim();

				String id;
				id = arr[0].replace("<", "");
				id = arr[0].replace(">", "");
				String[] idArr = id.split("_");
				if (idArr.length == 4) {
					id1 = idArr[1];
					id2 = idArr[3];
				} else {
					id1 = id;
					id2 = id;
				}

				if (typeSet.contains(id1)) {
					typeDictionary.put(id1, left);
				}
			}
			reader.close();
			fr.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File Not FOUND!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File open ERROR!");
		}
	}

	public void getRevTypeSet(String typeOfFile, Map<String, String> carDic, Set<String> revTypeIdSet) {
		// get the typeIdset based on the entityIdSet
		FileReader fr;
		String line;
		String id1 = "", id2 = "", left = "", right = "";
		try {
			fr = new FileReader(typeOfFile);
			BufferedReader reader = new BufferedReader(fr);
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {

				String[] arr = line.split("\t");
				left = arr[1].trim();
				right = arr[3].trim();

				String id;
				id = arr[0].replace("<", "");
				id = arr[0].replace(">", "");
				String[] idArr = id.split("_");
				if (idArr.length == 4) {
					id1 = idArr[1];
					id2 = idArr[3];
				} else {
					id1 = id;
					id2 = id;
				}

				if (carDic.containsKey(id1)) {
					revTypeIdSet.add(id2);
				}
			}
			reader.close();
			fr.close();
			System.out.println("Vehical Taxonomy has been loaded!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File Not FOUND!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File open ERROR!");
		}
	}

	public void getTypeIdSet(Map<String, ArrayList<String>> smallTaxonomy, Set<String> typeIdSet) {
		// get typeIdSet from a part of typeTaxomomy
		for (Map.Entry<String, ArrayList<String>> superClassList : smallTaxonomy.entrySet()) {
			String superClass = superClassList.getKey();
			typeIdSet.add(superClass);

			ArrayList<String> subClassList = smallTaxonomy.get(superClass);
			for (String subClass : subClassList) {
				typeIdSet.add(subClass);
			}
		}
	}

	public void getCatTypeSet(Set<String> tv, Set<String> tc, Set<String> tr, Set<String> tt) {
		// get the typeset for further categorization tc union
		// (tv-tc)intersect(tr-tc)
		// tt.addAll(tv);tt.retainAll(tr);
		//// tv.removeAll(tc);
		// tr.removeAll(tc);
		// tt.addAll(tv);
		// tt.retainAll(tr);
		// tt.addAll(tc);
		tt.addAll(tv);
		tt.retainAll(tr);
	}

	public void loadTaxonomy(Map<String, ArrayList<String>> yagoTaxonomy, String taxonoFile) {
		// load the whole taxonomy from the yagoTaxonomy.tsv
		FileReader fr;
		String line;
		String id1 = "", id2 = "";
		try {
			fr = new FileReader(taxonoFile);
			BufferedReader reader = new BufferedReader(fr);
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {

				String[] arr = line.split("\t");

				String id;
				id = arr[0].replace("<", "");
				id = arr[0].replace(">", "");
				String[] idArr = id.split("_");
				if (idArr.length == 4) {
					id1 = idArr[1];
					id2 = idArr[3];
				} else {
					id1 = id;
					id2 = id;
				}

				addToMap02(id2, id1, yagoTaxonomy);
			}
			reader.close();
			fr.close();
			System.out.println("the whole Taxonomy has been loaded!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File Not FOUND!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File open ERROR!");

		}
	}

	public void genSmallTaxo(Map<String, ArrayList<String>> yagoTaxonomy, Map<String, ArrayList<String>> smallTaxonomy,
			String startNode) {
		if (yagoTaxonomy.containsKey(startNode)) {
			ArrayList<String> subClassList = yagoTaxonomy.get(startNode);
			smallTaxonomy.put(startNode, subClassList);
			for (String subClass : subClassList) {
				genSmallTaxo(yagoTaxonomy, smallTaxonomy, subClass);
			}
		}
		// System.out.println("a part of Taxonomy rooted at " + startNode + "
		// has been obtained!");
	}

	public void loadTypeCat(String catFile, Map<String, ArrayList<Integer>> typeCat) {
		FileReader fr;
		String line;
		String className;
		int catNum;
		try {
			fr = new FileReader(catFile);
			BufferedReader reader = new BufferedReader(fr);
			while ((line = reader.readLine()) != null) {
				String[] t = line.split("\t");

				className = t[0];
				catNum = Integer.parseInt(t[1]);
				// System.out.println(pos + " " + className + " " + catNum);
				addToMap02(className, catNum, typeCat);
			}
			reader.close();
			fr.close();
			// System.out.println("Class List has been loaded!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File Not FOUND!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File open ERROR!");

		}
	}

	public void getPaths(Map<String, ArrayList<String>> smallTaxonomy, Map<String, ArrayList<String>> typePaths,
			String startNode, String cutNode) {
		// get the paths for all the nodes below the root or startnode
		String curParentPath = "";
		ArrayList<String> curParentArr = new ArrayList<String>();
		curParentArr.add(curParentPath);

		genPath(smallTaxonomy, typePaths, startNode, curParentArr);

		String cut = "/" + cutNode;
		for (Map.Entry<String, ArrayList<String>> nodeList : typePaths.entrySet()) {

			ArrayList<String> pathArr = nodeList.getValue();
			for (int i = 0; i < pathArr.size(); i++) {
				int pos = pathArr.get(i).indexOf(cut);
				if (pos >= 0) {
					String path = pathArr.get(i).substring(pos);
					pathArr.set(i, path);
				}
			}

		}

	}

	public void genPath(Map<String, ArrayList<String>> smallTaxonomy, Map<String, ArrayList<String>> typePaths,
			String curNode, ArrayList<String> parentPathArr) {
		// generating the path from startNode to the current type node
		// recursively
		
		if (typePaths.containsKey(curNode)) {
			ArrayList<String> curPathArr=typePaths.get(curNode);
			for(String parentPath:parentPathArr){
				curPathArr.add(parentPath+"/"+curNode);
			}
		} else {
			ArrayList<String> curPathArr=new ArrayList<String>();
			for(String parentPath:parentPathArr){
				curPathArr.add(parentPath+"/"+curNode);
			}
			typePaths.put(curNode, curPathArr);
		}

		if (smallTaxonomy.containsKey(curNode)) {
			ArrayList<String> adjList = new ArrayList<String>();
			ArrayList<String> pathArr=typePaths.get(curNode);
			adjList = smallTaxonomy.get(curNode);
			for (String s : adjList) {
				genPath(smallTaxonomy, typePaths, s, pathArr);
			}
		}
		return;
	}

	public void sortTaxonomy(Map<String, ArrayList<String>> mapList) {
		ArrayList<String> adjList;
		for (Map.Entry<String, ArrayList<String>> nodeList : mapList.entrySet()) {
			adjList = nodeList.getValue();
			Collections.sort(adjList);
		}
	}

	public void addToMap01(String key, String value, Map<String, String> map) {
		if (map.get(key) == null)
			map.put(key, value);
	}

	public void addToMap02(String key, String value, Map<String, ArrayList<String>> map) {
		if (map.containsKey(key)) {
			map.get(key).add(value);

		} else {
			ArrayList<String> adjList = new ArrayList<String>();
			adjList.add(value);
			map.put(key, adjList);
		}
	}

	public void addToMap02(String key, Integer value, Map<String, ArrayList<Integer>> map) {
		if (map.containsKey(key)) {
			map.get(key).add(value);

		} else {
			ArrayList<Integer> adjList = new ArrayList<Integer>();
			adjList.add(value);
			map.put(key, adjList);
		}

	}
}
