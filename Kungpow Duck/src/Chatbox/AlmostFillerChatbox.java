package Chatbox;

import Arrow.BlackDownArrow;
import Entity.Chatbox;
import java.awt.*;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class AlmostFillerChatbox extends Chatbox implements Serializable {
	private Font font;
	
	private BlackDownArrow ba;
	
	/*
	public static final int MAXOPTIONS = 7;
	
	private final int ITEMS = 0;
	private final int SKILLS = 1;
	private final int STATUS = 2;
	private final int PARTY = 3;
	private final int SAVE = 4;
	private final int OPTIONS = 5;
	private final int QUIT = 6;
	*/
	
	private int x;
	private int y;

	private int arrowX[];
	private int arrowY[];
	private String options[];
	
	private boolean isOpen;
	
	
	public AlmostFillerChatbox(){
		hasArrows = true;
		font = new Font("Arial", Font.PLAIN, 11);
		//x = GamePanel.WIDTH - 30;
		//y = GamePanel.HEIGHT - 33;
		//x = 0;
		maxPositions = 10;
		//x = GamePanel.WIDTH - 66;
		x = -1;
		y = 25;
		isOpen = false;
		arrowY = new int[maxPositions];
		arrowX = new int[1];
		
		for(int j = 0; j < maxPositions; j++){
			arrowY[j] = j*14+14;
		}
		options = new String[maxPositions];
		options[0] = "Items"; options[1] = "Skills"; options[2] = "Status";
		options[3] = "Party"; options[4] = "Save";   options[5] = "Options";
		options[6] = "Quit";
		options[7] = "Options 7";
		options[8] = "Options 8";
		options[9] = "Options 9";
		
		ba = new BlackDownArrow(x+5, arrowY[arrowPosition]);
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/Chatboxes/almostFillerChatbox.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isOpen() { return isOpen; }
	
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
	
	public void open() {
		isOpen = true;
		System.out.println("opened");
	}
	public void close() {
		isOpen = false;
	}
	/*
	public void enter() {
		if(arrowPosition == ITEMS){
			
		}
		else if(arrowPosition == SKILLS){
			
		}
		else if(arrowPosition == STATUS){
			
		}
		else if(arrowPosition == PARTY){
			
		}
		else if(arrowPosition == SAVE){
			
		}
		else if(arrowPosition == OPTIONS){
			
		}
		else if(arrowPosition == QUIT){
			close();
		}
	} 
	*/

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
	
	/*private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(image, "png", out);
	} */
}
