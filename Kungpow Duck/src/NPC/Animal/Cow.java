/*
 * Description: 		Cow using a temporary sprite
 * Date Created: 		Jun 17, 2015
 * Date Last Modified: 	Jun 17, 2015
 * Modification Notes:	
 */
package NPC.Animal;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.NPC;
import Entity.Enemies.Snake;

public class Cow extends NPC implements Serializable, AnimalInterface {
	private transient ArrayList<BufferedImage[]> sprites;
	
	private final int[] numFrames = {
			3, 3, 3, 3, 1, 1, 1, 1	
		};
	
	public Cow(TileMap tm) {
		super(tm);
		
		width = height = 32;
		cwidth = cheight = 20;
		
		damage = 0;
		
		moveSpeed = .1;
		maxSpeed = .3;
		
		health = maxHealth = 1;
		dead = false;
		damage = 0;
		chatting = false;
		chatboxCount = currentChatbox = 0;
		chatboxStrings = new String[0];
		chatboxStringsSecondLine = new String[0];
		hasSecondLine = new boolean[0];
		
		WALKINGBACK = 0;
		WALKINGLEFT = 1;
		WALKINGFRONT = 3;
		WALKINGRIGHT = 2;
		IDLEBACK = 4;
		IDLELEFT = 5;
		IDLEFRONT = 7;
		IDLERIGHT = 6;
		
		currentAction = IDLEFRONT;
		
		maxSpeedNormal = .3;
		maxSpeedFast = .7;
		
		spriteDelayTime = 144;
		flinchFrequency = 400;
		
		moveSpeedWait = false;
		waitLowerBound = 1.0;
		waitUpperBound = 4.0;
		
		// Later, change this to a beta or a normal, or something
		startOfNoMove = System.currentTimeMillis();
		Random r = new Random(startOfNoMove);
		Double d = (r.nextDouble() % waitUpperBound + waitLowerBound)*1000;
		milliseconds = d.intValue();
		
		System.out.println("CurrentTimeMillis: " + startOfNoMove);
		System.out.println("Millis: " + milliseconds);
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Animals/Animal.png"));
			sprites = new ArrayList<BufferedImage[]>();
			
			for(int j = 0; j < 4; j++){	
				BufferedImage[] bi = new BufferedImage[numFrames[j]];
				for(int k = 0; k < numFrames[j]; k++){
					bi[k] = spritesheet.getSubimage(k*width, j*height+128, width, height);
				}
				sprites.add(bi);
			}  
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Animals/Animal.png"));
			for(int j = 4; j < 8; j++){
				BufferedImage[] bi = new BufferedImage[numFrames[j]];				
				bi[0] = spritesheet.getSubimage(30, (j-4)*height+128, width, height);
				sprites.add(bi);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites.get(IDLEFRONT));
		animation.setDelay(300);
		
		right = true;
		directionFacing = FACING_RIGHT;
		
	}
	public int getType() { return COW; }
	
	@Override
	public void update() {
		if(moveSpeedWait) {
			if((startOfNoMove + milliseconds) < System.currentTimeMillis()){
				moveSpeedWait = false;
				startOfNoMove = System.currentTimeMillis();
				Random r = new Random(startOfNoMove);
				Double d = (r.nextDouble() % 3 + .5)*1000;
				milliseconds = d.intValue();
			}
		}
		if(!moveSpeedWait){
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
			
			if((startOfNoMove + milliseconds) < System.currentTimeMillis()){
				moveSpeedWait = true;
				startOfNoMove = System.currentTimeMillis();
				Random r = new Random(startOfNoMove);
				Double d = (r.nextDouble() % waitUpperBound + waitLowerBound)*1000;
				milliseconds = d.intValue();
				int i = r.nextInt()%4;
				System.out.println(i);
				switch(i){
				case 0: setRight();
				case 1: setLeft();
				case 2: setUp();
				case 3: setDown();
				case -1: setUp();
				case -2: setDown();
				}
				
			}
		}
	} 
	public void draw(Graphics2D g){
		setMapPosition();
		g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
	}
}
