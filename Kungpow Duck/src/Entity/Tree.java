package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Tree extends MapObject implements Serializable {
	
	private int type;
	public static final int NORMALTREE = 0;
	public static final int PINETREE = 1;
	public boolean isShort;
	private Point p;
	private transient BufferedImage image;
	private transient BufferedImage[] sprites;
	
	public Tree(TileMap tm, Point p, int type, boolean isShort){
		super(tm);
		directionFacing = FACING_NONE;
		this.p = p;
		x = p.x;
		y = p.y;
		this.type = type;
		this.isShort = isShort;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Tilesets/treetop.png"));
			sprites = new BufferedImage[1];
			int xMod = 0;
			int height = 78;
			if(type == 1) height = 92;
			if(isShort) xMod++;
			sprites[0] = image.getSubimage(xMod * 100, 16*xMod + 100*type, image.getWidth() - 100, height);
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(-1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setPoint(Point p){ this.p = p; }
	public void setType(int type){ this.type = type; }
	public void setIsShort(boolean isShort) { this.isShort = isShort; }
	
	
	@Override
	public void draw(Graphics2D g){
		setMapPosition();
		g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		/*out.writeInt(sprites.length); 	// The number of sprites 
		for(BufferedImage eachImage : sprites) {
			ImageIO.write(eachImage, "png", out);
		}
		ImageIO.write(image, "png", out); */
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Tilesets/treetop.png"));
			sprites = new BufferedImage[1];
			int xMod = 0;
			int height = 78;
			if(type == 1) height = 92;
			if(isShort) xMod++;
			sprites[0] = image.getSubimage(xMod * 100, 16*xMod + 100*type, image.getWidth() - 100, height);
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(-1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
