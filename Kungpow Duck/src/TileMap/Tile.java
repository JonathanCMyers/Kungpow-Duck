package TileMap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Tile implements Serializable {
	private transient BufferedImage image;
	private int type;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1; // we only need 2 types i think maybe who knows
	
	public Tile(BufferedImage image, int type){
		this.image = image;
		this.type = type;
	}
	
	public BufferedImage getImage() { return image; }
	public int getType() { return type; }
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(image, "png", out);
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		image = ImageIO.read(in);
	}
}
