package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[100];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/world01.txt");
		
		}
	
	public void getTileImage() {
		
		try {
			
			//base grass tile
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Serene_Village_16x16.png"));
			tile[0].image = tile[0].image.getSubimage(48, 0, 16, 16);
			BufferedImage scaledImage = new BufferedImage(gp.tileSize, gp.tileSize, tile[0].image.getType());
			Graphics2D g2 = scaledImage.createGraphics();
			g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
			tile[0].image = scaledImage;
		
			//wall side up
			setup(14, "wallup&down2", true);
			
 			//wall side up&down
			setup(1, "wallup&down", true);
			
			//wall side right
			setup(9, "wallright", true);	
			
			//wall side left			
			setup(16, "wallleft", true);
			
			//wall corner top left	 
			setup(10, "wallcornertopleft", true);
			
			//wall corner top right
			setup(11, "wallcornertopright", true);
			
			//wall corner bottom left
			setup(12, "wallcornerbottomleft", true);
			
			//wall corner bottom right
			setup(13, "wallcornerbottomright", true);
			
			//tile: wood floor
			setup(3, "woodfloor", false);

			//tile: tree
			setup(4, "tree", true);
			
			//WATER TILES
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Serene_Village_16x16.png")); // plain water
			tile[2].image = tile[2].image.getSubimage(192, 16, 16, 16);
			tile[2].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Serene_Village_16x16.png")); // top left corner water
			tile[6].image = tile[6].image.getSubimage(176, 0, 16, 16);
			tile[6].collision = true;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Serene_Village_16x16.png")); // left water bank
			tile[7].image = tile[7].image.getSubimage(176, 16, 16, 16);
			tile[7].collision = true;
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Serene_Village_16x16.png")); // bottom left corner water
			tile[8].image = tile[8].image.getSubimage(176, 32, 16, 16);
			tile[8].collision = true;
			
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/Serene_Village_16x16.png")); //sand
			tile[5].image = tile[5].image.getSubimage(48, 0, 16, 16);
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
 			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
 			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
 			tile[index].collision = collision;
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e){
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			
			int tileNum = mapTileNum[worldCol][worldRow];
		
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX  + gp.player.screenX && 
					worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			}

			worldCol++;
			if(worldCol == gp.maxWorldCol) {
				worldCol  = 0;

				worldRow++;

				
			}
		}
	}
	
}

