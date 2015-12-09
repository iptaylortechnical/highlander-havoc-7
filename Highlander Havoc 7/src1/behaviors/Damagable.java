package behaviors;

import objects.Bullet;

public interface Damagable{
	
	int totalDamage = 1000;
	int healthWidthFactor = 1;
	int healthHeight = 10;
	
	public void hit(Bullet b);
	
	public void destroyed();
	
	public double[][] health();
	
	public void isCollided(double x, double y);
	
}
