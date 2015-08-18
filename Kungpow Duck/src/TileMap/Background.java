package TileMap;


import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Background implements Serializable {
	private transient BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale; // scale at which the background moves (speed of scroll)
	
	public Background(String s, double ms) {
		try{	// this is how you import resource files into the game
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image, (int)x, (int)y, null);
		// and since this is scrolling bg we have to make sure its always filling screen
		// if it scrolls off the screen we have to make sure it fills another part to fill
		// up the space that its lacking
		if(x < 0){
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
			
		}
		// this is for if its scrolling to the left
		if(x > 0){
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
		// at any point int he game there will always be a maximum of 2 of these
		// at the screen at any given time
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(image, "png", out);
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		image = ImageIO.read(in);
	}
}
