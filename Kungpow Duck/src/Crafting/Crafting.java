/*
 * Description: 		Crafting class that all other crafting classes extend from, contains basic variables & functions
 * Date Created: 		Jun 18, 2015
 * Date Last Modified: 	Jun 18, 2015
 * Modification Notes:	
 */
package Crafting;

import java.io.Serializable;
import Entity.PartyMembers.PartyMemberInterface;

public class Crafting implements Serializable, CraftingInterface, PartyMemberInterface {
	private int currentLevel;
	private int maxLevel;
	private int currentExperience;
	private int[] experiencePerLevel;
	private int partyMemberID;
	private boolean mastery;
	
	public Crafting() {
		currentLevel = 1;
		maxLevel = 5;
		currentExperience = 0;
		experiencePerLevel = new int[]{100, 200, 300, 500, 1000};
		partyMemberID = ID_NULLPARTYMEMBER;
		mastery = false;
	}
	
	// Getters
	public int getCurrentLevel() { return currentLevel; }
	public int getMaxLevel() { return maxLevel; }
	public int getCurrentExperience() { return currentExperience; }
	public int getExperienceRequired(int level) { return experiencePerLevel[level-1]; }
	public int getPartyMemberID() { return partyMemberID; }
	
	// Setters
	public void setCurrentLevel(int currentLevel) { this.currentLevel = currentLevel; }
	public void setCurrentExperience(int currentExperience){
		this.currentExperience = currentExperience; 
		checkLevelUp();
	}
	public void addExperience(int exp) {
		currentExperience+=exp;
		checkLevelUp();
	}
	public void checkLevelUp() {
		if(currentLevel < maxLevel && currentExperience >= experiencePerLevel[currentLevel-1]){
			currentExperience = (currentExperience - experiencePerLevel[currentLevel-1]);
			currentLevel++;
		}
		if(currentLevel == maxLevel && currentExperience >= experiencePerLevel[currentLevel-1]){
			currentExperience = experiencePerLevel[currentLevel-1];
			mastery = true;
		}
	}
}
