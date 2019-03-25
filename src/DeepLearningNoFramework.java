
import java.lang.Math.*;
import java.util.Random;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.components.Figure;

public class DeepLearningNoFramework {

	public DeepLearningNoFramework() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * A function to transpose a two dimensional array
	 * @param arr
	 * @return double [][]
	 */
	public static double[][] transpose(double arr[][]){
	    int m = arr.length;
	    int n = arr[0].length;
	    double ret[][] = new double[n][m];
	
	    for (int i = 0; i < m; i++) {
	        for (int j = 0; j < n; j++) {
	            ret[j][i] = arr[i][j];
	        }
	    }
	
	    return ret;
	}
	/**
	 * Getting the summation of array contents
	 * @param myArray
	 * @return
	 */
	public static double getArraySummation(double[] myArray) {
		double thesum= 0;
		for (int i =0; i<myArray.length; i++) {
			thesum=thesum+myArray[i];
		}
		return thesum;
	}

	/**
	 * Defining the Sigmoid function
	 * @param x
	 * @return
	 */
	public static double[][] theSigmoid (double [][] x){
		double exponents [][] = new double [x.length][x[0].length];
		
		for(int i= 0; i<x.length; i++) {
			for (int k =0; k<x[i].length;k++) {
				double val= Math.exp(-(x[i][k]));
				double sigmoidVal = 1/(1+val);
				exponents[i][k]=sigmoidVal;
			}
		}		
		return exponents;
	}
	
	/**
	 * Defining the Softmax Function
	 * @param A
	 */	
	public static double[] theSoftmax (double [] x){
		double exponents [] = new double [x.length];
		
		for(int i= 0; i<x.length; i++) {
				double expVal= Math.exp((x[i]));
				
				//double sigmoidVal = 1/(1+val);
				exponents[i]=expVal;
		}
		
		//Calculating the exponential divide by the sum
		double softmaxExponents [] = new double [exponents.length];
		for(int j =0; j<exponents.length; j++) {
			
			double myVal = exponents[j]/getArraySummation(exponents);
			softmaxExponents[j]=myVal;
			
		}
		
		return softmaxExponents;
	}
	
	/**
	 * Convert two arrays to a two dimensional array
	 * @param 2 arrays of type float
	 */	
	public static float[][] convertTo2D(float [] x1, float[]x2){
		float[][] newArray = new float[500][2];
		for (int i=0;i<x1.length;i++){
			newArray[i][0]=x1[i];
        }
        for (int i=0;i<x2.length;i++){
        	newArray[i][1]=x2[i];
        }
		return newArray ;	
	}
	
	
	/**
	 * Merging 3 2d Arrays into One 2d array
	 * @param args
	 */
	public static float[][] merge2dArrays (float[][]x1,float[][]x2,float[][]x3){
		float[][] merge2dArrays	= new float[1500][2];
		
		for(int i = 0; i < x1.length;i++) {
			for (int j = 0; j<x1[j].length; j++) {
				merge2dArrays[i][j]=x1[i][j];
			}
		}
		for(int i = 500; i <1000;i++) {
			for (int j = 0; j<x2[j].length; j++) {
				merge2dArrays[i][j]=x2[i-500][j];
			}
		}
		for(int i = 1000; i <1500;i++) {
			for (int j = 0; j<x3[j].length; j++) {
				merge2dArrays[i][j]=x3[i-1000][j];
			}
		}
		 return merge2dArrays;		
	}
	/**
	 * Created a function to create labels, 0,1,2 for the dataset
	 * @return
	 */
	  public static int[] labels() {
		  
		  int[] labelArray = new int[1500];
		  
		  for(int i = 0; i<500; i++) {
			  labelArray[i]=0;  
		  }
		  for(int i =500; i<1000; i++) {
			  labelArray[i]=1;  
		  }
		  for(int i = 1000; i<1500; i++) {
			  labelArray[i]=2;  
		  }
		return labelArray; 
	  }
	  /**
	   * A matrix to generate the hot encodings for the labels in the previous function
	   * @return
	   */
	  public static int[][] labelHotEncodings(){		  
		  int[][] hotEncodingsArray = new int[1500][3];
		  for(int i = 0; i<500; i++) {
			  for(int j =0;j<3; j++) {
				  if(j==0) {
					  hotEncodingsArray[i][j] =1;
				  }else {
					  hotEncodingsArray[i][j]=0;
				  } 
			  }
		  }
			  
		  for(int i = 500; i<1000; i++) {
			  for(int j =0;j<3; j++) {
				  if(j==1) {
					  hotEncodingsArray[i][j] =1;
				  }else {
					  hotEncodingsArray[i][j]=0;
				  } 
			  }
		  }
		  for(int i =1000; i<1500; i++) {
			  for(int j =0;j<3; j++) {
				  if(j==2) {
					  hotEncodingsArray[i][j] =1;
				  }else {
					  hotEncodingsArray[i][j]=0;
				  } 
			  }
		  }
		return hotEncodingsArray;
		  
	  }
	/**
	 * Extracting an array from a 2d Array
	 * @param X
	 * @param j
	 * @return
	 */
	public static float[] extractArray(float [][]X,int j) {
		float[] myNewArray = new float[X.length];
		for(int i =0; i<X.length;i++) {
			myNewArray[i]=X[i][j];	
		}
		return myNewArray;		
	}
	
