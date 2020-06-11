import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import org.tc33.jheatchart.HeatChart;

import com.jimmoores.quandl.DataSetRequest;
import com.jimmoores.quandl.tablesaw.TableSawQuandlSession;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import smile.data.parser.ArffParser;
import smile.plot.Palette;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Heatmap;
//import tech.tablesaw.plotly.api.Pareto;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.traces.HeatmapTrace;
import tech.tablesaw.util.DoubleArrays;

import static tech.tablesaw.aggregate.AggregateFunctions.*;
import java.lang.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author rwangari
 * Section 3
 *
 */
public class DataDistribution {

	/*
	 * Constructor
	 */
	public DataDistribution() {
	}
	
	/**
	 * A function to transpose a two dimensional array
	 * @param arr
	 * @return double [][]
	 */
	public static double[][] transpose(double arr[][]){
	    int m = arr.length;
	    int n = arr[0].length;
	    double ret[][] = new double[n][m];
	
	    for (int i = 0; i < m; i++) {
	        for (int j = 0; j < n; j++) {
	            ret[j][i] = arr[i][j];
	        }
	    }
	
	    return ret;
	}

	/**
	 * A function to write words from String Column from the table to a file .txt
	 * @param mywords
	 * @return void
	 */
	public static void writeToMyFile(StringColumn mywords) {
		
		FileWriter myWriter = null;
		try {
			myWriter = new FileWriter("wordcloud.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String [] myWordsArr = (String[]) mywords.asObjectArray();
		
	    for (int i = 0; i < myWordsArr.length; i++) {
	      try {
	    	  myWriter.write(myWordsArr[i] + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    try {
	    	myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Function adopted from KUMO library for creating wordcloud
	 * @param theFileWords( file containing words  for wordcloud to analyze)
	 * @param imageToSave(.png)
	 * @return void
	 */
	public static void createWordCloud(String theFileWords){
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		List<WordFrequency> wordFrequencies = null;
		try {
			wordFrequencies = frequencyAnalyzer.load(theFileWords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File was not found");
		}
		final Dimension dimension = new Dimension(300, 300);
		final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
		wordCloud.setPadding(0);
		wordCloud.setBackground(new RectangleBackground(dimension));
		wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
		wordCloud.setFontScalar(new LinearFontScalar(10, 40));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("wordcloudToSave1.png");
	}
	
	/**
	 * A function adopted from KUMO libraly for creating wordCloud
	 * @param theFileWords
	 * @return void
	 */
	public static void createWordCloud2(String theFileWords) {
		final FrequencyAnalyzer myFrequencyAnalyzer = new FrequencyAnalyzer();
		myFrequencyAnalyzer.setWordFrequenciesToReturn(300);
		myFrequencyAnalyzer.setMinWordLength(4);
		//myFrequencyAnalyzer.setStopWords(loadStopWords());

		List<WordFrequency> myWordFrequencies = null;
		try {
			myWordFrequencies = myFrequencyAnalyzer.load(theFileWords);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Dimension myDimension = new Dimension(500, 312);
		final WordCloud myWordCloud = new WordCloud(myDimension, CollisionMode.PIXEL_PERFECT);
		myWordCloud.setPadding(2);
		
		
		myWordCloud.setBackground(new RectangleBackground(myDimension));
		myWordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
		myWordCloud.setFontScalar(new LinearFontScalar(10, 40));
		myWordCloud.build(myWordFrequencies);
		myWordCloud.writeToFile("wordcloud2.png");
	}
	
	/*
	 * Main Class to  test all functions
	 */
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
double[][] transposedDiabetesData = transpose(mycombinedDiabetes);

smile.plot.Heatmap test = new smile.plot.Heatmap(mycombinedDiabetes);
smile.plot.Heatmap.plot(mycombinedDiabetes);
System.out.println("We  are done with wmile");

smile.plot.Heatmap.plot(mycombinedDiabetes, Palette.jet(256));


//Creating a scatter plot instead!

String [] myLabels = {"AGE", "SEX","BMI","BP","S1","S2","S3","S4","S5","S6","Y"};


//smile.plot.ScatterPlot  mySmileScatter = new smile.plot.ScatterPlot(mycombinedDiabetes,myLabels);
System.out.println(mynewTable.length);
System.out.println( myLabels.length);
//

//double[][] transposedDiabetesData = transpose(mycombinedDiabetes);
System.out.println(transposedDiabetesData.length);

smile.plot.ScatterPlot  mySmileScatter = new smile.plot.ScatterPlot(transposedDiabetesData,myLabels);
System.out.println(mySmileScatter);
//plot(data: Array[Array[Double]], labels: Array[String]): Window

//smile.plot.ScatterPlot.plot(transposedDiabetesData,myLabels);
//: Window;

		
/**
 *  Section 3.3 :Trend Analysis for Feature
 *  In this section
 */

Table bitconPriceData  = Table.read().csv("Bitcoin-usd.csv");
Table bitprice = bitconPriceData.structure();
System.out.println(bitprice);

//Time series data trend Analysis
Plot.show(TimeSeriesPlot.create("Bitcoin Exchange Prices in USD", bitconPriceData, "Date", "High"));

/**
 * Section 3.4 : Visualizing different data forms
 * Text (Word cloud)
 * Number (bar charts)
 */
		
//Creating Bar Charts
	
Table ageAverage = diabetesData.summarize("AGE", mean).by("SEX");
Plot.show(
    HorizontalBarPlot.create(
                " Average Age by SEX",		// plot title
                ageAverage,				// Calculated averages of the aGE
                "SEX",					// grouping column name
                "mean [AGE]"));	

//Showcase AVG BMI by Age
Table bmiAverage = diabetesData.summarize("BMI", mean).by("AGE");
Plot.show(
    HorizontalBarPlot.create(
                " Average BMI by AGE",		// plot title
                bmiAverage,				// Calculated averages of the aGE
                "AGE",					// grouping column name
                "mean [BMI]"));	


//Using Pie Chart to showcase average age by SEX
Plot.show(
	    PiePlot.create("Average Age by SEX", ageAverage, "SEX", "mean [AGE]")); 


/*
 * Visualizing words  using wordcloud
 */

// Loading a new Set of data
Table kamitiData  = Table.read().csv("../KAMITI.csv");
Table kamiti = kamitiData.structure();
System.out.println(kamiti);

/*
 *  Calling to writeToMyFile function
 */

StringColumn theText = (StringColumn)kamitiData.column("Text");
writeToMyFile(theText);


createWordCloud("wordcloud.txt");
createWordCloud2("wordcloud.txt");
		
	}

}
