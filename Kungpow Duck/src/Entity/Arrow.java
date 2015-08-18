package Entity;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Arrow implements Serializable {
	protected transient BufferedImage[] sprites;
	protected int width;
	protected int height;
	protected Animation animation;
	protected int x;
	protected int y;
	protected int delay;
	
	public Arrow() {
		sprites = new BufferedImage[1];
	}
}
