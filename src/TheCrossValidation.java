import java.io.IOException;

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
	


	public TheCrossValidation() {
		}

	public static void main(String[] args) throws IOException {
	//Loading the IRIS dataset		
		Table IrisDataset  = Table.read().csv("DataSets/Iris.csv");
		
	//Getting the independent and dependent dataset
		NumberColumn speciesNum = IrisDataset.nCol("SpeciesNum");
		Table independentData = IrisDataset.retainColumns("SepalLengthCm","SepalWidthCm","PetalLengthCm","PetalWidthCm");
		
	//Dividing the data into K folds  K =5
		//Set 1	
		NumberColumn speciesNum10 =speciesNum.where(Selection.withRange(1,10));
		NumberColumn speciesNum11 =speciesNum.where(Selection.withRange(1,10));
		NumberColumn speciesNum12 =speciesNum.where(Selection.withRange(1,10));
		//Table
		
		
		
		

	}

}
