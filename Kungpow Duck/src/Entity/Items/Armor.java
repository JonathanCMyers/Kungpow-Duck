/*
 * Description: 		Base Class for all armor
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import java.io.Serializable;

import Entity.Item;

public class Armor extends Item implements Serializable {
	
	public static final int HAT = 0;
	public static final int BODY = 1;
	public static final int LEGS = 2;
	public static final int FEET = 3;
	
	protected int armorType;
	
	public Armor() {
		type = Item.ARMOR;
		canUse = false;
		canEat = false;
		canEquip = true;
		stackable = false;
		setIcon("armor.png");
	}
	
	public int getArmorType() { return armorType; }
}