	public static void main(String[] args) {
/**
 * generate three Gaussian clouds each holding 500 points
 */
		//Generating X1_1
	      Random rd = new Random();
	      float[] X1_1 = new float[500];
	      for (int i = 0; i < X1_1.length; i++) {
	    	  X1_1[i] = (rd.nextFloat())+0;
	      }
	      
	    //Generating X1_2
	      Random rd2 = new Random();
	      float[] X1_2 = new float[500];
	      for (int i = 0; i < X1_2.length; i++) {
	    	  X1_2[i] = (rd2.nextFloat())-2;
	      }
	      
	      
	   //Generating X2_1
	      Random rd3 = new Random();
	      float[] X1_3 = new float[500];
	      for (int i = 0; i < X1_3.length; i++) {
	    	  X1_3[i] = (rd3.nextFloat())+2;
	      }
	      
	    //Generating X2_2
	      Random rd4 = new Random();
	      float[] X1_4 = new float[500];
	      for (int i = 0; i < X1_4.length; i++) {
	    	  X1_4[i] = (rd4.nextFloat())+2;
	      }
	    
	      
	     //Generating X3_1
	      Random rd5 = new Random();
	      float[] X1_5 = new float[500];
	      for (int i = 0; i < X1_5.length; i++) {
	    	  X1_5[i] = (rd5.nextFloat())-2;
	      }
	      
	      
	    //Generating X3_2
	      Random rd6 = new Random();
	      float[] X1_6 = new float[500];
	      for (int i = 0; i < X1_6.length; i++) {
	    	  X1_6[i] = (rd6.nextFloat())+2;
	      }
	      
	      
	 //Merging them into 3  2 d arrays	      
	      float [][]x1=convertTo2D(X1_1,X1_2);
	      float [][]x2=convertTo2D(X1_3,X1_4);
	      float [][]x3=convertTo2D(X1_5,X1_6);
	      
	/**
	 * Concatinating the 2d arrays to one 2d array of size 500( into one big matrix)
	 */	      
	    float [][] X = merge2dArrays(x1,x2,x3);    
	
	/**
	 * Hot encodings
	 */
	    labelHotEncodings();

    /**
     * Visualizing the data, we will be using Tablesaw
     */ 
	    float [] myX =extractArray(X,0);
	    float [] myY = extractArray(X,1);
	    
	    //convert to Column
	    DoubleColumn theX = DoubleColumn.create("X", myX);
	    DoubleColumn theY = DoubleColumn.create("Y", myY);
	    Table dataset =Table.create("dataset",theX,theY);
		Figure scatterFigure = ScatterPlot.create("Dataset Plotted ", dataset, "X", "Y");
		Plot.show(scatterFigure);
		
		//Defining structure of dataset
		int totalSamples = X.length; //1500 samples
		int features = X[0].length;
		int hidden_nodes = 5;
		int classes = 3;
	    
	/**
	 * Randomly initializing the weights
	 */
		float[][]W1 = new float[features][hidden_nodes];
		for(int i=0; i<W1.length;i++) {
			
		}
		
		
		
		
		
		float[] b1 = new float[hidden_nodes];
		float[][]W2 = new float[hidden_nodes][classes];
		float[]b2 = new float[classes];
		
	
			

	    
		System.out.println("Processing the deeplearnign with no framework ");
	}

}
