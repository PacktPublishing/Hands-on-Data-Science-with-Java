import java.io.IOException;
import java.sql.*;

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
		 * When Loading Specific columns from the  csv
		 * 
		 */
	
		/**
		 * Getting the structure of the table, this is knowing the data types using Table saw 
		 */
			Table structure = hrAnalytics.structure();
			System.out.println(structure);
			
		
		/**
		 * Creating a function to load data from a database
		 * @param args
		 */
			
			//Table t = Table.read().db(ResultSet resultSet, String tableName);
			/**
			 * Creating a database connection, one requires
			 */
			try {
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/customers","root","");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	

	/**
	 * Creating a function  to load data from API
	 * @param args
	 */

	}

}
