/*
 * Description: 		Heals party member based on % of maximum mana of user
 * Date Created: 		Jun 11, 2015
 * Date Last Modified: 	Jun 11, 2015
 * Modification Notes:	
 */
package Ability;

import Entity.Enemy;
import Entity.PartyMember;

public class HealingSerum extends Ability {
	
	// Constructor for Party Members, using a default cost of 80 mana and giving 33% of maximum mana as HP
	public HealingSerum(PartyMember partyMember) {
		super(partyMember);
		name = "Healing Serum";
		description = "Target is healed by 33% of user's maximum mana in HP";
		appliesToAlly = true;
		typeOfDamage = TYPE_NONE;
		costsMana = true;
		cost = 80;
		restoresHealth = true;
		statusEffects.add(new StatusEffect(StatusEffect.EFFECT_HEAL , 1.0, 
				StatusEffect.APPLIES_TO_ALLY, partyMember.getMaxMana()/3));
	}
	
	// Constructor for Party Members, passing in the cost as well as the percentage of maximum mana the target is healed for
	public HealingSerum(PartyMember partyMember, int manaCost, double percentManaGiven) {
		this(partyMember);
		Double pmg = percentManaGiven * 100;
		description = "Target is healed by " + pmg.intValue() + "% of user's maximum mana in HP";
		cost = manaCost;
		Double healthGiven = partyMember.getMaxMana()*percentManaGiven;
		statusEffects.remove(0);
		statusEffects.add(new StatusEffect(StatusEffect.EFFECT_HEAL, 1.0, 
				StatusEffect.APPLIES_TO_ALLY, healthGiven.intValue()));
	}
	
	// Constructor for enemies
	public HealingSerum(Enemy enemy) {
		this(new PartyMember(null));
		setEnemy(enemy);
	}
}