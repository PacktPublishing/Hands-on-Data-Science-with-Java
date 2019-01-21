import java.io.IOException;

import smile.regression.OLS;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.util.DoubleArrays;

public class HouseLinearRegression {

	public HouseLinearRegression() {
		// TODO Auto-generated constructor stub
		
	}
	
	public  double[] predictingHousePrices (double[][] houseCharacteristis) {
		
		double [] myarr = {24,45,67};
		
		return myarr;
		
	}

	public static void main(String[] args) throws IOException {

	/**
	 * Loading the datasets ( Training datasets)
	 */
		
	Table HouseTrainIndependent  = Table.read().csv("DataSets/HouseTrainIndependent.csv");
	Table HouseTrainDependent  = Table.read().csv("DataSets/HouseTrainDependent.csv");
	
	
	/*
	 * Converting the tables to double arrays
	 */
	//double [][] HouseTrainIndependentArr = DoubleArrays.to2dArray(HouseTrainIndependent.numberColumns());
	double [][] HouseTrainIndependentArr =HouseTrainIndependent.as().doubleMatrix();
	System.out.println("We are done here");
	
	NumberColumn dependentPrice = (NumberColumn) HouseTrainDependent.column("SalePrice");
	double [] dependentPriceArr = dependentPrice.asDoubleArray();
	
	
/*
 * Fitting the OLS model  using SMILE lib
 * Returns a trainned  reggression Model
 * 
 */
	
	OLS myHousePriceTrainer = new OLS(HouseTrainIndependentArr,dependentPriceArr);
	//myHousePriceTrainer.train(HouseTrainIndependentArr, null);
	
/*
 * Evaluating the Model
 */
	
	System.out.println("The Adjusted Rsquared for the model is" + myHousePriceTrainer.adjustedRSquared());
	System.out.println("The Rsquared for the model is" + myHousePriceTrainer.RSquared());
	
	
	
	
	
/*
 * Using the trained regression model to predict house prices
 */
	//Loading testing data
	//Table HouseTestIndependent  = Table.read().csv("DataSets/HouseTestIndependent.csv");
	  
	double []  values = {20,64,	7406,7,5,2006,2006,684,515,1199,1220,1220,2,2,6,2006,2,632, 54,2006};

	double predictedaVal = myHousePriceTrainer.predict(values);
	System.out.println("The predicted Value is" +predictedaVal);
	System.out.println("The actual value is 194000");
	
	/*
	 * New strategy
	 */
	
	Table AllPricesHouse  = Table.read().csv("DataSets/AllHousePrices.csv");
	
	Table myNewTableHousePrices = AllPricesHouse.dropRowsWithMissingValues();
	System.out.println("Printing the last  rows of the table");
	Table tableTail = myNewTableHousePrices.last(5);
	System.out.println(tableTail);
	
	
	//Getting the structure of the table:
	String tableShape = myNewTableHousePrices.shape();
	System.out.println(tableShape);	
	System.out.println("We are done here");

	}
		

}
