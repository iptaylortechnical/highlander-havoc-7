package utilities;

public class Coords {

	public static double[][] C(int x, int y, int dX, int dY){
		
		double[] first = {
				(double) x,
				(double) y
		};
		
		double[] second = {
				(double) x + dX,
				(double) y
		};
		
		double[] third = {
				(double) x + dX,
				(double) y + dY
		};
		
		double[] fourth = {
				(double) x,
				(double) y + dY
		};
		
		return new double[][]{
			first,
			second,
			third,
			fourth
		};
	}
}
