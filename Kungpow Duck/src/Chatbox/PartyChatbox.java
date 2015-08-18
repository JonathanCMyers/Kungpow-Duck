package Chatbox;

import Entity.Chatbox;
import Main.GamePanel;

import java.awt.*;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class PartyChatbox extends Chatbox implements Serializable {
	public PartyChatbox(){					// It should take an Arraylist of Party Members
		try{
			//image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/PartyChatbox.png"));
			image = ImageIO.read(getClass().getResourceAsStream("/BattleBorders/allPartyBorder.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void draw(Graphics2D g){
		g.drawImage(image, -1, GamePanel.HEIGHT - 93, null);
	}
}
