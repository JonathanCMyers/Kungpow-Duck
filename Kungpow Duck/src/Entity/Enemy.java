package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Ability.*;
import TileMap.TileMap;

public abstract class Enemy extends MapObject implements Serializable {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage; // contact damage when player bumps into 
	
	protected boolean flinching;
	protected long  flinchTimer;
	protected Animation facingRightAnimation;
	protected Animation deathAnimation;
	protected ArrayList<Ability> knownAbilities;
	
	protected ArrayList<Integer> weaknesses;
	protected ArrayList<Integer> resistances;
	
	protected long lastTimeRandomlyMoved;
	
	public static final int SNAKE = 0;
	
	public Enemy(TileMap tm){
		super(tm);
		lastTimeRandomlyMoved = System.nanoTime();
	}
	
	public int getDamage() { return damage; }
	
	/*public void hit(int damage){
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}  */
	
	public void update() {
		
	}	
	public void randomMove() {
		int minimumDifferenceInMovements = 10000;
		if((System.nanoTime() - lastTimeRandomlyMoved) > minimumDifferenceInMovements){
			int maximumRandom = 500;
			int minimumRandom = 0;
			Random r = new Random(System.nanoTime());
			int x = r.nextInt(maximumRandom - minimumRandom + 1) + minimumRandom;
			if(x == 3 && !right) setRight();
			if(x == 5 && !left)  setLeft();
			if(x == 7 && !up)    setUp();
			if(x == 9 && !down)  setDown();
		}
	}
	
	public abstract int getType();
	
	public Animation getFacingRightAnimation() { return facingRightAnimation; }
	public Animation getDeathAnimation() { return deathAnimation; }
	public Ability getBasicAttack() { return knownAbilities.get(0); }
	public int getRemainingHP() { return health; }
	public int getCurrentHP() { return health; }
	public int getMaxHP() { return maxHealth; }
	public boolean isDead() { return dead; }
	public Enemy getEnemyType() { return this; }

	public boolean applyDamage(Ability playerAttack) {
		Random r = new Random(System.nanoTime());
		double d = r.nextDouble();
		double damage = playerAttack.getDamage();
		System.out.println("applying damage");
		if(d > playerAttack.getAccuracy()){
			return false;
		}
		else{
			
			if(weaknesses.size() > 0){
				for(int j = 0; j < weaknesses.size(); j++){
					if(playerAttack.getTypeOfDamage() == weaknesses.get(j)){
						damage = damage * 1.5;
						break;
					}
				}
			}
			
			else if(resistances.size() > 0){
				for(int j = 0; j < resistances.size(); j++){
					if(playerAttack.getTypeOfDamage() == resistances.get(j)){
						damage = damage * .667;
					}
				}
			}
			
			health = health - (int) damage;
			if(health <= 0) dead = true;
			return true;
		}
	}
}

/*
 * 
 * 		if(flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed / 100 % 2 == 0){
				//System.out.println("flinching");
				return;	// gives appearance of blinking every 100 milliseconds
			}
		}
		
		*/
