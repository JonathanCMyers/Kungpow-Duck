package Chatbox;

import Entity.Chatbox;
import Main.GamePanel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BattleChatbox extends Chatbox implements Serializable {
	private Font font;
	
	private boolean secondColumn;
	private boolean thirdColumn;
	
	public static final int NORMAL = 1;
	public static final int BOLD = 2;
	public static final int ITALIC = 3;
	
	private ArrayList<String> strings;
	private String[] stringA;
	
	private boolean usingList;
	private boolean usingArray;
	
	private String s;
	private String t;
	private int x;
	private int y;
	private int heightModifier;
	private int columnModifier;
	
	public BattleChatbox(ArrayList<String> strings){
		font = new Font("Arial", Font.PLAIN, 11);
		usingList = true;
		usingArray = false;
		x = 12;
		y = GamePanel.HEIGHT - 60;
		heightModifier = 24;
		columnModifier = 110;
		secondColumn = false;
		thirdColumn = false;
		this.strings = strings;
		if(strings.size() > 3) secondColumn = true;
		if(strings.size() > 6) thirdColumn = true;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/battlechatbox1.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public BattleChatbox(String[] stringA){
		font = new Font("Arial", Font.PLAIN, 11);
		usingList = false;
		usingArray = true;
		x = 12;
		y = GamePanel.HEIGHT - 60;
		heightModifier = 24;
		columnModifier = 110;
		secondColumn = false;
		thirdColumn = false;
		this.stringA = stringA;
		if(stringA.length > 3) secondColumn = true;
		if(stringA.length > 6) thirdColumn = true;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/battlechatbox1.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		strings = new ArrayList<String>();
		for(int j = 0; j < stringA.length; j++){
			strings.add(stringA[j]);
		}
	}
	
	public boolean hasSecondColumn() { return secondColumn; }
	public boolean hasThirdColumn() { return thirdColumn; }
	
	@Override
	public void draw(Graphics2D g){
		g.drawImage(image, 0, GamePanel.HEIGHT - 80, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		if(strings.size() > 3) secondColumn = true;
		if(strings.size() > 6) thirdColumn = true;
		for(int j = 0; j < strings.size() && j < 3; j++){
			g.drawString(strings.get(j), x, y + j*heightModifier);
		}
		if(secondColumn){
			for(int j = 3; j < strings.size(); j++){
				g.drawString(strings.get(j), x + columnModifier, y + (j-3)*heightModifier);
			}
		}
		if(thirdColumn){
			for(int j = 6; j < strings.size(); j++){
				g.drawString(strings.get(j), x + 2 * columnModifier, y + (j-6)*heightModifier);
			}
		}
	}
	
	/*private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(image, "png", out);
	} */
	
}


/*	g.drawImage(image, 0, GamePanel.HEIGHT - 80, null);   
g.setFont(font);
g.setColor(Color.BLACK);
g.drawString(s, x, y);
if(secondLine){
	g.drawString(t, x, y+14);
} */


/*	
public BattleChatbox(String s){
	font = new Font("Arial", Font.PLAIN, 11);
	x = 12;
	y = GamePanel.HEIGHT - 33;
	this.s = s;
	//secondLine = false;
	
	
	try{
		image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/battlechatbox1.png"));
	}
	catch(Exception e){
		e.printStackTrace();
	}
}

public BattleChatbox(String s, String t){
	font = new Font("Arial", Font.PLAIN, 11);
	x = 13;
	y = GamePanel.HEIGHT - 34;
	this.s = s;
	this.t = t;
	//secondLine = true;
	
	try{
		image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/battlechatbox1.png"));
	}
	catch(Exception e){
		e.printStackTrace();
	}
}

public BattleChatbox(String s, int state, int x, int y){
	if(state == NORMAL){
		font = new Font("Arial", Font.PLAIN, 12);
	}
	else if(state == BOLD){
		font = new Font("Arial", Font.BOLD, 12);
	}
	else if(state == ITALIC){
		font = new Font("Arial", Font.ITALIC, 12);
	}
	this.s = s;
	this.x = x;
	this.y = y;
	
	try{
		image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/battlechatbox1.png"));
		//font = new Font("Arial", Font.PLAIN, 13);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
} */
