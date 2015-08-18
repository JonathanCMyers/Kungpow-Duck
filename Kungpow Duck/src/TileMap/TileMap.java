package TileMap;

import java.awt.*;
import java.awt.image.*;
import java.io.*; // for the readers

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap implements Serializable {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double smoother; // something that smooths idk what else to call this
	
	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private transient BufferedImage tileset;
	private int numTilesAcross;
	private int numTilesHigh;
	private Tile[][] tiles;
	
	// drawing
	// instead of drawing entire tilemap all the time we only want to draw ones on screen
	private int rowOffset; // this tells us which row to start drawing
	private int colOffset; // which column to start drawing
	private int numRowsToDraw; // how many rows we are drawing
	private int numColsToDraw;	// how many cols we are drawing
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2; 
		numColsToDraw = GamePanel.WIDTH / tileSize + 2; // 11 tiles across
		smoother = 0.07;
	}
	
	
	/*public void loadTiles(String s) { // laods tilefiles into memory
		try{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++){
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize); // this is for 2nd row
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED); // sets blocked values
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}  */
	
	/*public void loadTiles(String s){		grass water tree
		try{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			numTilesHigh = tileset.getHeight() / tileSize;
			tiles = new Tile[numTilesHigh][numTilesAcross];
			BufferedImage VONNAIMAGE;
			int tileType = Tile.NORMAL;
			for(int row = 0; row < numTilesHigh; row++){	// j
				for(int col = 0; col < numTilesAcross; col++){	// k
					if(row == 0 && col < numTilesAcross - 2) tileType = Tile.BLOCKED;	// for entire first row
					else if(row == 1 && col < numTilesAcross - 2) tileType = Tile.BLOCKED;
					else if(row == 2 && col != 0 && col != 2) tileType = Tile.BLOCKED;
					else if(row == 3 && col != 0 && col != 2 && col != 3 && col != 5 && col != numTilesAcross - 1) tileType = Tile.BLOCKED;
					else if(row == 4 && col != 3) tileType = Tile.BLOCKED;
					else if(row == 5 && col < numTilesAcross - 3) tileType = Tile.BLOCKED;
					else if(row == 6 && col == 5) tileType = Tile.BLOCKED;
					else tileType = Tile.NORMAL;
					VONNAIMAGE = tileset.getSubimage(col * tileSize,  row * tileSize,  tileSize,  tileSize);
					tiles[row][col] = new Tile(VONNAIMAGE, tileType);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	 */
	public void loadTiles(String s){				//GRASS ONLY
		try{
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			numTilesHigh = tileset.getHeight() / tileSize; 
			tiles = new Tile[numTilesHigh][numTilesAcross];
			
			BufferedImage subimage;
			int tileType = Tile.NORMAL;
			for(int col = 0; col < numTilesAcross; col++){
				//subimage = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				for(int row = 0; row < numTilesHigh; row++){
					if(row == 2 || row == 4 || (row == 3 && (col == 1 || col == 6 || col == 8 || col == 9 || col == 11))) tileType = Tile.BLOCKED;
					else if(col == 1 && row < numTilesHigh-1) tileType = Tile.BLOCKED;
					else if(col == 4 && row < numTilesHigh-1) tileType = Tile.BLOCKED;
					else tileType = Tile.NORMAL;
					subimage = tileset.getSubimage(col * tileSize, row * tileSize, tileSize, tileSize);
					tiles[row][col] = new Tile(subimage, tileType);	
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}  
	
	public void loadMap(String s) { // loads map file into memory
		// the first line is num cols, 2nd is num rows
		// rest of the lines are the map itself
		
		try{
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); // now that map file is loaded we can read it
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			
			// now we can read in map itself
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getTileSize() {return tileSize;}
	public double getx() {return x; }
	public double gety() {return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public int getType(int row, int col) {
		//System.out.print(row + "    " + col);
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType(); // the type of tyle
	}
	
	public void setSmoother(double d) { smoother = d; }
	
	public void setPosition(double x, double y){
		this.x += (x - this.x) * smoother;
		this.y += (y - this.y) * smoother;	// makes it a little smoother ???
											// kinda twitch
		

	/*	this.x = x;
		this.y = y;  */ // makes sure camera follows player exactly
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	private void fixBounds() {
		if (x < xmin) x = xmin;
		if (y < ymin) y = ymin;
		if (x > xmax) x = xmax;
		if (y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++){
			if(row >= numRows) break; // dont have to keep drawing bc nothing else to draw
			for(int col = colOffset; col < colOffset + numColsToDraw; col++){
				if(col >= numCols) break; // same as above
				if(map[row][col] == 0) continue; // bc its pure transparent its nothing
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tileSize, (int)y + row * tileSize, null);
			}
		}
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();	// Writes the rest of the data to the byte[] array
		ImageIO.write(tileset, "png", out);	// Writes the BufferedImage 
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		tileset = ImageIO.read(in);
	}
	
}





