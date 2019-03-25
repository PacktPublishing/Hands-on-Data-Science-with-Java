
import java.lang.Math.*;

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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		# generate three Gaussian clouds each holding 500 points
//		X1 = np.random.randn(500, 2) + np.array([0, -2])
//		X2 = np.random.randn(500, 2) + np.array([2, 2])
//		X3 = np.random.randn(500, 2) + np.array([-2, 2])
	
		System.out.println("Processing the deeplearnign with no framework ");
	}

}
