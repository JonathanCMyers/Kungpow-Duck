/*
 * Description: 		XKey.java
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.Keys;

import Entity.Items.*;
import java.io.Serializable;

public class XKey extends Key implements Serializable {
	public XKey() {
		name = "XKey";
		description = "none";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "none";
	}
}
