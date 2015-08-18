/*
 * Description: 		Sandwich.java
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.Foods;

import Entity.Items.Food;
import java.io.Serializable;

public class Sandwich extends Food implements Serializable {
	public Sandwich() {
		name = "Sandwich";
		description = "One of Carrie's finest";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "One of Carrie's finest";
	}
}
