
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
		 * Implementing Delta 1
		 * //follows delta1 =(delta2).dot(W2.T) * A * (1 - A)
		 * @param delta2
		 * @param W2
		 * @param A
		 * @return
		 */
		public static double[][] delta1(double[][]delta2, double[][]W2,double[][]A){
			//Getting the transpose  of W
			double [][] w2Transpose = transpose(W2);
			
			//implementing 1-A
			double [][] subtractedA = new double[A.length][A[0].length];
			for(int i =0; i<A.length;i++) {
				for(int j=0; j<A[i].length;j++) {
					subtractedA[i][j]=1-(A[i][j]);					
				}
			}
			
			 double[][] multipliedDW = matrixMultiplication(delta2,w2Transpose);//Multipliying delta2 with transposed W2
			 double[][] multipliedDWA =matrixMultiplication(multipliedDW,A);//Multiply result above with A
			 double[][] multipliedDWAsubA =matrixMultiplication(multipliedDWA,subtractedA); //multiply result above with subtracted A			 
			return multipliedDWAsubA;
		}
		
		/**
		 * Updating the weights
		 * follows W2 -= alpha * A.T.dot(delta2)
		 * @param weight
		 * @param alpha
		 * @param A
		 * @param delta
		 * @return
		 */
		public static double [][] updatingWeights(double [][] weight , double alpha, double [][]AX, double[][] delta){
			double [][] updatedWeight = new  double [weight.length][weight[0].length];
			//Getting transpose of A
			double [][] AXTranspose =  transpose(AX);
			//Multiply transpose with Delta
			 double [][] AXTransposeD =matrixMultiplication(AXTranspose,delta);
			 
			 //Multiply result above with Alpha
			 double [][] AXTransposeDA = new double[AXTransposeD.length][AXTransposeD[0].length];
			 for(int i=0; i<AXTransposeD.length; i++) {
				 for(int j =0; j<AXTransposeD[0].length; j++) {
					 AXTransposeDA[i][j]=AXTransposeD[i][j]*alpha;
				 }
			 }
			  //Subtract result above from original weight 
			 updatedWeight =matrixSubtraction(weight,AXTransposeDA);
			return updatedWeight;
		}
		/**
		 * Updating the Weights
		 * follows b2 -= alpha * (delta2).sum(axis=0)
		 * @param bias
		 * @param alpha
		 * @param delta
		 * @return
		 */
		public static double[] updatingBias(double [] bias, double alpha, double[][] delta) {
			double [] updatedBias = new double [bias.length];
			// Summation of the delta column wise
			double col1=0;
			double col2=0;
			double col3=0;
			for(int i=0; i<delta.length; i++) {
				for(int j =0; j<delta[0].length;j++) {
					if(j==0) {
						col1=col1+delta[i][j];
					}
					else if(j==1){
						col2=col2+delta[i][j];
					}
					else {
						col3=col3+delta[i][j];
					}
				}
			}
			double [] summedDelta = {col1,col2,col3};
			
			//Multiply Alpha with Summed Delta
			double [] summedDeltaAlpha = {col1*alpha,col2*alpha,col3*alpha};
			
			//Subtract result above from original bias
			for(int i=0; i<bias.length;i++) {
				updatedBias[i]=bias[i]-summedDeltaAlpha[i];
			}		
			return updatedBias;
		}
		
		/**
		 * Implementing the Loss Function at each 100th Iteration
		 * follows: loss = np.sum(-T * np.log(Y))
		 * @param T
		 * @param Y
		 * @return theLoss which is a double
		 */
		public static double calculateLoss(double[][] T, double [][]Y) {
			double loss = 0;
			//Negatting the T
			double [][] negateT = new double [T.length][T[0].length];
			for(int i=0; i<T.length; i++) {
				for(int j=0; j<T[0].length; j++) {
					negateT[i][j]=(T[i][j]*-1);
				}
			}
			//Get the Log of Y
			double [][] logY = new double[Y.length][Y[0].length];
			for(int i=0; i<Y.length; i++) {
				for(int j=0; j<Y[0].length; j++) {
					logY[i][j]=Math.log(Y[i][j]);
				}
			}
			
			//Multiply the Negative of T and Log Y results above
			double [][] MultpyNegTLogY = matrixMultiplication(negateT,logY);
			
			//Sum the Content of the multidimensional array above to a one digit to get the loss
			for(int i=0; i<MultpyNegTLogY.length;i++) {
				for(int j=0; j<MultpyNegTLogY[0].length; j++) {
					loss = loss+MultpyNegTLogY[i][j];
				}
			}
			return loss;
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
		double []costs = new double[100];
		int lossIterator =0;
		
		
	for (int i =0; i<10000;i++) {
		
		 //Forward Pass		 
		double [][]A = theSigmoid(matrixVectorAddition((matrixMultiplication(X,W1)),b1)); // A = sigma(Z);
		double [][]Y = theSoftmax(matrixVectorAddition((matrixMultiplication(A,W2)),b2)); // Y = softmax(Z2)
		
		//backward pass
	    double [][] delta2 =matrixSubtraction(Y,labelHotEncodings());
	    double [][] delta1 = delta1(delta2,W2,A);
	    
	    //Updating weights and Bias
	    double [][] new_W2= updatingWeights(W2,alpha,A,delta2);
	    double [] new_b2 =updatingBias(b2,alpha,delta2);
	    double [][] new_W1 =updatingWeights(W1,alpha,X,delta1);
	    double [] new_b1 =updatingBias(b1,alpha,delta1);
	    
	   //save loss function values across training iterations at each an every 100th iteration
	    if((i % 100)==0) {
	    	double loss =calculateLoss(labelHotEncodings(),Y);	    	
	    	//Appending the loss to the costs array
	    	costs[lossIterator]=loss;	    	
	    }			
	}
	
	
	for(int i =0; i<costs.length;i++) {
		System.out.println(costs[i]);
		
	}
			

	    
		System.out.println("Processing the deeplearnign with no framework ");
	}

}
