package engines;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Prototypes.Drop;
import objects.Bullet;
import objects.Enemy;
import objects.Gun;
import objects.HealthDrop;
import objects.ShieldDrop;
import utilities.Coords;
import utilities.Squares;

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
	
	static int phase = 1;
	static int phaseCounter = 0;
	static int phaseLimit = 10;
	
	static int totalScore = 0;
	
	static Random r = new Random();
	
	//this is the random generator for
	//how often a shield spawns.
	
	static HashMap<String,Object> properties = new HashMap<>();
	
	public static void properties(){
		
		//this is the random generator for
		//how often a shield spawn
		// [0] / [1] chances
		properties.put("shieldRand", new int[]{
				18, 20
		});
		
	}
	
	public static void gameLoop(){
		
		int ticks = 0;
		
		while(!Display.isCloseRequested()){
			
			glClearColor(.3f, .3f,.3f,.8f);
			glClear(GL_COLOR_BUFFER_BIT);
			
			s.back(0);
			s.score(totalScore);
			
			if(phaseCounter < phaseLimit){
				s.back(phase);
				phaseCounter++;
			}else{
				if(phase < 3){
					phase++;
				}else{
					phase = 1;
				}
				phaseCounter = 0;
			}
			
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
				s.versatile(d.getTexture(), Coords.C(d.getX(), d.getY(), d.getdX(), d.getdY()));
				d.update(gun.getX(), gun.getY());
			}
			
			s.versatile("hansontest", coords);
			s.versatile("red", gunHealth);
			
			Display.update();
			Display.sync(60);
			
			ticks++;
			
			if(!isPlaying){
				endGame();
				try {
					Thread.sleep(700);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	public static void quit(){
		Display.destroy();
		System.exit(0);
	}

	public static void main(String[] args) {
		properties();
		createDisplay();
		initGL();
		splash();
		initStuff();
		gameLoop();
		quit();
	}
	
	public static void endGame(){
		s.splash = s.loadTexture("splash");
		s.loadedTextures.put("splash", s.splash);
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		s.versatile("splash", new double[][]{
			new double[]{
					0,0
			},
			new double[]{
					screenWidth-150,
					0
			},
			new double[]{
					screenWidth-150,
					screenHeight-100
			},
			new double[]{
					0,
					screenHeight-100
			}
		});
		
		s.score(totalScore);
		
		Display.update();
	}
	
	public static void splash() {
		// TODO Auto-generated method stub
		s.splash = s.loadTexture("splash");
		s.loadedTextures.put("splash", s.splash);
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		s.versatile("splash", new double[][]{
			new double[]{
					0,0
			},
			new double[]{
					screenWidth-150,
					0
			},
			new double[]{
					screenWidth-150,
					screenHeight-100
			},
			new double[]{
					0,
					screenHeight-100
			}
		});
		
		Display.update();
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
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
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
		
		s.hansontest = s.loadTexture("hanson");
		s.loadedTextures.put("hansontest", s.hansontest);
		
		s.background = s.loadTexture("background");
		s.loadedTextures.put("background", s.background);
		
		s.image_bg = s.loadTexture("image_bg");
		s.loadedTextures.put("image_bg", s.image_bg);
		
		s.hall = s.loadTexture("hall");
		s.loadedTextures.put("hall", s.hall);
		
		s.back1 = s.loadTexture("backfinal");
		s.loadedTextures.put("back1", s.back1);
		
		s.numbers = s.loadTexture("numbers");
		s.loadedTextures.put("numbers", s.numbers);
		
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
		
		for(int i = 0; i < num; i++){
			System.out.print("test from engine");
			
			int startX = 100 + r.nextInt(screenWidth-100);
			int startY = 100 + r.nextInt(screenHeight-100);
			
			toAddEnemies.add(new Enemy(startX, startY, gun.getX(), gun.getY(), screenHeight, screenWidth){
	
				@Override
				public void destroyed() {
					// TODO Auto-generated method stub
					totalScore++;
					generateEnemies((new Random()).nextInt(2)+1);
					toRemoveEnemies.add(this);
					this.dock();
					if(r.nextInt(((int[])properties.get("shieldRand"))[1]) < ((int[])properties.get("shieldRand"))[0]){
						drops.add(new HealthDrop(this.getX(), this.getY()){
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
					}else{
						drops.add(new ShieldDrop(this.getX(), this.getY()){
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
								gun.enableShield();
							}
		
						});
					}
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
					
					if(b.getOrigin() == gun){
						this.dock();
						toRemoveBullets.add(b);
					}
					
					int dmg = this.getDamage();
					if(dmg < 1 ){
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
