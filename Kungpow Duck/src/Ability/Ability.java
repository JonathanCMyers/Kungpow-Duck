package Ability;

import java.io.Serializable;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.PartyMember;

public class Ability implements Serializable {
	
	// Types of Damage 
	public static final int TYPE_NULL = -1;
	public static final int TYPE_PHYSICALMELEE = 0;
	public static final int TYPE_PHYSICALRANGED = 1;
	public static final int TYPE_WATER = 2;
	public static final int TYPE_ICE = 3;
	public static final int TYPE_WIND = 4;
	public static final int TYPE_EARTH = 5;
	public static final int TYPE_FIRE = 6;
	public static final int TYPE_HOLY = 7;
	public static final int TYPE_UNHOLY = 8;
	public static final int TYPE_POISON = 9;
	public static final int TYPE_ACOUSTIC = 10;
	public static final int TYPE_PURE = 11;
	public static final int TYPE_NONE = 12;
	
	// Name & Description
	protected String name;
	protected String description;
	
	// Basic Information
	protected double accuracy;
	protected int typeOfDamage;
	protected boolean isPercentDamage;
	protected boolean hasSecondDamageType;
	protected int typeOfSecondDamage;
	protected int damage;
	protected int cost;
	
	// Target of Ability
	protected boolean appliesToSelf;
	protected boolean appliesToAlly;
	protected boolean appliesToEnemy;
	protected boolean appliesToMultiple;
	
	// Costs
	protected boolean costsMana;
	protected boolean costsHealth;
	protected boolean noCost;
	
	// Restoration
	protected boolean restoresHealth;
	protected boolean restoresMana;
	protected boolean restoresLife;
	
	// Status Effect
	protected ArrayList<StatusEffect> statusEffects;			// in case the ability has more than one chance of status effect
	
	// User of the Ability
	protected PartyMember partyMember;
	protected Enemy enemy;
	protected boolean enemyUser;
	
	public Ability() {
		
	}
	
	// Constructor using Party Members
	public Ability(PartyMember partyMember) {
		this.partyMember = partyMember;
		enemyUser = false;
		
		name = "Ability";
		description = "N/A";
		accuracy = 1;
		typeOfDamage = TYPE_NULL;
		isPercentDamage = false;
		hasSecondDamageType = false;
		typeOfSecondDamage = TYPE_NULL;
		damage = 0;
		cost = 0;
		
		appliesToSelf = false;
		appliesToAlly = false;
		appliesToEnemy = false;
		appliesToMultiple = false;
		
		costsMana = false;
		costsHealth = false;
		noCost = false;
		
		restoresHealth = false;
		restoresMana = false;
		restoresLife = false;
		
		statusEffects = new ArrayList<StatusEffect>();
	}

	// Identical Constructor for Enemies
	public Ability(Enemy enemy) {
		this(new PartyMember(null));
		setEnemy(enemy);
	} 

	// Getters
	public double getAccuracy() { return accuracy; }
	public int getTypeOfDamage() { return typeOfDamage; }
	public int getSecondTypeOfDamage() { return typeOfSecondDamage; }
	public int getDamage() { return damage; }
	public String getName() { return name; }
	public String getDescription() { return description; }
	public int getCost() { return cost; }
	public boolean hasSecondDamageType() { return hasSecondDamageType; }
	public boolean appliesToSelf() { return appliesToSelf; }
	public boolean appliesToAlly() { return appliesToAlly; }
	public boolean appliesToEnemy() { return appliesToEnemy; }
	public boolean appliesToMultiple() { return appliesToMultiple; }
	public boolean isPercentDamage() { return isPercentDamage; }
	public boolean isHeal() { return ((appliesToSelf || appliesToAlly) && restoresHealth); }
	public boolean dealsDamage() { return typeOfDamage == TYPE_NONE; }
	public ArrayList<StatusEffect> getStatusEffects() { return statusEffects; }
	
	// Setters
	public void setDamage(int x) { damage = x; }
	public void addDamage(int x) { damage+=x; }
	public void setPartyMember(PartyMember partyMember) { 
		this.partyMember = partyMember; 
		enemyUser = false;
	}
	public void setEnemy(Enemy enemy) { 
		this.enemy = enemy; 
		enemyUser = true;
	}
}
