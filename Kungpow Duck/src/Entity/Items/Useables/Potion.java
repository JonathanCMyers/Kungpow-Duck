/*
 * Description: 		Generic Potion
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.Useables;

import Entity.Items.Useable;
import java.io.Serializable;

public class Potion extends Useable implements Serializable {
	public Potion() {
		name = "Potion";
		description = "A generic potion";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "A generic potion";
	}
}
