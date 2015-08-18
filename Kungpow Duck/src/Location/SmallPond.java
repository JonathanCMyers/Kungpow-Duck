/*
 * Description: 		Second map created in Achnic Incursions
 * Date Created: 		Jun 16, 2015
 * Date Last Modified: 	Jun 16, 2015
 * Modification Notes:	
 */
package Location;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Entity.Enemy;
import Entity.MapObject;
import Entity.NPC;
import Entity.Tree;
import Entity.Enemies.Snake;
import Entity.NPCs.SpiderNPC;
import NPC.Animal.Cow;
import TileMap.TileMap;

public class SmallPond extends Location implements Serializable, LocationInterface {
	
	public SmallPond() {
		
		// Basic Information
		name = "Small Pond";	
		location_ID = SMALL_POND;
		
		// TileMap Information
		tileMap = new TileMap(32);	// Generates a TileMap with tiles of size 32x32
		tileMap.loadTiles("/Tilesets/twgt.png");	// Loads the twgt.png Tileset
		tileMap.loadMap("/Maps/secondMap.map");		// level1-2.map is the name of the map layout file for this location.
		tileMap.setPosition(0, 0);	// Sets the initial position of the player to (0,0) , a suitable starting point upon game spawn.
		tileMap.setSmoother(1); 	// Generic smoother value of 1.
		
		// Initializes the LocationConnection ArrayList
		locationConnections = new ArrayList<LocationConnection>();
		
		// Add connection between this location and SmallPond
		LocationConnection connection = new LocationConnection();
		connection.setXYFrom(42, 45);
		connection.setXYTo(709, 42);
		connection.setXYRange(4, 4);
		connection.setFromID(this.location_ID);
		connection.setToID(STARTING_LOCATION);
		connection.setDirectionFacing(MapObject.FACING_LEFT);
		locationConnections.add(connection);		// Add the connection to the ArrayList
		
		// Populate the three fields
		//populateEnemies();
		populateNPCs();
		populateTrees();
	}

	public void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		Snake snak;
		points = new Point[]{
			new Point(200, 200), 
			new Point(200, 100),
			new Point(273, 143), 
			new Point(289, 189),
			new Point(500, 220),
			new Point(860, 200),
			new Point(1525, 200), 
			new Point(1680, 200), 
			new Point(1800, 200),
			new Point(860, 300),
			new Point(500, 200),
			new Point(1800, 300)
		}; 
		
		for(int j = 0; j < 1; j++){
			snak = new Snake(tileMap);
			snak.setPosition(points[j].x, points[j].y);
			if(j%2 == 0) snak.setRight();
			else snak.setLeft();
			enemies.add(snak);
		}	
	}

	public void addEnemiesAsNecessary() {
		/*
		if(enemies.size() < 3){
			Random r = new Random(System.nanoTime());
			int i = r.nextInt() % 77;
			if(i == 1){
				Snake snak = new Snake(tileMap);
				//snak.setPosition(points[r.nextInt(8)].getX(), points[r.nextInt(8)].getY());	idk why but this is very glitchy
				snak.setPosition(points[0].getX()+r.nextInt()%50, points[0].getY()+r.nextInt()%50);
				if(r.nextInt()%100 % 2 == 0) snak.setLeft();
				else snak.setRight();
				enemies.add(snak);
			}
		}
		*/
	}

	public void update() {
		updateEnemies();
		updateNPCs();
	}
	
	public void updateEnemies() {
		/*
		for(int j = 0; j < enemies.size(); j++){
			Enemy e = enemies.get(j);
			enemies.get(j).update();
			((Snake) enemies.get(j)).randomMove();
			if(enemies.get(j).isDead()){
				enemies.remove(j);
				j--;
			}
		} */
		enemies.clear();
	}

	public void populateNPCs() {
		NPCs = new ArrayList<NPC>();
		Cow cow = new Cow(tileMap);
		cow.setPosition(75, 95);
		NPCs.add(cow);
	}
	
	public void updateNPCs() {
		for(int j = 0; j < NPCs.size(); j++){
			NPCs.get(j).update();
		}
	}

	public void populateTrees() {
		trees = new ArrayList<Tree>();
	}
}
