/*
 * Description: 		Random Accessory
 * Date Created: 		Jun 7, 2015
 * Date Last Modified: 	Jun 7, 2015
 * Modification Notes:	
 */
package Entity.Items.Accessories;

import Entity.Items.Accessory;
import java.io.Serializable;

public class Pendant extends Accessory implements Serializable {
	public Pendant() {
		name = "Pendant";
		description = "Wear this object";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "Wear this object";
	}
}
