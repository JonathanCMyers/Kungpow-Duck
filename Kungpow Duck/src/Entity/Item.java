/*
 * Description: 		Base Class for all items
 * Date Created: 		6/7/2015
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	
 */

package Entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Item implements Serializable {
	
	// List of types that each item can be. Can be accessed from other classes.
	public static final int INGREDIENT = 0;
	public static final int MOBDROP = 1;
	public static final int GEM = 2;
	public static final int USEABLE = 3;
	public static final int FOOD = 4;
	public static final int KEY = 5;
	public static final int WEAPON = 6;
	public static final int ARMOR = 7;
	public static final int ACCESSORY = 8;
	public static final int VOID = -1;
	
	// These are initialized within the Entity.Items classes, so they do not need to be 
	//  initialized in the individual item class.
	protected int type;
	protected boolean canUse;
	protected boolean canEat;		// This is subject to deletion
	protected boolean canEquip;
	protected boolean stackable;	// if stackable is false, there will not be an item count next to the item
	protected transient BufferedImage icon;
	
	// These need to be initialized in the individual item classes under Entity.Items.(RespectableType)
	protected String name;
	protected String description;
	protected int descriptionLines;
	protected boolean droppedByMob; // This will mainly be determined on a per-item basis, except for MobDrop items.
	protected int mobDropCount;
	protected Enemy[] mobsThatDrop;
	protected double[] mobDropChance;	// Lists the drop chance of each mob that can drop the item
	protected int stackCount;
	
	protected String descriptionByLine[];
	
	public int getType() { return type; }
	public boolean canUse() { return canUse; }
	public boolean canEat() { return canEat; }
	public boolean canEquip() { return canEquip; }
	public boolean isStackable() { return stackable; }
	public boolean isDroppedByMob() { return droppedByMob; }
	
	public String getName() { return name; }
	public String getDescription() { return description; }
	
	public int getDescriptionLines() { return descriptionLines; }
	public String getDescriptionByLine(int n) { return descriptionByLine[n]; }
	
	public String getNameAndStacks() { 
		String nameandstacks = "";
		if(stackable){
			nameandstacks = name + " x" + stackCount;
		}
		else{
			nameandstacks = name;
		}
		return nameandstacks;
	}
	
	public BufferedImage getIcon() { return icon; }
	public void setIcon(BufferedImage icon) { this.icon = icon; }
	public void setIcon(String fileName) {
		try{
			icon = ImageIO.read(getClass().getResourceAsStream("/ItemIcons/" + fileName));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean equals(Item a){
		boolean isSame = false;
		if(this.name == a.name){
			isSame = true;
		}
		return isSame;
	}
	
	public int getMobDropCount() { return mobDropCount; }
	public Enemy[] getMobsThatDrop() { return mobsThatDrop; }
	public double[] getmobDropChance() { return mobDropChance; }
	public int getStackCount() { return stackCount; }
	public void increaseStackCount() { stackCount++; }
	public boolean decreaseStackCount() { 		// Returns true if the stackCount falls to zero
		stackCount--;
		if(stackCount == 0) return true;
		return false;
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(icon, "png", out);
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		icon = ImageIO.read(in);
	}
}
