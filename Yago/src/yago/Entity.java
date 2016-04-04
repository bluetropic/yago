package yago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Entity {
	private String id;
	private String name;
	private ArrayList<Type> typeList = new ArrayList<Type>();
	typeComparator tcmp = new typeComparator();

	public String getAttrString(int catNumber, Map<String, String> typeDic) {
		String line = this.name;
		sortTypeList();

		for (int i = 0; i < catNumber; i++) {
			String path = "";
			for (Type t : typeList) {
				if (t.getCat() == i) {
					path = path + t.getNamePath(typeDic);
				}
			}
			if (path != "") {
				line = line+"\t" + path;
			} else {
				line = line+"\t" + " ";
			}
		}
		return line;
	}

	public String getAttrString(int catNumber) {
		String line = this.name;
		sortTypeList();

		for (int i = 0; i < catNumber; i++) {
			String path = "";
			for (Type t : typeList) {
				if (t.getCat() == i) {
					path = path + t.getIdPath();
				}
			}
			if (path != "") {
				line = line+"\t" + path;
			} else {
				line = line+"\t" + " ";
			}
		}
		return line;
	}

	public Entity(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public void addType(Type t) {
		boolean notFound = true;
		for (Type type : typeList) {
			if (tcmp.compare(type, t) == 0) {
				notFound = false;
				break;
			}
		}
		if (notFound)
			typeList.add(t);
	}

	public void sortTypeList() {
		Collections.sort(this.typeList, this.tcmp);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(ArrayList<Type> typeList) {
		this.typeList = typeList;
	}

}
