/*
 * Description: 		StrangePlant.java
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.Ingredients;

import Entity.Items.*;
import java.io.Serializable;

public class StrangePlant extends Ingredient implements Serializable {
	public StrangePlant(){
		name = "Strange Plant";
		description = "Don't eat it!";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "Don't eat it!";
	}
}
