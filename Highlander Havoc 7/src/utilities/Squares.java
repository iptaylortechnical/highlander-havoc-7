package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class Squares {

	static Random r = new Random();
	public HashMap<String, Texture> loadedTextures = new HashMap<>();
	
	public Texture red, blue, yellow, image, hanson, hansontest, background, image_bg, hall, back1, numbers, splash;
	private int screenHeight;
	private int screenWidth;
	
	public Texture loadTexture(String par){
		try {
			return TextureLoader.getTexture("PNG", new FileInputStream(new File("res/" + par + ".png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Squares(int screenHeight, int screenWidth){
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
	}
	
	public void versatile(String ident, double[][] coords){
		loadedTextures.get(ident).bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((float)coords[0][0],(float)coords[0][1]);
			glTexCoord2f(1, 0);
			glVertex2f((float)coords[1][0],(float)coords[1][1]);
			glTexCoord2f(1, 1);
			glVertex2f((float)coords[2][0],(float)coords[2][1]);
			glTexCoord2f(0, 1);
			glVertex2f((float)coords[3][0],(float)coords[3][1]);
		glEnd();
	}
	
	public void health(double[][] coords){
		red.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((float)coords[0][0],(float)coords[0][1]);
			glTexCoord2f(1, 0);
			glVertex2f((float)coords[1][0],(float)coords[1][1]);
			glTexCoord2f(1, 1);
			glVertex2f((float)coords[2][0],(float)coords[2][1]);
			glTexCoord2f(0, 1);
			glVertex2f((float)coords[3][0],(float)coords[3][1]);
		glEnd();
	}

	
	public void small(double[][] coords){
		hansontest.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((float)coords[0][0],(float)coords[0][1]);
			glTexCoord2f(1, 0);
			glVertex2f((float)coords[1][0],(float)coords[1][1]);
			glTexCoord2f(1, 1);
			glVertex2f((float)coords[2][0],(float)coords[2][1]);
			glTexCoord2f(0, 1);
			glVertex2f((float)coords[3][0],(float)coords[3][1]);
		glEnd();
	}
	
	public void back(int phase){
		
		double p = .333333333333333;
		
		float start = (float) ((float)(phase*p) - p);
		float end = (float)(phase*p);
		
//		float start = 0f;
//		float end = .33f;
		
		back1.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(start, 0);
			glVertex2f(0,0);
			glTexCoord2f(end, 0);
			glVertex2f(screenWidth,0);
			glTexCoord2f(end, 1);
			glVertex2f(screenWidth,screenHeight);
			glTexCoord2f(start, 1);
			glVertex2f(0,screenHeight);
		glEnd();
	}
	
	public void score(int s){
		
		int sig[] = new int[2];
		
		if(s < 10){
			sig[0] = 0;
			sig[1] = s;
		}else{
			String lel = s + "";
			
			sig[0] = Integer.parseInt(lel.substring(0,1));
			sig[1] = Integer.parseInt(lel.substring(1));
		}
		
		float tempXA = (float) (sig[0]*0.2);
		float tempXB = (float) (sig[1]*0.2);
		
		float tempYA = 0;
		float tempYB = 0;
		
		if(sig[0] < 5){
			tempYA = 0;
		}else{
			tempYA = .5f;
		}
		
		if(sig[1] < 5){
			tempYB = 0;
		}else{
			tempYB = .5f;
		}
		
		float[] startA = {
			screenWidth - 250,
			screenHeight - 175
		};
		
		float[] startB = {
			screenWidth - 230,
			screenHeight - 175
		};
		
		numbers.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(tempXA,tempYA);
			glVertex2f(startA[0],startA[1]);
			glTexCoord2f(tempXA + .2f, tempYA);
			glVertex2f(startA[0] + 25,startA[1]);
			glTexCoord2f(tempXA + .2f, tempYA + .5f);
			glVertex2f(startA[0] + 25,startA[1] + 60);
			glTexCoord2f(tempXA, tempYA + .5f);
			glVertex2f(startA[0],startA[1] + 60);
		glEnd();
		
		glBegin(GL_QUADS);
			glTexCoord2f(tempXB,tempYB);
			glVertex2f(startB[0],startB[1]);
			glTexCoord2f(tempXB + .2f, tempYB);
			glVertex2f(startB[0] + 25,startB[1]);
			glTexCoord2f(tempXB + .2f, tempYB + .5f);
			glVertex2f(startB[0] + 25,startB[1] + 60);
			glTexCoord2f(tempXB, tempYB + .5f);
			glVertex2f(startB[0],startB[1] + 60);
		glEnd();
	}
	
	public void bullet(double[][] coords){
		red.bind();
		glBegin(GL_QUADS);
			glTexCoord2f(0,0);
			glVertex2f((float)coords[0][0],(float)coords[0][1]);
			glTexCoord2f(1, 0);
			glVertex2f((float)coords[1][0],(float)coords[1][1]);
			glTexCoord2f(1, 1);
			glVertex2f((float)coords[2][0],(float)coords[2][1]);
			glTexCoord2f(0, 1);
			glVertex2f((float)coords[3][0],(float)coords[3][1]);
		glEnd();
	}
	
	public void medium(){
		glBegin(GL_QUADS);
			glVertex2f(300,300);
			glVertex2f(300,400);
			glVertex2f(400,400);
			glVertex2f(400,300);
		glEnd();
	}
	
	public void large(){
		glBegin(GL_QUADS);
			glVertex2f(300,300);
			glVertex2f(300,400);
			glVertex2f(400,400);
			glVertex2f(400,300);
		glEnd();	
	}
	
}
