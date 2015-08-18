package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Slugger extends Enemy {

	private BufferedImage sprites[];
	
	public Slugger(TileMap tm) {
		super(tm);
		moveSpeed = 1.8;
		maxSpeed = 1.8;
		width = 30;
		height = 30;	// for the tilesheet
		cwidth = 20;
		cheight = 20; 	// real width and height
		
		health = maxHealth = 2;
		
		damage = 1;
		
		// load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
			sprites = new BufferedImage[3];
			for(int j = 0; j < sprites.length; j++){
				sprites[j] = spritesheet.getSubimage(j*width,  0,  width,  height);
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
	/*	if(falling) {
			dy+= fallSpeed;
		} */
	
	}
	
	@Override
	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		// check flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		// if it hits a wall go other direction
		if(right && dx == 0){
			right = false;
			left = true;
			directionFacing = FACING_LEFT;
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

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
