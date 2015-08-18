package Chatbox;

import Arrow.BlueArrow;
import Entity.Chatbox;
import Main.GamePanel;

import java.awt.*;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class OptionsChatbox extends Chatbox implements Serializable {
	private Font font;

	private boolean isOpen;
	
	public final static int ITEMS = 0;
	public final static int SKILLS = 1;
	public final static int STATUS = 2;
	public final static int PARTY = 3;
	public final static int CHANGELOCATION = 4;
	public final static int SAVE = 5;
	public final static int OPTIONS = 6;
	public final static int QUIT = 7;

	private String options[];
	
	private BlueArrow ba;
	
	public OptionsChatbox(){
		maxPositions = 8;
		hasArrows = true;
		font = new Font("Arial", Font.PLAIN, 11);
		//x = GamePanel.WIDTH - 30;
		//y = GamePanel.HEIGHT - 33;
		//x = 0;
		x = GamePanel.WIDTH - 66;
		y = 25;
		isOpen = false;
		arrowY = new int[maxPositions];
		arrowPosition = 0;
		arrowY[0] = 14; arrowY[1] = 28; arrowY[2] = 42;
		arrowY[3] = 56; arrowY[4] = 70; arrowY[5] = 84;
		arrowY[6] = 98; arrowY[7] = 113;
		options = new String[maxPositions];
		options[0] = "Items"; options[1] = "Skills"; options[2] = "Status";
		options[3] = "Party"; options[4] = "Change Location"; options[5] = "Save";   
		options[6] = "Options"; options[7] = "Quit";
		ba = new BlueArrow(x+5, arrowY[arrowPosition]);
		
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/optionsChatbox.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void goUp() {
		if(hasArrows){
			arrowPosition--;
			if(arrowPosition < 0) arrowPosition++;
			ba.setXY(x+5, arrowY[arrowPosition]);
		}
	}
	
	@Override
	public void goDown() {
		if(hasArrows){
			arrowPosition++;
			if(arrowPosition > (maxPositions - 1)) arrowPosition--;
			ba.setXY(x+5, arrowY[arrowPosition]);
		}
	}
	
	public boolean isOpen() { return isOpen; }
	
	public void open() {
		isOpen = true;
		System.out.println("opened");
	}
	public void close() {
		isOpen = false;
	}

	public int enter() {
		int value = -1;
		value = arrowPosition;
		/*
		if(arrowPosition == ITEMS){
			value = ITEMS;
		}
		else if(arrowPosition == SKILLS){
			value = SKILLS;
		}
		else if(arrowPosition == STATUS){
			value = STATUS;
		}
		else if(arrowPosition == PARTY){
			value = PARTY;
		}
		else if(arrowPosition == SAVE){
			value = SAVE;
		}
		else if(arrowPosition == OPTIONS){
			value = OPTIONS
		} */
		if(arrowPosition == QUIT){
			close();
		}
		return value;
	}

	@Override
	public void draw(Graphics2D g){
		if(isOpen){
			g.drawImage(image, x, /*GamePanel.HEIGHT - 50*/ 0, null);   
			g.setFont(font);
			g.setColor(Color.BLACK);
			//g.drawString("Save", x, y);
			for(int j = 0; j < (maxPositions); j++){
				if(arrowPosition == j)
					g.drawString(options[j], x+17, (y-2) + 14*j);
				else
					g.drawString(options[j], x+12, (y-2) + 14*j);
			}
			ba.draw(g);
			//g.drawString("Exit", x, 240);
			/*if(secondLine){
				g.drawString(t, x, y+14);
			}*/
		}
	}
}
