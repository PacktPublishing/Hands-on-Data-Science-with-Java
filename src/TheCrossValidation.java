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


	public TheCrossValidation() {
		}

	public static void main(String[] args) throws IOException {
	//Loading the IRIS dataset		
		Table IrisDataset  = Table.read().csv("DataSets/Iris.csv");
		
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
		 * Testing the model using K1
		 */
		
		NumberColumn speciesNumK2toK5 = (NumberColumn) speciesNumk2.append(speciesNumk3).append(speciesNumk4).append(speciesNumk5);
		Table flowerIndependentK2toK5 = flowerIndependentK2.append(flowerIndependentK3).append(flowerIndependentK4).append(flowerIndependentK5);
		
		
		//Combining the Table to shuffle it 
		Table Combined = flowerIndependentK2toK5.addColumns(speciesNumK2toK5);
		double [][] stillCombined = Combined.as().doubleMatrix();
		double [][] combinedShuffle =shuffle2DArray( stillCombined);
		
		//Split double dimensional Array
		int [] speciesNumK2toK5Ar =getLastColumn(combinedShuffle);
		System.out.println(speciesNumK2toK5Ar.length);
		
		 for(int i =0; i< combinedShuffle.length;i++) {
        	 for(int j=0; j<combinedShuffle[i].length; j++){
        		 System.out.println(combinedShuffle[i][j]);	        		 
        	 }
        	 if(i==1) {
        		 break;
        	 }
        		 
         }
		
		
		
		
//		
//		//Converting to Array 
//		int [] speciesNumK2toK5Arr = speciesNumK2toK5.asIntArray();
//		double [][] flowerIndependentK2toK5Arr = flowerIndependentK2toK5.as().doubleMatrix();
//		
//		
//		
//		
//		//Testing set
//		int [] speciesNumk1Arr = speciesNumk1.asIntArray();
//		double [][] flowerIndependentK1Arr = flowerIndependentK1.as().doubleMatrix();
//			
//		/**
//		 * Fitting the model
//		 */
//			System.out.println("Fitting the model, testing with K1 dataset");
//			SVM<double[]> FlowerSvm = new SVM<>(new LinearKernel(), 10.0, Math.max(speciesNumK2toK5Arr) + 1, SVM.Multiclass.ONE_VS_ALL);
//			FlowerSvm.learn(flowerIndependentK2toK5Arr, speciesNumK2toK5Arr);
//			FlowerSvm.finish();
//			
//	   /**
//	    * Testing using K1
//	    */
//	         int K1error = 0;
//	         for (int i = 0; i < flowerIndependentK1Arr.length; i++) {
//	             if (FlowerSvm.predict(flowerIndependentK1Arr[i]) != speciesNumk1Arr[i]) {
//	            	 K1error++;
//	             }
//	         }
//	         
//	         System.out.println(K1error);
	         
	         
	         
	         
	         
	         
	     //Testing the shuffling
//	         double [][] myArray =shuffle2DArray(flowerIndependentK1Arr);
//	         for(int i =0; i< myArray.length;i++) {
//	        	 for(int j=0; j<myArray[i].length; j++){
//	        		 System.out.println(myArray[i][j]);	        		 
//	        	 }
//	        		 
//	         }

	}

}
