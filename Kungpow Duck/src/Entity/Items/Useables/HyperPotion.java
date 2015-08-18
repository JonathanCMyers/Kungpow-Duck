/*
 * Description: 		More potent than regular potion
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.Useables;

import Entity.Items.Useable;
import java.io.Serializable;

public class HyperPotion extends Useable implements Serializable {
	public HyperPotion() {
		name = "Hyper Potion";
		description = "More potent than a regular potion";
		descriptionLines = 2;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "More potent than a";
		descriptionByLine[1] = "regular potion";
	}
}
