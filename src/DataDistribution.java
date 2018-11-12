import java.io.IOException;

import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import static tech.tablesaw.aggregate.AggregateFunctions.*;

/**
 * @author rwangari
 * Section 3
 *
 */


public class DataDistribution {

	public DataDistribution() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
/**
 * Section 3.1 :Efficient distribution of the data
 * Getting the mean, mode and the median
 * Using Split Apply Combine Functions
 */
		Table diabetesData  = Table.read().csv("../Diabetes_Data.csv");
		
		Table diabetes = diabetesData.structure();
		System.out.println(diabetes);
	/**
	 * Getting the Summaries from the diabetes age
	 * t.summarize(column, functions...)
	 */
	//Retrieving the AGE, BMI  columns to calculate the Mean, Max and Min
		NumberColumn Age = diabetesData.nCol("AGE");
		NumberColumn bmi = diabetesData.nCol("BMI");
		
	// Making use of the inbuild function ( Summarize)
		Table ageDistribution = diabetesData.summarize(Age, mean, max, min).apply();
		System.out.println(ageDistribution);
		
		Table bmiDistribution = diabetesData.summarize(bmi, mean, max, min).apply();
		System.out.println(bmiDistribution);	
		
	// Calculating the mean AGE, min ans max by SEX, where 1 is female and 2 
		Table avgAge  = diabetesData.summarize("AGE", mean, max, min,median).by("SEX");
		System.out.println(avgAge);
	
	/**
	 * Calculating the Median and Mode
	 */
		
/**
 * Section 3.2 :Correlation in the data
 * One feature ( Scatter plot)
 * Many features (Heatmap)
 */
	
		
/**
 *  Section 3.3 :Trend Analysis for Feature
 *  
 */

/**
 * Section 3.4 : Visualizing different data forms
 * Text (Word cloud)
 * Number (bar charts)
 */
		
		
		

	}

}
