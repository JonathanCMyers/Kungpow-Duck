package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import Ability.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Snake extends Enemy implements Serializable {

	private transient ArrayList<BufferedImage[]> sprites;
	
	private final int[] numFrames = {
			3, 3, 3, 3, 1, 1, 1, 1	
		};
	
	public Snake(TileMap tm) {
		super(tm);
		moveSpeed = 1.0;
		maxSpeed = 1.0;
		width = 32;
		height = 32;	// for the tilesheet
		cwidth = 20;
		cheight = 20; 	// real width and height
		
		health = maxHealth = 2;
		
		damage = 1;
		
		WALKINGBACK = 0;
		WALKINGLEFT = 1;
		WALKINGFRONT = 2;
		WALKINGRIGHT = 3;
		IDLEBACK = 4;
		IDLELEFT = 5;
		IDLEFRONT = 6;
		IDLERIGHT = 7;
		
		currentAction = IDLEFRONT;
		
		spriteDelayTime = 90;
		flinchFrequency = 700;
		
		BasicAttack b = new BasicAttack();
		knownAbilities = new ArrayList<Ability>();
		knownAbilities.add(b);
		
		maxSpeedNormal = .9;
		maxSpeedFast = 1.6;
		
		try{
			weaknesses = new ArrayList<Integer>();
			resistances = new ArrayList<Integer>();
			health = maxHealth = 90;
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Snake2.png"));
			sprites = new ArrayList<BufferedImage[]>();
			
			for(int j = 0; j < 4; j++){		// since we have 4 walking animation actions
				BufferedImage[] bi = new BufferedImage[numFrames[j]];
				for(int k = 0; k < numFrames[j]; k++){
					bi[k] = spritesheet.getSubimage(k*width, j*height, width, height);
				}
				sprites.add(bi);
			}  
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Snake2.png"));
			for(int j = 4; j < 8; j++){		// since we have 4 idle animation actions
				BufferedImage[] bi = new BufferedImage[numFrames[j]];				
				bi[0] = spritesheet.getSubimage(30, (j-4)*height, width, height);		// DO U KNOW HOW LONG IT TOOK ME TO FIGURE OUT (J-4) IT TOOK WAY TOO LONG
				sprites.add(bi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		facingRightAnimation = new Animation();
		facingRightAnimation.setFrames(sprites.get(WALKINGRIGHT));
		facingRightAnimation.setDelay(200);
		
		deathAnimation = new Animation();
		deathAnimation.setFrames(sprites.get(IDLEBACK));
		facingRightAnimation.setDelay(-1);
		
		animation = new Animation();
		animation.setFrames(sprites.get(IDLEFRONT));
		animation.setDelay(300);
		
		right = true;
		directionFacing = FACING_RIGHT;
		
	}
	
	@Override
	public int getType() { return Enemy.SNAKE; }

	@Override
	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		// check flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > flinchFrequency){
				flinching = false;
			}
		}
		if(right){
			if(currentAction != WALKINGRIGHT){
				currentAction = WALKINGRIGHT;
				animation.setFrames(sprites.get(WALKINGRIGHT));
				animation.setDelay(spriteDelayTime);
			}
		}
		else if(left){
			if(currentAction != WALKINGLEFT){
				currentAction = WALKINGLEFT;
				animation.setFrames(sprites.get(WALKINGLEFT));
				animation.setDelay(spriteDelayTime);
			}
		}
		else if(down){
			if(currentAction != WALKINGFRONT){
				currentAction = WALKINGFRONT;
				animation.setFrames(sprites.get(WALKINGFRONT));
				animation.setDelay(spriteDelayTime);
			}
		}
		else if(up){
			if(currentAction != WALKINGBACK){
				currentAction = WALKINGBACK;
				animation.setFrames(sprites.get(WALKINGBACK));
				animation.setDelay(spriteDelayTime);
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
		else if(up && dy == 0){
			up = false;
			down = true;
		}
		else if(down && dy == 0){
			down = false;
			up = true;
		}
		animation.update();
	} 
	
	@Override
	public void draw(Graphics2D g) {
		//if(notOnScreen()) return;
		setMapPosition();
		//super.draw(g);	
		g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		out.writeInt(sprites.size()); 	// The number of frames 
		for(int j = 0; j < sprites.size(); j++){
			if(j < 4){
				for(int k = 0; k < 3; k++){
					ImageIO.write(sprites.get(j)[k], "png", out);
				}
			}
			else{
				ImageIO.write(sprites.get(j)[0], "png", out);
			}
		}
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		int x = in.readInt();
		sprites = new ArrayList<BufferedImage[]>();
		for(int j = 0; j < x; j++){
			if(j < 4){
				BufferedImage[] readInImages = new BufferedImage[3];
				for(int k = 0; k < 3; k++){
					readInImages[k] = ImageIO.read(in);
				}
				sprites.add(readInImages);
			}
			else{
				sprites.add(new BufferedImage[0]);
				sprites.get(j)[0] = ImageIO.read(in);
			}
		}
	}
}
