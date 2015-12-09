package objects;

import java.util.ArrayList;

public class Obstacle {
	
	private double[][] coords;
	private ArrayList<Integer[]> bounds = new ArrayList<>();
	
	private int interval = 5;
	
	public Obstacle(int x, int y, int height, int width, int t){
		double theta = Math.toRadians(t);
		
		double[] first = {
				x,
				y
		};
		
		double s_x = x + (width*Math.cos(theta));
		double s_y = y + (width*Math.sin(theta));
		
		double[] second = {
				s_x,
				s_y
		};
		
		double[] third = {
			(s_x) + height*Math.cos(theta + (Math.PI/2)),
			(s_y) + height*Math.sin(theta + (Math.PI/2))
		};
		
		double[] fourth = {
			x + height*Math.cos(theta + (Math.PI/2)),
			y + height*Math.sin(theta + (Math.PI/2))
		};
		
		coords = new double[][]{
				first,
				second,
				third,
				fourth
		};
		
		generate(first, second);
		generate(second, third);
		generate(third, fourth);
		generate(fourth, first);
	}
	
	public void generate(double[] start, double[] end){
		
		double difX = end[0] - start[0];
		double difY = end[1] - start[1];
		
		double hype = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
		double slots = hype/(interval*1.0);
		double over = difX/difY;
		
		double theta;
		
		if(difY == 0){
			theta = 0;
		}else{
			theta = Math.atan(over);
		}
		
		
		for(int i = 0; i < slots; i++){
			double loc = i * interval;
			
			bounds.add(new Integer[]{
					(int)Math.round(start[0]) + (int)Math.round(loc*Math.cos(theta)),
					(int)Math.round(start[1]) + (int)Math.round(loc*Math.sin(theta))
			});
			
		}
	}
	
	public ArrayList<Integer[]> getBounds(){
		return bounds;
	}
	
	public double[][] getCoords(){
		return coords;
	}
	
}
