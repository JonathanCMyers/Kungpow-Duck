package Entity;

import Arrow.BlueArrow;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Chatbox implements Serializable {
	protected transient BufferedImage image;
	private Font font;
	
	private boolean secondLine;
	
	// inherited things:
	protected boolean hasArrows;
	protected int arrowPosition;
	protected BlueArrow ba;
	protected int arrowY[];
	protected int maxPositions;
	protected int x;
	protected int y;
	
	public static final int NORMAL = 1;
	public static final int BOLD = 2;
	public static final int ITALIC = 3;
	
	// GOOD VALUES FOR X AND Y:
	// X: 15
	// Y: GamePanel.HEIGHT-21
	// These are for small chatbox idk i bet it would work the same for bigger chatbox too
	// So I suppose we can set these for the default chatbox values for the time being.
	
	private String s;
	private String t;
	
	public Chatbox(){
		
	}
	
	public Chatbox(String s){
		font = new Font("Arial", Font.PLAIN, 11);
		x = 12;
		y = GamePanel.HEIGHT - 33;
		this.s = s;
		secondLine = false;
		
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/normalChatbox89.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Chatbox(String s, String t){
		font = new Font("Arial", Font.PLAIN, 11);
		x = 13;
		y = GamePanel.HEIGHT - 34;
		this.s = s;
		this.t = t;
		secondLine = true;
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/normalChatbox89.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Chatbox(String s, int state, int x, int y){
		if(state == NORMAL){
			font = new Font("Arial", Font.PLAIN, 12);
		}
		else if(state == BOLD){
			font = new Font("Arial", Font.BOLD, 12);
		}
		else if(state == ITALIC){
			font = new Font("Arial", Font.ITALIC, 12);
		}
		this.s = s;
		this.x = x;
		this.y = y;
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/normalChatbox34.png"));
			//font = new Font("Arial", Font.PLAIN, 13);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void goUp() {
		// blank so you can override to your heart's content
	}
	public void goDown() {
		// blank so you can override to your heart's content
	}
	public void goLeft() {
		// blank so you can override to your heart's content
	}
	public void goRight() {
		// blank so you can override to your heart's content
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image, 0, GamePanel.HEIGHT - 50, null);   
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(s, x, y);
		if(secondLine){
			g.drawString(t, x, y+14);
		}
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
