import java.io.IOException;

import smile.classification.Classifier;
import smile.classification.NaiveBayes;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;
import smile.math.kernel.LinearKernel;
import smile.math.*;
import smile.math.Math;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.selection.Selection;


public class IrisFlowersClassification {

	public IrisFlowersClassification() {
		// TODO Auto-generated constructor stub
	}
	
	public static int[] classifyFlowersIndex(StringColumn myCol) {
		int [] myreal = null;
		
		
		return myreal;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	/**
	 * Loading the IRIS Dataset
	 */

		Table irisFlowersData  = Table.read().csv("DataSets/IrisDataset.csv");
		
	/**
	 * Change the Columns with a number
	 * 0-Iris-setosa
	 * 1-Iris-Virginica
	 * 2-Iris-versicolor
	 */
		System.out.println("Printing the last  rows of the table");
		Table tableTail = irisFlowersData.last(5);
		System.out.println(tableTail);
	/**
	 * Dividing the dataset to independent and Dependent
	 */		
		NumberColumn flowerSpeciesDependent = irisFlowersData.numberColumn(6);
		Table irisIndependent = (Table)irisFlowersData.removeColumns("Id","Species","SpeciesNum");
		
		
	/**
	 * Splitting Independent & dependent  dataset to test and Training
	 */
		Table irisIndependentTrain = (Table)irisIndependent.where(Selection.withRange(1,100));
		Table irisIndependentTest =  (Table)irisIndependent.where(Selection.withRange(101,150));
		NumberColumn flowerSpeciesDependentTrain = (NumberColumn)flowerSpeciesDependent.where(Selection.withRange(1,100));
		NumberColumn flowerSpeciesDependentTest =  (NumberColumn)flowerSpeciesDependent.where(Selection.withRange(101,150));
	
	/**
	 * Converting the training & testing dataset( independent & dependent) to array	
	 */
		double [][] irisIndependentTrainArr =irisIndependentTrain.as().doubleMatrix();
		int[] flowerSpeciesDependentTrainArr = flowerSpeciesDependentTrain.asIntArray();
	
		double [][] irisIndependentTestArr =irisIndependentTest.as().doubleMatrix();
		int[] flowerSpeciesDependentTestArr = flowerSpeciesDependentTest.asIntArray();
		
		//Full dataset
		 double [][] irisIndependentFull =irisIndependent.as().doubleMatrix();
		 int [] flowerSpeciesDependentFull = flowerSpeciesDependent.asIntArray();
		
	/**
	 * Fitting the Model (Support Vector Machine - Using Linear method)
	 */		 
		System.out.println("We are done here");
		SVM<double[]> svm = new SVM<>(new LinearKernel(), 10.0, Math.max(flowerSpeciesDependentTrainArr) + 1, SVM.Multiclass.ONE_VS_ALL);
         svm.learn(irisIndependentTrainArr, flowerSpeciesDependentTrainArr);
         svm.learn(irisIndependentTrainArr, flowerSpeciesDependentTrainArr);
         svm.finish();
		
		//Getting the error
         
         int error = 0;
         for (int i = 0; i < irisIndependentTestArr.length; i++) {
             if (svm.predict(irisIndependentTestArr[i]) != flowerSpeciesDependentTestArr[i]) {
                 error++;
             }
         }
         
         System.out.println("Linear ONE vs. ALL error  on testing dataset is = " + error);
	/**
	 * Fitting the model,(Support Vector Machine - Using Gaussian Kernel method 
	 */
         
         System.out.println("We are testing Gaussian Model");
 		 SVM<double[]> theSvm = new SVM<>(new GaussianKernel(1), 1.0, Math.max(flowerSpeciesDependentFull) + 1, SVM.Multiclass.ONE_VS_ALL);
 		 theSvm.learn(irisIndependentFull, flowerSpeciesDependentFull);
 		 theSvm.learn(irisIndependentFull, flowerSpeciesDependentFull);
 		 theSvm.finish();
 		 theSvm.trainPlattScaling(irisIndependentFull,flowerSpeciesDependentFull);
 		
 		//Getting the error
          
          int theError = 0;
          for (int i = 0; i < irisIndependentFull.length; i++) {
              if (svm.predict(irisIndependentFull[i]) != flowerSpeciesDependentFull[i]) {
            	  theError++;
              }
          }
          
          System.out.println("Gaussian ONE vs. ALL error = " + theError);
         
         
     
     /**
      * Testing the accuracy of SVM model
      */
          
         
         
         
		
	}

}
