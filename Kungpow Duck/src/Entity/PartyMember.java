/*
 * Description: 		Base Class for all items
 * Date Created: 		Unknown
 * Date Last Modified: 	6/7/2015
 * Modification Notes:	Added weapons, armor & accessory arraylists. Implemented functions for the equip and un-equip of the three.
 */

package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;
import TileMap.TileMap;
import Ability.*;
import Entity.Items.Accessory;
import Entity.Items.Armor;
import Entity.Items.Weapon;

public class PartyMember extends MapObject implements Serializable {
	
	protected ArrayList<Ability> knownAbilities;
	protected Animation facingLeftAnimation;
	
	protected transient BufferedImage battleBorder;
	
	protected ArrayList<Integer> weaknesses;
	protected ArrayList<Integer> resistances;
	
	protected String name;
	protected int partyMemberID;
	
	protected int health;
	protected int maxHealth;
	protected int mana;
	protected int maxMana;
	
	protected int numAbilities;
	protected boolean dead;
	
	protected ArrayList<Weapon> weapons;
	protected ArrayList<Armor> armor;
	protected ArrayList<Accessory> accessories;
	
	protected int maxWeapons;
	protected int maxAccessories;
	
	private int level;
	private int currentExp;
	
	private int[] expPerLevel;
	
	private int strength;
	private int dexterity;
	private int intelligence;
	private int luck;
	private int agility;
	
	// this section is devoted to stat modifiers
	private int speedAdditive, speedDeficit;
	private int dodgeAdditive, dodgeDeficit;
	private int critAdditive, critDeficit;
	private int critMitigateAdditive, critMitigateDeficit;

	public PartyMember(TileMap tm) {
		super(tm);
		// the following three initializations may have to be done individually
		weapons = new ArrayList<Weapon>();
		armor = new ArrayList<Armor>();
		accessories = new ArrayList<Accessory>();
		maxWeapons = 1;
		maxAccessories = 1;
		
		level = 1;
		expPerLevel = new int[100];
		expPerLevel[0] = 10;
		for(int j = 1; j < 100; j++){
			Double d = expPerLevel[j-1]*1.6;
			expPerLevel[j] = d.intValue();
		}
		strength = 0;
		dexterity = 0;
		intelligence = 0;
		luck = 0;
		agility = 0;
	}
	
	public int getPartyMemberID() { return partyMemberID; }
	public Animation getFacingLeftAnimation() { return facingLeftAnimation; }
	public Ability getBasicAttack() { return knownAbilities.get(0); }
	public int getCurrentHP() { return health; }
	public int getMaxHP() { return maxHealth; }
	public int getCurrentMana() { return mana; }
	public int getMaxMana() { return maxMana; }
	public int getNumAbilities() { return numAbilities; }
	public ArrayList<Ability> getAbilities() { return knownAbilities; }
	public boolean isDead() { return dead; }
	
	public ArrayList<Weapon> getWeapons() { return weapons; }
	public ArrayList<Armor> getArmor() { return armor; }
	public ArrayList<Accessory> getAccessories() { return accessories; }
	
	public boolean hasWeapon(Weapon w) { return weapons.contains(w); }
	public boolean hasArmor(Armor a){ return armor.contains(a);	}
	public boolean hasAccessory(Accessory a) { return accessories.contains(a); }
	
	public int getLevel() { return level; }
	public int getCurrentExp() { return currentExp; }
	public int getRemainingExpUntilNextLevel() { return expPerLevel[level-1] - currentExp; }
	public int getExpRequiredForLevel(int level) { return expPerLevel[level-1]; }
	public int getSTR() { return strength; }
	public int getDEX() { return dexterity; }
	public int getINT() { return intelligence; }
	public int getLCK() { return luck; }
	public int getAGI() { return agility; }
	
