package Entity.PartyMembers;

import TileMap.*;
import Ability.*;
import Entity.Animation;
import Entity.Enemy;
import Entity.FireBall;
import Entity.NPC;
import Entity.PartyMember;

import java.util.ArrayList;

import javax.imageio.ImageIO; // for reading in sprite sheets

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Player extends PartyMember implements Serializable, PartyMemberInterface {

	// player stuff
	private boolean flinching;
	private long flinchTimer;

	private ArrayList<Integer> priority; 
	private boolean chatting;

	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 3, 3, 3, 3, 1, 1, 1, 1 };

	public Player(TileMap tm) {

		super(tm);
		name="Player";
		partyMemberID = ID_Player;
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

		height = width = 32;
		cwidth = cheight = 20;
		moveSpeed = .3;
		maxSpeed = 1.6;
		stopSpeed = .4;
		
		maxSpeedNormal = 1.6;
		maxSpeedFast = 3.3;

		directionFacing = FACING_DOWN;
		
		flinchFrequency = 1000;
		spriteDelayTime = 189;

		health = maxHealth = 300;
		mana = maxMana = 300;

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
		
		ManaPool manapool = new ManaPool(this);
		knownAbilities.add(manapool);
		numAbilities++;

		weaknesses = new ArrayList<Integer>();
		resistances = new ArrayList<Integer>();
		
		try {
			priority = new ArrayList<Integer>();
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/char1.png"));
			sprites = new ArrayList<BufferedImage[]>();
			BufferedImage[] bi = new BufferedImage[3];
			for (int j = 0; j < 4; j++) { 
				bi = new BufferedImage[numFrames[j]];
				for (int k = 0; k < numFrames[j]; k++) {
					bi[k] = spritesheet.getSubimage(k * width, j * height,
							width, height);
				}
				sprites.add(bi);
			}
			spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/Sprites/Player/char1Copy.png"));
			for (int j = 4; j < 8; j++) { 
				bi = new BufferedImage[numFrames[j]];
				bi[0] = spritesheet.getSubimage(30, (j - 4) * height, width,
						height); 
				sprites.add(bi);
			}
			facingLeftAnimation = new Animation();
			facingLeftAnimation.setFrames(sprites.get(WALKINGLEFT));
			facingLeftAnimation.setDelay(250);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			battleBorder = ImageIO.read(getClass().getResourceAsStream("/BattleBorders/player.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = IDLEFRONT;
		animation.setFrames(sprites.get(WALKINGFRONT));
		animation.setDelay(spriteDelayTime);

	}

	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public boolean getChatting() { return chatting; }
	public void setCurrentAction(int action) { currentAction = action; }
	public void setMaxSpeed(double d) { maxSpeed = d; }
	public void setHighSpeed(boolean b) { highSpeed = b; }
	public void addPriority(int x) { priority.add(x); }
	public boolean priorityEmpty() { return priority.isEmpty(); }
	public void emptyPriority() { priority.clear(); }
	public void setChatting(boolean b) { chatting = b; }

	public void removePriority(int x) {
		for (int j = 0; j < priority.size(); j++) {
			if (priority.get(j) == x) {
				priority.remove(j);
				j--;
			}
		}
	}
	
	public int checkIfPlayerCanChat(ArrayList<NPC> NPCs) {
		// loop through NPCs
		int npcNumber = -1;
		for (int j = 0; j < NPCs.size(); j++) {
			NPC n = NPCs.get(j);
			// check dialogue
			switch(directionFacing){
			case FACING_RIGHT: 				
				if (n.getx() > x && n.getx() < (x + 35) && n.gety() > (y - height / 2) && n.gety() < (y + height / 2)) {
					chatting = true;
					n.setChatting(true);
				}
				break;
			case FACING_LEFT:
				if (n.getx() < x && n.getx() > (x - 35)
						&& n.gety() > (y - height / 2)
						&& n.gety() < (y + height / 2)) {
					chatting = true;
					n.setChatting(true);
				}
				break;
			case FACING_UP:
				if (n.getx() > (x - width / 2) && n.getx() < (x + width / 2)
						&& n.gety() > (y - 35) && n.gety() < (y)) {
					chatting = true;
					n.setChatting(true);
				}
				break;
			case FACING_DOWN:
				if (n.getx() > (x - width / 2) && n.getx() < (x + width / 2)
						&& n.gety() > (y) && n.gety() < (y + 35)) {
					chatting = true;
					n.setChatting(true);
				}
				break;
			}
			if (intersects(n)) {
				dy = 0;
				dx = 0;
			}
			if (chatting) {
				npcNumber = j;
				break;
			}
		}
		return npcNumber;
	}

	/*
	 * public void checkNPCCollision(){
	 * 
	 * for(int j = 0; j < NPCs.size(); j++){
	 * 
	 * } // if player hits NPC from above currCol = (int)x / tileSize; currRow =
	 * (int)y / tileSize;
	 * 
	 * xdest = x + dx; ydest = y + dy;
	 * 
	 * xtemp = x; ytemp = y;
	 * 
	 * //boolean movedOnce = false;
	 * 
	 * calculateCorners(x, ydest); if(dy < 0) { if(topLeft || topRight) { dy =
	 * 0; ytemp = currRow * tileSize + cheight / 2; } else { ytemp += dy;
	 * //movedOnce = true; } } else if(dy > 0) { if(bottomLeft || bottomRight){
	 * // || movedOnce) { dy = 0; //falling = false; ytemp = (currRow + 1) *
	 * tileSize - cheight / 2; } else { ytemp += dy; //movedOnce = true; } }
	 * 
	 * calculateCorners(xdest, y);
	 * 
	 * if(dx < 0) { if(topLeft || bottomLeft){ // || movedOnce) { dx = 0; xtemp
	 * = currCol * tileSize + cwidth / 2; } else { xtemp += dx; //movedOnce =
	 * true; } } else if(dx > 0) { if(topRight || bottomRight){ // || movedOnce)
	 * { dx = 0; xtemp = (currCol + 1) * tileSize - cwidth / 2; } else { xtemp
	 * += dx; //movedOnce = true; } }
	 * 
	 * }
	 */

	public void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0)
			health = 0;
		if (health == 0)
			dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	@Override
	public void getNextPosition() {
		if (priority.isEmpty()
				|| priority.get(priority.size() - 1) == PRIORITYNONE) {
			up = false;
			right = false;
			down = false;
			left = false;
		} else {
			if (priority.get(priority.size() - 1) == PRIORITYUP) {
				up = true;
				right = false;
				down = false;
				left = false;
			}
			if (priority.get(priority.size() - 1) == PRIORITYRIGHT) {
				up = false;
				right = true;
				down = false;
				left = false;
			}
			if (priority.get(priority.size() - 1) == PRIORITYDOWN) {
				up = false;
				right = false;
				down = true;
				left = false;
			}
			if (priority.get(priority.size() - 1) == PRIORITYLEFT) {
				up = false;
				right = false;
				down = false;
				left = true;
			}
		}
		if (chatting) chatting = false;
		else super.getNextPosition();
	}

	public void update() {
		// update position
		getNextPosition();
		checkTileMapCollision();
		// checkNPCCollision();
		setPosition(xtemp, ytemp);

		// check attack has stopped
		/*
		 * if(currentAction == SCRATCHING){ DRAGON if(animation.hasPlayedOnce())
		 * scratching = false; } if(currentAction == FIREBALL){
		 * if(animation.hasPlayedOnce()) firing = false; } if(currentAction ==
		 * CHATTING){ if(animation.hasPlayedOnce()) chatting = false; }
		 */

		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > flinchFrequency) {
				flinching = false;
			}
		} 

		if (left) {
			if (currentAction != WALKINGLEFT) {
				currentAction = WALKINGLEFT;
				animation.setFrames(sprites.get(WALKINGLEFT));
				animation.setDelay(spriteDelayTime);
			}
		} else if (right) {
			if (currentAction != WALKINGRIGHT) {
				currentAction = WALKINGRIGHT;
				animation.setFrames(sprites.get(WALKINGRIGHT));
				animation.setDelay(spriteDelayTime);
			}
		} else if (down) {
			if (currentAction != WALKINGFRONT) {
				currentAction = WALKINGFRONT;
				animation.setFrames(sprites.get(WALKINGFRONT));
				animation.setDelay(spriteDelayTime);
			}
		} else if (up) {
			if (currentAction != WALKINGBACK) {
				currentAction = WALKINGBACK;
				animation.setFrames(sprites.get(WALKINGBACK));
				animation.setDelay(spriteDelayTime);
			}
		} 
		else {
			if (currentAction >= 4) {
				switch(directionFacing){
				case FACING_LEFT:
					if (currentAction != IDLELEFT) {
						animation.setFrames(sprites.get(IDLELEFT));
						animation.setDelay(-1);
					}
					break;
				case FACING_RIGHT:
					animation.setFrames(sprites.get(IDLERIGHT));
					animation.setDelay(-1);
					break;
				case FACING_UP:
					animation.setFrames(sprites.get(IDLEBACK));
					animation.setDelay(-1);
					break;
				case FACING_DOWN:
					animation.setFrames(sprites.get(IDLEFRONT));
					animation.setDelay(-1);
					break;
				}
			}

		}
		animation.update();
	}

	@Override
	public void draw(Graphics2D g) {
		setMapPosition();
		/*	Fireballs? What fireballs
		for (int j = 0; j < fireBalls.size(); j++) {
			fireBalls.get(j).draw(g);
		}
		*/
		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return; // gives appearance of blinking every 100 milliseconds
			}
		}
		g.drawImage(animation.getImage(), (int) (x + xmap - width / 2),
				(int) (y + ymap - height / 2), null);
	}
}
