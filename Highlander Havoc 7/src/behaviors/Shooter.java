package behaviors;

public interface Shooter {
	
	int bulletLimit = 35;
	
	public void shoot(double x, double y, double t, double pyth);
}
