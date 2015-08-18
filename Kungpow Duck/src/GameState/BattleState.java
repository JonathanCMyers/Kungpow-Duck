package GameState;

import Main.GamePanel;
import TileMap.*;
import Ability.*;
import Arrow.BlueArrow;
import Chatbox.BattleChatbox;
import Chatbox.PartyChatbox;
import Chatbox.PromptChatbox;
import Chatbox.SkillDescriptionChatbox;
import Entity.*;
import Entity.Enemies.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class BattleState extends GameState implements BattleInterface {
	
	//private TileMap tileMap;
	private Background bg;
	//private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Animation> enemyPictures;
	//private ArrayList<PartyMember> partyMembers;
	private ArrayList<Animation> partyPictures;
	private Boolean[] deadEnemies;
	private boolean choosingAttack;
	private boolean promptChatBoolean;
	private boolean choseSkill;
	private boolean startOfPartyMemberChoosingPhase;
	private ArrayList<BlueArrow> blueArrows;
	private ArrayList<String> strings;
	private ArrayList<String> strings2;
	private PartyChatbox partyChatbox;
	private BattleChatbox chatbox;
	private BattleChatbox skillChatbox;
	private PromptChatbox promptChatbox;
	private SkillDescriptionChatbox descriptionChatbox;
	private BlueArrow ba;
	private TileMap tileMap;
	private Ability currentAbility;
	private ArrayList<ArrayList<Ability>> partyAbilities;
	private ArrayList<ArrayList<String>> partyAbilityStrings;
	private PartyBorder partyBorder;
	private PartyBorder partyBorder2;
	private PartyBorder partyBorder3;
	private PartyBorder partyBorder4;
	private PartyBorder partyBorder5;
	
	public static int wonBattle;
	
	// Real organization of party members:
	private int[] ALLYXPOSITION = {0, 0, 0, 0, 0};
	private int[] ALLYYPOSITION = {0, 0, 0, 0, 0};
	private int partyMemberOrdering[] = {0, 1, 2, 3, 4};
	
	private int currentArrow;
	private int currentSkillArrow;
	private int currentArrowE;
	private int currentArrowP;
	private int currentPhase;
	private int currentChoice;
	private int currentSkillChoice;
	private int currentColumn;
	private int currentSkillColumn;
	private int currentEnemy;
	private int currentAlly; // used when selecting ally to use a spell on 
	private int currentPartyMember; // used when selecting party member to use an attack
	private int currentPartyMemberChoice; // used on PARTYMEMBERCHOOSINGPHASE
	
	private int skillSaveX;
	private int skillSaveY;
	
	private int partyMemberChoosing;
	private int numPartyMembersGone;
	private int partyMembersIteratedThrough;
	
	private long enemyAttackStartTime;
	
	private int numEnemies;
	private int numPartyMembers;
	private int numDeadEnemies;
	private int numDeadPartyMembers;
	
	private double runChance;
	private int num;
	
	
	private String[] mainMenuOptions = {
			"  Attack",
			"  Skill",
			"  Run", 
			"  Quit"
	};
	
	@Override
	public int returnStateType() {
		return GameStateManager.BATTLESTATE;
	}
	
	public BattleState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		enemies = new ArrayList<Enemy>();
		//partyMembers = new ArrayList<PartyMember>();
		blueArrows = new ArrayList<BlueArrow>();
		strings = new ArrayList<String>();
		choosingAttack = false;
		currentChoice = 0;
		currentSkillChoice = 0;
	}
	
	@Override
	public void init() {
		numPartyMembersGone = 0;
		currentPartyMemberChoice = 0;
		num = 0;
		currentPhase = 0;
		currentChoice = 0;
		currentSkillChoice = 0;
		currentArrow = 0;
		currentSkillArrow = 0;
		currentArrowE = 0;
		currentArrowP = 0;
		currentColumn = 0;
		currentSkillColumn = 0;
		currentEnemy = 0;
		currentPartyMember = 0;
		numDeadEnemies = 0;
		numDeadPartyMembers = 0;
		partyMembersIteratedThrough = 0;
		partyBorder = new PartyBorder(0);				// Party Border
		/*partyBorder2 = new PartyBorder(2);
		partyBorder3 = new PartyBorder(3);
		partyBorder4 = new PartyBorder(4);
		partyBorder5 = new PartyBorder(5); */
		
		enemyAttackStartTime = 0;
		
		choseSkill = false;
		
		currentAbility = null;
		partyMemberChoosing = 0;
		
		skillSaveX = ARROW1X;
		skillSaveY = ARROW1Y;
		
		runChance = .9;
		
		tileMap = new TileMap(32);
		//tileMap.loadTiles("/Tilesets/grass.png");
		tileMap.loadTiles("/Tilesets/twgt.png");
		tileMap.loadMap("/Maps/level1-2.map");	
		tileMap.setPosition(0, 0); // good starting point maybe 
		tileMap.setSmoother(1); 
		
		
		promptChatBoolean = false;
		
		// brings in the party members from Level1State, for now it only brings in player
		//partyMembers = new ArrayList<PartyMember>();
		//partyMembers.add(Level1State.player);
		
		// partyPictures holds the facing left animations of the party members
		numPartyMembers = Level1State.partyMembers.size();
		
		ALLYXPOSITION[0] = 245;
		ALLYXPOSITION[1] = 265;
		ALLYXPOSITION[2] = 245;
		ALLYXPOSITION[3] = 265;
		ALLYXPOSITION[4] = 245;
		
		if(numPartyMembers == 5){
			ALLYYPOSITION[0] = GamePanel.HEIGHT/2 - 100;
			ALLYYPOSITION[1] = GamePanel.HEIGHT/2 - 80;
			ALLYYPOSITION[2] = GamePanel.HEIGHT/2 - 60;
			ALLYYPOSITION[3] = GamePanel.HEIGHT/2 - 40;
			ALLYYPOSITION[4] = GamePanel.HEIGHT/2 - 20;
		}
		else if(numPartyMembers == 4){
			ALLYYPOSITION[0] = GamePanel.HEIGHT/2 - 90;
			ALLYYPOSITION[1] = GamePanel.HEIGHT/2 - 70;
			ALLYYPOSITION[2] = GamePanel.HEIGHT/2 - 50;
			ALLYYPOSITION[3] = GamePanel.HEIGHT/2 - 30;
			ALLYYPOSITION[4] = GamePanel.HEIGHT/2 - 10;
		}
		else if(numPartyMembers == 3){
			ALLYYPOSITION[0] = GamePanel.HEIGHT/2 - 80;
			ALLYYPOSITION[1] = GamePanel.HEIGHT/2 - 60;
			ALLYYPOSITION[2] = GamePanel.HEIGHT/2 - 40;
			ALLYYPOSITION[3] = GamePanel.HEIGHT/2 - 20;
			ALLYYPOSITION[4] = GamePanel.HEIGHT/2;
		}
		else if(numPartyMembers == 2){
			ALLYYPOSITION[0] = GamePanel.HEIGHT/2 - 70;
			ALLYYPOSITION[1] = GamePanel.HEIGHT/2 - 40;
			ALLYYPOSITION[2] = GamePanel.HEIGHT/2 - 20;
			ALLYYPOSITION[3] = GamePanel.HEIGHT/2 - 0;
			ALLYYPOSITION[4] = GamePanel.HEIGHT/2 + 20;
		}
		else{
			ALLYYPOSITION[0] = GamePanel.HEIGHT/2 - 65;
			ALLYYPOSITION[1] = GamePanel.HEIGHT/2 - 45;
			ALLYYPOSITION[2] = GamePanel.HEIGHT/2 - 25;
			ALLYYPOSITION[3] = GamePanel.HEIGHT/2 - 5;
			ALLYYPOSITION[4] = GamePanel.HEIGHT/2 + 15;
		}
		
		// initialize the ordering of the last iteration of the party members
	/*	for(int j = 0; j < numPartyMembers; j++){
			partyMemberOrdering[j] = j;
		} */
		
		//int[] partyMemberOrdering = {0, 1, 2, 3, 4};
		
		
		partyPictures = new ArrayList<Animation>();
		for(int j = 0; j < numPartyMembers; j++){
			partyPictures.add(Level1State.partyMembers.get(j).getFacingLeftAnimation());
		}
		
		partyAbilities = new ArrayList<ArrayList<Ability>>();
		partyAbilityStrings = new ArrayList<ArrayList<String>>();
		
		// set the array that holds the names of all the party members's abilities
		System.out.println(numPartyMembers);
		for(int j = 0; j < numPartyMembers; j++){
			ArrayList<Ability> pma = new ArrayList<Ability>();
			pma = Level1State.partyMembers.get(j).getAbilities();
			ArrayList<String> skillstring = new ArrayList<String>();
			partyAbilities.add(pma);
			for(int k = 1; k < pma.size(); k++){
				String s = "  " + pma.get(k).getName();
				skillstring.add(s);
				System.out.println("something");
			}
			partyAbilityStrings.add(skillstring);
		}
		
		// creates a random amount of enemies between 1 and 3
		enemies = new ArrayList<Enemy>();
		Random r = new Random(System.nanoTime());
		numEnemies = r.nextInt(3);
		numEnemies++;
		deadEnemies = new Boolean[numEnemies];
		
		// switches based off the type of enemy hit in the overworld and creates numEnemies iterations of that enemy
		if(Level1State.enemyHit.getType() == Enemy.SNAKE){
			//numEnemies = 1;
			for(int j = 0; j < numEnemies; j++){
				Enemy enem = new Snake(tileMap);
				enemies.add(enem);
				deadEnemies[j] = false;
			}
		}
		
		// enemyPictures holds the facing right animation of the enemies
		enemyPictures = new ArrayList<Animation>();
		for(int j = 0; j < enemies.size(); j++){
			enemyPictures.add(enemies.get(j).getFacingRightAnimation());
		}
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
		choosingAttack = false;
		
		blueArrows = new ArrayList<BlueArrow>();
		
		// strings holds the options each party member can select when attacking
		strings = new ArrayList<String>();
		strings2 = new ArrayList<String>();
		ba = new BlueArrow(ARROW1X, ARROW1Y);
		blueArrows.add(ba);
		
		currentPhase = PARTYMEMBERCHOOSINGPHASE;
		startOfPartyMemberChoosingPhase = true;
		
		strings.add("  Attack");
		strings.add("  Skill");
		strings.add("  Items");
		strings.add("  Run");

		
		// creates a chatbox based off the array of options
		chatbox = new BattleChatbox(strings);
		partyChatbox = new PartyChatbox();
		
	}

	public void addEnemies(Enemy e){ enemies.add(e); }
	
	@Override
	public void update() {
		//player.update();
		if(Level1State.player.isDead()) gsm.setState(GameStateManager.ENDGAMESTATE); 
		//tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
		//bg.setPosition(tileMap.getx(),  tileMap.gety());
		bg.setPosition(0, 0);
		// attack enemies
		//Level1State.player.checkAttack(enemies);
		// update all enemies
	/*	for(int j = 0; j < enemies.size(); j++){
			//Enemy e = enemies.get(j);
			enemies.get(j).update();
		/*	if(enemies.get(j).isDead()){
				enemies.remove(j);
				j--;
				explosions.add(new Explosion(e.getx(), e.gety())); 
			} */
		//}
/*		if(currentChoice < 3 && currentColumn != 0) currentColumn = 0;
		else if(currentChoice < 6 && currentColumn != 1)	currentColumn = 1;
		else	currentColumn = 2;  */
		if(currentPhase == PARTYMEMBERCHOOSINGPHASE){
			if(startOfPartyMemberChoosingPhase){
				partyBorder.setPosition(partyMemberOrdering[0]);
				num = partyMemberOrdering[0];
				startOfPartyMemberChoosingPhase = false;
				partyMembersIteratedThrough++;
			}
		}
		if(currentPhase == MAINMENUOPTIONSPHASE){
			switch(currentChoice){
			case 0: if(currentArrow != 1){
						ba = new BlueArrow(ARROW1X, ARROW1Y);
						currentArrow = 1;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 1: if(currentArrow != 2){
						ba = new BlueArrow(ARROW1X, ARROW2Y);
						currentArrow = 2;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 2: if(currentArrow != 3){
						ba = new BlueArrow(ARROW1X, ARROW3Y);
						currentArrow = 3;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 3:	if(currentArrow != 4){
						ba = new BlueArrow(ARROW2X, ARROW1Y);
						currentArrow = 4;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 4:	if(currentArrow != 5){
						ba = new BlueArrow(ARROW2X, ARROW2Y);
						currentArrow = 5;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 5: if(currentArrow != 6){
						ba = new BlueArrow(ARROW2X, ARROW3Y);
						currentArrow = 6;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 6: if(currentArrow != 7){
						ba = new BlueArrow(ARROW3X, ARROW1Y);
						currentArrow = 7;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 7: if(currentArrow != 8){
						ba = new BlueArrow(ARROW3X, ARROW2Y);
						currentArrow = 8;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
					}
					break;
			case 8:	if(currentArrow != 9){
						ba = new BlueArrow(ARROW3X, ARROW3Y);
						currentArrow = 9;
						if(blueArrows.size() > 0) blueArrows.remove(0);
					}
			}
		}
		if(currentPhase == CHOOSINGSKILLPHASE){
			switch(currentSkillChoice){
			case 0: if(currentSkillArrow != 1){
						ba = new BlueArrow(ARROW1X, ARROW1Y);
						currentSkillArrow = 1;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW1X;
						skillSaveY = ARROW1Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(1).getDescription(), partyAbilities.get(currentPartyMember).get(1).getCost());
					}
					break;
			case 1: if(currentSkillArrow != 2){
						ba = new BlueArrow(ARROW1X, ARROW2Y);
						currentSkillArrow = 2;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW1X;
						skillSaveY = ARROW2Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(2).getDescription(), partyAbilities.get(currentPartyMember).get(2).getCost());
					}
					break;
			case 2: if(currentSkillArrow != 3){
						ba = new BlueArrow(ARROW1X, ARROW3Y);
						currentSkillArrow = 3;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW1X;
						skillSaveY = ARROW3Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(3).getDescription(), partyAbilities.get(currentPartyMember).get(3).getCost());
					}
					break;
			case 3:	if(currentSkillArrow != 4){
						ba = new BlueArrow(ARROW2X, ARROW1Y);
						currentSkillArrow = 4;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW2X;
						skillSaveY = ARROW1Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(4).getDescription(), partyAbilities.get(currentPartyMember).get(4).getCost());
					}
					break;
			case 4:	if(currentSkillArrow != 5){
						ba = new BlueArrow(ARROW2X, ARROW2Y);
						currentSkillArrow = 5;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW2X;
						skillSaveY = ARROW2Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(5).getDescription(), partyAbilities.get(currentPartyMember).get(5).getCost());
					}
					break;
			case 5: if(currentSkillArrow != 6){
						ba = new BlueArrow(ARROW2X, ARROW3Y);
						currentSkillArrow = 6;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW2X;
						skillSaveY = ARROW3Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(6).getDescription(), partyAbilities.get(currentPartyMember).get(6).getCost());
					}
					break;
			case 6: if(currentSkillArrow != 7){
						ba = new BlueArrow(ARROW3X, ARROW1Y);
						currentSkillArrow = 7;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW3X;
						skillSaveY = ARROW1Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(7).getDescription(), partyAbilities.get(currentPartyMember).get(7).getCost());
					}
					break;
			case 7: if(currentSkillArrow != 8){
						ba = new BlueArrow(ARROW3X, ARROW2Y);
						currentSkillArrow = 8;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						blueArrows.add(ba);
						skillSaveX = ARROW3X;
						skillSaveY = ARROW2Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(8).getDescription(), partyAbilities.get(currentPartyMember).get(8).getCost());
					}
					break;
			case 8:	if(currentSkillArrow != 9){
						ba = new BlueArrow(ARROW3X, ARROW3Y);
						currentSkillArrow = 9;
						if(blueArrows.size() > 0) blueArrows.remove(0);
						skillSaveX = ARROW3X;
						skillSaveY = ARROW3Y;
						descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(9).getDescription(), partyAbilities.get(currentPartyMember).get(9).getCost());
					}
			}
		}
		else if(currentPhase == PLAYERCHOOSINGATTACKPHASE){
			if(numEnemies == 1){
				//System.out.println("1 ENEMY");
				// do nothing, i think. because the arrow is already established.
			}
			else if(numEnemies == 2){
				//System.out.println("2 ENEMIES");
				if(currentEnemy == 0 && enemies.get(currentEnemy).isDead() == false){
					//System.out.println("choosing 1");
					if(currentArrowE != 1){
						ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTWO);
						currentArrowE = 1;
						if(blueArrows.size() > 1) blueArrows.remove(1);
						blueArrows.add(ba);
						System.out.println("1 added.");
					}
				}
				else if(currentEnemy == 1 && enemies.get(currentEnemy).isDead() == false){
					//System.out.println("choosing 2");
					if(currentArrowE != 2){
						ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTWO + 35);
						currentArrowE = 2;
						if(blueArrows.size() > 1) blueArrows.remove(1);
						blueArrows.add(ba);
						System.out.println("2 added.");
					}
				}
			}
			else if(numEnemies == 3){
				//System.out.println("3 ENEMIES");
				//System.out.println("choosing 1");
				if(currentEnemy == 0 && enemies.get(currentEnemy).isDead() == false){
					if(currentArrowE != 1){
						ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTHREE);
						currentArrowE = 1;
						if(blueArrows.size() > 1) blueArrows.remove(1);
						blueArrows.add(ba);
					}
				}
				else if(currentEnemy == 1 && enemies.get(currentEnemy).isDead() == false){
					//System.out.println("choosing 2");
					if(currentArrowE != 2){
						ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTHREE + 35);
						currentArrowE = 2;
						if(blueArrows.size() > 1) blueArrows.remove(1);
						blueArrows.add(ba);
					}
				}
				else if(currentEnemy == 2 && enemies.get(currentEnemy).isDead() == false){
					if(currentArrowE != 3){
						//System.out.println("choosing 3");
						ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTHREE + 70);
						currentArrowE = 3;
						if(blueArrows.size() > 1) blueArrows.remove(1);
						blueArrows.add(ba);
					}
				}
			}
		}
		else if(currentPhase == ENEMYCHOOSINGATTACKPHASE){
			long currentTime = System.nanoTime();
			if(currentTime - enemyAttackStartTime > 1000){
				if(numEnemies == 1){
					boolean didHit = Level1State.partyMembers.get(currentPartyMember).applyDamage(enemies.get(0).getBasicAttack());
					if(didHit){
						System.out.print("Enemy 1 Attacked! ");
						System.out.println("Remaining Player HP: " + Level1State.partyMembers.get(currentPartyMember).getCurrentHP() + "/" 
											+ Level1State.partyMembers.get(currentPartyMember).getMaxHP());	
					}
					else{
						System.out.println("Attack Missed!");
					}
					currentPhase = MAINMENUOPTIONSPHASE;
					//blueArrows.remove(1);
					blueArrows.get(0).unfreeze();
					promptChatBoolean = false;
					//enemyAttackStartTime = System.nanoTime();
				}
				else if(numEnemies == 2){
					
				}
			}
		}
		//System.out.println(currentChoice);
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		Font titleFont2 = new Font("CenturyGothic", Font.PLAIN, 18);
		g.setFont(titleFont2);
		Font font = new Font("Arial", Font.PLAIN, 12);
		for(int j = 0; j < numPartyMembers; j++){
			partyPictures.get(j).update();
			g.drawImage(partyPictures.get(j).getImage(), ALLYXPOSITION[j], ALLYYPOSITION[j], null );
		}
		for(int j = 0; j < enemies.size(); j++){
			if(enemies.get(j).isDead() && deadEnemies[j] == false){
				deadEnemies[j] = true;
				enemyPictures.get(j).setAnimation(Level1State.enemyHit.getDeathAnimation());
			}
		}
		for(int j = 0; j < enemyPictures.size(); j++){
			enemyPictures.get(j).update();
			if(numEnemies == 1){
				g.drawImage(enemyPictures.get(j).getImage(), ENEMYX, ENEMYYONE, null);
			}
			if(numEnemies == 2){
				g.drawImage(enemyPictures.get(j).getImage(), ENEMYX, ENEMYYTWO + 35*j, null);
			}
			if(numEnemies == 3){
				g.drawImage(enemyPictures.get(j).getImage(), ENEMYX, ENEMYYTHREE + 35*j, null);
			}
		}
		g.setFont(font);
		if(currentPhase == PARTYMEMBERCHOOSINGPHASE){
			partyChatbox.draw(g);
			Level1State.partyMembers.get(0).drawBorder(g, 0);
			partyBorder.draw(g);
			
		}
		if(currentPhase == MAINMENUOPTIONSPHASE || currentPhase == PLAYERCHOOSINGATTACKPHASE
												|| currentPhase == PLAYERCHOOSINGENEMYFORSKILLPHASE){
			chatbox.draw(g);
			if(choseSkill){
				skillChatbox.draw(g);
				descriptionChatbox.draw(g);
			}
		}
		else if(currentPhase == CHOOSINGSKILLPHASE){
			skillChatbox.draw(g);
			descriptionChatbox.draw(g);
		}
		else if(currentPhase == PLAYERCHOOSINGALLYPHASE){
			skillChatbox.draw(g);
			descriptionChatbox.draw(g);
		}
		for(int j = 0; j < blueArrows.size(); j++){
			blueArrows.get(j).draw(g);
		}
		if(promptChatBoolean){
			promptChatbox.draw(g);
		}
		/*partyChatbox.draw(g);
		partyBorder.draw(g);
		partyBorder2.draw(g);
		partyBorder3.draw(g);
		partyBorder4.draw(g);
		partyBorder5.draw(g); */
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_BACK_SPACE){
			unselect();
		}
		if(k == KeyEvent.VK_UP){
			if(currentPhase == MAINMENUOPTIONSPHASE){
				currentChoice--;
				if(currentChoice == -1) currentChoice = 0;
				if(currentChoice < 3 && currentColumn == 1) currentColumn = 0;
				if(currentChoice < 6 && currentChoice > 2 && currentColumn == 2) currentColumn = 1;
			}
			if(currentPhase == CHOOSINGSKILLPHASE){
				currentSkillChoice--;
				if(currentSkillChoice == -1) currentSkillChoice = 0;
				if(currentSkillChoice < 3 && currentSkillColumn == 1) currentSkillColumn = 0;
				if(currentSkillChoice < 6 && currentSkillChoice > 2 && currentSkillColumn == 2) currentSkillColumn = 1;
			}
			else if(currentPhase == PLAYERCHOOSINGATTACKPHASE || currentPhase == PLAYERCHOOSINGENEMYFORSKILLPHASE){
				if(numEnemies == 1){
					// do nothing
				}
				else if(numEnemies == 2){
					if(currentEnemy == 1 && enemies.get(0).isDead()){
						// do nothing
					}
					else{
						currentEnemy--;
						blueArrows.get(1).setXY(ARROWENEMYX, ARROWENEMYYTWO);
					}
				}
				else if(numEnemies == 3){
					if(currentEnemy == 2 && enemies.get(1).isDead() && enemies.get(0).isDead() == false){
						currentEnemy--;
						currentEnemy--;
						blueArrows.get(1).setXY(ARROWENEMYX, ARROWENEMYYTHREE);
					}
					else if(currentEnemy == 2 && enemies.get(1).isDead() && enemies.get(0).isDead()){
						// do nothing
					}
					else if(currentEnemy == 1 && enemies.get(0).isDead()){
						// do nothing
					}
					else{
						currentEnemy--;
						if(currentEnemy < 0) currentEnemy++;
						blueArrows.get(1).setXY(ARROWENEMYX, ARROWENEMYYTHREE + 35*currentEnemy);
					}
				}
				if(currentEnemy < 0) currentEnemy++;
			}
			else if(currentPhase == PLAYERCHOOSINGALLYPHASE){
				currentAlly--;
				if(currentAlly < 0)
					currentAlly++;
			}
		}
		if(k == KeyEvent.VK_DOWN){
			if(currentPhase == MAINMENUOPTIONSPHASE){
				currentChoice++;
				if(currentChoice == strings.size()) currentChoice--;
				if(currentChoice > 2 && currentColumn == 0) currentColumn = 1;
				if(currentChoice > 5 && currentColumn == 1) currentColumn = 2;
			}
			if(currentPhase == CHOOSINGSKILLPHASE){
				currentSkillChoice++;
				if(currentSkillChoice == strings2.size()) currentSkillChoice--;
				if(currentSkillChoice > 2 && currentSkillColumn == 0) currentSkillColumn = 1;
				if(currentSkillChoice > 5 && currentSkillColumn == 1) currentSkillColumn = 2;
			}
			else if(currentPhase == PLAYERCHOOSINGATTACKPHASE || currentPhase == PLAYERCHOOSINGENEMYFORSKILLPHASE){
				if(numEnemies == 1){
					// do nothing
				}
				else if(numEnemies == 2){
					if(currentEnemy == 0 && enemies.get(1).isDead()){
						// do nothing
					}
					else{
						currentEnemy++;
						blueArrows.get(1).setXY(ARROWENEMYX, ARROWENEMYYTWO + 35);
					}
				}
				else if(numEnemies == 3){
					if(currentEnemy == 0 && enemies.get(1).isDead() && enemies.get(2).isDead() == false){
						currentEnemy++;
						currentEnemy++;
						blueArrows.get(1).setXY(ARROWENEMYX, ARROWENEMYYTHREE + 70);
					}
					else if(currentEnemy == 0 && enemies.get(1).isDead() && enemies.get(2).isDead()){
						// do nothing
					}
					else if(currentEnemy == 1 && enemies.get(2).isDead()){
						// do nothing
					}
					else{
						currentEnemy++;
						if(currentEnemy == numEnemies) currentEnemy--;
						blueArrows.get(1).setXY(ARROWENEMYX, ARROWENEMYYTHREE + 35*currentEnemy);
					}
				}
				if(currentEnemy == numEnemies) currentEnemy--;
			}
			else if(currentPhase == PLAYERCHOOSINGALLYPHASE){
				currentAlly++;
				if(currentAlly == numPartyMembers)
					currentAlly--;
			}
		} 
		if(k == KeyEvent.VK_RIGHT){
			if(currentPhase == PARTYMEMBERCHOOSINGPHASE){
				if(num < numPartyMembers - 1){
					num++;
					partyBorder.setPosition(num);
				}
			}
			if(currentPhase == MAINMENUOPTIONSPHASE){
				if(currentColumn == 0){
					if(currentChoice < 3 && chatbox.hasSecondColumn()){
						if(currentChoice + 3 >= strings.size())		currentChoice = strings.size() - 1;
						else	currentChoice+=3;
						currentColumn++;
					}
				}
				else if(currentColumn == 1){
					if(currentChoice < 6 && chatbox.hasThirdColumn()){
						if(currentChoice + 3 >= strings.size())		currentChoice = strings.size() - 1;
						else	currentChoice+=3;
						currentColumn++;
					}
				}
			}
			if(currentPhase == CHOOSINGSKILLPHASE){
				if(currentSkillColumn == 0){
					if(currentSkillChoice < 3 && skillChatbox.hasSecondColumn()){
						if(currentSkillChoice + 3 >= strings.size())		currentSkillChoice = strings2.size() - 1;
						else	currentSkillChoice+=3;
						currentSkillColumn++;
					}
				}
				else if(currentSkillColumn == 1){
					if(currentSkillChoice < 6 && skillChatbox.hasThirdColumn()){
						if(currentSkillChoice + 3 >= strings.size())		currentSkillChoice = strings2.size() - 1;
						else	currentSkillChoice+=3;
						currentSkillColumn++;
					}
				}
			}
		}
		if(k == KeyEvent.VK_LEFT){
			if(currentPhase == PARTYMEMBERCHOOSINGPHASE){
				if(num > 0){
					num--;
					partyBorder.setPosition(num);
				}
			}
			if(currentPhase == MAINMENUOPTIONSPHASE){
				if(currentChoice >= 3){
					currentChoice-=3;
					currentColumn--;
				}
			}
			if(currentPhase == CHOOSINGSKILLPHASE){
				if(currentSkillChoice >= 3){
					currentSkillChoice-=3;
					currentSkillColumn--;
				}
			}
		}
		
	}
	
	private void select() {
		if(currentPhase == PARTYMEMBERCHOOSINGPHASE){
			currentPartyMember = num;
			currentPhase = MAINMENUOPTIONSPHASE;
			startOfPartyMemberChoosingPhase = true;
			
			// NOTE::::::::: THIS SHOULD ALL GO AFTER THE PARTY MEMBER USES ABILITY IN CASE DESELECT IS DESIRED
			// secondary note made 2 month after first note: idk what the following commented out code is supposed
			// to do, but it auto-pushes the border to the right by 1 each time you iterate to PARTYMEMBERCHOOSINGPHASE
			/*
			for(int j = 0; j < numPartyMembers; j++){
				if(partyMemberOrdering[j] == num){
					int temp = partyMemberOrdering[j];
					partyMemberOrdering[j] = partyMemberOrdering[numPartyMembersGone];
					partyMemberOrdering[numPartyMembersGone] = temp;
				}
			}
			*/
			numPartyMembersGone++;
			
			for(int j = 0; j < numPartyMembers; j++){
				System.out.print(partyMemberOrdering[j] + "! ");
			}
			System.out.print("\n"); 
			
		}
		else if(currentPhase == MAINMENUOPTIONSPHASE){
			if(currentChoice == ATTACK){
				//currentAbility = partyMembers.get(partyMemberChoosing).getBasicAttack();
				currentAbility = partyAbilities.get(partyMemberChoosing).get(0);
				useAbility();
			}
			if(currentChoice == SKILL){
				strings2 = new ArrayList<String>();
				for(int j = 0; j < partyAbilityStrings.get(currentPartyMember).size(); j++){
					strings2.add(partyAbilityStrings.get(currentPartyMember).get(j));
				}				
				System.out.println("Num Abilities: " + partyAbilityStrings.get(currentPartyMember).size());
				skillChatbox = new BattleChatbox(strings2);
				
				// The following line + switch statement is so that when you re-click skill, it puts the arrow on whatever
				// it was last on. so if you backspace when you're on the 3rd skill, if you press skill again, the cursor will be on the
				// third skill.
				///// make this shorter later
				descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(1).getDescription(), partyAbilities.get(currentPartyMember).get(1).getCost());
				switch(currentSkillChoice){
				case 0: descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(1).getDescription(), partyAbilities.get(currentPartyMember).get(1).getCost());
						break;
				case 1: descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(2).getDescription(), partyAbilities.get(currentPartyMember).get(2).getCost());
						break;
				case 2: descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(3).getDescription(), partyAbilities.get(currentPartyMember).get(3).getCost());
						break;
				case 3:	descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(4).getDescription(), partyAbilities.get(currentPartyMember).get(4).getCost());
						break;
				case 4:	descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(5).getDescription(), partyAbilities.get(currentPartyMember).get(5).getCost());
						break;
				case 5: descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(6).getDescription(), partyAbilities.get(currentPartyMember).get(6).getCost());
						break;
				case 6: descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(7).getDescription(), partyAbilities.get(currentPartyMember).get(7).getCost());
						break;
				case 7: descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(8).getDescription(), partyAbilities.get(currentPartyMember).get(8).getCost());
						break;
				case 8:	descriptionChatbox = new SkillDescriptionChatbox(partyAbilities.get(currentPartyMember).get(9).getDescription(), partyAbilities.get(currentPartyMember).get(9).getCost());
				}
				
				// switch phase so that when we are making choices on this menu we know we're choosing a skill
				currentPhase = CHOOSINGSKILLPHASE;
				
				// 
				blueArrows.get(0).setXY(skillSaveX, skillSaveY);
				
				// make chatbox
				// choose from this list of skills
				// set currentAbility to the one chosen
				// useAbility()
			}
			if(currentChoice == ITEM){
				currentPhase = CHOOSINGITEMPHASE;
			}
			if(currentChoice == RUN){
				Random r = new Random(System.nanoTime());
				double d = r.nextDouble();
				System.out.println("run");
				if(d < runChance){
					gsm.setStateNoInit(GameStateManager.LEVEL1STATE);
					wonBattle = FLEDBATTLE;
				}
			}
		}
		else if(currentPhase == CHOOSINGSKILLPHASE){
			currentAbility = partyAbilities.get(currentPartyMember).get(currentSkillChoice + 1);
			choseSkill = true;
			useAbility();
		}
		else if(currentPhase == PLAYERCHOOSINGATTACKPHASE || currentPhase == PLAYERCHOOSINGENEMYFORSKILLPHASE){
			choseSkill = false;
			if(currentEnemy == -1)	System.out.println("ERROR: ENEMY CHOSEN NOT AVAILABLE");
			else{
				System.out.println(currentEnemy);
				if(enemies.get(currentEnemy).isDead()){
					System.out.println("already dead.");
					currentEnemy++;
					currentArrowE++;
				}
				else{
					////////////////////if()
					//boolean didHit = enemies.get(currentEnemy).applyDamage(partyMembers.get(currentPartyMember).getBasicAttack());
					boolean didHit = enemies.get(currentEnemy).applyDamage(currentAbility);
					if(!didHit){
						System.out.println("MISSED!");
					}
					else{
						System.out.println("Remaining Enemy HP: " + enemies.get(currentEnemy).getCurrentHP() + "/" + enemies.get(currentEnemy).getMaxHP());
					}
					if(enemies.get(currentEnemy).isDead()){
						System.out.println("REMOVE CURRENT ENEMY");
						numDeadEnemies = 0;
						for(int j = 0; j < numEnemies; j++){
							if(enemies.get(j).isDead()) numDeadEnemies++;
						}
						if(numDeadEnemies == numEnemies){
							System.out.println("YOU WON THE BATTLE");
							gsm.setStateNoInit(GameStateManager.LEVEL1STATE);
							wonBattle = WONBATTLE;
						}
					}
				}
			}
			//currentPhase = ENEMYCHOOSINGATTACKPHASE;
			//currentPhase = MAINMENUOPTIONSPHASE;
			currentPhase = PARTYMEMBERCHOOSINGPHASE;
			blueArrows.remove(1);
			blueArrows.get(0).unfreeze();
			promptChatBoolean = false;
			enemyAttackStartTime = System.nanoTime();
		}
		else if(currentPhase == PLAYERCHOOSINGALLYPHASE){
			if(Level1State.partyMembers.get(currentAlly).canReceiveAbility(currentAbility)){
				boolean didHit = Level1State.partyMembers.get(currentAlly).applyAbility(currentAbility);
				if(!didHit)
					System.out.println("MISSED!");
				else
					System.out.println("Success in using ability");
				currentPhase = PARTYMEMBERCHOOSINGPHASE;
				//currentPhase = MAINMENUOPTIONSPHASE;
				choseSkill = false;
				blueArrows.remove(1);
				blueArrows.get(0).unfreeze();
				blueArrows.get(0).setXY(ARROW1X, ARROW2Y);
				promptChatBoolean = false;
			}
			else{
				// IMPLEMENT THIS LATER
				//partyMembers.get(currentAlly).displayAppropriateErrorMessage(currentAbility);
				// store that in a description box
				// and set back to choosing skill pha
				currentPhase = PLAYERCHOOSINGALLYPHASE;
			}
		}
	}
	
	private void useAbility() {
		if(currentAbility.appliesToEnemy()){
			promptChatbox = new PromptChatbox("Which Enemy?");
			promptChatBoolean = true;
			blueArrows.get(0).freeze();
			if(currentPhase == CHOOSINGSKILLPHASE) currentPhase = PLAYERCHOOSINGENEMYFORSKILLPHASE;
			else currentPhase = PLAYERCHOOSINGATTACKPHASE;
			currentEnemy = 0;
			currentArrowE = 1;
			/*	if(enemies.get(currentEnemy).isDead()){
				currentEnemy++;
				currentArrowE++;
			} */
			if(numEnemies == 1){
				ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYONE);
			}
			if(numEnemies == 2){
				if(enemies.get(0).isDead()){
					currentEnemy++;
					currentArrowE++;
					ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTWO + 35);
				}
				else{
					ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTWO);
				}
			}
			if(numEnemies == 3){
				if(enemies.get(0).isDead() && enemies.get(1).isDead()){
					currentEnemy++;
					currentEnemy++;
					currentArrowE++;
					currentArrowE++;
					ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTHREE + 70);
				}
				else if(enemies.get(0).isDead()){
					currentEnemy++;
					currentArrowE++;
					ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTHREE + 35);
				}
				else{
					ba = new BlueArrow(ARROWENEMYX, ARROWENEMYYTHREE);
				}
			}
			blueArrows.add(ba); 
		}
		else if(currentAbility.appliesToSelf()){
			
		}
		else if(currentAbility.appliesToAlly()){
			promptChatbox = new PromptChatbox("  Which Ally?");
			promptChatBoolean = true;
			blueArrows.get(0).freeze();
			currentPhase = PLAYERCHOOSINGALLYPHASE;
			choseSkill = true;
			currentAlly = 0;
			currentArrowP = 1;
			// this is in no way finished
			if(numPartyMembers == 1){
				ba = new BlueArrow(ARROWALLYX, ARROWALLYYONE);
			}
			if(numPartyMembers == 2){
				if(Level1State.partyMembers.get(0).isDead()){
					currentAlly++;
					currentArrowP++;
					ba = new BlueArrow(ARROWALLYX, ARROWALLYYTWO + 35);
				}
				else{
					ba = new BlueArrow(ARROWALLYX, ARROWALLYYTWO);
				}
			}
			if(numPartyMembers == 3){
				if(Level1State.partyMembers.get(0).isDead() && Level1State.partyMembers.get(1).isDead()){
					currentAlly++;
					currentAlly++;
					currentArrowP++;
					currentArrowP++;
					ba = new BlueArrow(ARROWALLYX, ARROWALLYYTHREE + 70);
				}
				else if(Level1State.partyMembers.get(0).isDead()){
					currentAlly++;
					currentArrowP++;
					ba = new BlueArrow(ARROWALLYX, ARROWALLYYTHREE + 35);
				}
				else{
					ba = new BlueArrow(ARROWALLYX, ARROWALLYYTHREE);
				}
			}
			blueArrows.add(ba); 
		}
	}
	
	private void unselect() {
		if(currentPhase == MAINMENUOPTIONSPHASE){
			currentPhase = PARTYMEMBERCHOOSINGPHASE;
			// FINISH THIS LATER: 
			//	REMOVE THE PARTY MEMBER FROM THE CHOSEN ARRAY (WHICH U ALSO NEED TO WRITE)
		}
		if(currentPhase == PLAYERCHOOSINGATTACKPHASE){
			currentPhase = MAINMENUOPTIONSPHASE;
			blueArrows.remove(1);
			blueArrows.get(0).unfreeze();
			promptChatBoolean = false;
		}
		else if(currentPhase == CHOOSINGSKILLPHASE){
			currentPhase = MAINMENUOPTIONSPHASE;
			choseSkill = false;
			blueArrows.get(0).unfreeze();
			blueArrows.get(0).setXY(ARROW1X, ARROW2Y);
		}
		else if(currentPhase == PLAYERCHOOSINGENEMYFORSKILLPHASE){
			currentPhase = CHOOSINGSKILLPHASE;
			blueArrows.remove(1);
			choseSkill = false;
			blueArrows.get(0).unfreeze();
		}
		else if(currentPhase == PLAYERCHOOSINGALLYPHASE){
			currentPhase = CHOOSINGSKILLPHASE;
			choseSkill = false;
			blueArrows.remove(1);
			blueArrows.get(0).unfreeze();
		}
	}

	@Override
	public void keyReleased(int k) {
		
	}

}