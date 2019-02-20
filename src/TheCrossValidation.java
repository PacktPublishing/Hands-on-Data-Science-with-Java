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
		 * Combining the specific gr
		 */
		System.out.println(speciesNum11.size());
		
		//Converting to a double array
		
		double [][] indepen = independentData10.as().doubleMatrix();
		double [][] indepen1 = independentData11.as().doubleMatrix();
		
		System.out.println(indepen[indepen.length-1][0]);
		System.out.println(indepen1[0][0]);

		
		
		

	}

}
