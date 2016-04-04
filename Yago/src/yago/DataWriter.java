package yago;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class DataWriter {
	String nextLine = System.getProperty("line.separator");
	int maxClum = 13;
	DataLoader dl=new DataLoader();
	
	public void printTable(Map<String, Entity> entityTable) {
			for (Map.Entry<String, Entity> entities : entityTable.entrySet()) {
				Entity e = entities.getValue();
				System.out.println(e.getAttrString(maxClum));
			}

	}
	public void writeEntityTable(Map<String, Entity> entityTable, String headFile,String tableFile) {
		
		
		try {
			FileWriter fw = new FileWriter(tableFile, true);
			BufferedWriter bufw = new BufferedWriter(fw);
			String tableHead=dl.loadTableHead(headFile);
			bufw.write(tableHead);
			bufw.newLine();
			
			for (Map.Entry<String, Entity> entities : entityTable.entrySet()) {
				Entity e = entities.getValue();
				bufw.write(e.getAttrString(maxClum));
				bufw.newLine();
			}
			bufw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeEntityTableX(Map<String, Entity> entityTable, String headFile,String tableFile,Map<String, String> typeDic) {

		try {
			FileWriter fw = new FileWriter(tableFile, true);
			BufferedWriter bufw = new BufferedWriter(fw);
			String tableHead=dl.loadTableHead(headFile);
			bufw.write(tableHead);
			bufw.newLine();
			
			for (Map.Entry<String, Entity> entities : entityTable.entrySet()) {
				Entity e = entities.getValue();
				bufw.write(e.getAttrString(maxClum, typeDic));
				bufw.newLine();
			}
			bufw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writCategoryType(Map<String, String> TypeDictionary, String catTypeFile) {
		String line = "";
		try {
			File outputFile = new File(catTypeFile);
			FileWriter fw = new FileWriter(outputFile);
			BufferedWriter bufw = new BufferedWriter(fw);
			for (Map.Entry<String, String> types : TypeDictionary.entrySet()) {
				line = types.getKey() + "\t" + types.getValue();
				bufw.write(line);
				bufw.newLine();
			}
			bufw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeSet(Set<String> strSet, String fileName) {
		try {
			File outputFile = new File(fileName);
			FileWriter fw = new FileWriter(outputFile);
			BufferedWriter bufw = new BufferedWriter(fw);
			for (String line : strSet) {
				bufw.write(line);
				bufw.newLine();
			}
			bufw.newLine();
			bufw.close();
			fw.close();
			System.out.println(fileName + " has been created!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeTypeList(Map<String, ArrayList<String>> yagoType, String typeListFile) {
		try {
			File outputFile = new File(typeListFile);
			FileWriter fw = new FileWriter(outputFile);
			BufferedWriter bufw = new BufferedWriter(fw);
			String superClass;
			ArrayList<String> adjList;
			String line = "";
			for (Map.Entry<String, ArrayList<String>> superList : yagoType.entrySet()) {
				superClass = superList.getKey();
				line = superClass + ":\t";
				adjList = superList.getValue();

				for (String subClass : adjList) {
					line = line + "\t" + subClass;
				}

				bufw.write(line);
				bufw.newLine();
			}

			bufw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeClassTypeTree(Map<String, ArrayList<String>> smallTaxonomy,
			Map<String, ArrayList<String>> yagoType, String classTypeTreeFile, String startNode, int level) {
		printStartNode(classTypeTreeFile, startNode, level);
		printEntList(classTypeTreeFile, startNode, level + 1, yagoType);

		if (smallTaxonomy.containsKey(startNode)) {
			ArrayList<String> adjList = new ArrayList<String>();
			adjList = smallTaxonomy.get(startNode);
			for (String s : adjList) {
				writeClassTypeTree(smallTaxonomy, yagoType, classTypeTreeFile, s, level + 1);
			}
		}
		return;
	}

	public void writeClassTree(Map<String, ArrayList<String>> smallTaxonomy, String classTreeFile, String startNode,
			int level) {
		printStartNode(classTreeFile, startNode, level);
		if (smallTaxonomy.containsKey(startNode)) {
			ArrayList<String> adjList = smallTaxonomy.get(startNode);
			for (String s : adjList) {
				writeClassTree(smallTaxonomy, classTreeFile, s, level + 1);
			}
		}
	}

	public void printStartNode(String classTreeFile, String startNode, int level) {
		String prefix = "";
		String line = "";
		for (int i = 0; i < level; i++) {
			prefix = prefix + "\t\t";
		}
		line = prefix + startNode + nextLine;
		try {
			// ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
			FileWriter writer = new FileWriter(classTreeFile, true);
			writer.write(line);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeClassPath(Map<String, ArrayList<String>> yagoClassPath, String pathFile) {
		String line = "";
		ArrayList<String> curPathArr;

		try {
			// ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
			FileWriter writer = new FileWriter(pathFile);

			for (Map.Entry<String, ArrayList<String>> classList : yagoClassPath.entrySet()) {
				String className = classList.getKey();
				line = line + className + ":";
				curPathArr = classList.getValue();
				for (String path : curPathArr) {
					line = line + "\t[" + path + "]";
				}
				line = line + nextLine;
				writer.write(line);
				line = "";
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printEntList(String fileName, String classNode, int level, Map<String, ArrayList<String>> typeList) {
		if (typeList.containsKey(classNode)) {
			ArrayList<String> entList = typeList.get(classNode);
			for (String entity : entList) {
				outputEntity(fileName, entity, level);
			}
		}
		return;
	}

	public void outputEntity(String fileName, String entity, int level) {

		String content = "";

		for (int i = 0; i < level; i++) {
			content = content + "\t\t";
		}
		content = content + " [" + entity + "] " + nextLine;
		try {
			// ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
