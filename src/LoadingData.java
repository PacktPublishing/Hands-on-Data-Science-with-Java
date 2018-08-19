import java.io.IOException;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;

/**
 * Video 2.1 : Loading data from different sources
 * @author rwangari
 *
 */
public class LoadingData {

	public LoadingData() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		
		/**
		 * When loading the entire CSV sheet
		 */
			Table hrAnalytics  = Table.read().csv("../HR_comma_sep2.csv");
			
		/**
		 * When Loading Specific columns from the  scv
		 * 
		 */
//			ColumnType[] types = {LOCAL_DATE, INTEGER, FLOAT, FLOAT, CATEGORY};
//			Table t = Table.read().csv(CsvReadOptions
//			    .builder("myFile.csv")
//			    .columnTypes(types))
					
		
			//Loading from CSV when you don't want all the columns
			//https://jtablesaw.wordpress.com/user-guide/tables/
//			ColumnType[] types = { SKIP, SKIP, INTEGER, INTEGER }; 
//					//INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, INTEGER, STRING };
//			Table myHrStuffs = Table.read().csv(CsvReadOptions
//			    .builder("../HR_comma_sep2.csv")
//			    .columnTypes(types));
//			
		
		/**
		 * Getting the structure of the table, this is knowing the data types using Table saw 
		 */
			Table structure = hrAnalytics.structure();
			System.out.println(structure);
			
		
		/**
		 * Creating a function to load data from a database
		 * @param args
		 */
	

	/**
	 * Creating a function  to load data from API
	 * @param args
	 */

	}

}
