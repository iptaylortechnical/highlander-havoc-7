package objects;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import behaviors.Controllable;
import behaviors.Damagable;
import behaviors.Shooter;
import utilities.MouseUtil;

public abstract class Gun implements Controllable, Shooter, Damagable {
	private double T;
	private double pyth/* = 70.71*/;
	private int side;
	private int x;
	private int y;
	private int motion = 7;
	private int damage;
	private int pointsPerHit = 150;
	private int extra = 4;
	private boolean reset = true;
	private boolean shieldEnabled = false;
	private int shieldCount = 0;
	private int shieldTotal = 150;
	
	int screenHeight;
	int screenWidth;
	
	private double[][] coords;
	
	private int bulletTick = 0;
	
	public Gun(int x, int y, int side, int screenHeight, int screenWidth){
		this.x = x;
		this.y = y;
		this.side = side;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		this.damage = totalDamage;
		
		this.pyth = Math.sqrt((Math.pow(side/2, 2))*2);
		
	}
	
	public double[][] update(){
		
		if(shieldEnabled){
			shieldCount--;
			if(shieldCount < 0){
				shieldCount = shieldTotal;
				shieldEnabled = false;
			}
		}
		
		isCollided(x, y);
		
		if(Mouse.isButtonDown(0) && (bulletTick > bulletLimit || reset)){
			shoot(x, y, T, pyth);
			bulletTick = 0;
			reset = false;
		}
		
		if(!Mouse.isButtonDown(0)){
			reset = true;
		}
		
		bulletTick++;
		
		move();
		
		coords = getSCoords();
		
		return coords;
	}
	
	private double[][] getSCoords() {
		// TODO Auto-generated method stub
		double mX = MouseUtil.getMouseX();
		double mY = MouseUtil.getMouseY();
		double dX = mX - x;
		double dY = mY - y;
		
		if(dX < 0 && dY > 0){
			T = -Math.atan(dY/dX)+(Math.PI);
		}else if(dX < 0 && dY < 0){
			T = -Math.atan(dY/dX)+(Math.PI);
		}else{
			T = -Math.atan(dY/dX);
		}
		
		
		double x_d_1 = Math.sin(T-Math.PI*3/4);
		double y_d_1 = Math.cos(T-Math.PI*3/4);
		
		double x_d_2 = Math.sin(T-Math.PI/4);
		double y_d_2 = Math.cos(T-Math.PI/4);
		
		double x_d_3 = Math.sin(T-Math.PI*7/4);
		double y_d_3 = Math.cos(T-Math.PI*7/4);
		
		double x_d_4 = Math.sin(T-Math.PI*5/4);
		double y_d_4 = Math.cos(T-Math.PI*5/4);
		
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
		
		return new double[][]{first, second, third, fourth};
	}
	
	public void pump(double p){
		damage += p;
	}
	
	public double getT(){
		return T;
	}
	
	public double getPyth(){
		return pyth;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void dock(){
		if(!shieldEnabled){
			damage -= pointsPerHit;
		}else{
			damage -= pointsPerHit/4;
		}
	}
	
	public void enableShield(){
		shieldCount += shieldTotal;
		shieldEnabled = true;
	}
	
	public void moveX(boolean forward){
		
		int tempX = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			tempX = extra;
		}	
		
		if(forward){
			if(x < screenWidth-125){
				x = x + (motion + tempX);
			}else{
				System.out.println("kek");
			}
		}else{
			if(x > 0){
				x = x - (motion + tempX);
			}
		}
		
		this.pyth = Math.sqrt((Math.pow(side/2, 2))*2);
	}
	
	public void moveY(boolean forward){
		
		int tempY = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			tempY = extra;
		}
		
		if(forward){
			if(y > 0){
				y = y - (motion + tempY);
			}
		}else{
			if(y < screenHeight-125){
				y = y + (motion + tempY);
			}
		}
		this.pyth = Math.sqrt((Math.pow(side/2, 2))*2);
	}
	
	@Override
	public double[][] health(){
		
		double[][] healthCoords = new double[4][2];
		
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
}
