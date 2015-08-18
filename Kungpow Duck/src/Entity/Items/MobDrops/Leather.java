/*
 * Description: 		Leather.java
 * Date Created: 		Jun 7, 2015
 * Date Last Modified: 	Jun 7, 2015
 * Modification Notes:	
 */
package Entity.Items.MobDrops;

import Entity.Items.MobDrop;
import java.io.Serializable;

public class Leather extends MobDrop implements Serializable {
	public Leather() {
		name = "Leather";
		description = "Tough hide";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "Tough hide";
	}
}
