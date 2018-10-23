import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

import com.mysql.fabric.xmlrpc.base.Array;

import java.util.*;

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
	Table tableHead = hrAnalytics.first(15);
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
					
		//Creating arrays to Hold columns		
			List<String> nameList = new ArrayList<String>();
			List<Double>  SatisfactionLevelList= new ArrayList<Double>();
			List<Double>  LastEvaluationList= new ArrayList<Double>();
			List<Double>  numProjectsList= new ArrayList<Double>();
			List<Double>  aveMonHoursList= new ArrayList<Double>();
			List<Double>  leftList= new ArrayList<Double>();
			List<Double>  promotionList= new ArrayList<Double>();
			List<String>  debtList= new ArrayList<String>();
			
			//To be used for interpolation, double arrays
			double [] SatisfactionLevel_raw = new double [50];
			double [] LastEvaluation_raw = new double [50];
			int index =0;
		//Loopin gthrough the table to remove the rows with NANs
			for (Row row : hrAnalytics) { 				
				//Extract everything in the row
				String theName = row.getString("NAME");
				Double theSL = row.getDouble("Satisfaction level");
			    Double theLE= row.getDouble("Last Evaluation");
			    Double theProjectsTaken = row.getDouble("No. of Projects Undertaken");
			    Double theAMH = row.getDouble("Av monthly hours");
			    //Double theTM = row.getDouble("Time Spent");
			    Double theNOA = row.getDouble("No. of accidents");
			    Double thePromo = row.getDouble("Promotion");
			    Double theLeft = row.getDouble("Left");
			    String theDept = row.getString("Department");
			    
			    //Printing Everything
			    System.out.println(theName);
			    System.out.println("The SL1 is" +theSL );
			    System.out.println("The LE is " +theLE);
			    System.out.println("The projects taken is " +theProjectsTaken);
			    System.out.println("The AVG is " + theAMH);
			    System.out.println("The department is  " +theDept); 		    

			    
			    if(theName.length()!=0 && theDept.length()!=0 && theSL!=0 && theLE!=0 && theAMH!=0 && (theLeft==0 ||theLeft==1)) {
			    	
			    	nameList.add(theName);
			    	SatisfactionLevelList.add(theSL);
			    	LastEvaluationList.add(theLE);
			    	aveMonHoursList.add(theAMH);
			    	leftList.add(theLeft);
			    	debtList.add(theDept);			    			    	  
			    }
			    
			    else {
			    	
			    }		
				//Adding the data for Interpolation
			    SatisfactionLevel_raw[index] =theSL;
				LastEvaluation_raw[index]=theLE;
				index++;
			}
				
			
			//Creating columns to store the variables:
			String[] nameArr =nameList.toArray(new String[SatisfactionLevelList.size()]);
			StringColumn name = StringColumn.create("name",nameArr);
			Double[] SLArr = SatisfactionLevelList.toArray(new Double[SatisfactionLevelList.size()]);
			DoubleColumn SL = DoubleColumn.create("SE",SLArr);
			Double[] LEArr = LastEvaluationList.toArray(new Double[LastEvaluationList.size()]);
			DoubleColumn LastE = DoubleColumn.create("Last Eva",LEArr);
			Double[] aveHrsArr = aveMonHoursList.toArray(new Double[aveMonHoursList.size()]);
			DoubleColumn aveHrs = DoubleColumn.create("Average hours",aveHrsArr);
			Double[] leftListArr = leftList.toArray(new Double[leftList.size()]);
			DoubleColumn theLeftList = DoubleColumn.create("Left List",leftListArr);		
			String[] debtArr  = debtList.toArray(new String[debtList.size()]);
			StringColumn deptcol = StringColumn.create("dept",debtArr);
					
			//Adding the columns to the table
			Table droppedRows =Table.create("DroppedRows",name,SL,LastE,aveHrs,theLeftList,deptcol);
					
			String tableShape2 = droppedRows.shape();
			System.out.println(tableShape2);
			System.out.println(tableShape);
			
			

	
		// Linear Interpolation
		//Satisfaction Level (y) depends on Last Evaluation(X)
			
			//Creating an object of the class LinearInterpolrtor
			LinearInterpolator myLinearInterp = new LinearInterpolator();

			
			
			myLinearInterp.interpolate(SatisfactionLevel_raw, LastEvaluation_raw);
			
			
/**
 * Section 2.6 :Formatting various data types
 */	
					
	//Check data types for the imported Columns
	//Change the data type  of the columns
					
					
	//Getting the table structures
		System.out.println("Printing the structure of the table  loaded from my local machine");
		Table tableDataType = hrAnalytics.structure();
		System.out.println(tableDataType);
		
		
		//Incase of Arrays with Int to double
		int[] age = {30, 27, 52, 41, 20};
		double[] double_age = Arrays.stream(age).asDoubleStream().toArray();
					
	}

}
