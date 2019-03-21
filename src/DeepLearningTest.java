
import java.io.IOException;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.io.ClassPathResource;
//import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
/**
 * Section 5 
 * Creating a deeplearning Model using DeePLearnign 4J
 * @author rwangari
 *
 */

public class DeepLearningTest {

	public DeepLearningTest() {
	}

	public static void main(String[] args) {
		/**
		 * Loading the Dataset using Record Reader. CSV reader handles Loading and parsing of the data
		 */
        int numLinesToSkip = 0;
        char delimiter = ',';
        RecordReader irisRecordReader = new CSVRecordReader(numLinesToSkip,delimiter);
        try {
        	irisRecordReader.initialize(new FileSplit(new ClassPathResource("IrisFlowerData.txt").getFile()));
		} catch (IOException e) {
			System.out.println("We couldnt load the file because of FILE IO");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("The file could not be loaded because of interupted Exeception");			
			e.printStackTrace();
		}
        System.out.println("We have loaded the file");
        
    /**
     * Defining the number of x variables, the number of classes in the flowers data and the total number of flowers in the file
     */
        int numExplanatoryVariables = 4; //Sepal length and width, petal Length and width
        int numFlowerClasses =3; //Setosa, Virginica & Versicolor
        int flowerBatchSize = 150; //Our iris dataset contains 150 samples 
        
        
  /**
   *  Using RecordReaderDataSetIterator to handle conversion  of our dataset to objects,
   *  ready for use in neural network
   */ 
        DataSetIterator iterator = new RecordReaderDataSetIterator(irisRecordReader,flowerBatchSize,numExplanatoryVariables,numFlowerClasses);
       // DataSet allData = iterator.next();
       // allData.shuffle();

	}

}
