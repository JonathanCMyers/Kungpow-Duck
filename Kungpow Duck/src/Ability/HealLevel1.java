package Ability;

public class HealLevel1 extends Ability {

	public HealLevel1() {
		name = "Cure";
		description = "Basic heal spell";
		accuracy = 1.0;
		typeOfDamage = TYPE_NONE;
		damage = 30;
		cost = 15;
		appliesToAlly = true;
		appliesToEnemy = false;
	}
}