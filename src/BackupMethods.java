import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

public class BackupMethods {

	public BackupMethods() {
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
	 * Now we have loaded the data, Next step is accessing different objects/ sections of the 
	 * data. This involves interacting with the data itself
	 * We have three tables so far: HRAnalyticsTable, DBcustomers and hrAnalytics
	 */
	
	//Getting to know the structure of the table fetched, this includes the data types before interacting with the data
	
	//Testing  the output from the local csv machine
			System.out.println("Printing the structure of the table  loaded from my local machine");
			Table localStructure = hrAnalytics.structure();
			System.out.println(localStructure);
			
	//Knowing the size of the table that loaded. Knowing the columns and the rows
			System.out.println("Getting the total number of columns and rows");
			String tableShape = hrAnalytics.shape();
			System.out.println(tableShape);
	
	//Exploring the tables and first rows
			System.out.println("Printing the first rows of the table");
			Table tableHead = hrAnalytics.first(5);
			System.out.println(tableHead);
			
//Exploring the tables and the last rows
			System.out.println("Printing the last  rows of the table");
			Table tableTail = hrAnalytics.last(5);
			System.out.println(tableTail);
			
//Working with Table columns, column names
			System.out.println(hrAnalytics.columnNames());
			
//Adding new columns to the  table you have loaded.
			
			double [] theIndexing = {0, 1, 2, 3, 4, 5, 6};
			DoubleColumn myIndexColumn = DoubleColumn.create("theIndexes",theIndexing);
			hrAnalytics.insertColumn(0,myIndexColumn);
			
			//print the column names to see whether the column has been added.
			System.out.println(hrAnalytics.columnNames());
		
//removing some columns  from the table
			hrAnalytics.removeColumns("theIndexes");
			//print the column names to see whether the column has been removed
			System.out.println(hrAnalytics.columnNames());
			
			//Specifying the columns that you want left with is also easier incase the columns to be removed are many
			//hrAnalytics.retainColumns("Me","you");
			
//Creating a new table as a subset of the old one
			Table latestHrAnalytics = hrAnalytics.select("Name", "Satisfaction level", "Last Evaluation", "Left", "Promotion");
			System.out.println(latestHrAnalytics.columnNames());
			
//Retrieving a single column from the table
			StringColumn theSatisfaction = (StringColumn) hrAnalytics.column("Satisfaction level");
			
//Combining tables:
			//Combining  rows
			Table mergedTables1 = hrAnalytics.append(latestHrAnalytics);
			
			//Combining columns
			Table mergedTables2 = latestHrAnalytics.concat(hrAnalytics);

			

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
	
