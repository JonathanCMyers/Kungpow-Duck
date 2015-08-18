package Entity.NPCs;

import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SpiderNPC extends NPC implements Serializable {
	private transient BufferedImage sprites[];
	
	//private boolean chatting;
	
	public SpiderNPC(TileMap tm){
		super(tm);
		moveSpeed = .3;
		maxSpeed = 1.8;
		width = 30;
		height = 30;	// for the tilesheet
		cwidth = 20;
		cheight = 20;	// real width and height
		
		chatting = false;
		
		/*
		chatboxCount = 3; 
		currentChatbox = 0;
		chatboxStrings = new String[3];
		hasSecondLine = new boolean[3];
		chatboxStringsSecondLine = new String[3];
		chatboxStrings[0] = "Get outta here dragon!";
		chatboxStringsSecondLine[0] = "Go!!!";
		chatboxStrings[1] = "What are you still doing here?";
		chatboxStrings[2] = "If you don't get out of here soon... ";
		chatboxStringsSecondLine[2] = "You'll be sorry.";
		hasSecondLine[0] = true;
		hasSecondLine[1] = false;
		hasSecondLine[2] = true;
		*/
		
		chatboxCount = 1;
		currentChatbox = 0;
		chatboxStrings = new String[1];
		hasSecondLine = new boolean[1];
		chatboxStrings[0] = "Why can you walk through me";
		hasSecondLine[0] = false;
		
		
		
		
		// load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/arachnik.gif"));
			sprites = new BufferedImage[1];
			for(int j = 0; j < sprites.length; j++){
				sprites[j] = spritesheet.getSubimage(j*width, 0, width, height);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		directionFacing = FACING_RIGHT;
		
	}
	
	@Override
	public void getNextPosition() {
		boolean movedOnce = false;
		if(left){
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
			movedOnce = true;
		}
		if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
			movedOnce = true;
		}
	}
	
	@Override
	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		// if it hits a wall go other direction
		if(right && dx == 0){
			directionFacing = FACING_LEFT;
			right = false;
			left = true;
		}
		else if(left && dx == 0){
			right = true;
			left = false;
			directionFacing = FACING_RIGHT;
		}
		animation.update();
	}
	
	@Override
	public void draw(Graphics2D g) {
		//if(notOnScreen()) return;
		setMapPosition();
		super.draw(g);			
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
