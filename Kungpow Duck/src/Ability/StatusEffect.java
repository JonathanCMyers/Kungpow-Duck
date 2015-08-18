/*
 * Description: 		It's just easier to have this in a separate class so the effect + percentage can be together
 * Date Created: 		Jun 11, 2015
 * Date Last Modified: 	Jun 11, 2015
 * Modification Notes:	
 */
package Ability;

import java.io.Serializable;

public class StatusEffect implements Serializable {
	
	// Default Effect
	public static final int EFFECT_NONE = -1;
	
	// Positive Effects
	public static final int EFFECT_HEAL = 0;
	public static final int EFFECT_MANAVAMP = 1;
	public static final int EFFECT_REVIVE = 2;
	public static final int EFFECT_STATUSCLEAR = 3;
	
	// Negative Effects
	public static final int EFFECT_POISONED = 4;		// damage over time & can't use consumables (topical poison cream)
	public static final int EFFECT_STUN = 5;			// prevented from attacking next turn
	public static final int EFFECT_SLOW = 6;			// slows down the order in which attack occurs
	public static final int EFFECT_BURN = 7;			// increases damage taken slightly
	public static final int EFFECT_HEX = 8;				// unable to use abilities, can still use weapon attacks
	public static final int EFFECT_BIND = 9;			// unable to use weapon attacks, can still use abilities
	
	// Stat Modifier Effects
	public static final int EFFECT_CHANGEMOBDROPCHANCE = 10;
	public static final int EFFECT_CHANGEPHYSICALDAMAGE = 11;
	public static final int EFFECT_CHANGEMAGICALDAMAGE = 12;
	public static final int EFFECT_CHANGEONHITEFFECTS = 13;
	public static final int EFFECT_CHANGEHITCHANCE = 17;
	public static final int EFFECT_CHANGECRITCHANCE = 14;
	public static final int EFFECT_CHANGECRITDODGECHANCE = 15;
	public static final int EFFECT_CHANGEDODGECHANCE = 16;
	public static final int EFFECT_CHANCESPEED = 17;
	public static final int EFFECT_CHANGESTATUSSUSCEPTIBILITY = 18;
	
	// Who it applies to
	public static final int APPLIES_TO_NONE = -1;
	public static final int APPLIES_TO_SELF = 0;
	public static final int APPLIES_TO_ALLY = 1;
	public static final int APPLIES_TO_ALLY_AOE = 2;
	public static final int APPLIES_TO_ENEMY = 3;
	public static final int APPLIES_TO_ENEMY_AOE = 4;
	public static final int APPLIES_TO_ALLY_AND_ENEMY = 5;
	public static final int APPLIES_TO_ALLY_AND_ENEMY_AOE = 6;
	
	// When it applies percent changes
	public static final int APPLIES_TO_SELF_PERCENT = 7;
	public static final int APPLIES_TO_ALLY_PERCENT = 8;
	public static final int APPLIES_TO_ALLY_AOE_PERCENT = 9;
	public static final int APPLIES_TO_ENEMY_PERCENT = 10;
	public static final int APPLIES_TO_ENEMY_AOE_PERCENT = 11;
	public static final int APPLIES_TO_ALLY_AND_ENEMY_PERCENT = 12;
	public static final int APPLIES_TO_ALLY_AND_ENEMY_AOE_PERCENT = 13;
	
	// Variables of the StatusEffect object
	private int statusEffect;
	private double chanceOfEffect;
	private int appliesTo;
	private int amountModification;		// Used for amount of modification. Can be positive or negative. 
	
	// Default Constructor
	public StatusEffect() {
		statusEffect = EFFECT_NONE;
		chanceOfEffect = 0;
		appliesTo = APPLIES_TO_NONE;
		amountModification = 0;
	}
	
	/**
	 * Constructor
	 * @param statusEffect
	 * @param chanceOfEffect
	 * @param appliesTo
	 * @param amountModification
	 */
	public StatusEffect(int statusEffect, double chanceOfEffect, int appliesTo, int amountModification){
		this.statusEffect = statusEffect;
		this.chanceOfEffect = chanceOfEffect;
		this.appliesTo = appliesTo;
		this.amountModification = amountModification;
	}
	
	// Getters
	public int getStatusEffect() { return statusEffect; }
	public double getChanceOfEffect() { return chanceOfEffect; }
	public int getAppliesTo() { return appliesTo; }
	public int returnAmountModification() { return amountModification; }
	
	// Setters
	public void setStatusEffect(int statusEffect) { this.statusEffect = statusEffect; }
	public void setChanceOfEffect(double chanceOfEffect) { this.chanceOfEffect = chanceOfEffect; }
	public void setAppliesTo(int appliesTo) { this.appliesTo = appliesTo; }
	public void setAmountModification(int amountModification) { this.amountModification = amountModification; }
}
