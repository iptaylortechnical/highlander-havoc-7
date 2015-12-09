package behaviors;

public interface Collideable {
	public boolean isCollided(double x, double y);
	
	public void destroy();
	
	public void update(double gX, double gY);
}
