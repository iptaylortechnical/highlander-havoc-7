package engines;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import objects.Bullet;
import objects.Drop;
import objects.Enemy;
import objects.Gun;
import tests.Squares;
import utilities.Coords;

public class engine {
	
	static Squares s;
	static ArrayList<Bullet> bullets = new ArrayList<>();
	static ArrayList<Bullet> toRemoveBullets = new ArrayList<>();
	static ArrayList<Enemy> enemies = new ArrayList<>();
	static ArrayList<Enemy> toRemoveEnemies = new ArrayList<>();
	static ArrayList<Enemy> toAddEnemies = new ArrayList<>();
	static ArrayList<Enemy> toRemoveAddEnemies = new ArrayList<>();
	static ArrayList<Drop> drops = new ArrayList<>();
	static ArrayList<Drop> toRemoveDrops = new ArrayList<>();
	
	static boolean isPlaying;
	static Gun gun;
	static private int screenWidth;
	static private int screenHeight;
	
	static Random r = new Random();
	
	public static void gameLoop(){
		
		int ticks = 0;
		
		while(!Display.isCloseRequested()){
			
			glClear(GL_COLOR_BUFFER_BIT);
			
			double[][] coords = gun.update();
			double[][] gunHealth = gun.health();
			
			for(Bullet r:toRemoveBullets){
				bullets.remove(r);
			}
			
			toRemoveBullets.clear();
			
			for(Enemy e:toRemoveEnemies){
				enemies.remove(e);
			}
			
			toRemoveEnemies.clear();
			
			for(Drop d:toRemoveDrops){
				drops.remove(d);
			}
			
			toRemoveDrops.clear();
			
			for(Enemy tAE:toAddEnemies){
				enemies.add(tAE);
				toRemoveAddEnemies.add(tAE);
			}
			
			toAddEnemies.clear();
			
			if(isPlaying){
				for(Bullet b:bullets){
					b.update();
					s.bullet(b.getPos());
				}
			}
			
			for(Enemy e:enemies){
				double[][] encoord = e.update(gun.getX(), gun.getY());
				s.versatile("hall", encoord);
				s.versatile("red", e.health());
			}
			
			for(Drop d:drops){
				s.versatile("red", Coords.C(d.getX(), d.getY(), d.getdX(), d.getdY()));
				d.update(gun.getX(), gun.getY());
			}
			
			s.versatile("hansontest", coords);
			s.versatile("red", gunHealth);
			
			Display.update();
			Display.sync(60);
			
			ticks++;
		}
	}

	public static void quit(){
		Display.destroy();
		System.exit(0);
	}

	public static void main(String[] args) {
		createDisplay();
		initGL();
		initStuff();
		gameLoop();
		quit();
	}
	
