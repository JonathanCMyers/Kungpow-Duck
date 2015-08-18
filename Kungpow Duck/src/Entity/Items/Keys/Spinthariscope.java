/*
 * Description: 		Spinthariscope.java
 * Date Created: 		Jun 7, 2015
 * Date Last Modified: 	Jun 7, 2015
 * Modification Notes:	
 */
package Entity.Items.Keys;

import Entity.Items.*;
import java.io.Serializable;

public class Spinthariscope extends Key implements Serializable {
	public Spinthariscope() {
		name = "Spinthariscope";
		description = "A mysterious device that allows the holder to craft gems.";
		descriptionLines = 3;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "A mysterious device that";
		descriptionByLine[1] = "allows the holder to";
		descriptionByLine[2] = "craft gems.";
	}
}
