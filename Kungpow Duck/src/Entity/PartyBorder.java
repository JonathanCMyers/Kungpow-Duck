package Entity;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class PartyBorder implements Serializable {
	private transient BufferedImage sprites[];
	private int width;
	private int height;
	private Animation animation;
	private int x;
	private int y;
	private int scale = 63;
	private int delay;
	
	public PartyBorder(int num){
		delay = 89;
		x = 1 + ((num)*scale);
		y = GamePanel.HEIGHT - 91;
		try{
			sprites = new BufferedImage[2];
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Borders/PartyBorder3a.png"));
			sprites[0] = spritesheet;
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Borders/PartyBorder3b.png"));
			sprites[1] = spritesheet;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(delay);
	}
	
	public void setPosition(int num){
		x = 1  + ((num)*scale);
	}
	
	public void setDelay(int delay) {animation.setDelay(delay);}
	public void freeze() { animation.setDelay(-1); }
	public void unfreeze() { animation.setDelay(delay); }
	
	public void draw(Graphics2D g){
		animation.update();  
		g.drawImage(animation.getImage(), x, y, null);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		out.writeInt(sprites.length); 	// The number of sprites
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
