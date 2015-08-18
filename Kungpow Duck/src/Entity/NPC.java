package Entity;

import java.io.Serializable;

import TileMap.TileMap;

public class NPC extends MapObject implements Serializable {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage; // contact damage when player bumps into 
	
	protected boolean flinching;
	protected long  flinchTimer;
	protected boolean chatting;
	
	protected String[] chatboxStrings;
	protected String[] chatboxStringsSecondLine;
	protected boolean[] hasSecondLine;
	protected int chatboxCount;
	protected int currentChatbox;
	
	// These all have to do with contiguous movement
	protected boolean moveSpeedWait;
	protected double waitLowerBound;
	protected double waitUpperBound;
	protected int milliseconds;
	protected long startOfNoMove;
	
	public NPC(TileMap tm){
		super(tm);
	}
	
	public boolean isDead() { return dead; }
	public int getDamage() { return damage; }
	
	public boolean getChatting() { return chatting; }
	public void setChatting(boolean b) { chatting = b; }
	public void setChattingTrue() { chatting = true; }
	public void setChattingFalse() { chatting = false; }
	public boolean hasSecondLine() { return hasSecondLine[currentChatbox]; }
	public boolean hasNextSecondLine() { return hasSecondLine[currentChatbox+1]; }
	
	public String getFirstChatbox() {
		return chatboxStrings[0];
	}
	
	public String getFirstChatboxLine2() {
		return chatboxStringsSecondLine[0];
	}
	
	public void incrementChatboxCounter() {
		currentChatbox++;
	}
	
	public String getNextChatboxLine2() {
		return chatboxStringsSecondLine[currentChatbox];
	}
	
	public String getCurrentChatbox() {
		if(currentChatbox == chatboxCount) return "-0";
		return chatboxStrings[currentChatbox];
	}
	
	public String getCurrentChatboxLine2() {
		return chatboxStringsSecondLine[currentChatbox];
	}
	
	public void resetChatbox() {
		currentChatbox = 0;
	}
	
	public void hit(int damage){
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void update(){
		
	}

}