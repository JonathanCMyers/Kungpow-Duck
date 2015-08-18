/*
 * Description: 		BattleGem.java
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.Gems;

import Entity.Items.*;
import java.io.Serializable;

public class BattleGem extends Gem implements Serializable {
	public BattleGem() {
		name = "Battle Gem";
		description = "The most basic of all gems";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "The most basic of all gems";
	}
}
