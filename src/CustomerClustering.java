import static tech.tablesaw.aggregate.AggregateFunctions.max;
import static tech.tablesaw.aggregate.AggregateFunctions.mean;
import static tech.tablesaw.aggregate.AggregateFunctions.median;
import static tech.tablesaw.aggregate.AggregateFunctions.min;

import java.io.IOException;

import smile.clustering.KMeans;
import smile.plot.Heatmap;
import smile.plot.Palette;
import smile.plot.PlotCanvas;
import smile.plot.ScatterPlot;
import smile.validation.AdjustedRandIndex;
import smile.validation.RandIndex;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;

/**
 * Unsupervised Learning :Clustering using K Means on the Customer buying behaviours dataset
 * @author rwangari
 *Section 4.1
 */
public class CustomerClustering {

	public CustomerClustering() {
		// TODO Auto-generated constructor stub
	}
			
	public static void main(String[] args) throws IOException {
		/**
		 * Loading the data 
		 */
		Table customersSegmentationData  = Table.read().csv("DataSets/customersData.csv");
		/**
		 * Cleaning the dataset and removing the rows which contains null
		 */
		Table customerSegmentation_Clean =customersSegmentationData.dropRowsWithMissingValues();
		
		//Getting a brief drscription of the entire dataset
		
		
		
		/**
		 * Exploring the dataset
		 */
		System.out.println("Printing the last rows of the table");
		Table tablehead = customerSegmentation_Clean.first(5);
		System.out.println(tablehead);
		
		//Table Shape
		String tableShape = customerSegmentation_Clean.shape();
		System.out.println(tableShape);	
		
		/**
		 * Removing the first 2 columns of the dataset
		 */
		//Table customerSegmentation_Categorical = (Table)customerSegmentation_Clean.column("Channel", "Region");
		Table customerSegmentation_CleanFinal = (Table) customerSegmentation_Clean.removeColumns("Channel","Region");
		
		Table tablehead1 = customerSegmentation_CleanFinal.first(5);
		System.out.println(tablehead1);
		
		
		/**
		 * Brief understanding of the dataset
		 */
		Table custDataDist1=customerSegmentation_CleanFinal.summarize("Milk","Frozen","Grocery","Fresh", mean).apply();
		System.out.println(custDataDist1);
		Table custDataDist4=customerSegmentation_CleanFinal.summarize("Detergents_Paper","Delicatessen",mean,median,max,min).apply();
		System.out.println(custDataDist4);
		Table custDataDist2=customerSegmentation_CleanFinal.summarize("Milk","Frozen","Grocery","Fresh", median).apply();
		System.out.println(custDataDist2);
		Table custDataDist3=customerSegmentation_CleanFinal.summarize("Milk","Frozen","Grocery","Fresh",max,min).apply();
		System.out.println(custDataDist3);
		
		/**
		 * Separating the dataset into  training and testing
		 */
		Table customerSegmentationTrain = customerSegmentation_CleanFinal.where(Selection.withRange(1,350));
		Table customerSegmentationTest = customerSegmentation_CleanFinal.where(Selection.withRange(351,440));
		
		//Converting tables to double arrays
		double [][] customerSegmentationTrain_Arr =customerSegmentationTrain.as().doubleMatrix();
		double [][] customerSegmentationTest_Arr =customerSegmentationTest.as().doubleMatrix();
		
		/**
		 * Plotting the dataset
		 */
		
		ScatterPlot myScatter = new ScatterPlot(customerSegmentationTrain_Arr);
		System.out.println(myScatter);
		
		/**
		 * Fitting the KMeans Model
		 */
		 KMeans kmeans = new KMeans(customerSegmentationTrain_Arr,3, 100, 20);	
		 System.out.println(kmeans);

	 
		/**
		 * Testing our K means Model
		 */
		 
		 double [] theX = {10290,1981,2232,1038,168,2125};
		 System.out.println("we are predicting"+ kmeans.predict(theX));
		 System.out.println("we printing " +kmeans.getClusterLabel()[8]);
		 System.out.println("we printing " +kmeans.getClusterSize()[1]);
		 System.out.println("we printing "+kmeans.centroids()[0][1]);


  
  
	}

}
