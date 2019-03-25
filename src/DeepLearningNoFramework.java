
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

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		System.out.println("Processing the deeplearnign with no framework ");
	}

}
