import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import smile.classification.SVM;
import smile.math.Math;
import smile.math.kernel.LinearKernel;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;


/**
 * Section 5
 * @author rwangari
 * Cross Validation on Iris Dataset : K folds Cross Validation
 *  
 */
public class TheCrossValidation {
	
	/**
	 * A function to shuffle an array
	 * @param arrayToShuffle
	 * @return
	 */
	
	public static double [][] shuffle2DArray(double [][] arrayToShuffle){
			for(int j =0 ; j<arrayToShuffle.length;j++) {
			int ar1 =(int)(Math.random()* arrayToShuffle.length);
			double [] temp =arrayToShuffle[j];
			arrayToShuffle[j] =arrayToShuffle[ar1];
			arrayToShuffle[ar1] =temp;	
		}		
		return arrayToShuffle;
	}
	
	/**
	 * A function to split the last column of a two D array
	 * @param arrayToSplitFrom
	 * @return
	 */
	public static int [] getLastColumn (double [][] arrayToSplitFrom) {
		int [] lastColumn = new int[arrayToSplitFrom.length];
		
		for(int i =0; i<arrayToSplitFrom.length;i++) {
			for(int j =0; j<arrayToSplitFrom[i].length;j++) {
				if(j+1==arrayToSplitFrom[i].length) {
					lastColumn[i]=(int) arrayToSplitFrom[i][j];
					arrayToSplitFrom[i][j]=0;
				}
			}
		}		
		return lastColumn;		
	}
	
	/**
	 * A function to remove the last column from a 2d Array
	 * @param arrayToRemoveFrom
	 * @return
	 */
	public static double [][] removeLastColumn(double[][] arrayToRemoveFrom){
		double [][] newArray = new double[arrayToRemoveFrom.length][arrayToRemoveFrom[0].length-1];
		for(int i =0; i<arrayToRemoveFrom.length;i++) {
			for(int j =0; j<arrayToRemoveFrom[i].length;j++) {
				
				if(j!=arrayToRemoveFrom[i].length-1) {
					newArray[i][j]=arrayToRemoveFrom[i][j];
				}			
			}			
		}	
		return newArray;
	}
	/**
	 * Constructor
	 */


	public TheCrossValidation() {
		}

