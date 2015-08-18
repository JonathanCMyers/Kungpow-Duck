package GameState;

import Main.GamePanel;
import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EndGameState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0; // keep track of which choice we have selected
	private String[] options = {
			"Menu",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	private Font titleFont2;
	
	private Font font;
	
	public EndGameState(GameStateManager gsm) {
		this.gsm = gsm;
		
		titleFont = new Font("CenturyGothic", Font.PLAIN, 24);
		titleFont2 = new Font("CenturyGothic", Font.PLAIN, 18);
		font = new Font("Arial", Font.PLAIN, 12);
		
/*		try{
			bg = new Background("/Backgrounds/menubg.gif", 1);	 // this isn't resources/background
			// we dont need to do Resources/Background because they're already in the build path
			bg.setVector(-0.1, 0); // move to the left at .1 pixels
			
			titleColor = new Color(0, 0, 0); // white
			titleFont = new Font("CenturyGothic", Font.PLAIN, 34);
			font = new Font("Arial", Font.PLAIN, 12);
		}
		catch(Exception e){
			e.printStackTrace();
		} */
	}
	
	@Override
	public int returnStateType() {
		return GameStateManager.ENDGAMESTATE;
	}
	
	@Override
	public void init() {}
	@Override
	public void update() {
		//bg.update();
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,0,GamePanel.WIDTH, GamePanel.HEIGHT);  
		// draw bg
		//bg.draw(g);
		// draw title
		//g.setColor(titleColor);
		g.setFont(titleFont);
		g.setColor(Color.WHITE);
		g.drawString("GAME OVER", 55, 75); 
		g.setFont(titleFont2);
		g.drawString("(wow u die d 2 a snale", 20, 95);
		
		// draw menu options
		g.setFont(font);
		for(int j = 0; j < options.length; j++){
			if(j == currentChoice) {
				g.setColor(Color.YELLOW);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawString(options[j], 145, 140 + j * 15);
		}
	}
	
	private void select() {
		if(currentChoice == 0){
			gsm.setState(GameStateManager.MENUSTATE); // we picked the menu option
		}
		if(currentChoice == 1){
			// we picked the quit option
			System.exit(0);
		}
	}
	
	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP){
			currentChoice--;
			if(currentChoice == -1){
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN){
			currentChoice++;
			if(currentChoice == options.length){
				currentChoice = 0;
			}
		}
	}
	@Override
	public void keyReleased(int k){}
	
}