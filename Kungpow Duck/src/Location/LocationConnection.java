/*
 * Description: 		Describes the aspects in the connection between two Locations
 * Date Created: 		Jun 16, 2015
 * Date Last Modified: 	Jun 16, 2015
 * Modification Notes:	
 */
package Location;

import java.io.Serializable;
import java.util.ArrayList;

import Entity.MapObject;
import Entity.PartyMember;

public class LocationConnection implements Serializable {
	
	protected int xFrom, yFrom;			// X and Y values you need to travel to in order to reach the new location
	protected int xTo, yTo;				// X and Y values in which you will spawn in the new location
	protected int xRange, yRange;		// Range at which you enter the connection between the two locations (Default of 16)
	
	protected int from_ID, to_ID;		// The location ID values of the original location to the new location
	
	protected boolean hiddenConnection;		// True if the connection is not currently accessible to the player
	protected boolean requiresActivation;	// Does the portal need to be activated with the activate key? (Could be reduntant of hiddenConnection)
	
	protected boolean requiresItem;			// Does the connection require a specific item to be passed through?
	protected int requiredItemID;			// The ID of the item required to pass through the connection.
	
	protected int directionFacing;			// What direction does the player face when spawning in the new location?

	public LocationConnection() {
		xFrom = 0; yFrom = 0; xTo = 0; yTo = 0;
		xRange = 16; yRange = 16;
		from_ID = 0; to_ID = 0;
		hiddenConnection = false; requiresActivation = false; requiresItem = false;
		requiredItemID = 0;
		directionFacing = MapObject.FACING_NONE;
	}
	
	public Location checkTeleport(PartyMember pm) {
		Location newLocation = null;
		// Check if the player is within the bounds for the teleport
		if(pm.getx() > xFrom-xRange && pm.getx() < xFrom+xRange && pm.gety() > yFrom-yRange && pm.gety() < yFrom+yRange){
			switch(to_ID){
			case LocationInterface.STARTING_LOCATION:		
				newLocation = new Location(); 
				newLocation.setXY(xTo, yTo);
				break;
			case LocationInterface.SMALL_POND:			
				newLocation = new SmallPond(); 
				newLocation.setXY(xTo, yTo);
				break;
			}
		}
		return newLocation;
	}
	
	// Getters
	public int getXFrom() { return xFrom; }
	public int getYFrom() { return yFrom; }
	public int getXTo() { return xTo; }
	public int getYTo() { return yTo; }
	public int getXRange() { return xRange; }
	public int getYRange() { return yRange; }
	public int getFromID() { return from_ID; }
	public int getToID() { return to_ID; }
	public boolean hiddenConnection() { return hiddenConnection; }
	public boolean requiresActivation() { return requiresActivation; }
	public boolean requiresItem() { return requiresItem; }
	public int requiredItemID() { return requiredItemID; }
	public int getDirectionFacing() { return directionFacing; }
	
	
	// Setters
	public void setXFrom(int xFrom) { this.xFrom = xFrom; }
	public void setYFrom(int yFrom) { this.yFrom = yFrom; }
	public void setXYFrom(int xFrom, int yFrom) { this.xFrom = xFrom; this.yFrom = yFrom; }
	public void setXTo(int xTo) { this.xTo = xTo; }
	public void setYTo(int yTo) { this.yTo = yTo; }
	public void setXYTo(int xTo, int yTo) { this.xTo = xTo; this.yTo = yTo; }
	public void setXRange(int xRange) { this.xRange = xRange; }
	public void setYRange(int yRange) { this.yRange = yRange; }
	public void setXYRange(int xRange, int yRange) { this.xRange = xRange; this.yRange = yRange; }
	public void setFromID(int from_ID) { this.from_ID = from_ID; }
	public void setToID(int to_ID) { this.to_ID = to_ID; }
	public void setHiddenConnection(boolean hiddenConnection) { this.hiddenConnection = hiddenConnection; }
	public void setRequiresActivation(boolean requiresActivation) { this.requiresActivation = requiresActivation; }
	public void setRequiresItem(boolean requiresItem) { this.requiresItem = requiresItem; }
	public void setRequiredItemID(int requiredItemID) { this.requiredItemID = requiredItemID; }
	public void setDirectionFacing(int directionFacing) { this.directionFacing = directionFacing; }
	
}
