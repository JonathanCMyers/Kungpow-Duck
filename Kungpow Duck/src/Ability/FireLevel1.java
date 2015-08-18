package Ability;

public class FireLevel1 extends Ability {

	public FireLevel1() {
		name = "Flame";
		description = "Basic fire spell";
		accuracy = .80;
		typeOfDamage = TYPE_FIRE;
		damage = 60;
		cost = 30;
		costsMana = true;
	}
}