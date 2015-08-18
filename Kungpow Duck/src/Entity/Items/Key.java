/*
 * Description: 		Base Class for all key items
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class Key extends Item implements Serializable {
	public Key() {
		type = Item.KEY;
		canUse = true; 	// This can be changed for the individual item, as needed.
		canEat = false;
		canEquip = false;
		stackable = false;
		setIcon("key.png");
	}
}
