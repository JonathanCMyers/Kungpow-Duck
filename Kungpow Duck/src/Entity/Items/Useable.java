/*
 * Description: 		Base Class for all useable items
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class Useable extends Item implements Serializable {
	public Useable() {
		type = Item.USEABLE;
		canUse = true;
		canEat = false;
		canEquip = false;
		stackable = true; 		// Probably. Can be changed if necessary.
		setIcon("useable.png");
		
		// There may be ingredients necessary, on a per-item basis.
	}
}
