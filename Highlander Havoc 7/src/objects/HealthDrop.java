package objects;

import Prototypes.Drop;

public abstract class HealthDrop extends Drop{
	
	private String texture = "red";

	public HealthDrop(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getTexture() {
		// TODO Auto-generated method stub
		return texture;
	}

}
