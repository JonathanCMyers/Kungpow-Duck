package Ability;

public class BasicAttack extends Ability {
	
	public BasicAttack() {
		name = "Attack";
		description = "Basic attack";
		accuracy = .90;
		typeOfDamage = TYPE_PHYSICALMELEE;
		appliesToEnemy = true;
		damage = 30;
		name = "Attack";
	}
	public BasicAttack(int damage) {
		super();
		name = "Attack";
		description = "Basic attack";
		accuracy = .90;
		typeOfDamage = TYPE_PHYSICALMELEE;
		appliesToEnemy = true;
		this.damage = damage;
	}
}