	public int getSpeed() { return agility*2 + speedAdditive - speedDeficit; }
	public int getDodge() { return dexterity/4 + dodgeAdditive - dodgeDeficit; }
	
	public void levelUp() { level++; currentExp = 0; }
	public void strUp() { strength++; }
	public void dexUp() { dexterity++; }
	public void intUp() { intelligence++; }
	public void luckUp() { luck++; }
	public void agiUP() { agility++; }
	public void expUp() { 
		currentExp++;
		if(currentExp >= expPerLevel[level-1]){
			currentExp = currentExp - expPerLevel[level-1];
			level++;
		}
	}
	
	public void levelUp(int n) { level = level + n; currentExp = 0; }
	public void strUp(int n) { strength+=n; }
	public void dexUp(int n) { dexterity+=n; }
	public void intUp(int n) { intelligence+=n; }
	public void luckUp(int n) { luck+=n; }
	public void expUp(int n) { 
		currentExp+=n;
		if(currentExp >= expPerLevel[level-1]){
			currentExp = currentExp - expPerLevel[level-1];
			level++;
		}
	}
	
	public int equipWeapon(Weapon w){
		if(weapons.contains(w)) return 0;	// 0 = condition where weapon of this type is already equipped
		else{
			if(maxWeapons == 1 && weapons.size() > 0)
				return 1;	// 1 = condition where prompted to equip selected weapon over current weapon
		}
		weapons.add(w);	
		return 2;		// 2 = condition where weapon successfully equipped
	}
	public Weapon equipWeaponOverCurrentWeapon(Weapon w) {
		Weapon w2 = weapons.get(0);
		weapons.clear();
		weapons.add(w);
		return w2;
	}
	public int equipArmor(Armor a){
		if(armor.contains(a)) return 0;	// 0 = condition where armor of this type is already equipped
		else{
			for(int j = 0; j < armor.size(); j++){
				if(a.getArmorType() == armor.get(j).getArmorType()){
					return 1;	// 1 = condition where prompted to equip armor over currently equipped armor
				}
			}
		}
		armor.add(a);	
		return 2;		// 2 = condition where weapon successfully equipped
	}
	public Armor equipArmorOverCurrentArmor(Armor a){
		Armor a2 = armor.get(0);
		for(int j = 0; j < armor.size(); j++){
			if(armor.get(j).getArmorType() == a.getArmorType()){
				a2 = armor.get(j);
				armor.remove(j);
				armor.add(a);
				break;
			}
		}
		return a2;
	}
	public int equipAccessory(Accessory a){
		if(accessories.contains(a) && accessories.size() == maxAccessories) return 0;
		else if(accessories.size() == maxAccessories) return 1;
		else if(accessories.contains(a) && accessories.size() < maxAccessories) return 2;
		else return 3;
	}
	public Accessory equipAccessoryOverCurrentAccessory(Accessory a, int num){
		Accessory a2 = accessories.get(num);
		accessories.remove(num);
		accessories.add(a);
		return a2;
	}
	
	public boolean applyDamage(Ability enemyAttack) {
		Random r = new Random(System.nanoTime());
		double d = r.nextDouble();
		double damage = enemyAttack.getDamage();
		if(d > enemyAttack.getAccuracy()){
			return false;
		}
		else{
			
			if(weaknesses.size() > 0){
				for(int j = 0; j < weaknesses.size(); j++){
					if(enemyAttack.getTypeOfDamage() == weaknesses.get(j)){
						damage = damage * 1.5;
						break;
					}
				}
			}
			
			else if(resistances.size() > 0){
				for(int j = 0; j < resistances.size(); j++){
					if(enemyAttack.getTypeOfDamage() == resistances.get(j)){
						damage = damage * .667;
					}
				}
			}
			
			health = health - (int) damage;
			return true;
		}
	}	
	
