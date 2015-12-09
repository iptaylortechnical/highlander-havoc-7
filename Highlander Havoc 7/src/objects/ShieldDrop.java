package objects;

import Prototypes.Drop;

public abstract class ShieldDrop extends Drop{
	
	private String texture = "yellow";

	public ShieldDrop(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getTexture(){
		return texture;
	}

}
