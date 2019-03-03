import java.io.IOException;

import smile.regression.OLS;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;
import tech.tablesaw.util.DoubleArrays;

/**
 * Supervised Learning: Linear Regression on Housing dataset
 * @author rwangari
 *Section 4.2
 */
public class HouseLinearRegression {
	public HouseLinearRegression() {
		// TODO Auto-generated constructor stub
		
	}
	
	/**
	 * A function to predict the prices of the houses using the model we created
	 * @param houseCharacteristis
	 * @param theLinearModel
	 * @return predicted Prices 
	 */
	public  static double[] predictingHousePrices (double[][] houseCharacteristis, OLS theLinearModel) {
		
		double [] predictedPrices = new double [houseCharacteristis.length];
		
		for(int i=0; i<houseCharacteristis.length;i++) {
			
			double []  oneHseCharacteristics= new double[houseCharacteristis[i].length];
						
			for(int j =0; j<houseCharacteristis[i].length; j++) {
				oneHseCharacteristics[j]=houseCharacteristis[i][j];
			}
			
			//Predicting
			double predictedaVal = theLinearModel.predict(oneHseCharacteristics);
			predictedPrices[i]=predictedaVal;
			
		}
		return predictedPrices;
	}
	
	/*
	 * Getting the absolute errors but subtracting predicted values from actual Values
	 * @param double[] actualValues
	 * @param double[] predictedValues
	 * @return double [] theAbsoluteError
	 */
	
	public static double[] calculateAbsoluteError (double [] actualValues, double []  predictedValues) {
		
		double [] theAbsoluteError  = new double [actualValues.length];
		
		for(int k = 0; k<actualValues.length; k++) {
			double j =actualValues[k]-predictedValues[k];
			//putting the absolute error in the array			
			theAbsoluteError[k]=Math.abs(j);		
		}
		return theAbsoluteError;
	}
	/*
	 * Getting the Mean of the Absolute Errors
	 * @param double [] theAbsoluteError
	 * @return double ,Mean
	 */
	public static double getMAE(double [] theAbsoluteError) {
		double totalSum=0;
		double theMean;
		for(int i =0; i<theAbsoluteError.length; i++) {
			totalSum=totalSum+theAbsoluteError[i];
		}
		theMean=totalSum/theAbsoluteError.length;
		return theMean;		
	}
	

	public static void main(String[] args) throws IOException {
	
	/*
	 * Predicting the House prices using Linear Regression (Supervised Learning)
	 */
	
	Table AllPricesHouse  = Table.read().csv("DataSets/HousePricesAll.csv");

	//Dropping the rows with the missing values
	Table myNewTableHousePrices = AllPricesHouse.dropRowsWithMissingValues();
	
	System.out.println("Printing the last  rows of the table");
	Table tableTail = myNewTableHousePrices.last(5);
	System.out.println(tableTail);
	
	
	//Getting the structure of the table:
	String tableShape = myNewTableHousePrices.shape();
	System.out.println(tableShape);	
	System.out.println("We are done here");
	
	//Getting the structure of the table:
	System.out.println("We hear printing the structure");
	Table tableStructure= myNewTableHousePrices.structure();
	System.out.println(tableStructure);	
	System.out.println("We are done here");
	
	
	//Splitting the table into two (Independent vs Dependent)
	NumberColumn AllPricesHouseDependent = myNewTableHousePrices.numberColumn(20);
	Table AllPricesHouseIndependent = (Table)myNewTableHousePrices.removeColumns("SalePrices");
		
	
	
	//Test and training
	Table AllPricesHouseTrainIndependent = (Table)AllPricesHouseIndependent.where(Selection.withRange(1,727));
	Table AllPricesHouseTestIndependent =  (Table)AllPricesHouseIndependent.where(Selection.withRange(728,1127));
	
	//Dependent
	NumberColumn AllPricesHouseTrainDependent = (NumberColumn)AllPricesHouseDependent.where(Selection.withRange(1,727));
	NumberColumn AllPricesHouseTestDependent =  (NumberColumn)AllPricesHouseDependent.where(Selection.withRange(728,1127));
	
	//Trying all the ways and testing the tables	
	System.out.println("Checking the tables");
	Table tableTail1 = AllPricesHouseTrainIndependent.last(5);
	System.out.println(tableTail1);
	
//	Table tableTail2 = AllPricesHouseTrainDependent.last(5);
//	System.out.println(tableTail2);
	
	Table tableTail3 = AllPricesHouseTestIndependent.last(5);
	System.out.println(tableTail3);
	
//	Table tableTail4 = AllPricesHouseTestDependent.last(5);
//	System.out.println(tableTail4);
	
	
	
	//Converting the training dataset to double [][]:
	double [][] AllPricesHouseTrainIndependentArr =AllPricesHouseTrainIndependent.as().doubleMatrix();
	
	//NumberColumn nc = AllPricesHouseTrainDependent.numberColumn(0);
	double [] AllPricesHouseTrainDependentArr = AllPricesHouseTrainDependent.asDoubleArray();
	
	
	//Fitting the Model
	OLS myHousePriceTrainer = new OLS(AllPricesHouseTrainIndependentArr,AllPricesHouseTrainDependentArr);
	
	
	/**
	 * Evaluating the Model
	 */
	System.out.println("The Adjusted Rsquared for the model is" + myHousePriceTrainer.adjustedRSquared());
	System.out.println("The Rsquared for the model is" + myHousePriceTrainer.RSquared());
	
	
	/*
	 * Using the trained regression model to predict house prices
	 */
 
		double []  values = {20,64,	7406,7,5,2006,2006,684,515,1199,1220,1220,2,2,6,2006,2,632, 54,2006};
	
		double predictedaVal = myHousePriceTrainer.predict(values);
		System.out.println("The predicted Value is" +predictedaVal);
		System.out.println("The actual value is 194000");
		
	
/**
 * Using the function to Predict the Values of our testing dataset
 */
		
	//converting out test independent table to double array
		double [][] AllPricesHouseTestIndependentArr =AllPricesHouseTestIndependent.as().doubleMatrix();
	
   //Calling the function
		double []  predictedVals = predictingHousePrices(AllPricesHouseTestIndependentArr,myHousePriceTrainer);
		System.out.println("Like we are done here" +predictedVals.length);
		
		
/**
 * Calculating the Mean Average error
 */
		double [] ActualHousePrices = AllPricesHouseTestDependent.asDoubleArray();
		//Getting the MAE 
		double theMae =getMAE(calculateAbsoluteError(ActualHousePrices,predictedVals));		
		System.out.println("The MAE of the House Linear Regression Is" + theMae );
		System.out.println("This means that our model is off by average of"+ theMae);
					
	}
		 

}
