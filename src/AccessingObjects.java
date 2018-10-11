import java.io.IOException;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

public class AccessingObjects {

	public AccessingObjects() throws IOException {
		
/*
 * importing the file that we will be using
 */
		
	Table hrAnalytics  = Table.read().csv("../HR_comma_sep2.csv");	
	
	
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
	Table mergedTables1 = hrAnalytics.append(latestHrAnalytics);
	
	//Combining  based on columns
	Table mergedTables2 = latestHrAnalytics.concat(hrAnalytics);
	
/**
 * Interacting with Columns
 */
	//Printing all the column names
	System.out.println(hrAnalytics.columnNames());
	
	
	//Retrieving a single column from the table
	StringColumn theSatisfaction = (StringColumn) hrAnalytics.column("Satisfaction level");
	
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
		
		
		
		
		
	}

}
