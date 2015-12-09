package objects;

import behaviors.Projectile;


public abstract class Bullet implements Projectile{
	private double x;
	private double y;
	private double t;
	
	private int dimX = 10;
	private int dimY = 2;
	private int vel = 2;
	private int screenHeight;
	private int screenWidth;
	
	private Object origin;
	
	public Bullet(double x, double y, double t, int screenHeight, int screenWidth, Object origin){
		this.x = x;
		this.y = y;
		this.t = t;
		
		this.vel = speed;
		
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		this.origin = origin;
	}
	
	public void update(){
		x = x + vel*Math.cos(t);
		y = y + vel*Math.sin(t);
		
		if(x > screenWidth || y > screenHeight || x < 0 || y < 0){
			useless();
		}
	}
	
	public double[][] getPos(){
		return new double[][]{
				{
					x,
					y
				},
				
				{
					x + dimX,
					y
				},
				
				{
					x + dimX,
					y - dimY
				},
				
				{
					x,
					y - dimY
				}
		};
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public Object getOrigin(){
		return origin;
	}
}
