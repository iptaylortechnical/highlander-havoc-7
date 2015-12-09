package behaviors;

public interface Shooter {
	
	int bulletLimit = 5;
	
	public void shoot(double x, double y, double t, double pyth);
}