	public static void main(String[] args) throws IOException {
	//Loading the IRIS dataset		
		Table IrisDataset  = Table.read().csv("DataSets/Iris_Second.csv");
		
	//Getting the independent and dependent dataset
		NumberColumn speciesNum = IrisDataset.nCol("SpeciesNum");
		Table independentData = IrisDataset.retainColumns("SepalLengthCm","SepalWidthCm","PetalLengthCm","PetalWidthCm");
		
	//Dividing the data into K folds  K =5
		// K=1
		NumberColumn speciesNum10 =speciesNum.where(Selection.withRange(0,10));
		NumberColumn speciesNum20 =speciesNum.where(Selection.withRange(50,60));
		NumberColumn speciesNum30 =speciesNum.where(Selection.withRange(100,110));
		Table independentData10 =independentData.where(Selection.withRange(0,10));
		Table independentData20 =independentData.where(Selection.withRange(50,60));
		Table independentData30 =independentData.where(Selection.withRange(100,110));
		NumberColumn speciesNumk1 = (NumberColumn) speciesNum10.append(speciesNum20).append(speciesNum30);
		Table flowerIndependentK1 =independentData10.append(independentData20).append(independentData30);
		//K=2		
		NumberColumn speciesNum11 =speciesNum.where(Selection.withRange(10,20));
		NumberColumn speciesNum21 =speciesNum.where(Selection.withRange(60,70));
		NumberColumn speciesNum31 =speciesNum.where(Selection.withRange(110,120));
		Table independentData11 =independentData.where(Selection.withRange(10,20));
		Table independentData21 =independentData.where(Selection.withRange(60,70));
		Table independentData31 =independentData.where(Selection.withRange(110,120));
		NumberColumn speciesNumk2 = (NumberColumn) speciesNum11.append(speciesNum21).append(speciesNum31);
		Table flowerIndependentK2 =independentData11.append(independentData21).append(independentData31);
		//K=3		
		NumberColumn speciesNum12 =speciesNum.where(Selection.withRange(20,30));
		NumberColumn speciesNum22 =speciesNum.where(Selection.withRange(70,80));
		NumberColumn speciesNum32 =speciesNum.where(Selection.withRange(120,130));
		Table independentData12 =independentData.where(Selection.withRange(20,30));
		Table independentData22 =independentData.where(Selection.withRange(70,80));
		Table independentData32 =independentData.where(Selection.withRange(120,130));
		NumberColumn speciesNumk3 = (NumberColumn) speciesNum12.append(speciesNum22).append(speciesNum32);
		Table flowerIndependentK3 =independentData12.append(independentData22).append(independentData32);
		//K4
		NumberColumn speciesNum13 =speciesNum.where(Selection.withRange(30,40));
		NumberColumn speciesNum23 =speciesNum.where(Selection.withRange(80,90));
		NumberColumn speciesNum33 =speciesNum.where(Selection.withRange(130,140));
		Table independentData13 =independentData.where(Selection.withRange(30,40));
		Table independentData23 =independentData.where(Selection.withRange(80,90));
		Table independentData33 =independentData.where(Selection.withRange(130,140));
		NumberColumn speciesNumk4 = (NumberColumn) speciesNum13.append(speciesNum23).append(speciesNum33);
		Table flowerIndependentK4 =independentData13.append(independentData23).append(independentData33);
		//K5
		NumberColumn speciesNum14 =speciesNum.where(Selection.withRange(40,50));
		NumberColumn speciesNum24 =speciesNum.where(Selection.withRange(90,100));
		NumberColumn speciesNum34 =speciesNum.where(Selection.withRange(140,150));
		Table independentData14 =independentData.where(Selection.withRange(40,50));
		Table independentData24 =independentData.where(Selection.withRange(90,100));
		Table independentData34 =independentData.where(Selection.withRange(140,150));
		NumberColumn speciesNumk5 = (NumberColumn) speciesNum14.append(speciesNum24).append(speciesNum34);
		Table flowerIndependentK5 =independentData14.append(independentData24).append(independentData34);
				
		
		/**
		 * Combining the Data for Sections K1
		 */
		NumberColumn speciesNumK2toK5 = (NumberColumn) speciesNumk2.append(speciesNumk3).append(speciesNumk4).append(speciesNumk5);
		Table flowerIndependentK2toK5 = flowerIndependentK2.append(flowerIndependentK3).append(flowerIndependentK4).append(flowerIndependentK5);
		//Combining the Table to shuffle it 
		Table Combined = flowerIndependentK2toK5.addColumns(speciesNumK2toK5);
		double [][] stillCombined = Combined.as().doubleMatrix();
		double [][] combinedShuffle =shuffle2DArray( stillCombined);
		//Split double dimensional Array
		int [] speciesNumK2toK5Arr =getLastColumn(combinedShuffle);
		double [][] flowerIndependentK2toK5Arr=removeLastColumn(combinedShuffle);
		//Testing set
		int [] speciesNumk1Arr = speciesNumk1.asIntArray();
		double [][] flowerIndependentK1Arr = flowerIndependentK1.as().doubleMatrix();
		
		
		/**
		 * Combining the Data for Sections K2
		 */
		NumberColumn speciesNumK1toK5no2 = (NumberColumn) speciesNumk1.append(speciesNumk3).append(speciesNumk4).append(speciesNumk5);
		Table flowerIndependentK1toK5no2 = flowerIndependentK1.append(flowerIndependentK3).append(flowerIndependentK4).append(flowerIndependentK5);
		//Combining the Table to shuffle it 
		Table Combined2 = flowerIndependentK1toK5no2.addColumns(speciesNumK1toK5no2);
		double [][] stillCombined2 = Combined2.as().doubleMatrix();
		double [][] combinedShuffle2 =shuffle2DArray(stillCombined2);
		//Split double dimensional Array
		int [] speciesNumK1toK5Arr2 =getLastColumn(combinedShuffle2);
		double [][] flowerIndependentK2toK5Arr2=removeLastColumn(combinedShuffle2);
		
		//Testing set
		int [] speciesNumk2Arr = speciesNumk2.asIntArray();
		double [][] flowerIndependentK2Arr =removeLastColumn( flowerIndependentK2.as().doubleMatrix());	
		
		
		/**
		 * Combining the Data for Sections K3
		 */
		NumberColumn speciesNumK1toK5no3 = (NumberColumn) speciesNumk1.append(speciesNumk2).append(speciesNumk4).append(speciesNumk5);
		
		//System.out.println(flowerIndependentK3.shape());
		Table flowerIndependentK1toK5no3 = flowerIndependentK1.append(flowerIndependentK2).append(flowerIndependentK4).append(flowerIndependentK5);
		//Combining the Table to shuffle it 
		Table Combined3 = flowerIndependentK1toK5no3.addColumns(speciesNumK1toK5no3);
		double [][] stillCombined3 = Combined3.as().doubleMatrix();
		double [][] combinedShuffle3 =shuffle2DArray(stillCombined3);
		
		//Split double dimensional Array
		int [] speciesNumK1toK5Arr3 =getLastColumn(combinedShuffle3);
		double [][] flowerIndependentK2toK5Arr3=removeLastColumn(combinedShuffle3);
		
		//Testing set
		int [] speciesNumk3Arr = speciesNumk3.asIntArray();
		double [][] flowerIndependentK3Arr = flowerIndependentK3.as().doubleMatrix();
		
		
		/**
		 * Combining the Data for Sections K4
		 */
		NumberColumn speciesNumK1toK5no4 = (NumberColumn) speciesNumk1.append(speciesNumk2).append(speciesNumk3).append(speciesNumk5);
		
		//System.out.println(flowerIndependentK3.shape());
		Table flowerIndependentK1toK5no4 = flowerIndependentK1.append(flowerIndependentK2).append(flowerIndependentK3).append(flowerIndependentK5);
		//Combining the Table to shuffle it 
		Table Combined4 = flowerIndependentK1toK5no4.addColumns(speciesNumK1toK5no4);
		double [][] stillCombined4 = Combined4.as().doubleMatrix();
		double [][] combinedShuffle4 =shuffle2DArray(stillCombined4);
		
		//Split double dimensional Array
		int [] speciesNumK1toK5Arr4 =getLastColumn(combinedShuffle4);
		double [][] flowerIndependentK2toK5Arr4=removeLastColumn(combinedShuffle4);
		
		//Testing set
		int [] speciesNumk4Arr = speciesNumk4.asIntArray();
		double [][] flowerIndependentK4Arr = flowerIndependentK4.as().doubleMatrix();
		
		
		
		/**
		 * Combining the Data for Sections K5
		 */
		NumberColumn speciesNumK1toK5no5 = (NumberColumn) speciesNumk1.append(speciesNumk2).append(speciesNumk3).append(speciesNumk4);
		
		System.out.println(flowerIndependentK3.shape());
		Table flowerIndependentK1toK5no5 = flowerIndependentK1.append(flowerIndependentK2).append(flowerIndependentK3).append(flowerIndependentK4);
		//Combining the Table to shuffle it 
		Table Combined5 = flowerIndependentK1toK5no5.addColumns(speciesNumK1toK5no5);
		double [][] stillCombined5 = Combined5.as().doubleMatrix();
		double [][] combinedShuffle5 =shuffle2DArray(stillCombined5);
		
		//Split double dimensional Array
		int [] speciesNumK1toK5Arr5 =getLastColumn(combinedShuffle5);
		double [][] flowerIndependentK2toK5Arr5=removeLastColumn(combinedShuffle5);
		
		//Testing set
		int [] speciesNumk5Arr = speciesNumk5.asIntArray();
		double [][] flowerIndependentK5Arr = flowerIndependentK5.as().doubleMatrix();
		
		
	/**
	 * Fitting the model, using data from k2 to K5, and will test with K1  fold 
	 */
		System.out.println("Fitting the model, testing with K1 dataset");
		SVM<double[]> FlowerSvm = new SVM<>(new LinearKernel(), 10.0, Math.max(speciesNumK2toK5Arr) + 1, SVM.Multiclass.ONE_VS_ALL);
		FlowerSvm.learn(flowerIndependentK2toK5Arr, speciesNumK2toK5Arr);
		FlowerSvm.finish();
			
	/**
	 * Fitting the model, using data from k1 to K5 without k2, and will test with K2  fold 
	 */
		System.out.println("Fitting the model, testing with K2 dataset");
		FlowerSvm.learn(flowerIndependentK2toK5Arr2, speciesNumK1toK5Arr2);
		FlowerSvm.finish();	
			

	/**
	 * Fitting the model, using data from k1 to K5 without k3, and will test with K3  fold 
	 */
		System.out.println("Fitting the model, testing with K3 dataset");
		FlowerSvm.learn(flowerIndependentK2toK5Arr3, speciesNumK1toK5Arr3);
		FlowerSvm.finish();
	
	/**
	 * Fitting the model, using data from k1 to K5 without k4, and will test with K4  fold 
	 */
		System.out.println("Fitting the model, testing with K4 dataset");
		FlowerSvm.learn(flowerIndependentK2toK5Arr4, speciesNumK1toK5Arr4);
		FlowerSvm.finish();
		
	/**
	 * Fitting the model, using data from k1 to K4 without k5, and will test with K5  fold 
	 */
		System.out.println("Fitting the model, testing with K3 dataset");
		FlowerSvm.learn(flowerIndependentK2toK5Arr5, speciesNumK1toK5Arr5);
		FlowerSvm.finish();
			

     

		
		

   /**
    * Testing using K1
    */
         int K1error = 0;
         for (int i = 0; i < flowerIndependentK1Arr.length; i++) {
             if (FlowerSvm.predict(flowerIndependentK1Arr[i]) != speciesNumk1Arr[i]) {
            	 K1error++;
             }
         }
         System.out.println(K1error);
           
     /**
    * Testing using K2
    */
         int K2error = 0;
         for (int i = 0; i < flowerIndependentK2Arr.length; i++) {
             if (FlowerSvm.predict(flowerIndependentK2Arr[i]) != speciesNumk2Arr[i]) {
            	 K2error++;
             }
         }
         
         System.out.println(K2error);
		         
		         

 /**
  * Testing using K3
  */
       int K3error = 0;
       for (int i = 0; i < flowerIndependentK3Arr.length; i++) {
           if (FlowerSvm.predict(flowerIndependentK3Arr[i]) != speciesNumk3Arr[i]) {
          	 K3error++;
           }
       }
       
       System.out.println("we printing" +K3error);
	     

	 /**
	  * Testing using K4
	  */
	       int K4error = 0;
	       for (int i = 0; i < flowerIndependentK4Arr.length; i++) {
	           if (FlowerSvm.predict(flowerIndependentK4Arr[i]) != speciesNumk4Arr[i]) {
	          	 K4error++;
	           }
	       }
	       
	       System.out.println("we printing" +K4error);
	       
	       
   /**
    * Testing using K5
    */
         int K5error = 0;
         for (int i = 0; i < flowerIndependentK5Arr.length; i++) {
             if (FlowerSvm.predict(flowerIndependentK5Arr[i]) != speciesNumk5Arr[i]) {
            	 K5error++;
             }
         }
         
         System.out.println(K5error);
         
         
         
   double avgError = (double)((K1error+K2error+K3error+K4error+K5error)/5);
   System.out.println("The average error rate of our model is"+ avgError);
	         
	}
	
	

}
