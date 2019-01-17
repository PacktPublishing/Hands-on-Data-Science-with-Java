import java.io.IOException;

import tech.tablesaw.api.Table;

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

		//Loading the train dataset
		Table HousePrices_Train  = Table.read().csv("DataSets/HousePrices.csv");
		
		
		System.out.println("Printing the last  rows of the table");
		Table tableTail = HousePrices_Train.last(5);
		System.out.println(tableTail);
	}

}
