/*
 * Description: 		BlackUpArrow.java
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
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

public class BlackUpArrow extends Arrow implements Serializable {
	
	public BlackUpArrow(int x, int y) {
		width = 9;
		height = 12;
		this.x = x;
		this.y = y;
		delay = 89;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Arrows/blackupdownarrow.png"));
			sprites = new BufferedImage[11];
			for(int j = 0; j < sprites.length; j++){
				sprites[j] = spritesheet.getSubimage(j*width,  12,  width,  height);
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
	public void setXY(int x, int y) { this.x = x; this.y = y; }
	public void freeze() { animation.setDelay(-1); }
	public void unfreeze() { animation.setDelay(delay); }
	
	public void draw(Graphics2D g){
		animation.update();
		g.drawImage(animation.getImage(), x, y, null);   
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		out.writeInt(sprites.length); 	// The frames of sprites 
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