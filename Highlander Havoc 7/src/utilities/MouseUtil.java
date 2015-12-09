package utilities;

import org.lwjgl.input.Mouse;

public class MouseUtil {
	public static int getMouseX(){
		return Mouse.getX();
	}
	
	public static int getMouseY(){
		return 770 - Mouse.getY();
	}
}
