import java.io.IOException;

import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

/**
 * 
 * @author rwangari
 * Section 4
 * Analyzing House data with Linear Regression
 *
 */

public class HousePricesAnalysis {

	public HousePricesAnalysis() {
		// TODO Auto-generated constructor stub	
	
		
	}
	
	public void cleanData (Table myTable) {
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
/**
 * Section 4
 */

	//Loading the train dataset
	//Table HousePrices_Train  = Table.read().csv("DataSets/HousePrices.csv");
	
	Table HousePrices_Train = Table.read().csv(CsvReadOptions
		    .builder("DataSets/HousePrices.csv")
		    .missingValueIndicator("NA"));
		
		
	System.out.println("Printing the last  rows of the table");
	Table tableTail = HousePrices_Train.last(5);
	System.out.println(tableTail);
		
		
	//Getting the structure of the dataset		
	Table HouseSpec = HousePrices_Train.structure();
	System.out.println(HouseSpec);
		
		
	//Splitting the dataset into two sets
	Table housePricesTrain = (Table)HousePrices_Train.where(Selection.withRange(1, 1022));
	Table housePricesTest = (Table)	HousePrices_Train.where(Selection.withRange(1023, 1460));
	
	
	//Cleaning the dataset	
	
	
	// Cleaning the data, Looking for the null & empty rows
	
	//Table  housePricesTrain_filtered =housePricesTrain.dropRowsWithMissingValues();
	Table  housePricesTrain_filtered =housePricesTrain.removeColumnsWithMissingValues();
	
	
	
	//Getting the structure of the dataset		
		Table HouseSpec1 = housePricesTrain.structure();
		System.out.println(HouseSpec1);
	
	//Deleting the last Column of the test dataset 
	
	//Getting the structure of the dataset		
			Table TrainSpec = housePricesTrain.structure();
			System.out.println(TrainSpec);
			
			Table table2Tail = housePricesTrain_filtered.last(5);
			System.out.println(table2Tail);
			
			Table table3Tail = housePricesTest.last(5);
			System.out.println(table3Tail);
			

	
	}

}