	public boolean applyAbility(Ability attack){
		Random r = new Random(System.nanoTime());
		double d = r.nextDouble();
		boolean ret = false;
		if(attack.appliesToEnemy()){
			double damage = attack.getDamage();
			if(d > attack.getAccuracy()){
				ret = false;
			}
			else{
				
				if(weaknesses.size() > 0){
					for(int j = 0; j < weaknesses.size(); j++){
						if(attack.getTypeOfDamage() == weaknesses.get(j)){
							damage = damage * 1.5;
							break;
						}
					}
				}
				else if(resistances.size() > 0){
					for(int j = 0; j < resistances.size(); j++){
						if(attack.getTypeOfDamage() == resistances.get(j)){
							damage = damage * .667;
						}
					}
				}
				
				health = health - (int) damage;
				ret = true;
			}
		}
		else if(attack.appliesToAlly()){
			if(attack.isHeal()){
				if(d > attack.getAccuracy()){
					ret = false;
				}
				else{
					health = health + attack.getDamage();
					ret = true;
				}
			}
		}
		
		return ret;
	}
	
	public boolean canReceiveAbility(Ability attack){
		// This needs complete overhaul
		return true;
	}
	
	public void drawBorder(Graphics2D g, int n) {
		int xposition = n*64 + 1;
		int yposition = GamePanel.HEIGHT - 91;
		g.drawImage(battleBorder, xposition, yposition, null); 
		//g.drawImage(battleBorder, 12, GamePanel.HEIGHT - 60, null);
		//System.out.println("drawn");
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(battleBorder, "png", out);
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		battleBorder = ImageIO.read(in);
	}
	
