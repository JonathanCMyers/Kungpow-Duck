package Entity.PartyMembers;

import TileMap.*;
import Ability.*;
import Entity.Animation;
import Entity.PartyMember;

import java.util.ArrayList;

import javax.imageio.ImageIO;	// for reading in sprite sheets

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Carrie extends PartyMember implements Serializable, PartyMemberInterface {
	
	// player stuff
	private int fire;
	private int maxFire;
	private boolean flinching;
	private long flinchTimer;
	
	public static final int PRIORITYNONE = 0;
	public static final int PRIORITYUP = 1;
	public static final int PRIORITYRIGHT = 2;
	public static final int PRIORITYDOWN = 3;
	public static final int PRIORITYLEFT = 4;
	
	// animation actions
	
	private ArrayList<Integer> priority;
	
	
	private int direction;
	
	private boolean highSpeed;

	
	private boolean chatting;

	private ArrayList<BufferedImage[]> sprites;

	private final int[] numFrames = {
		3, 3, 3, 3, 1, 1, 1, 1	
	};
	
	
	public Carrie(TileMap tm){
		
		super(tm);
		name = "Carrie";
		partyMemberID = ID_Carrie;
		dead = false;
		// animation actions
		WALKINGFRONT = 0;
		WALKINGLEFT = 1;
		WALKINGRIGHT = 2;
		WALKINGBACK = 3;
		IDLEFRONT = 4;
		IDLELEFT = 5;
		IDLERIGHT = 6;
		IDLEBACK = 7;
		
		height = 32;	
		width = 32;
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = .3;
		maxSpeed = 1.6;
		stopSpeed = .4;
		
		directionFacing = FACING_DOWN;
		highSpeed = false;
		
		health = maxHealth = 300;
		
		BasicAttack b = new BasicAttack(30);
		knownAbilities = new ArrayList<Ability>();
		knownAbilities.add(b);
		numAbilities++;
		
		HealLevel1 cure = new HealLevel1();
		knownAbilities.add(cure);
		numAbilities++;
		
		FireLevel1 flame = new FireLevel1();
		knownAbilities.add(flame);
		numAbilities++;
		
		weaknesses = new ArrayList<Integer>();
		resistances = new ArrayList<Integer>();
		
		try{
			priority = new ArrayList<Integer>();
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/PartyMembers/Carrie.png"));
			sprites = new ArrayList<BufferedImage[]>();
			BufferedImage[] bi = new BufferedImage[3];
			for(int j = 0; j < 4; j++){		// since we have 4 walking animation actions
				bi = new BufferedImage[numFrames[j]];
				for(int k = 0; k < numFrames[j]; k++){
					bi[k] = spritesheet.getSubimage(k*width, j*height, width, height);
				}
				sprites.add(bi);
			}  
			spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/char1Copy.png"));
			for(int j = 4; j < 8; j++){		// since we have 4 idle animation actions
				bi = new BufferedImage[numFrames[j]];					
				bi[0] = spritesheet.getSubimage(30, (j-4)*height, width, height);
				sprites.add(bi);
			}
			facingLeftAnimation = new Animation();
			facingLeftAnimation.setFrames(sprites.get(WALKINGLEFT));
			facingLeftAnimation.setDelay(250);
		}
		catch(Exception e){
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = IDLEFRONT;
		animation.setFrames(sprites.get(WALKINGFRONT));
		animation.setDelay(400);
		
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getFire() { return fire; }
	public int getMaxFire() { return maxFire; }
	public boolean getChatting() { return chatting; }
	public void setCurrentAction(int action) { currentAction = action; }
	public void setMaxSpeed(double d) { maxSpeed = d; }
	public void setHighSpeed(boolean b) { highSpeed = b; }
	
	public void removePriority(int x){
		for(int j = 0; j < priority.size(); j++){
			if(priority.get(j) == x){
				priority.remove(j);
				//System.out.println(x);
				j--;
			}
		}
	}
	public boolean priorityEmpty() {
		return priority.isEmpty();
	}
	public void emptyPriority() {
		priority.clear();
	}

	public void setChatting(boolean b) { chatting = b; }
	
	public void hit(int damage){
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	/*private void getNextPosition() {
		if(highSpeed == true) maxSpeed = 2.5;
		if(highSpeed == false) maxSpeed = 1.6;
		if(priority.isEmpty() || priority.get(priority.size() - 1) == PRIORITYNONE){
			//System.out.println("empty");
			up = false;
			right = false;
			down = false;
			left = false;
		}
		else{
			if(priority.get(priority.size() - 1) == PRIORITYUP){
				up = true;
				right = false;
				down = false;
				left = false;
			}
			if(priority.get(priority.size() - 1) == PRIORITYRIGHT){
				up = false;
				right = true;
				down = false;
				left = false;
			}
			if(priority.get(priority.size() - 1) == PRIORITYDOWN){
				up = false;
				right = false;
				down = true;
				left = false;
			}
			if(priority.get(priority.size() - 1) == PRIORITYLEFT){
				up = false;
				right = false;
				down = false;
				left = true;
			}
		}
		// movement
		if(chatting){
			chatting = false;
		} 
		else{
			boolean movedOnce = false;
			if(left){
				facingLeft = true;
				facingRight = false;
				facingBack = false;
				facingFront = false;
				dx -= moveSpeed;
				if(dx < -maxSpeed){
					dx = -maxSpeed;
				}
				movedOnce = true;
				//System.out.println("left");
			}
			else if(right) {
				//currentAction = WALKINGRIGHT;
				facingLeft = false;
				facingRight = true;
				facingBack = false;
				facingFront = false;
				dx += moveSpeed;
				if(dx > maxSpeed) {
					dx = maxSpeed;
				}
				//System.out.println("right");
				movedOnce = true;
			}
			else if(up){
				//currentAction = WALKINGBACK;
				facingLeft = false;
				facingRight = false;
				facingBack = true;
				facingFront = false;
				dy -= moveSpeed;
				if(dy < -maxSpeed){
					dy = -maxSpeed;
				}
				movedOnce = true;
				//System.out.println("up");
			}
			else if(down){
				//currentAction = WALKINGFRONT;
				facingLeft = false;
				facingRight = false;
				facingBack = false;
				facingFront = true;
				dy += moveSpeed;
				if(dy > maxSpeed){
					dy = maxSpeed;
				}
				movedOnce = true;
				//System.out.println("down");
			}
			if(!movedOnce){
				currentAction = 4;
				if(dx > 0){
					dx -= stopSpeed;
					if(dx < 0){
						dx = 0;
					}
				}
				else if(dx < 0) {
					dx += stopSpeed;
					if(dx > 0) {
						dx = 0;
					}
				}
				if(dy > 0){
					dy -= stopSpeed * 2;
					if(dy < 0){
						dy = 0;
					}
				}
				else if(dy < 0){
					dy += stopSpeed;
					if(dy > 0)
						dy = 0;
				}
			}
			
		}
		
		// cannot move while attacking except in the air

		
		// jumping
	}
	*/
	
	public void update() {
		animation.update();/*
		
		// update position
		getNextPosition();
		checkTileMapCollision();
		//checkNPCCollision();
		setPosition(xtemp, ytemp);
		
		if(left){
			if(currentAction != WALKINGLEFT){
				currentAction = WALKINGLEFT;
				//System.out.println("walking left");
				animation.setFrames(sprites.get(WALKINGLEFT));
				animation.setDelay(200);
				width = 30;
			}
		}
		else if(right){
			//System.out.println("RIGHT");
			if(currentAction != WALKINGRIGHT){
				currentAction = WALKINGRIGHT;
				//System.out.println("walking right");
				animation.setFrames(sprites.get(WALKINGRIGHT));
				animation.setDelay(200);
				width = 30;
			}
		}
		else if(down){
			//System.out.println("RIGHT");
			if(currentAction != WALKINGFRONT){
				currentAction = WALKINGFRONT;
				//System.out.println("walking right");
				animation.setFrames(sprites.get(WALKINGFRONT));
				animation.setDelay(200);
				width = 30;
			}
		}
		else if(up){
			//System.out.println("RIGHT");
			if(currentAction != WALKINGBACK){
				currentAction = WALKINGBACK;
				//System.out.println("walking right");
				animation.setFrames(sprites.get(WALKINGBACK));
				animation.setDelay(200);
				width = 30;
			}
		} 
		else{
			//System.out.println(currentAction);
			if(currentAction >= 4){
				if(facingLeft){
					if(currentAction != IDLELEFT){
						//System.out.println("IDLELEFT");
						animation.setFrames(sprites.get(IDLELEFT));
						animation.setDelay(-1);
						width = 30;
					}
				}
				if(facingRight){
					//currentAction = IDLERIGHT;
					//System.out.println("IDLERIGHT");
					animation.setFrames(sprites.get(IDLERIGHT));
					animation.setDelay(-1);
					width = 30;
				}
				if(facingBack){
					//currentAction = IDLEBACK;
					//System.out.println("IDLEBACK");
					animation.setFrames(sprites.get(IDLEBACK));
					animation.setDelay(-1);
					width = 30;
				}
				if(facingFront){
					//currentAction = IDLEFRONT;
					//System.out.println("IDLEFRONT");
					animation.setFrames(sprites.get(IDLEFRONT));
					animation.setDelay(50);
					width = 30;
				}
				//System.out.println(currentAction);
			}
		}
		
		// set direction  */
	}
	
	@Override
	public void draw(Graphics2D g) {
		setMapPosition(); 	// could be unnecessary for party members

		
		// draw player
		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0){
				//System.out.println("flinching");
				return;	// gives appearance of blinking every 100 milliseconds
			}
		}
		g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
	}
}

