package utilities;

public class ArrayConcat {

	public static double[] doub(double[] first, double[] second){
		
		double[] array1and2 = new double[first.length + second.length];
		System.arraycopy(first, 0, array1and2, 0, first.length);
		System.arraycopy(second, 0, array1and2, first.length, second.length);
		
		return array1and2;
	}

	public static int[] integer(int[] first, int[] second){
		
		int[] array1and2 = new int[first.length + second.length];
		System.arraycopy(first, 0, array1and2, 0, first.length);
		System.arraycopy(second, 0, array1and2, first.length, second.length);
		
		return array1and2;
	}
	
}
