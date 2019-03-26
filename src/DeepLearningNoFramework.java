
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

	public static double[][] theSoftmax (double [][] x){
		
		//Getting the exponents
		double [][] theExponents = new double [x.length][x[0].length];
		for(int i =0; i<x.length;i++) {
			for(int j=0; j<x[i].length;j++) {
				theExponents[i][j]=Math.exp(x[i][j]);
			}
		}
		
		//Summing the  expnonets to one array
		double [] sumArray = new double [theExponents.length];
		for(int i =0; i<theExponents.length;i++) {
			double sum =0;
			for(int j=0; j<theExponents[i].length;j++) {
				sum = sum +theExponents[i][j];
			}
			sumArray[i]=sum;
		}
		
		//division of the :theExponents by summation of exponents:sumArray
		double [][] Y =  new double[theExponents.length][theExponents[0].length];
		for (int i=0; i<theExponents.length;i++) {
			for(int j=0;j<theExponents[i].length;j++) {
				Y[i][j]= theExponents[i][j]/sumArray[i];
			}
		}
		return Y;
		
	}
	
	/**
	 * Convert two arrays to a two dimensional array
	 * @param 2 arrays of type float
	 */	
	public static double[][] convertTo2D(double [] x1, double[]x2){
		double[][] newArray = new double[500][2];
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
	public static double[][] merge2dArrays (double[][]x1,double[][]x2,double[][]x3){
		double [][] merge2dArrays	= new double[1500][2];
		
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
	  public static double[][] labelHotEncodings(){		  
		  double[][] hotEncodingsArray = new double[1500][3];
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
	public static double[] extractArray(double [][]X,int j) {
		double[] myNewArray = new double[X.length];
		for(int i =0; i<X.length;i++) {
			myNewArray[i]=X[i][j];	
		}
		return myNewArray;		
	}
	
	
	/**
	 * dotProdut ( multiplying two arrays)
	 *Adopted from Matrix .Java
	 * @param args
	 */
		public static double[][] matrixMultiplication(double[][] a, double[][] b) {
		        int m1 = a.length;
		        int n1 = a[0].length;
		        int m2 = b.length;
		        int n2 = b[0].length;
		        if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
		        double[][] c = new double[m1][n2];
		        for (int i = 0; i < m1; i++)
		            for (int j = 0; j < n2; j++)
		                for (int k = 0; k < n1; k++)
		                    c[i][j] += a[i][k] * b[k][j];
		        return c;
		    }
		/**
		 * Matrix vector addition
		 * @param a
		 * @param b
		 * @return
		 */
		
		public static double[][] matrixVectorAddition (double[][]a , double []b ){
			
			double [][] broadcastedMatrix = new double [a.length][b.length];
			
			for(int i=0; i<a.length;i++) {
				for (int j=0; j<a[i].length;j++) {
					broadcastedMatrix[i][j]=a[i][j]+b[j];
				}
				
			}
			return broadcastedMatrix;
			
		}
		/**
		 * Subtracting the matrixex
		 * @param a
		 * @param b
		 * @return
		 */
		public static double[][] matrixSubtraction(double [][]a , double[][]b){
			int m = a.length;
	        int n = a[0].length;
	        double[][] c = new double[m][n];
	        for (int i = 0; i < m; i++)
	            for (int j = 0; j < n; j++)
	                c[i][j] = a[i][j] - b[i][j];
	        return c;			
		}
		
		
		
	/**
	 * Adding two dimenstional array  to a one dimensional array
	 * @param args
	 */
	
	
	public static void main(String[] args) {
/**
 * generate three Gaussian clouds each holding 500 points
 */
		//Generating X1_1
	      Random rd = new Random();
	      double[] X1_1 = new double[500];
	      for (int i = 0; i < X1_1.length; i++) {
	    	  X1_1[i] = (rd.nextGaussian())+0;
	      }
	      
	    //Generating X1_2
	      Random rd2 = new Random();
	      double[] X1_2 = new double[500];
	      for (int i = 0; i < X1_2.length; i++) {
	    	  X1_2[i] = (rd2.nextGaussian())-2;
	      }
	      
	      
	   //Generating X2_1
	      Random rd3 = new Random();
	      double[] X1_3 = new double[500];
	      for (int i = 0; i < X1_3.length; i++) {
	    	  X1_3[i] = (rd3.nextGaussian())+2;
	      }
	      
	    //Generating X2_2
	      Random rd4 = new Random();
	      double[] X1_4 = new double[500];
	      for (int i = 0; i < X1_4.length; i++) {
	    	  X1_4[i] = (rd4.nextGaussian())+2;
	      }
	    
	      
	     //Generating X3_1
	      Random rd5 = new Random();
	      double[] X1_5 = new double[500];
	      for (int i = 0; i < X1_5.length; i++) {
	    	  X1_5[i] = (rd5.nextGaussian())-2;
	      }
	      
	      
	    //Generating X3_2
	      Random rd6 = new Random();
	      double[] X1_6 = new double[500];
	      for (int i = 0; i < X1_6.length; i++) {
	    	  X1_6[i] = (rd6.nextGaussian())+2;
	      }
	      
	      
	 //Merging them into 3  2 d arrays	      
	      double [][]x1=convertTo2D(X1_1,X1_2);
	      double [][]x2=convertTo2D(X1_3,X1_4);
	      double [][]x3=convertTo2D(X1_5,X1_6);
	      
	/**
	 * Concatinating the 2d arrays to one 2d array of size 500( into one big matrix)
	 */	      
	    double [][] X =merge2dArrays(x1,x2,x3);    
	
	/**
	 * Hot encodings
	 */
	    labelHotEncodings();

    /**
     * Visualizing the data, we will be using Tablesaw
     */ 
	    double [] myX =extractArray(X,0);
	    double [] myY = extractArray(X,1);
	    
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
		//W1
		double [][]W1 = new double[features][hidden_nodes];
		Random newRand = new Random();
		for(int i=0; i<W1.length;i++) {
			for(int j = 0; j<W1[i].length; j++) {
				W1[i][j]=newRand.nextGaussian();
			}
		}
		//b1
		double [] b1 = new double[hidden_nodes];
		Random myRand = new Random();
		for(int i = 0;i<b1.length;i++) {
			b1[i]=myRand.nextGaussian();
		}
		
		//W2
		double[][]W2 = new double[hidden_nodes][classes];
		Random randW2 = new Random();
		for(int i=0; i<W2.length;i++) {
			for(int j = 0; j<W2[i].length; j++) {
				W2[i][j]=randW2.nextGaussian();
			}
		}
		//b2		
		double[]b2 = new double[classes];
		Random randb2 = new Random();
		for(int j= 0;j<b2.length;j++) {
			b2[j]=randb2.nextGaussian();
		}
		
		//Defining  Alpha and the cost array
		double alpha = 10e-6;
		double []costs;
		
		
	for (int i =0; i<10000;i++) {
		
		 //Forward Pass		 
		double [][]A = theSigmoid(matrixVectorAddition((matrixMultiplication(X,W1)),b1)); // A = sigma(Z);
		double [][]Y = theSoftmax(matrixVectorAddition((matrixMultiplication(A,W2)),b2)); // Y = softmax(Z2)
		
		//backward pass
	    double [][] delta2 =matrixSubtraction(Y,labelHotEncodings());
	    	//Calculating delta1
	    double [][] delta1 =
	    //delta1 = (delta2).dot(W2.T) * A * (1 - A)
		
		
				
	}
			

	    
		System.out.println("Processing the deeplearnign with no framework ");
	}

}
