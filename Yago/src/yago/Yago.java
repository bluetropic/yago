package yago;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Yago {
	
	private String carNode;
	private String vehicleNode;
	//storage
	Map<String, ArrayList<String>> yagoTaxonomy = new HashMap<String, ArrayList<String>>();
	Map<String, ArrayList<String>> carTaxonomy = new HashMap<String, ArrayList<String>>();
	Map<String, ArrayList<String>> vehicleTaxonomy = new HashMap<String, ArrayList<String>>();

	Map<String, String> carEntityDictionary = new HashMap<String, String>();
	Map<String, String> typeDictionary = new HashMap<String, String>();

	Set<String> revTypeSet = new HashSet<String>();
	Set<String> carTypeSet = new HashSet<String>();
	Set<String> vehicleTypeSet = new HashSet<String>();
	Set<String> categoryTypeSet = new HashSet<String>();

	Map<String, ArrayList<String>> yagoClassPath = new HashMap<String, ArrayList<String>>();
	Map<String, ArrayList<Integer>> typeCat = new HashMap<String, ArrayList<Integer>>();
	Map<String, Entity> entityTable = new HashMap<String, Entity>();

	DataLoader dl = new DataLoader();
	DataWriter dw = new DataWriter();

	public void genTaxonomy(String taxonomyFile) {
		dl.loadTaxonomy(yagoTaxonomy, taxonomyFile);
		dl.sortTaxonomy(yagoTaxonomy);
		dl.genSmallTaxo(yagoTaxonomy, carTaxonomy, carNode);
		dl.genSmallTaxo(yagoTaxonomy, vehicleTaxonomy, vehicleNode);
		yagoTaxonomy.clear();
	}

	public void genCatTypeSet(String typeOfFile,String revTypeFile) {

		dl.getTypeIdSet(carTaxonomy, carTypeSet);
		dl.getTypeIdSet(vehicleTaxonomy, vehicleTypeSet);
		dl.getEntityDictionary(typeOfFile, carTypeSet, carEntityDictionary);
		dl.getRevTypeSet(typeOfFile, carEntityDictionary, revTypeSet);
		dl.getCatTypeSet(vehicleTypeSet, carTypeSet, revTypeSet, categoryTypeSet);

		vehicleTypeSet.clear();
		carTypeSet.clear();
		dl.getCatTypeDictionary(typeOfFile, categoryTypeSet, typeDictionary);
		dw.writCategoryType(typeDictionary, revTypeFile);
		System.out.println("the category of types has been obtained!");
	}

	public void genTable(String taxonomyFile,String typeOfFile,String typeCatFile) {
		dl.loadTaxonomy(yagoTaxonomy, taxonomyFile);	
		dl.genSmallTaxo(yagoTaxonomy, vehicleTaxonomy, vehicleNode);
		yagoTaxonomy.clear();
		//dl.sortTaxonomy(vehicleTaxonomy);
		dl.getPaths(vehicleTaxonomy, yagoClassPath,vehicleNode, carNode);
		dl.loadTypeCat(typeCatFile, typeCat);
		//dl.getCatTypeDictionary(taxonomyFile, categoryTypeSet, typeDictionary);
		dl.loadEntityTable(typeOfFile, typeCat, yagoClassPath, entityTable);	
	}
	public void writeTable(){
		dw.printTable(entityTable);
	}
	public void writeTable(String tableFile,String headFile){
		System.out.println("We have found "+entityTable.size()+" cars.");
		dw.writeEntityTable(entityTable, headFile,tableFile);
	}
	public void writeTableX(String taxonomyFile,String tableFile,String headFile){
		dl.getTypeIdSet(vehicleTaxonomy, vehicleTypeSet);
		dl.getCatTypeDictionary(taxonomyFile, vehicleTypeSet, typeDictionary);
		//dl.getCatTypeDictionary(typeOfFile, categoryTypeSet, typeDictionary);
		System.out.println("We have found "+entityTable.size()+" cars.");
		dw.writeEntityTableX(entityTable, headFile, tableFile,typeDictionary);
	}
	
	public Yago(String carNode, String vehicleNode) {
		super();
		this.carNode = carNode;
		this.vehicleNode = vehicleNode;
	}
}
