/*
 * Description: 		Let's see how we're going to deal with various maps
 * Date Created: 		Jun 16, 2015
 * Date Last Modified: 	Jun 16, 2015
 * Modification Notes:	
 */
package Location;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import Entity.Enemy;
import Entity.MapObject;
import Entity.NPC;
import Entity.PartyMember;
import Entity.Tree;
import Entity.Enemies.Slugger;
import Entity.Enemies.Snake;
import Entity.NPCs.SpiderNPC;
import TileMap.TileMap;

public class Location implements Serializable, LocationInterface {
	
	protected TileMap tileMap;			// TileMap used for tiles and repositioning of player & enemies.
	protected String name;				// Name to be displayed at the top (potentially) upon changing locations.
	protected int location_ID;			// ID number to identify each location.
	
	//protected Location previousLocation;		// This could be an unnecessary value, but I will record it just in case.
	protected ArrayList<LocationConnection> locationConnections;	// Holds all of the connections that exist between this location and other locations.

	// Values of which the player enters the location
	protected int startingX, startingY;
	
	// Holds the NPCs and Enemies at this location
	protected ArrayList<Enemy> enemies;
	protected ArrayList<NPC> NPCs;
	protected ArrayList<Tree> trees;
	protected Point[] points;
	
	// Constructor
	public Location() {
		// Basic Information
		name = "Starting Area";		// The generic Location constructor is currently for the spawn point of the game.
		location_ID = STARTING_LOCATION;
		startingX = 0;
		startingY = 0;
		
		// TileMap Information
		tileMap = new TileMap(32);	// Generates a TileMap with tiles of size 32x32
		tileMap.loadTiles("/Tilesets/twgt.png");	// Loads the twgt.png Tileset
		tileMap.loadMap("/Maps/level1-2.map");		// level1-2.map is the name of the map layout file for this location.
		tileMap.setPosition(0, 0);	// Sets the initial position of the player to (0,0) , a suitable starting point upon game spawn.
		tileMap.setSmoother(1); 	// Generic smoother value of 1.
		
		// Initializes the LocationConnection ArrayList
		locationConnections = new ArrayList<LocationConnection>();
		
		// Add connection between this location and SmallPond
		LocationConnection connection = new LocationConnection();
		connection.setXYFrom(726, 42);
		connection.setXYTo(52, 45);
		connection.setXYRange(16, 16);
		connection.setFromID(this.location_ID);
		connection.setToID(SMALL_POND);
		connection.setDirectionFacing(MapObject.FACING_RIGHT);
		locationConnections.add(connection);		// Add the connection to the ArrayList
		
		// Populate the three fields
		populateEnemies();
		populateNPCs();
		populateTrees();
	}
	
	// Getters
	public TileMap getTileMap() { return tileMap; }
	public String getLocationName() { return name; }
	public int getX() { return startingX; }
	public int getY() { return startingY; }
	public int getNumEnemies() { return enemies.size(); }
	public Enemy getEnemy(int x) { return enemies.get(x); }
	
	// Setters
	public void setXY(int startingX, int startingY) { this.startingX = startingX; this.startingY = startingY; }
	
	public void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		Snake snak;
		points = new Point[]{
			new Point(150, 65), 
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
	public void addEnemiesAsNecessary(){
		if(enemies.size() < 4){
			Random r = new Random(System.nanoTime());
			int i = r.nextInt() % 100;
			if(i == 1){
				Snake snak = new Snake(tileMap);
				//snak.setPosition(points[r.nextInt(8)].getX(), points[r.nextInt(8)].getY());	idk why but this is very glitchy
				snak.setPosition(points[0].getX(), points[0].getY());
				if(r.nextInt()%100 % 2 == 0) snak.setLeft();
				else snak.setRight();
				enemies.add(snak);
			}
		}
	}
	public void removeEnemy(int enemyPosition) {
		enemies.remove(enemyPosition);
	}
	public void updateEnemies() {
		for(int j = 0; j < enemies.size(); j++){
			Enemy e = enemies.get(j);
			enemies.get(j).update();
			((Snake) enemies.get(j)).randomMove();
			if(enemies.get(j).isDead()){
				enemies.remove(j);
				j--;
			}
		}
	}
	public void drawEnemies(Graphics2D g) {
		for(int j = 0; j < enemies.size(); j++){
			enemies.get(j).draw(g);
		}
	}
	public void populateNPCs() {
		NPCs = new ArrayList<NPC>();
		SpiderNPC spiderNPC1 = new SpiderNPC(tileMap);
		spiderNPC1.setPosition(75, 195);	// puts him on the blocked tile
		NPCs.add(spiderNPC1);
	}
	public void update() {
		updateEnemies();
	}
	public void drawNPCs(Graphics2D g) {
		for(int j = 0; j < NPCs.size(); j++){
			NPCs.get(j).draw(g);
		}
	}
	public void populateTrees() {
		trees = new ArrayList<Tree>();
		Point pxx = new Point(360 - (37), 120);
		Tree tree1 = new Tree(tileMap, pxx, Tree.PINETREE, true);
		trees.add(tree1);
	}
	public void drawTrees(Graphics2D g){
		for(int j = 0; j < trees.size(); j++){
			trees.get(j).draw(g);
		}
	}
	public boolean equals(Location loc) {
		if(this.location_ID == loc.location_ID) return true;
		return false;
	}
	public Location update(PartyMember pm) {
		addEnemiesAsNecessary();
		Location newLocation = null;	// Set the initial value to null as a default
		for(int j = 0; j < locationConnections.size(); j++){
			newLocation = locationConnections.get(j).checkTeleport(pm);	// Check if player is in zone for teleporting
			if(newLocation != null){	// If the player was in a legitimate area, a non-null location was returned.
				return newLocation;	// Break from the method to return the new location.
			}
		}
		return newLocation;
	}
}

/*
 * 		connection.setXYFrom();
		connection.setXYTo();
		connection.setXYRange();
		connection.setFromID();
		connection.setToID();
		connection.setHiddenConnection();
		connection.setRequiresActivation();
		connection.setRequiresItem();
		connection.setRequiredItemID();
		connection.setDirectionFacing();
 */