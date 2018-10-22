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
	}}			
			
			
			
			
			import java.io.IOException;
			import java.time.LocalDate;

			import tech.tablesaw.api.ColumnType;
			import tech.tablesaw.api.DoubleColumn;
			import tech.tablesaw.api.NumberColumn;
			import tech.tablesaw.api.Row;
			import tech.tablesaw.api.StringColumn;
			import tech.tablesaw.api.Table;
			import tech.tablesaw.filtering.Filter;
			import tech.tablesaw.io.csv.CsvReadOptions;
			import tech.tablesaw.selection.Selection;

			public class AccessingObjects {

				public AccessingObjects() throws IOException {}
				
				public static void main(String[] args)throws IOException {
					
					
			 
					
			/*
			 * importing the file that we will be using
			 */
					
				Table hrAnalytics  = Table.read().csv("../HR_comma_sep2.csv");	
				
				
			/**
			 * Section 2.3 :  Accessing different objects from the data sets ( Columns and Rows)
			 */
				
			/**
			 * Interacting with the table
			 */
				//Getting the table structure
				System.out.println("Printing the structure of the table  loaded from my local machine");
				Table localStructure = hrAnalytics.structure();
				System.out.println(localStructure);
				
				//Knowing the size of the table that loaded. Knowing the columns and the rows
				System.out.println("Getting the total number of columns and rows");
				String tableShape = hrAnalytics.shape();
				System.out.println(tableShape);
				
				//Creating a new table as a subset of the old one
				Table latestHrAnalytics = hrAnalytics.select("Name", "Satisfaction level", "Last Evaluation", "Left", "Promotion");
				System.out.println(latestHrAnalytics.columnNames());
				
				//Combining tables:
				//Combining based on  rows
				//Table mergedTables1 = hrAnalytics.append(latestHrAnalytics);
				
				//Combining  based on columns
				//Table mergedTables2 = latestHrAnalytics.concat(hrAnalytics);
				
			/**
			 * Interacting with Columns
			 */
				//Printing all the column names
				System.out.println(hrAnalytics.columnNames());
				
				
				//Retrieving a single column from the table
				DoubleColumn theSatisfaction = (DoubleColumn) hrAnalytics.column("Satisfaction level");
				
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
				
				//Sorting the table with specific Columns( ascending or descending manner)
				Table ascendingHr = hrAnalytics.sortAscendingOn("Satisfaction level");
				ascendingHr.first(8);
				
				
			/**
			 * Interacting with rows
			 */
				
				//Accessing the first 5 rows of the table
				System.out.println("Printing the first rows of the table");
				Table tableHead = hrAnalytics.first(5);
				System.out.println(tableHead);
				
				//Accessing the last 5 rows of the table
				System.out.println("Printing the last  rows of the table");
				Table tableTail = hrAnalytics.last(5);
				System.out.println(tableTail);
				
					
					
				

			/**
			 * Section 2.4 , Filtering unwanted  data
			 */
				
				// printing the structure of the table to identify the columns that we would like to remove	
				System.out.println(hrAnalytics.columnNames());
				
				
				//removing some columns  from the table
					//hrAnalytics.removeColumns("theIndexes");
				
				//Creating a table from the loaded data set, a situation you want specific columns
					Table filteredTable = hrAnalytics.select("Name", "Satisfaction level", "Last Evaluation", "Left", "Promotion");
					System.out.println(filteredTable.columnNames());
					
				// Want table which fits a specific criteria
					//EXAMPLe: All employees whose last evaluation is equal to 0.75		
							
					NumberColumn  LE = hrAnalytics.numberColumn("Last Evaluation");
					Selection LEscore = LE.isEqualTo(1.0);
					Table EmployeesWithLE = hrAnalytics.where(LEscore);
					
					//Accessing the first 5 rows of the table
					System.out.println("Printing the first rows of the table");
					Table tableHead2 = EmployeesWithLE.first(5);
					System.out.println(tableHead2);
				
					//Talk about : Selections, filters, most used filters, where, drop where and give a link to the filter API 
					

			/**
			 * Section 2.5, Handling the Null and the NAN
			 */
				
				//Removing Columns with Missing data
						//hrAnalytics.removeColumnsWithMissingValues();

				//Removing Rows with Missing data based on specific columns. Using a for loop
						
						//Looping through the rows
						//Table myNewHRTable= Table.create("NonDroppedRows");
						Table droppedRows =Table.create("DroppedRows");
						
						NumberColumn mycolumn = null;
						StringColumn thecolumn = null;
						int indexing =0;
						
						
						//Table myNewHRTable = Table.create("NonDroppedRows",mycolumn,thecolumn);
						//Table 
						
						
					
						for (Row row : hrAnalytics) { 
							System.out.println("The index is " + indexing);
							String theName = row.getString("NAME");
							System.out.println(theName);
							int theLeft = row.getInt("Left");
							//int theNo = row.getInt("No.");
						    
						    int theSL= row.getInt("Satisfaction level");
						    System.out.println("The SL is" +theSL);
						    int theLE= row.getInt("Last Evaluation");
						    System.out.println("The LE is " +theLE);
						    int theProjectsTaken = row.getInt("No. of Projects Undertaken");
						    int theAMH = row.getInt("Av monthly hours");
						    //int theTM = row.getInt("Time Spent");
						    int theNOA = row.getInt("No. of accidents");
						  
						    int thePromo = row.getInt("Promotion");
						    String theDept = row.getString("Department");
						    
						    
						    //System.out.println();
						    
						    //Checking if any of the column is empty
						    if(theName.isEmpty()== false && theSL != 0 && theLE != 0 && theProjectsTaken != 0 && theAMH != 0 && 
						    		theNOA != 0  && thePromo != 0 && theDept.isEmpty()== false) {
						    	
						    	if(theSL != 0 && theName.isEmpty() == false) {
//						    		System.out.println(theLE);
//						    		System.out.println(theName);
						    	}
						    	
						    	
//						    	mycolumn.add(theSL);
//						    	thecolumn.append(theName);
						    	
						  

						    }
						    
						    else {
						    	

						    }
						      
						    indexing++;
						}
						droppedRows.addColumns(mycolumn);
						droppedRows.addColumns(thecolumn);
						
						
						
					
						
						
						String tableShape2 = droppedRows.shape();
						System.out.println(tableShape2);
						System.out.println(tableShape);
						//System.out.println(rowInt);
						
//						String tableShape3 = myNewHRTable.shape();
//						System.out.println(tableShape3);
					
				// Interpolation
				//Filling the columns with the Average or mode
				//If the data has a trend, previous + next ( average)
				
			/**
			 * Section 2.6 : Formatting various data types
			 */
					
					//Check data types for the imported Columns
					//Change the data type  of the columns
					
					
				//Getting the table structures
//					System.out.println("Printing the structure of the table  loaded from my local machine");
//					Table tableDataType = hrAnalytics.structure();
//					System.out.println(tableDataType);
			//	
				//Typecasting a column 
					
//					//DoubleColumn satisfactionLevel = DoubleColumn.create("theIndexes",theIndexing);
//					hrAnalytics.setName("newHR");
//					DoubleColumn newSatisfactionLevel = (DoubleColumn) hrAnalytics.column("Satisfaction level");
//					//newHR.insertColumn(0, newSatisfactionLevel);
//					
//					System.out.println("Printing the structure of the table  loaded from my local machine");
//					Table tableDataType2 = hrAnalytics.structure();
//					System.out.println(tableDataType2);
//					
					
					//Doublecolumn 
					
				// Specifying data types for each Column
					
//					ColumnType[] columnTypes = { DOUBLE, STRING , DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE, DOUBLE,DOUBLE,DOUBLE,STRING };
//					Table t = Table.read().csv(CsvReadOptions
//					    .builder("../HR_comma_sep2.csv")
//					    .columnTypes(columnTypes));
					
					
				}

			}

	}

}
	
