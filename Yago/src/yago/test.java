package yago;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String startNodeVehicle = "<wordnet_vehicle_104524313>";
		//String startNodeCar = "<wordnet_car_102958343>";
		String carId="2sczqq";
		String vehicleId="1sba8z8";
		//01. input file    B is subClass of  A
		String taxonomyFile = "in/yagoTaxonomy.tsv";
		//02. input file   C is a type of B
		String typeOfFile = "in/yagoTypes.tsv";	
		//03. input file  A  0
		String typeCategory="in/936typeCategory.txt";
		//04. headfile
		String headFile="in/tableHead.txt";
		//01. output file
		//String revTypeFile="out/raw-typeCategory.txt";
		//String carSetFile="out/carSetFile.txt";
		//String carTaxoFile="out/carTypeFile.txt";
		//String VehiTaxoFile="out/VehicalType.txt";
		String tableFile="out/entityTable-short.txt";
		
		Yago yago = new Yago(carId,vehicleId);
		
		yago.genTable(taxonomyFile,typeOfFile, typeCategory);
		//yago.writeTable();
		yago.writeTable(tableFile, headFile);
		//yago.writeTableX(taxonomyFile,tableFile,headFile);
	
		System.out.println("-----ok-----");
	
	}

}
