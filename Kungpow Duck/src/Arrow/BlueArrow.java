package Arrow;

import Entity.Animation;
import Entity.Arrow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class BlueArrow extends Arrow implements Serializable {
	private transient BufferedImage sprites[];
	private int width;
	private int height;
	private Animation animation;
	private int x;
	private int y;
	
	public BlueArrow(int x, int y){
		width = 11;
		height = 9;
		this.x = x;
		this.y = y;
		delay = 144;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Arrows/blue arrow.png"));
			sprites = new BufferedImage[2];
			for(int j = 0; j < sprites.length; j++){
				sprites[j] = spritesheet.getSubimage(j*width,  0,  width,  height);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(delay);
	}
	
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	public int getX() { return x; }
	public int getY() { return y; }
	public void setXY(int x, int y) { this.x = x; this.y = y; }
	public void freeze() { animation.setDelay(-1); }
	public void unfreeze() { animation.setDelay(delay); }
	
	public void draw(Graphics2D g){
		animation.update();
		g.drawImage(animation.getImage(), x, y, null);   
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		out.writeInt(sprites.length); 	// The number of frames 
		for(BufferedImage eachImage : sprites) {
			ImageIO.write(eachImage, "png", out);
		}
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		int x = in.readInt();
		sprites = new BufferedImage[x];
		for(int j = 0; j < x; j++){
			sprites[j] = ImageIO.read(in);
		}
	}
}
