package Chatbox;

import Entity.Chatbox;
import java.awt.*;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class PromptChatbox extends Chatbox implements Serializable {
	private Font font;
	
	public static final int NORMAL = 1;
	public static final int BOLD = 2;
	public static final int ITALIC = 3;

	private String s;
	private int x;
	private int y;
	
	public PromptChatbox(String s){
		font = new Font("Arial", Font.BOLD, 11);
		this.s = s;
		x = 48;
		y = 1;
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/promptChatbox1.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void draw(Graphics2D g){
		g.drawImage(image, x, y, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(s, x + 70, y + 22);
	}
}