import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;

public class AccessingObjects {

	public AccessingObjects() {
		// TODO Auto-generated constructor stub
		double[] numbers = {1, 2, 3, 4};
		DoubleColumn nc = DoubleColumn.create("Test", numbers);
		System.out.println(nc.print());

	}

}
