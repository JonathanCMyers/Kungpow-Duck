package GameState;

import TileMap.Background;
import UtilityClasses.Save;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0; // keep track of which choice we have selected
	private String[] options = {
			"Start",
			"Help",
			"Load",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try{
			bg = new Background("/Backgrounds/menubg.gif", 1);	 // this isn't resources/background
			// we dont need to do Resources/Background because they're already in the build path
			bg.setVector(-0.1, 0); // move to the left at .1 pixels
			
			titleColor = new Color(128, 0, 0); // red
			titleFont = new Font("CenturyGothic", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 12);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {}
	@Override
	public void update() {
		bg.update();
	}
	@Override
	public int returnStateType() {
		return GameStateManager.MENUSTATE;
	}
	@Override
	public void draw(Graphics2D g) {
	/*	if(g == null){
			System.out.println("IMAGE NULL");
			System.exit(0);
		}  */
		// draw bg
		bg.draw(g);
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Kungpow Duck", 70, 70); // for practical method you want auto-centering function
		// for when you're trying to draw strings
		
		// draw menu options
		g.setFont(font);
		for(int j = 0; j < options.length; j++){
			if(j == currentChoice) {
				g.setColor(Color.BLUE);
			}
			else{
				g.setColor(Color.RED);
			}
			g.drawString(options[j], 145, 140 + j * 15);
		}
	}
	
	private void select() {
		if(currentChoice == 0){
			gsm.setState(GameStateManager.LEVEL1STATE); // we picked the start option
		}
		if(currentChoice == 1){
			// we picked the help option
		}
		if(currentChoice == 2){
			Save saveFile = new Save("C:/achnic/achnicsave.out");
			gsm.replaceLevel1State(saveFile.read());
		}
		if(currentChoice == 3){
			System.exit(0); // we picked the quit option
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




