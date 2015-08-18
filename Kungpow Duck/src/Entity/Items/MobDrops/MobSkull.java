/*
 * Description: 		Dropped from a mob
 * Date Created: 		Jun 8, 2015
 * Date Last Modified: 	Jun 8, 2015
 * Modification Notes:	
 */
package Entity.Items.MobDrops;

import Entity.Items.*;
import java.io.Serializable;

public class MobSkull extends MobDrop implements Serializable {
	public MobSkull() {
		name = "Mob Skull";
		description = "Dropped from a mob";
		descriptionLines = 1;
		descriptionByLine = new String[descriptionLines];
		descriptionByLine[0] = "Dropped from a mob";
	}
}
