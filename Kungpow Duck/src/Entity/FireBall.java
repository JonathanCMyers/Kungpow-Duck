package Entity;

import TileMap.TileMap;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class FireBall extends MapObject {
	
	private boolean hit;	// the fireball has hit something
	private boolean remove;	// whether or not we should remove the fireball from the game
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	public FireBall(TileMap tm, boolean right){	// tells us which direction fireball is going
		
		super(tm);
		
		directionFacing = FACING_RIGHT;
		//facingRight = right;
		
		moveSpeed = 3.8; // good speed
		if(right){
			dx = moveSpeed;
		}
		else{
			dx = -moveSpeed; // going other direction
		}
		
		width = 30;
		height = 30;
		cwidth = 14;
		cheight = 14;
		
		// load sprites
		
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
			sprites = new BufferedImage[4];
			for(int j = 0; j < sprites.length; j++){
				sprites[j] = spritesheet.getSubimage(j * width,  0,  width,  height);
			}
			
			hitSprites = new BufferedImage[3];
			for(int j = 0; j < hitSprites.length; j++){
				hitSprites[j] = spritesheet.getSubimage(j * width, height, width, height);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void setHit() {	// figures whether or not it has hit something
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit){
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()){
			remove = true; // we should take it out of the game
		}
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		setMapPosition();	// just like with player (important to always do at the start)
		
		super.draw(g);
		
	}
	
	
}
