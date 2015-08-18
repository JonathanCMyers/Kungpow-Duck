/*
 * Description: 		Base Class for all gems
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class Gem extends Item implements Serializable {
	
	private int damage;
	private int damageType;		// set this later once abilities are fleshed out more
	
	public Gem() {
		type = Item.GEM;
		canUse = false;
		canEat = false;
		canEquip = false;
		stackable = true;
		setIcon("gem.png");
	}
	
	public int getDamage() { return damage; }
	public int getDamageType() { return damageType; }
	public void setDamage(int damage) { this.damage = damage; }
	public void setDamageType(int damageType) { this.damageType = damageType; }
}
