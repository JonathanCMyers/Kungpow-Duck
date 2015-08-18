/*
 * Description: 		Base Class for all weapons.
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity.Items;

import Entity.Item;
import java.io.Serializable;
import Entity.PartyMember;

public class Weapon extends Item implements Serializable {
	public Weapon() {
		type = Item.WEAPON;
		canUse = false;
		canEat = false;
		canEquip = true;
		stackable = false;
		setIcon("weapon.png");
	}
	
	public void equip(PartyMember person) {
		int result = person.equipWeapon(this);
		if(result == 0){
			// Produce a text box that says weapon is already equipped
			// OK <-
		}
		else if(result == 1){
			// Produce a text box that asks user if they want to equip this weapon over current weapon they hold
			// Also list the party member's current weapon & stats, preferably in a separate window next to text box.
			// If they select okay, use person.equipWeaponOverCurrentWeapon(this) and place the item that was replaced
			//  in the player's inventory.
		}
		else if(result == 2){
			// Produce a text box that says the weapon was successfully equipped.
			// OK <-
		}
	}
}
