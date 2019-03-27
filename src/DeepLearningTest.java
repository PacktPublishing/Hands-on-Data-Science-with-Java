
import java.io.IOException;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
//import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;
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
        DataSetIterator irisIterator = new RecordReaderDataSetIterator(irisRecordReader,flowerBatchSize,numExplanatoryVariables,numFlowerClasses);
        DataSet irisFlowerAllData = irisIterator.next();
        irisFlowerAllData.shuffle();
        
        
   /**
    * Splitting our dataset to training and Testing
    * We will use 70:30 split
    */
        SplitTestAndTrain flowerDataTestTrain = irisFlowerAllData.splitTestAndTrain(0.7);
        DataSet irisTrainingData = flowerDataTestTrain.getTrain();
        DataSet irisTestData = flowerDataTestTrain.getTest();
       
    /**
     * We need to normalize our data. We'll use NormalizeStandardize (which gives us mean 0, unit variance):
     */
        DataNormalization flowerNormalizer = new NormalizerStandardize();
        flowerNormalizer.fit(irisTrainingData);           //Collect the statistics (mean/stdev) from the training data. This does not modify the input data
        flowerNormalizer.transform(irisTrainingData);     //Apply normalization to the training data
        flowerNormalizer.transform(irisTestData);
        
        final int numInputs = 4;
        int outputNum = 3;
        long seed = 6;
        
        System.out.println("Building the  deeplearning Model");
        MultiLayerConfiguration layerConfiguration = new NeuralNetConfiguration.Builder()
            .seed(seed)
            .activation(Activation.TANH)
            .weightInit(WeightInit.XAVIER)
            .updater(new Sgd(0.1))
            .l2(1e-4)
            .list()
            .layer(0, new DenseLayer.Builder().nIn(numInputs).nOut(3)
                .build())
            .layer(1, new DenseLayer.Builder().nIn(3).nOut(3)
                .build())
            .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .activation(Activation.SOFTMAX)
                .nIn(3).nOut(outputNum).build())
            .backprop(true).pretrain(false)
            .build();
        
        System.out.print("Running the model"); 
        MultiLayerNetwork theModel = new MultiLayerNetwork(layerConfiguration);
        theModel.init();
        theModel.setListeners(new ScoreIterationListener(100));

        for(int i=0; i<1000; i++ ) {
        	theModel.fit(irisTrainingData);
        }
        
      /**
       * Looking at the performance  and the evaluation of the model
       */
        Evaluation modelEvaluation = new Evaluation(3);
        INDArray output = theModel.output(irisTestData.getFeatures());
        modelEvaluation.eval(irisTestData.getLabels(), output);
        System.out.println(modelEvaluation.stats());
        
        
        
        
        
        
        
        
        

	}

}
