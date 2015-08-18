package Chatbox;

import Entity.Chatbox;
import Main.GamePanel;

import java.awt.*;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class SkillDescriptionChatbox extends Chatbox implements Serializable {
	private Font font;
	
	public static final int NORMAL = 1;
	public static final int BOLD = 2;
	public static final int ITALIC = 3;
	
	private String descriptionString;
	private String cost;
	private boolean noCost;
	private boolean alreadyPutInCost;
	
	private String t;
	private int x;
	private int y;
	private int heightModifier;
	private int columnModifier;
	private int height;
	private int width;
	private boolean hasStringCost;
	
	public SkillDescriptionChatbox(String description, String cost){
		height = 27;
		width = 320;
		alreadyPutInCost = false;
		if(cost == null)
			noCost = true;
		this.cost = cost;
		descriptionString = description;
		font = new Font("Arial", Font.PLAIN, 11);
		x = 8;
		y = GamePanel.HEIGHT - 90;
		heightModifier = 24;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/description3.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		hasStringCost = true;
	}
	
	public SkillDescriptionChatbox(String description, Integer cost){
		height = 27;
		width = 320;
		alreadyPutInCost = false;
		if(cost <= 0)
			noCost = true;
		this.cost = cost.toString();
		descriptionString = description;
		font = new Font("Arial", Font.PLAIN, 11);
		x = 8;
		y = GamePanel.HEIGHT - 90;
		heightModifier = 24;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/description3.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		hasStringCost = false;
	}

	@Override
	public void draw(Graphics2D g){
		g.drawImage(image, 0, GamePanel.HEIGHT - 108, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(descriptionString, x, y);
		g.setColor(Color.BLUE);
		g.drawString("Cost: " + cost,  GamePanel.WIDTH - 60, y);

		
		/*if(!noCost && hasStringCost)
			g.drawString(cost, x + descriptionString.length(), y);
		if(!noCost && !hasStringCost)
			g.drawString(icost.toString(), x + descriptionString.length(), y); */
	}
}
