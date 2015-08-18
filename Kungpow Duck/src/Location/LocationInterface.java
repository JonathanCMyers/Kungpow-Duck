/*
 * Description: 		Holds the constants for Location.java, so it doesn't clutter the class.
 * Date Created: 		Jun 16, 2015
 * Date Last Modified: 	Jun 16, 2015
 * Modification Notes:	
 */
package Location;

import Entity.PartyMember;

public interface LocationInterface {
	// Location ID Values
	public static final int STARTING_LOCATION = 12001;
	public static final int SMALL_POND = 12002;
	
	// Enemies
	public void populateEnemies();
	public void addEnemiesAsNecessary();
	public void updateEnemies();
	// NPCs
	public void populateNPCs();
	// Trees
	public void populateTrees();
}