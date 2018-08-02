
import java.io.IOException;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;

/**
 * 
 */

/**
 * @author rwangari
 *
 */
public class Introduction {

	/**
	 * 
	 */
	public Introduction() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	public static void printNonsense() {
		
		System.out.println ("We are here being wasted");
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("We are in Main nuka");
		printNonsense();
		
		/**
		 * Using table saw, the NumberColumn Class
		 */
		
		double[] numbers = {1, 2, 3, 4};
		NumberColumn nc = DoubleColumn.create("Test", numbers);
		System.out.print(nc.print());
		
		//Getting item at Index 2
		
		System.out.println("Getting item at the second index, indexes starts from zero");
		System.out.println(nc.get(2));
		
		//multiply every item in the column with a 4
		
		NumberColumn nc2 = nc.multiply(4);
		System.out.println(nc2.print());
		
		//getting the items in rthe column that are less than 3
		//nc4 =nc.isLessThan(3);
		//System.out.println(nc.isLessThan(3).print();
		
		//Getting the mathematical Calculations
		
		double stdDev = nc.standardDeviation();	
		//double theCount = nc.count();	
		double theMean = nc.mean();	
		double theMedian = nc.median();	
		double variance = nc.variance();	
		
		System.out.println("Deviation" +stdDev+"mean" +theMean);
				
		/**
		 * https://jtablesaw.github.io/tablesaw/userguide/filters.html
		 * Filters
		 */
		
		//Loading data from a csv, when you need all the columns
		
		Table hrAnalytics  = Table.read().csv("../HR_comma_sep2.csv");
		
		//Getting the structure of the table
		Table structure = hrAnalytics.structure();
		
		System.out.println(structure);
		
		//Getting the shape and size of the table
		
		String shape = hrAnalytics.shape();
		System.out.println(shape);
		
		
		//Printing the first few rows
		Table head = hrAnalytics.first(3);
		System.out.println(head);
		
//		//Loading from CSV when you don't want all the columns
//		//ColumnType[] types = {SKIP, SKIP, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, STRING };
//		Table myHrStuffs = Table.read().csv(CsvReadOptions
//		    .builder("../HR_comma_sep2.csv")
//		    .columnTypes(types));
				
		/**
		 * Loading data from a database ( MSQL)
		 * https://jtablesaw.github.io/tablesaw/userguide/importing_data
		 */
		
				
		//Handling Missing data
		
		/**
		 * https://jtablesaw.github.io/tablesaw/userguide/importing_data
		 */
		//Cleaning the data
		
//		<groupId>com.kennycason</groupId>
//		<artifactId>kumo-core</artifactId>
//		<version>1.13</version>

		
		//
		
		
	}

}
