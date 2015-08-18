/*
 * Description: 		Provides a small amount of mana to all party members, based on the percent of missing mana of the individual.
 * Date Created: 		Jun 11, 2015
 * Date Last Modified: 	Jun 11, 2015
 * Modification Notes:	
 */
package Ability;

import Entity.Enemy;
import Entity.PartyMember;

public class ManaPool extends Ability {
	
	// Constructor for Party Members, using a default cost of 8% max mana and giving 10% of missing mana
	public ManaPool(PartyMember partyMember) {
		super(partyMember);
		name = "Mana Pool";
		description = "User sacrifices 8% of his mana"; //to give his\nallies mana equal to 10%\nof his missing mana.";
		appliesToAlly = true;
		appliesToMultiple = true;
		typeOfDamage = TYPE_NONE;
		costsMana = true;
		cost = ((partyMember.getMaxMana())/25) * 2;	// 8% of user's mana
		restoresMana = true;
		statusEffects.add(new StatusEffect(StatusEffect.EFFECT_MANAVAMP, 1.0, 
				StatusEffect.APPLIES_TO_ALLY_AOE, (partyMember.getMaxMana() - partyMember.getCurrentMana())/10));
	}
	
	// Constructor for Party Members, being given the % of missing mana that the user gives to other party members
	public ManaPool(PartyMember partyMember, double percentManaGiven) {
		this(partyMember);
		statusEffects.remove(0);
		Double manaGiven = (partyMember.getMaxMana() - partyMember.getCurrentMana())*percentManaGiven;
		statusEffects.add(new StatusEffect(StatusEffect.EFFECT_MANAVAMP, 1.0, 
				StatusEffect.APPLIES_TO_ALLY_AOE, manaGiven.intValue()));
	}
	
	// Constructor for enemy use
	public ManaPool(Enemy enemy){
		this(new PartyMember(null));
		setEnemy(enemy);
	}
}
