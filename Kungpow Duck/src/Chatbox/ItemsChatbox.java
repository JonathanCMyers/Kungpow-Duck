package Chatbox;

import Arrow.BlackDownArrow;
import Arrow.BlackUpArrow;
import Arrow.BlueArrow;
import Entity.Chatbox;
import Entity.Item;
import UtilityClasses.Inventory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ItemsChatbox extends Chatbox implements Serializable {
	private transient BufferedImage itemDescription;
	private Font font;
	private Font tabFont;
	private Font smallFont;
	
	private BlackDownArrow bda;
	private BlackUpArrow bua;
	
	private BlueArrow slotSelectionArrow;
	
	private int x;
	private int y;
	private int slotY;

	private int arrowX[];
	private int arrowY[];
	private String options[];
	
	private boolean isOpen;
	private int itemSlotNumber;
	
	private boolean[] isEmpty;
	private boolean[] itemOverflow;
	
	private int[] lowerBound;
	private int[] upperBound;
	private int[] currentChoice;
	
	private ArrayList<ArrayList<Item>> items;
	private ArrayList<Item> tempItemSlot;
	
	public ItemsChatbox(Inventory inv){

		hasArrows = true;
		font = new Font("Sathu", Font.PLAIN, 11);
		tabFont = new Font("Sathu", Font.BOLD, 10);
		smallFont = new Font("Sathu", Font.PLAIN, 9);
		maxPositions = 11;
		x = -1;
		y = 25;
		isOpen = false;
		arrowY = new int[maxPositions];
		arrowX = new int[maxPositions];
		lowerBound = new int[5];
		upperBound = new int[5];
		currentChoice = new int[5];
		for(int j = 0; j < 5; j++){
			lowerBound[j] = 0;
			upperBound[j] = 0;
			currentChoice[j] = 0;
		}
		
		itemSlotNumber = 0;
		slotY = 10;
		
		arrowX[0] = 7;
		arrowX[1] = 55;
		arrowX[2] = 105;
		arrowX[3] = 144;
		arrowX[4] = 200;
		for(int j = 0; j < maxPositions; j++){
			arrowY[j] = j*13+32;
		}
		
		slotSelectionArrow = new BlueArrow(arrowX[itemSlotNumber], slotY);
		ba = new BlueArrow(x+5, arrowY[0]);
		bda = new BlackDownArrow(105, 165);		// These are bizarre parameters but leave them for now
		bua = new BlackUpArrow(105, 28);

		items = new ArrayList<ArrayList<Item>>();
		tempItemSlot = inv.getEtc();
		items.add(tempItemSlot);
		tempItemSlot = inv.getUseable();
		items.add(tempItemSlot);
		tempItemSlot = inv.getKey();
		items.add(tempItemSlot);
		tempItemSlot = inv.getWeapons();
		items.add(tempItemSlot);
		tempItemSlot = inv.getArmor();
		items.add(tempItemSlot);
		
		isEmpty = new boolean[5];
		itemOverflow = new boolean[5];
		
		for(int j = 0; j < 5; j++){
			if(items.get(j).get(0).getType() == Item.VOID) isEmpty[j] = true;
			else isEmpty[j] = false;
			itemOverflow[j] = false;
		}
		
		if(inv.getEtcCount() > maxPositions) itemOverflow[0] = true;
		if(inv.getUseableCount() > maxPositions) itemOverflow[1] = true;
		if(inv.getKeyCount() > maxPositions) itemOverflow[2] = true;
		if(inv.getWeaponCount() > maxPositions) itemOverflow[3] = true;
		if(inv.getArmorCount() > maxPositions) itemOverflow[4] = true;

		for(int j = 0; j < 5; j++){
			if(!isEmpty[j] && !itemOverflow[j]){
				lowerBound[j] = 0;
				upperBound[j] = items.get(j).size() - 1;
			}
			else if(itemOverflow[j]){
				upperBound[j] = maxPositions - 1;
			}
		}
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/itemsChatbox.png"));
			itemDescription = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/itemDescriptionChatbox.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isOpen() { return isOpen; }
	
	@Override
	public void goUp() {
		if(!isEmpty[itemSlotNumber] && currentChoice[itemSlotNumber] != 0){
			arrowPosition--;
			currentChoice[itemSlotNumber]--;
			if(currentChoice[itemSlotNumber] < lowerBound[itemSlotNumber] && lowerBound[itemSlotNumber] > 0){
				lowerBound[itemSlotNumber]--;
				upperBound[itemSlotNumber]--;
				arrowPosition++;
			}
			else if(currentChoice[itemSlotNumber] < lowerBound[itemSlotNumber] && lowerBound[itemSlotNumber] == 0){
				arrowPosition++;
				currentChoice[itemSlotNumber]++;
			}
			ba.setXY(x+5, arrowY[arrowPosition]);
		}
	}
	
	@Override
	public void goDown() {
		if(!isEmpty[itemSlotNumber] && (currentChoice[itemSlotNumber] != items.get(itemSlotNumber).size() - 1)){	// Code Bandaid
			arrowPosition++;
			currentChoice[itemSlotNumber]++;
			if(currentChoice[itemSlotNumber] > upperBound[itemSlotNumber] && upperBound[itemSlotNumber] < items.get(itemSlotNumber).size() && itemOverflow[itemSlotNumber]){
				lowerBound[itemSlotNumber]++;
				upperBound[itemSlotNumber]++;
				arrowPosition--;
			}
			else if((currentChoice[itemSlotNumber] > upperBound[itemSlotNumber]) && (!itemOverflow[itemSlotNumber])){
				arrowPosition--;
				currentChoice[itemSlotNumber]--;
			}
			ba.setXY(x+5, arrowY[arrowPosition]);
		}
	}
	@Override
	public void goLeft() {
		itemSlotNumber--;
		if(itemSlotNumber < 0) itemSlotNumber++;
		slotSelectionArrow.setXY(arrowX[itemSlotNumber], slotY);
		arrowPosition = currentChoice[itemSlotNumber];
		ba.setXY(x+5, arrowY[arrowPosition]);
	}
	@Override
	public void goRight() {
		itemSlotNumber++;
		if(itemSlotNumber > 4) itemSlotNumber--;
		slotSelectionArrow.setXY(arrowX[itemSlotNumber], slotY);
		arrowPosition = currentChoice[itemSlotNumber];
		ba.setXY(x+5, arrowY[arrowPosition]);
	}
	
	public void open() {
		isOpen = true;
		System.out.println("opened");
	}
	public void close() {
		isOpen = false;
	}
	public void enter() {
		
	}

	@Override
	public void draw(Graphics2D g){
		if(isOpen){
			g.drawImage(image, x, 0, null);   
			g.setFont(tabFont);
			g.setColor(Color.BLACK);
			g.drawString("Etc", 18, 18);
			g.drawString("Use", 66, 18);
			g.drawString("Key", 116, 18);
			g.drawString("Weapons", 154, 18);
			g.drawString("Armor", 210, 18);
			g.setFont(font);
			if(isEmpty[itemSlotNumber]){
				g.setColor(Color.DARK_GRAY);
				g.drawString("Empty", 34, 55);
			}
			else{
				for(int j = lowerBound[itemSlotNumber], counter = 0; j <= upperBound[itemSlotNumber]; j++, counter++){
					if(j - lowerBound[itemSlotNumber] != 11){
						if(currentChoice[itemSlotNumber] == j){
							g.drawImage(items.get(itemSlotNumber).get(j).getIcon(), x+17, 13*counter + 30, null);
							g.drawString(items.get(itemSlotNumber).get(j).getNameAndStacks(), x+31, 13*counter + 40);
						}
						else{
							g.drawImage(items.get(itemSlotNumber).get(j).getIcon(), x+12, 13*counter + 30, null);
							g.drawString(items.get(itemSlotNumber).get(j).getNameAndStacks(), x+26, 13*counter + 40);
						}
					}
				}
			}
			slotSelectionArrow.draw(g);
			if(!isEmpty[itemSlotNumber]){
				ba.draw(g);
			}
			g.drawImage(itemDescription, 112, 30, null);
			g.setFont(smallFont);
			g.setColor(Color.DARK_GRAY);
			for(int j = 0; j < items.get(itemSlotNumber).get(currentChoice[itemSlotNumber]).getDescriptionLines(); j++){
				g.drawString(items.get(itemSlotNumber).get(currentChoice[itemSlotNumber]).getDescriptionByLine(j), 120, 48 + j*12);
			}
			if(itemOverflow[itemSlotNumber] && upperBound[itemSlotNumber]!=items.get(itemSlotNumber).size()-1){
				bda.draw(g);
			}
			if(lowerBound[itemSlotNumber] != 0){
				bua.draw(g);
			}
		}
	}
	public void printBefore() {
		System.out.println("\nBefore:\n");
		System.out.println("Arrow Position: " + arrowPosition);
		System.out.println("Current Position: " + currentChoice[itemSlotNumber]);
		System.out.println("Lower Bound: " + lowerBound[itemSlotNumber]);
		System.out.println("Upper Bound: " + upperBound[itemSlotNumber]);
	}
	public void printAfter() {
		System.out.println("\nAfter:\n");
		System.out.println("Arrow Position: " + arrowPosition);
		System.out.println("Current Position: " + currentChoice[itemSlotNumber]);
		System.out.println("Lower Bound: " + lowerBound[itemSlotNumber]);
		System.out.println("Upper Bound: " + upperBound[itemSlotNumber]);
	}
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(itemDescription, "png", out);
	}
	private void readObject(ObjectInputStream in)  throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		itemDescription = ImageIO.read(in);
	}
}

