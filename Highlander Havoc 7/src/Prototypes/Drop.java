package Prototypes;

import java.util.Random;

import behaviors.Collideable;

public abstract class Drop implements Collideable{
	
	private int dX = 10;
	private int dY = 10;

	private int x;
	private int y;
	
	private int health;
	
	public abstract String getTexture();
	
	public int getHealth() {
		return health;
	}

	public Drop(int x, int y){
		this.x = x;
		this.y = y;
		
		Random r = new Random();
		
		this.health = 100 * (r.nextInt(2)+1);
	}
	
	@Override
	public void update(double gX, double gY) {
		// TODO Auto-generated method stub
		if(isCollided(gX, gY)){
			destroy();
		}
	}

	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public int getdX() {
		// TODO Auto-generated method stub
		return dX;
	}

	public int getdY() {
		// TODO Auto-generated method stub
		return dY;
	}
	
}
