/*
 * Description: 		Class that represents the character's inventory. Consists of an arrayList of Item arrays.
 * Date Created: 		Jun 7, 2015
 * Date Last Modified: 	Jun 7, 2015
 * Modification Notes:	
 */
package UtilityClasses;

import java.io.Serializable;
import java.util.ArrayList;

import Entity.Item;
import Entity.Items.*;

public class Inventory implements Serializable {
	private ArrayList<Item[]> inv;
	
	private Item[] ingredients;
	private Item[] mobdrops;
	private Item[] gems;
	private Item[] useable;
	private Item[] food;
	private Item[] key;
	private Item[] weapons;
	private Item[] armor;
	private Item[] accessories;
	
	private int[] size;
	
	public Inventory() {
		inv = new ArrayList<Item[]>();
		ingredients = new Item[50];
		mobdrops = new Item[50];
		gems = new Item[50];
		useable = new Item[50];
		food = new Item[50];
		key = new Item[50];
		weapons = new Item[50];
		armor = new Item[50];
		accessories = new Item[50];
		inv.add(ingredients);
		inv.add(mobdrops);
		inv.add(gems);
		inv.add(useable);
		inv.add(food);
		inv.add(key);
		inv.add(weapons);
		inv.add(armor);
		inv.add(accessories);
		
		size = new int[9];
		for(int j = 0; j < 9; j++){
			size[j] = 0;
		}
	}
	public void add(Item a) {
		boolean found = false;
		for(int j = 0; j < size[a.getType()]; j++){
			if(inv.get(a.getType())[j].equals(a)){
				found = true;
				if(inv.get(a.getType())[j].isStackable()){
					inv.get(a.getType())[j].increaseStackCount();
				}
				else{
					a.increaseStackCount();
					inv.get(a.getType())[size[a.getType()]] = a;
					size[a.getType()]++;
					if(size[a.getType()] >= 49) System.out.println("TOO MANY ITEMS! INCREASE INVENTORY MAX SIZE");
				}
				break;
			}
		}
		if(!found){
			a.increaseStackCount();
			inv.get(a.getType())[size[a.getType()]] = a;
			size[a.getType()]++;
			if(size[a.getType()] >= 49) System.out.println("TOO MANY ITEMS! INCREASE INVENTORY MAX SIZE");
		}
		alphabetize(a.getType()); 		// Re-alphabetize the specific inventory section that added an item to it
	}
	public boolean remove(Item a) {		// returns true if the item was removed, false if stack count decremented
		for(int j = 0; j < size[a.getType()]; j++){	// This may need to be getType()]-1
			if(inv.get(a.getType())[j] == a){
				boolean fallsToZero = inv.get(a.getType())[j].decreaseStackCount();
				if(fallsToZero){	// These two should work
					if(j == (size[a.getType()]-1)){	// if the item to be removed is the last item in the array
						size[a.getType()]--;
						alphabetize(a.getType());
						return true;
					}
					else{	// if the item to be removed is not the last item
						inv.get(a.getType())[j] = inv.get(a.getType())[size[a.getType()]-1];	// sets the last item to that location
						size[a.getType()]--;
						alphabetize(a.getType());
						return true;
					}
				}
				break;
			}
		}
		return false;
	}
	public void alphabetize(int n) {
		if(size[n] == 1) return;		// No need to organize if only 1 value
		for(int j = 0; j < size[n] - 1; j++){
			if(inv.get(n)[j].getName().compareTo(inv.get(n)[j+1].getName()) > 0){
				Item temp = inv.get(n)[j];
				inv.get(n)[j] = inv.get(n)[j+1];
				inv.get(n)[j+1] = temp;
			}
		}
	}
	public void alphabetize() { 		// Total alphabetization, very inefficient, try not to use if possible.	
		for(int j = 0; j < 9; j++){
			for(int k = 0; k < size[j] - 1; k++){
				if(inv.get(j)[k].getName().compareTo(inv.get(j)[k+1].getName()) > 0){
					Item temp = inv.get(j)[k];
					inv.get(j)[k] = inv.get(j)[k+1];
					inv.get(j)[k+1] = temp;
				}
			}
		}
	}
	public ArrayList<Item> getEtc() {
		ArrayList<Item> returnables = new ArrayList<Item>();
		if(size[0] == 0 && size[1] == 0 && size[2] == 0){
			VoidItem voidItem = new VoidItem();
			returnables.add(voidItem);
			return returnables;
		}
		if(size[0] != 0){
			for(int j = 0; j < size[0]; j++){
				returnables.add(inv.get(0)[j]);
			}
		}
		if(size[1] != 0){
			for(int j = 0; j < size[1]; j++){
				returnables.add(inv.get(1)[j]);
			}
		}
		if(size[2] != 0){
			for(int j = 0; j < size[2]; j++){
				returnables.add(inv.get(2)[j]);
			}
		}
		return returnables;
	}
	public ArrayList<Item> getUseable() {
		ArrayList<Item> returnables = new ArrayList<Item>();
		if(size[3] == 0 && size[4] == 0){
			VoidItem voidItem = new VoidItem();
			returnables.add(voidItem);
			return returnables;
		}
		for(int j = 0; j < size[3]; j++){
			returnables.add(inv.get(3)[j]);
		}
		for(int j = 0; j < size[4]; j++){
			returnables.add(inv.get(4)[j]);
		}
		return returnables;
	}
	public ArrayList<Item> getKey() {
		ArrayList<Item> returnables = new ArrayList<Item>();
		if(size[5] == 0){
			VoidItem voidItem = new VoidItem();
			returnables.add(voidItem);
			return returnables;
		}
		for(int j = 0; j < size[5]; j++){
			returnables.add(inv.get(5)[j]);
		}
		return returnables;
		
	}
	public ArrayList<Item> getWeapons() {
		ArrayList<Item> returnables = new ArrayList<Item>();
		if(size[6] == 0){
			VoidItem voidItem = new VoidItem();
			returnables.add(voidItem);
			return returnables;
		}
		for(int j = 0; j < size[6]; j++){
			returnables.add(inv.get(6)[j]);
		}
		return returnables;
	}
	public ArrayList<Item> getArmor() {
		ArrayList<Item> returnables = new ArrayList<Item>();
		if(size[7] == 0 && size[8] == 0){
			VoidItem voidItem = new VoidItem();
			returnables.add(voidItem);
			return returnables;
		}
		for(int j = 0; j < size[7]; j++){
			returnables.add(inv.get(7)[j]);
		}
		for(int j = 0; j < size[8]; j++){
			returnables.add(inv.get(8)[j]);
		}
		return returnables;
	}
	public int getEtcCount() { return (size[0] + size[1] + size[2]); }
	public int getUseableCount() { return (size[3] + size[4]); }
	public int getKeyCount() { return size[5]; }
	public int getWeaponCount() { return size[6]; }
	public int getArmorCount() { return (size[7] + size[8]); }
	
}
