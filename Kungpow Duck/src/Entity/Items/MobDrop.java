/*
 * Description: 		Base Class for all mob drops. Lists which mobs drop.
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;

public class MobDrop extends Item implements Serializable {
	public MobDrop() {
		type = Item.MOBDROP;
		canUse = false;
		canEat = false; 
		canEquip = false;
		stackable = true;
		droppedByMob = true;
		setIcon("mobdrop.png");
	}
}