	public static void createDisplay(){
		try {
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			screenWidth = (int)Math.round(screen.getWidth());
			screenHeight = (int)Math.round(screen.getHeight());
			s =  new Squares(screenHeight, screenWidth);
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.setTitle("game proto1");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isPlaying = true;
	}
	
	public static void initGL(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,1280,720,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		
	}
	
	public static void initStuff(){
		s.red = s.loadTexture("red");
		s.loadedTextures.put("red", s.red);
		
		s.blue = s.loadTexture("blue");
		s.loadedTextures.put("blue", s.blue);
		
		s.yellow = s.loadTexture("yellow");
		s.loadedTextures.put("yellow", s.yellow);
		
		s.image = s.loadTexture("image");
		s.loadedTextures.put("image", s.image);
		
		s.hanson = s.loadTexture("hanson");
		s.loadedTextures.put("hanson", s.hanson);
		
		s.hansontest = s.loadTexture("hanson1");
		s.loadedTextures.put("hansontest", s.hansontest);
		
		s.background = s.loadTexture("background");
		s.loadedTextures.put("background", s.background);
		
		s.image_bg = s.loadTexture("image_bg");
		s.loadedTextures.put("image_bg", s.image_bg);
		
		s.hall = s.loadTexture("hall");
		s.loadedTextures.put("hall", s.hall);
		
		gun  = new Gun(0, 0, 100, screenHeight, screenWidth){

			@Override
			public void move() {
				// TODO Auto-generated method stub
				if(Keyboard.isKeyDown(Keyboard.KEY_W)){
					this.moveY(true);
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_S)){
					this.moveY(false);
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_A)){
					this.moveX(false);
				}
				
				if(Keyboard.isKeyDown(Keyboard.KEY_D)){
					this.moveX(true);
				}
			}

			@Override
			public void shoot(double x, double y, double t, double pyth) {
				// TODO Auto-generated method stub
				
				bullets.add(new Bullet(x+(pyth*Math.cos(-t)), y+(pyth*Math.sin(-t)), -t, screenHeight, screenWidth, this){

					@Override
					public void useless() {
						// TODO Auto-generated method stub
						toRemoveBullets.add(this);
					}

					@Override
					public void hit() {
						// TODO Auto-generated method stub
						useless();
					}
					
				});
				
			}

			@Override
			public void destroyed() {
				// TODO Auto-generated method stub
				isPlaying = false;
				for(Bullet b:bullets){
					toRemoveBullets.add(b);
				}
			}

			@Override
			public void isCollided(double x, double y) {
				// TODO Auto-generated method stub
				if(isPlaying){
					for(Bullet b:bullets){
						if(b.getOrigin() != this){
							if(Math.abs((b.getX()-x)) < 50 && Math.abs((b.getY()-y)) < 50){
								hit(b);
							}
						}
					}
				}
			}

			@Override
			public void hit(Bullet b) {
				// TODO Auto-generated method stub
				
				toRemoveBullets.add(b);
				this.dock();
				
				int dmg = this.getDamage();
				if(dmg < 1){
					destroyed();
				}
			}
			
		};
		
		generateEnemies(2);
		
	}
	
	public static void generateEnemies(int num){
		
		for(int i = 0; i < 1; i++){
			System.out.print("test from engine");
			
			int startX = 100 + r.nextInt(screenWidth-100);
			int startY = 100 + r.nextInt(screenHeight-100);
			
			toAddEnemies.add(new Enemy(startX, startY, gun.getX(), gun.getY(), screenHeight, screenWidth){
	
				@Override
				public void destroyed() {
					// TODO Auto-generated method stub
					generateEnemies(r.nextInt(3));
					toRemoveEnemies.add(this);
					System.out.println("gone");
					this.dock();
					drops.add(new Drop(this.getX(), this.getY()){
						@Override
						public boolean isCollided(double x, double y){
							if(Math.abs(x - this.getX()) < 50 && Math.abs(y - this.getY()) < 50){
								return true;
							}else{
								return false;
							}
						}
	
						@Override
						public void destroy() {
							// TODO Auto-generated method stub
							toRemoveDrops.add(this);
							gun.pump(this.getHealth());
						}
	
					});
				}
	
				@Override
				public void isCollided(double x, double y) {
					// TODO Auto-generated method stub
					for(Bullet b:bullets){
						if(b.getOrigin() != this){
							if(Math.abs((b.getX()-x)) < 50 && Math.abs((b.getY()-y)) < 50){
								hit(b);
							}
						}
					}
				}
	
				@Override
				public void hit(Bullet b) {
					// TODO Auto-generated method stub
					
					toRemoveBullets.add(b);
					this.dock();
					
					System.out.println(bullets.size());
					
					int dmg = this.getDamage();
					if(dmg < 1){
						destroyed();
					}
				}
	
				@Override
				public void shoot(double x, double y, double t, double pyth) {
					// TODO Auto-generated method stub
					if(isPlaying){
						bullets.add(new Bullet(x+(pyth*Math.cos(-t)), y+(pyth*Math.sin(-t)), -t, screenHeight, screenWidth, this){
		
							@Override
							public void useless() {
								// TODO Auto-generated method stub
								toRemoveBullets.add(this);
							}
		
							@Override
							public void hit() {
								// TODO Auto-generated method stub
								useless();
							}
							
						});
					}
				}
				
			});
		}
	}

}
