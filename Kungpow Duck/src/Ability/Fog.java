/*
 * Description: 		Fog spell you can access through librarian's spellbook
 * Date Created: 		Jun 10, 2015
 * Date Last Modified: 	Jun 10, 2015
 * Modification Notes:	
 */
package Ability;

import Entity.PartyMember;
import Entity.Enemy;

public class Fog extends Ability {
	// Constructor using Party Members
	public Fog(PartyMember partyMember) {
		super(partyMember);
		name = "Fog";
		description = "Covers the enemy with a fog to lower hit chance by 30%";
		appliesToEnemy = true;
		typeOfDamage = TYPE_NONE;
		costsMana = true;
		cost = 30;
		statusEffects.add(new StatusEffect(StatusEffect.EFFECT_CHANGEHITCHANCE, .8, StatusEffect.APPLIES_TO_ENEMY_PERCENT, -30));	
	}
	// Identical Constructor for Enemies
	public Fog(Enemy enemy) {
		super(enemy);
		name = "Fog";
		description = "Covers the enemy with a fog to lower hit chance by 30%";
		appliesToEnemy = true;
		typeOfDamage = TYPE_NONE;
		costsMana = true;
		cost = 30;
		statusEffects.add(new StatusEffect(StatusEffect.EFFECT_CHANGEHITCHANCE, .8, StatusEffect.APPLIES_TO_ENEMY_PERCENT, -30));	
	}
}
