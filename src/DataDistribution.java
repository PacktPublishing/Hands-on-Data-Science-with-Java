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
import smile.math.distance.CorrelationDistance;
//import smile.plot.Heatmap;
import smile.plot.Palette;
import smile.stat.hypothesis.CorTest;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Heatmap;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.api.TimeSeriesPlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.plotly.traces.HeatmapTrace;
import tech.tablesaw.util.DoubleArrays;
import static tech.tablesaw.aggregate.AggregateFunctions.*;
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
	 * Correlation
	 * @param x
	 * @param y
	 * @return
	 */
	public static double getCorrelation(double[] x, double[] y) {
		CorrelationDistance myCorr = new CorrelationDistance();
		double corTestAns = 1 -(myCorr.pearson(x, y));
		return corTestAns;		
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
	 */
		Table diabetesData  = Table.read().csv("../Diabetes_Data.csv");
		
		Table diabetes = diabetesData.structure();
		System.out.println(diabetes);
	/**
	 * Getting the Summaries from the diabetes age
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
 * Using Cortest
 * Many features (Heatmap)
 */
	//Creating the table with the columns that will be used for the scatter plot
	Table theScatterData =Table.create("theScatterData",Age,bmi);
	Figure scatterFigure = ScatterPlot.create("Age by BMI ",theScatterData,"Age", "BMI");
	Plot.show(scatterFigure);	
	
	
	/**
	 * Correlation  coefficient using Smile
	 */
	DoubleColumn theAge=(DoubleColumn)diabetesData.nCol("AGE");
	DoubleColumn theBMI =(DoubleColumn)diabetesData.nCol("BMI");
	DoubleColumn theS3 =(DoubleColumn)diabetesData.nCol("S3");
	System.out.println("We are here");
	System.out.println(getCorrelation(theS3.asDoubleArray(),theBMI.asDoubleArray()));
	
	
	/**
	 * Creating Heatmap with Smile
	 * S1,S2,S3
	 */
//	DoubleColumn theS1=(DoubleColumn)diabetesData.nCol("S1");
//	DoubleColumn theS2 =(DoubleColumn)diabetesData.nCol("S2");
//	DoubleColumn theS32 =(DoubleColumn)diabetesData.nCol("S3");
//	
//	Table heatMapTable = Table.create("heatMapTable", theS1, theS2, theS32);
//	
//	System.out.println("Plotting the Heatmap");
//	double [][] heatMapTableArr = heatMapTable.as().doubleMatrix();
//	System.out.println(Heatmap.plot(heatMapTableArr));
//	
//	System.out.println("Plotting the Heatmap");
////	Heatmap diabetesHeatmap = new Heatmap(heatMapTableArr);
////	diabetesHeatmap.plot(heatMapTableArr);
	
	//Creating a heatMap of  of the dataset : dropping the sex Column and creating a heatmap
	Figure theHeatMap = Heatmap.create("Correlation using Heatmap for AGE and BMI", 
			diabetesData,"AGE","BMI");
	Plot.show(theHeatMap);
	Figure newHeatMap = Heatmap.create("Correlation using Heatmap for S3 and S4 ", diabetesData,"S3", "S4");
	Plot.show(newHeatMap);
		
	// Step 1
	double [][] mycombinedDiabetes = DoubleArrays.to2dArray(diabetesData.numberColumns());
	HeatChart map = new HeatChart(mycombinedDiabetes);
	
	// Step 2: Customizing the Chart
	map.setTitle("Correlation in diabetes data");
	map.setXAxisLabel("X Axis");
	map.setYAxisLabel("Y Axis");

	//Step 3: Output the chart to a file.
	map.saveToFile(new File("diabetes-heat-chart.png"));

		
/**
 *  Section 3.3 :Trend Analysis for Feature
 *  In this section
 */

Table bitconPriceData  = Table.read().csv("Bitcoin-usd.csv");
Table bitprice = bitconPriceData.structure();
System.out.println(bitprice);

//Time series data trend Analysis
Plot.show(TimeSeriesPlot.create("Trend analysis for Bitcoin Exchange Prices in USD", bitconPriceData, "Date", "Prices"));


/**
 * Section 3.4 : Visualizing different data forms
 * Text (Word cloud)
 * Number (bar charts)
 */
		
//Creating Bar Charts

Table ageAverage = diabetesData.summarize("AGE", mean).by("SEX");
Plot.show(HorizontalBarPlot.create(
                " Average Age by SEX",		// plot title
                ageAverage,				// Calculated averages of the aGE
                "SEX",					// grouping column name
                "mean [AGE]"));	

//Showcase AVG BMI by Age
Table bmiAverage = diabetesData.summarize("BMI", mean).by("AGE");
Plot.show( HorizontalBarPlot.create(
                " Average BMI by AGE",		// plot title
                bmiAverage,				// Calculated averages of the AGE
                "AGE",					
                "mean [BMI]"));	


//Using Pie Chart to showcase average age by SEX
Plot.show(PiePlot.create("Average Age by SEX", ageAverage, "SEX", "mean [AGE]")); 


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