	/*
	public String dismantle() {
		String dismantle = "";
		dismantle+="_PARTY"+name+":";
		dismantle+="_abil";
		for(int j = 0; j < knownAbilities.size(); j++)
			dismantle+=knownAbilities.get(j).dismantle()+"!";
		dismantle+="_wea";
		for(int j = 0; j < weaknesses.size(); j++)
			dismantle+=weaknesses.get(j)+"!";
		dismantle+="_res";
		for(int j = 0; j < resistances.size(); j++)
			dismantle+=resistances.get(j)+"!";
		dismantle+="_O";
		dismantle+=level+"!";
		dismantle+=currentExp+"!";
		dismantle+=maxHealth+"!";
		dismantle+=health+"!";
		dismantle+=maxMana+"!";
		dismantle+=mana+"!";
		dismantle+=numAbilities+"!";
		dismantle+=dead+"!";
		dismantle+=strength+"!"+dexterity+"!"+intelligence+"!"+luck+"!"+agility+"!";
		dismantle+=speedAdditive+"!"+speedDeficit+"!";
		dismantle+=dodgeAdditive+"!"+dodgeDeficit+"!";
		dismantle+=critAdditive+"!"+critDeficit+"!";
		dismantle+=critMitigateAdditive+"!"+critMitigateDeficit+"!";
		dismantle+=maxWeapons+"!";
		dismantle+=maxAccessories+"!";
		dismantle+="_wpn";
		for(int j = 0; j < weapons.size(); j++)
			dismantle+=weapons.get(j).dismantle()+"!";
		dismantle+="_arm";
		for(int j = 0; j < armor.size(); j++)
			dismantle+=armor.get(j).dismantle()+"!";
		dismantle+="_acc";
		for(int j = 0; j < accessories.size(); j++)
			dismantle+=accessories.get(j).dismantle+"!";
		dismantle+="_exl";
		for(int j = 0; j < 100; j++)
			dismantle+=expPerLevel[j]+"!";
		dismantle+=".";
		return dismantle;
	}
	public PartyMember remantle(String dismantle, TileMap tm){
		PartyMember p;
		char[] c = dismantle.toCharArray();
		int seekPoint = 0;
		seekPoint+=6;
		if(c[seekPoint] == 'C' && c[seekPoint+1] == 'a'){
			seekPoint+=7;
			p = new Carrie(tm);
		}
		else if(c[seekPoint] == 'P' && c[seekPoint+1] == 'l'){
			p = new Player(tm);
			seekPoint+=7;
		}
		else{	// Something bizarre has happened & return a null PartyMember
			p = new PartyMember(tm);
		}
		if(c[seekPoint] != '_'){
			System.out.println("SOMETHING WRONG HAS HAPPENED");
		}
		else{
			System.out.println("True: " + c[seekPoint] + c[seekPoint+1] + c[seekPoint+2] + c[seekPoint+3] + c[seekPoint+4]);
			System.out.println("Theorized: _abil\n");
			seekPoint+=5;
			while(c[seekPoint]!='_'){
				String abil = "";
				while(c[seekPoint]!='!'){
					abil+=c[seekPoint];
					seekPoint++;
				}
				p.addAbility(Ability.remantle(abil));
			}
			System.out.println("True: " + c[seekPoint] + c[seekPoint+1] + c[seekPoint+2] + c[seekPoint+3]);
			System.out.println("Theorized: _wea\n");
			seekPoint+=4;
			p.resistances = new ArrayList<Integer>();
			while(c[seekPoint]!='_'){
				p.resistances.add( (int) c[seekPoint] );
				seekPoint++;
			}
			System.out.println("True: " + c[seekPoint] + c[seekPoint+1] + c[seekPoint+2] + c[seekPoint+3]);
			System.out.println("Theorized: _res\n");
			seekPoint+=4;
			p.weaknesses = new ArrayList<Integer>();
			while(c[seekPoint]!='_'){
				p.weaknesses.add( (int) c[seekPoint] );
				seekPoint++;
			}
			System.out.println("True: " + c[seekPoint] + c[seekPoint+1]);
			System.out.println("Theorized: _O\n");
			String x = "";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
				seekPoint++;
			}
			seekPoint++;
			p.level = Integer.parseInt(x);
			x="";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
			}
			seekPoint++;
			p.currentExp = Integer.parseInt(x);
			x="";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
			}
			seekPoint++;
			p.maxHealth = Integer.parseInt(x);
			x="";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
			}
			seekPoint++;
			p.health = Integer.parseInt(x);
			x="";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
			}
			seekPoint++;
			p.maxMana = Integer.parseInt(x);
			x="";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
			}
			seekPoint++;
			p.mana = Integer.parseInt(x);
			x="";
			while(c[seekPoint]!='!'){
				x+=c[seekPoint];
			}
			seekPoint++;
			p.numAbilities = Integer.parseInt(x);
			x="";
			System.out.println(c[seekPoint] + c[seekPoint+1] + c[seekPoint+2] + c[seekPoint+3]);
		}
		

		/*
		dismantle+=dead+"!";
		dismantle+=strength+"!"+dexterity+"!"+intelligence+"!"+luck+"!"+agility+"!";
		dismantle+=speedAdditive+"!"+speedDeficit+"!";
		dismantle+=dodgeAdditive+"!"+dodgeDeficit+"!";
		dismantle+=critAdditive+"!"+critDeficit+"!";
		dismantle+=critMitigateAdditive+"!"+critMitigateDeficit+"!";
		dismantle+=maxWeapons+"!";
		dismantle+=maxAccessories+"!";
		dismantle+="_wpn";
		for(int j = 0; j < weapons.size(); j++)
			dismantle+=weapons.get(j).dismantle()+"!";
		dismantle+="_arm";
		for(int j = 0; j < armor.size(); j++)
			dismantle+=armor.get(j).dismantle()+"!";
		dismantle+="_acc";
		for(int j = 0; j < accessories.size(); j++)
			dismantle+=accessories.get(j).dismantle+"!";
		dismantle+="_exl";
		for(int j = 0; j < 100; j++)
			dismantle+=expPerLevel[j]+"!";
		dismantle+=".";
		
		
		
		return p;
	} */
}
