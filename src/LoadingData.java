import java.io.IOException;
import java.net.URL;
import java.sql.*;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;
import java.io.*;
;/**
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
		 * Give the path that the csv is located
		 */
			Table hrAnalytics  = Table.read().csv("../HR_comma_sep2.csv");
			
			
		/**
		 * Getting the structure of the table, this is knowing the data types using Table saw 
		 */
			Table structure = hrAnalytics.structure();
			System.out.println(structure);
			System.out.println("WE ARE HERE, DONE PRINTING hr_comma_sep2.csv ?");
	
		/**
		 *  Getting the data from  mysql database
		 *  USING JDBC
		 *  I have created a table in my local database
		 *  Database Name : Customers
		 *  Table Name : Customer
		 */
		
			String DB_URL = "jdbc:mysql://localhost:3306/customers";
			Connection conn= null;
			try {
				conn = DriverManager.getConnection(DB_URL,"root","");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			Table DBcustomers = null; 
			try (Statement myStament = conn.createStatement()) {
			  String sql = "SELECT * FROM Customer";
			  try (ResultSet results = myStament.executeQuery(sql)) {
				  DBcustomers = Table.read().db(results, "Customer");
			  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//Testing the database table
		Table DBstructure = DBcustomers.structure();
		System.out.println(DBstructure);
		System.out.println("This two are good , off to streaming");
		
		
		/**
		 * Loading the data from the API
		 * We will be using the Stream API function
		 * Creating a function  to load data from  Stream API
		 * The data has to be passed through the Stream Interface
		 * You need to have the link to where the data is located and the name of the file that you want to retrieve.
		 */		
		
		String location =  "https://raw.githubusercontent.com/jtablesaw/tablesaw/master/data/bush.csv";
		Table HRAnalyticsTable;
		try (InputStream input = new URL(location).openStream()) {
			HRAnalyticsTable = Table.read().csv(input, "bush");					
		}
		
		//Testing  the output from the API
		Table streamStructure = HRAnalyticsTable.structure();
		System.out.println(streamStructure);			
	
		

	/**
	 * We will be focussing on different ways to handle the missing data
	 * Removing the  missing data i.e the rows and the columns
	 * Linear interpolation , Last observation carried forward,  Next observation carried forward
	 * 
	 */
			
	//Removing Columns with Missing data
			
			//hrAnalytics.removeColumnsWithMissingValues();

				

			
	
			
			
}
	

	
	
	
	
	
	
	
	
	
	
	
}
