import java.io.IOException;

import smile.regression.OLS;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;
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
	
	/*
	 * New strategy
	 */
	
	Table AllPricesHouse  = Table.read().csv("DataSets/HousePricesAll.csv");
	
	
	Table tableTail10 = AllPricesHouse.last(5);
	System.out.println(tableTail10);
	
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
	//Table AllPricesHouseDependent = (Table)myNewTableHousePrices.retainColumns("SalePrices");
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
		
	

	}
		

}
