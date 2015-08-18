/*
 * Description: 		Base Class for all accessories
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import java.io.Serializable;

import Entity.Item;

public class Accessory extends Item implements Serializable {
	public Accessory() {
		type = Item.ACCESSORY;
		canUse = false;
		canEat = false;
		canEquip = true;
		stackable = false; // Probably.
		setIcon("accessory.png");
	}
}
