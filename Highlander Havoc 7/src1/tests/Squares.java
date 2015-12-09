package tests;

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
	public Texture red, blue, yellow, image, hanson, hansontest, background, image_bg, hall;
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
	
	public void back(double[][] coords){
		background.bind();
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
