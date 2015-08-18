package Entity;

import Main.GamePanel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.Rectangle;
import java.io.Serializable;

public abstract class MapObject implements Serializable {
	
	public final static int FACING_NONE = -1;
	public final static int FACING_UP = 0;
	public final static int FACING_RIGHT = 1;
	public final static int FACING_DOWN = 2;
	public final static int FACING_LEFT = 3;
	
	public static final int PRIORITYNONE = 0;
	public static final int PRIORITYUP = 1;
	public static final int PRIORITYRIGHT = 2;
	public static final int PRIORITYDOWN = 3;
	public static final int PRIORITYLEFT = 4;
	
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected int directionFacing;
	
	// animation actions
	protected int WALKINGFRONT; // = 0;
	protected int WALKINGLEFT; //= 1;
	protected int WALKINGRIGHT; //= 2;
	protected int WALKINGBACK; //= 3;
	protected int IDLEFRONT; //= 4;
	protected int IDLELEFT; //= 5;
	protected int IDLERIGHT; //= 6;
	protected int IDLEBACK; //=7;
	
	// movement
									
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;

	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double maxSpeedFast;
	protected double maxSpeedNormal;
	protected boolean highSpeed;
	
	// Sort Later
	protected int flinchFrequency;
	protected int spriteDelayTime;
	
	// constructor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize(); 
		maxSpeedFast = 3.3;
		highSpeed = false;
	}
	
	public void setTileMap(TileMap tileMap) { this.tileMap = tileMap; }
	
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(
				(int)x - cwidth,
				(int)y - cheight,
				cwidth,
				cheight
		);
	}
	
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
	}
	
	public void checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		boolean movedOnce = false;
		
		calculateCorners(x, ydest);
		if(dy < 0 && up) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else {
				ytemp += dy;
				//System.out.println("dy changes");
				//movedOnce = true;
			}
		}
		else if(dy > 0 && down) {
			if(bottomLeft || bottomRight){ //|| movedOnce) {
				dy = 0;
				//falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			else {
				ytemp += dy;
				//System.out.println("dy changes");
				//movedOnce = true;
			}
		}
		
		calculateCorners(xdest, y);
		
		if(dx < 0 && left) {
			if(topLeft || bottomLeft){ // || movedOnce) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			else {
				xtemp += dx;
				//System.out.println("dx changes");
				//movedOnce = true;
			}
		}
		else if(dx > 0 && right) {
			if(topRight || bottomRight){ // || movedOnce) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
				//System.out.println("dx changes");
				//movedOnce = true;
			}
		}
	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	
	public void setXY(int x, int y) { this.x = x; this.y = y; }
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; } 
	
	public void setRight() { 
		right = true;
		left = false;
		up = false;
		down = false;
	}
	public void setLeft() {
		right = false;
		left = true;
		up = false;
		down = false;
	}
	public void setUp() {
		right = false;
		left = false;
		up = true;
		down = false;
	}
	public void setDown() { 
		right = false;
		left = false;
		up = false;
		down = true;
	}
	
	public void getNextPosition() {
		if (highSpeed == true) maxSpeed = maxSpeedFast;
		if (highSpeed == false)	maxSpeed = maxSpeedNormal;
		boolean movedOnce = false;
		if(left){
			directionFacing = FACING_LEFT;
			dx -= moveSpeed;
			if(dx < -maxSpeed) dx = -maxSpeed;
			movedOnce = true;
		} else if(right) {
			directionFacing = FACING_RIGHT;
			dx += moveSpeed;
			if(dx > maxSpeed) dx = maxSpeed;
			movedOnce = true;
		} else if(up){
			directionFacing = FACING_UP;
			dy -= moveSpeed;
			if(dy < -maxSpeed) dy = -maxSpeed;
			movedOnce = true;
		} else if(down){
			directionFacing = FACING_DOWN;
			dy += moveSpeed;
			if(dy > maxSpeed) dy = maxSpeed;
			movedOnce = true;
		}
		if(!movedOnce){
			currentAction = 4;
			if(dx > 0){
				dx -= stopSpeed;
				if(dx < 0) dx = 0;
			} else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) dx = 0;
			}
			if(dy > 0){
				dy -= stopSpeed * 2;
				if(dy < 0) dy = 0;
			} else if(dy < 0){
				dy += stopSpeed;
				if(dy > 0) dy = 0;
			}
		}
	
	}
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > GamePanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > GamePanel.HEIGHT;
	}

	public void runTo(int x, int y, int time) {
		
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(directionFacing == FACING_RIGHT){
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}
		else{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null); 
		} 
	}
	
}
















