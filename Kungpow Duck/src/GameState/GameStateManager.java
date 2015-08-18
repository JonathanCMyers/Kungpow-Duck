/*package GameState;

import java.util.ArrayList;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState; // going to be the index of the game state in the list
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int ENDGAMESTATE = 2;
	
	public GameStateManager(){
		gameStates = new GameState[3];
		gameStates[0] = new MenuState(this);
		gameStates[1] = new Level1State(this);
		gameStates[2] = new EndGameState(this);
		
		currentState = MENUSTATE;
	}
	
	public void setState(int state){
		currentState = state;
		if(currentState == LEVEL1STATE){
			System.out.println("YES");
			gameStates[1] = new Level1State(this);
		}
		gameStates[currentState].init();
	}
	
	public void update() {
		gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates[currentState].draw(g);
	}
	
	public void keyPressed (int k) {
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k){
		gameStates[currentState].keyReleased(k);
	}
	
}  */



package GameState;

import java.util.ArrayList;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private int currentState; // going to be the index of the game state in the list
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int BATTLESTATE = 2;
	public static final int ENDGAMESTATE = 3;
	
	public GameStateManager(){
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this)); // menu state first gamestate in the list
		gameStates.add(new Level1State(this));
		if(gameStates.size() == 3) System.out.println("ERROR1");
		//gameStates.add(new EndGameState(this));
		//gameStates.add(new BattleState(this));
	}
	
	public void setState(int state){
		currentState = state;
		if(currentState == MENUSTATE){
			gameStates.clear();
			gameStates = new ArrayList<GameState>();
			gameStates.add(new MenuState(this));
			gameStates.add(new Level1State(this));
			if(gameStates.size() == 3){
				System.out.println("ERROR2");
			}
			//gameStates.add(new EndGameState(this));
			//gameStates.add(new BattleState(this));
		}
		gameStates.get(currentState).init();
		//System.out.print("SetSt: ");
		for(int j = 0; j < gameStates.size(); j++){
			System.out.print(gameStates.get(j).returnStateType() + " ");
		}
		System.out.println(""); 
	}
	
	public void setStateNoInit(int state) {
		currentState = state;
		for(int j = 0; j < gameStates.size(); j++){
			System.out.print(gameStates.get(j).returnStateType() + " ");
			System.out.println("");
		}
	}
	
	// this is the dumbest bug in the entire world IN THE ENTIRE WORLD
	public void update() {
		if(gameStates.size() == 0){
			//gameStates.clear(); even with this in there it still weirds out
			gameStates = new ArrayList<GameState>();
			gameStates.add(new MenuState(this));
			gameStates.add(new Level1State(this));
			if(gameStates.size() == 3){						// why is this necessary
				System.out.println("ERROR3");
				gameStates.remove(2);
			}
			//gameStates.add(new EndGameState(this));
			//gameStates.add(new BattleState(this));
		}
		gameStates.get(currentState).update();
	}
	
	//QUESTIONABLE
	public void addState(GameState g) {
		gameStates.add(g);
		System.out.println("questionable");
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed (int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}

	public void removeBattleState() {
		for(int j = 0; j < gameStates.size(); j++){
			if(gameStates.get(j).returnStateType() == BATTLESTATE){
				gameStates.remove(j);
				j--;
			}
		}
		/*if(gameStates.size() >= 4){
			gameStates.remove(BATTLESTATE);
		} */
	}
	
	public void replaceLevel1State(Level1State l1s) {
		gameStates.remove(LEVEL1STATE);
		gameStates.add(l1s);
	}
}  