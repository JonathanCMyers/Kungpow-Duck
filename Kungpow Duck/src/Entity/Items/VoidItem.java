/*
 * Description: 		Represents a void item, so you can know when an item slot is empty if it contains only a void item
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class VoidItem extends Item implements Serializable {
	public VoidItem() {
		type = Item.VOID;
		canUse = false;
		canEat = false;
		canEquip = false;
		stackable = false; 
		setIcon("accessory.png");
	}
}
