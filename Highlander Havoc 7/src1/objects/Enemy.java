package objects;


import behaviors.Autonomous;
import behaviors.Damagable;
import behaviors.Shooter;

public abstract class Enemy implements Damagable, Shooter, Autonomous {

	private int x;
	private int y;
	private int bulletTick = 0;
	private int damage;
	private double theta;
	private double pyth;
	final private int side = 100;
	final private int oscillation = 100;
	final private int[] osBank = new int[]{1, 2, 3, 0};
	private int osMode = 0;
	private int osLen = 0;
	private int vX = 0;
	private int vY = 0;
	private int v = 3;
	int screenHeight;
	int screenWidth;
	
	private double[][] coords;
	
	public Enemy(int x, int y, double playerX, double playerY, int screenHeight, int screenWidth){
		this.damage = totalDamage;
		this.x = x;
		this.y = y;
		this.pyth = Math.sqrt((Math.pow(side/2, 2))*2);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}
	
	public double[][] update(double d, double e){
		isCollided(x, y);
		move();
		
		double dX = x - d;
		double dY = y - e;
		
		theta = getTheta(d, e, dX, dY);
		
		if(bulletTick > bulletLimit){
			shoot(x, y, theta+Math.PI, pyth);
			bulletTick = 0;
		}
		
		bulletTick++;
		
		double x_d_1 = Math.sin(theta-Math.PI*3/4);
		double y_d_1 = Math.cos(theta-Math.PI*3/4);
		
		double x_d_2 = Math.sin(theta-Math.PI/4);
		double y_d_2 = Math.cos(theta-Math.PI/4);
		
		double x_d_3 = Math.sin(theta-Math.PI*7/4);
		double y_d_3 = Math.cos(theta-Math.PI*7/4);
		
		double x_d_4 = Math.sin(theta-Math.PI*5/4);
		double y_d_4 = Math.cos(theta-Math.PI*5/4);
		
		double[] first = {
				x+(pyth*x_d_1),
				y+(pyth*y_d_1)
		};
		
		double[] second = {
				x+(pyth*x_d_2),
				y+(pyth*y_d_2)
		};
		
		double[] third = {
				x+(pyth*x_d_3),
				y+(pyth*y_d_3)
		};
		
		double[] fourth = {
				x+(pyth*x_d_4),
				y+(pyth*y_d_4)
		};
		
		coords = new double[][]{
				first, 
				second, 
				third, 
				fourth
			};
		
		return coords;
		
	}
	
	private double getTheta(double playerX, double playerY, double dX, double dY) {
		double T;
		
		if(dX < 0 && dY > 0){
			T = -Math.atan(dY/dX)+(Math.PI);
		}else if(dX < 0 && dY < 0){
			T = -Math.atan(dY/dX)+(Math.PI);
		}else{
			T = -Math.atan(dY/dX);
		}
		
		return T;
	}
	
	public void dock(){
		damage -= 100;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	@Override
	public double[][] health(){
		
		double[][] healthCoords = coords;
		
		healthCoords[0][1] = y + (pyth);
		healthCoords[1][1] = y + (pyth);
		healthCoords[2][1] = y + (pyth+healthHeight);
		healthCoords[3][1] = y + (pyth+healthHeight);
		
		healthCoords[0][0] = x - (Math.round(pyth/healthWidthFactor));
		healthCoords[1][0] = x - (Math.round((pyth/healthWidthFactor)) - Math.round((pyth/healthWidthFactor)*(damage/500.0)));
		healthCoords[2][0] = x - (Math.round((pyth/healthWidthFactor)) - Math.round((pyth/healthWidthFactor)*(damage/500.0)));
		healthCoords[3][0] = x - (Math.round(pyth/healthWidthFactor));
		
		return healthCoords;
	}
	
	@Override
	public void move(){
		
		
		//AI
		x = Math.round((float)(x + v*Math.cos(theta+Math.PI)));
		y = Math.round((float)(y - v*Math.sin(theta+Math.PI)));
		
		//SQUARE AUTONOMOUS SYSTEM
//		if(osLen > oscillation){
//			
//			osMode = osBank[osMode];
//			
//			switch(osMode){
//				case 1:
//					vX = v;
//					vY = 0;
//					break;
//				case 2:
//					vX = 0;
//					vY = -v;
//					break;
//				case 3:
//					vX = -v;
//					vY = 0;
//					break;
//				case 0:
//					vX = 0;
//					vY = v;
//					break;
//			}
//			osLen = 0;
//		}else{
//			osLen++;
//		}
//		
//		x+=vX;
//		y+=vY;
//		
//		if(x < 0){
//			x = 0;
//		}
//		
//		if(x > screenWidth-75){
//			x = screenWidth-75;
//		}
//		
//		if(y < 0){
//			y = 0;
//		}
//		
//		if(y > screenHeight-75){
//			y = screenHeight-75;
//		}
	}
}
