import java.io.File;
import java.io.IOException;

import org.tc33.jheatchart.HeatChart;

import smile.data.parser.ArffParser;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Heatmap;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.traces.HeatmapTrace;
import tech.tablesaw.util.DoubleArrays;

import static tech.tablesaw.aggregate.AggregateFunctions.*;
import java.lang.*;
import java.util.stream.Stream;


/**
 * @author rwangari
 * Section 3
 *
 */


@SuppressWarnings("unused")
public class DataDistribution {

	public DataDistribution() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
/**
 * Section 3.1 :Efficient distribution of the data
 * Getting the mean,max, min and the median
 * Using Split Apply Combine Functions
 * //Reference :http://www.javadoc.io/doc/tech.tablesaw/tablesaw-core/0.23.2
 * Has other functions depending on dataset
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
		Table ageDistribution = diabetesData.summarize(Age, mean, max, min, median).apply();
		System.out.println(ageDistribution);
		
		Table bmiDistribution = diabetesData.summarize(bmi, mean, max, min, median).apply();
		System.out.println(bmiDistribution);	
		
	// Calculating the mean AGE, min ans max by SEX, where 1 is female and 2 
		Table avgAge  = diabetesData.summarize("AGE", mean, max, min,median).by("SEX");
		System.out.println(avgAge);


		
/**
 * Section 3.2 :Correlation in the data
 * One feature ( Scatter plot) vs another feature
 * Many features (Heatmap)
 */

	
		
	//Creating the table with the columns that will be used for the scatter plot
		
	Table theScatterData =Table.create("theScatterData",Age,bmi);
	Figure scatterFigure = ScatterPlot.create("Age by BMI ", theScatterData, "Age", "Bmi");
	Plot.show(scatterFigure);
	
	//Time series
	//Plot.show(TimeSeriesPlot.create("", foxOnly, "date", "approval"));
		
//Creating a heatMap of  of the dataset : dropping the sex Column and creating a heatmap
	
	Figure theHeatMap = Heatmap.create("Correlation of the different variables ", 
			diabetesData,"AGE","BMI");
	
	Plot.show(theHeatMap);
	
//Changing a table to a double [][]
	
	DoubleColumn theAge=(DoubleColumn)diabetesData.nCol("AGE");
	DoubleColumn theBMI =(DoubleColumn)diabetesData.nCol("BMI");
	Figure newHeatMap = Heatmap.create("Trial ", diabetesData,"S3", "S4");
	Plot.show(newHeatMap);
	
//Solution Moving Forward (Converting a Table to double [][]);	
	
	double [] onearray = new double[10];
	double [] twoarray = new double[10];
	
	//double [][] mycombinedArray = new double[100][10];
	//mycombinedArray.
	
	// Step 1
	double [][] mycombinedDiabetes = DoubleArrays.to2dArray(diabetesData.numberColumns());
	HeatChart map = new HeatChart(mycombinedDiabetes);
	
	// Step 2: Customizing the Chart
	map.setTitle("Correlation in diabetes data");
	map.setXAxisLabel("X Axis");
	map.setYAxisLabel("Y Axis");

	//Step 3: Output the chart to a file.
	map.saveToFile(new File("diabetes-heat-chart.png"));

//Converting a table to double dimensional array:
	double [][] mynewTable =diabetesData.as().doubleMatrix();
	
//Creating a HeatMap with Smile

System.out.println("We are here");

smile.plot.Heatmap test = new smile.plot.Heatmap(mycombinedDiabetes);
smile.plot.Heatmap.plot(mycombinedDiabetes);

System.out.println("We  are done with wmile");
	
		
/**
 *  Section 3.3 :Trend Analysis for Feature
 *  In this section we will use Financial data from Quandal
 */
	
// Getting data from Quandl
	
	
	
	

/**
 * Section 3.4 : Visualizing different data forms
 * Text (Word cloud)
 * Number (bar charts)
 */
		
		
		

	}

}
