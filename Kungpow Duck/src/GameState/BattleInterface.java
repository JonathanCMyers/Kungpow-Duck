/*
 * Description: 		Trying to tone down the number of functions within the BattleState class
 * Date Created: 		Jun 16, 2015
 * Date Last Modified: 	Jun 16, 2015
 * Modification Notes:	
 */
package GameState;

import java.io.Serializable;

import Main.GamePanel;

public interface BattleInterface {
	
	public static final int OPENINGPHASE = 0;
	public static final int PARTYMEMBERCHOOSINGPHASE = 1;
	public static final int MAINMENUOPTIONSPHASE = 2;
	public static final int CHOOSINGSKILLPHASE = 3;
	public static final int CHOOSINGITEMPHASE = 4;
	public static final int PLAYERCHOOSINGATTACKPHASE = 5;
	public static final int PLAYERCHOOSINGENEMYFORSKILLPHASE = 6;
	public static final int PLAYERCHOOSINGALLYPHASE = 7;
	public static final int ENEMYCHOOSINGATTACKPHASE = 8;
	
	public static final int NOBATTLE = 0;
	public static final int WONBATTLE = 1;
	public static final int LOSTBATTLE = 2;
	public static final int FLEDBATTLE = 3;
	
	// these two will need to be changed when we add in other party members
	public static final int PLAYERX = 260;
	public static final int PLAYERY = GamePanel.HEIGHT/2 - 60;
	
	public static final int ATTACK = 0;
	public static final int SKILL = 1;
	public static final int ITEM = 2;
	public static final int RUN = 3;
	
	// example of when we have multiple party members:
	public static final int ALLYX = 260;
	public static final int ALLYYONE = GamePanel.HEIGHT/2 - 50;
	public static final int ALLYYTWO = GamePanel.HEIGHT/2 - 70;
	public static final int ALLYYTHREE = GamePanel.HEIGHT/2 - 90;
	
	public static final int ENEMYX = 14;
	public static final int ENEMYYONE = GamePanel.HEIGHT/2 - 50;
	public static final int ENEMYYTWO = GamePanel.HEIGHT/2 - 70;
	public static final int ENEMYYTHREE = GamePanel.HEIGHT/2 - 90;
	
	public static final int ARROW1X = 4;
	public static final int ARROW2X = 114;
	public static final int ARROW3X = 224;
	public static final int ARROW1Y = GamePanel.HEIGHT - 68;
	public static final int ARROW2Y = GamePanel.HEIGHT - 44;
	public static final int ARROW3Y = GamePanel.HEIGHT - 20;
	
	// the following 8 ints are structured horribly. please change
	public static final int ARROWENEMYX = 20;
	public static final int ARROWENEMYYONE = ENEMYYONE+10;
	public static final int ARROWENEMYYTWO = ENEMYYTWO+10;
	public static final int ARROWENEMYYTHREE = ENEMYYTHREE+10;
	
	public static final int ARROWALLYX = 250;
	public static final int ARROWALLYYONE = ALLYYONE+10;
	public static final int ARROWALLYYTWO = ALLYYTWO+10;
	public static final int ARROWALLYYTHREE = ALLYYTHREE+10;
	
}
