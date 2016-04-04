package yago;

import java.util.ArrayList;
import java.util.Map;

public class Type {
	private int cat;
	private String typeId;
	private String typeName;
	private ArrayList<String> pathArr = new ArrayList<String>();

	public String getIdPath() {
		String path = "";
		if (pathArr.size() > 0) {
			for (String p : pathArr) {
				path = "[" + p + "]";
			}
		}
		return path;
	}

	public String getNamePath(Map<String, String> typeDic) {
		String path = "";
		if (pathArr.size() > 0) {
			for (String p : pathArr) {
				path = "[" + parsePath(p, typeDic) + "]";
			}
		}
		return path;
	}

	public String parsePath(String p, Map<String, String> typeSet) {
		String path = "";
		String typeName = "";
		String sp=p.substring(1);
		String[] typeIdSet = sp.split("/");
		for (String s : typeIdSet) {
			if (s != null) {
				typeName = typeSet.get(s);
				path = path + "/" + typeName;
			}

		}
		return path;
	}

	public Type(int cat, String typeId, String typeName, ArrayList<String> pathArr) {
		super();
		this.cat = cat;
		this.typeId = typeId;
		this.typeName = typeName;
		this.pathArr = pathArr;
	}

	public int getCat() {
		return cat;
	}

	public void setCat(int cat) {
		this.cat = cat;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public ArrayList<String> getPathArr() {
		return pathArr;
	}

	public void setPathArr(ArrayList<String> pathArr) {
		this.pathArr = pathArr;
	}

}
