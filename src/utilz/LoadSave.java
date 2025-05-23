package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	public static final String SHOUNEN_SAMURAI_ATLAS = "shounen_samurai.png";
	public static final String DEMON_SAMURAI_ATLAS = "demon_samurai.png";
	public static final String WOLF_SAMURAI_ATLAS = "wolf_samurai.png";
	public static final String SHOGUN_SAMURAI_ATLAS = "shogun_samurai.png";
	public static final String MASTER_SAMURAI_ATLAS = "master_samurai.png";
	public static final String WANDERLUST_SAMURAI_ATLAS = "wanderlust_samurai.png";
	public static final String MAP_ATLAS = "complete_tileset.png";
	public static final String SHURIKEN = "shuriken.png";

	public static final String MAP1_DATA = "map1.png";
	public static final String MAP2_DATA = "map2.png";
	public static final String MAP3_DATA = "map3.png";
	public static final String MAP4_DATA = "map4.png";
	public static final String MAP5_DATA = "map5.png";

	public static final String MENU_BUTTON = "MenuButtons_atlas.png";
	public static final String RIGHT_BUTTON = "RightKey.png";
	public static final String LEFT_BUTTON = "LeftKey.png";
	public static final String SOUND_BUTTON = "sound_button.png";
	public static final String URM_BUTTON = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "Swiper2.png";
	public static final String SLIDER= "Swiper1.png";
	public static final String MENU_BACKGROUND_IMG = "menu_background.jpg";
	public static final String TREES = "Trees.png";
	public static final String OBJECTS = "Objects.png";
	public static final String PROPS = "Props.png";
	public static final String FLAG = "Flag.png";
	public static final String BACKGROUND_IMG1 = "1.png";
	public static final String BACKGROUND_IMG2 = "2.png";
	public static final String BACKGROUND_IMG3= "3.png";
	public static final String BACKGROUND_IMG4 = "4.png";
	public static final String BACKGROUND_IMG5 = "5.png";
	public static final String HEALTH_BAR = "MinimumDamage-Sheet.png";
	public static final String HEALTH_REGEN = "LifeHealing-Sheet.png";
	public static final String MANA_REGEN = "ManaRegeneration-Sheet.png";
	public static final String MANA_FADING = "ManaFading-Sheet.png";
	public static final String MANA_SPARKLING = "ManaSparkling-Sheet.png";

	public static final String PLAYER1_WIN = "player_1_win.png";
	public static final String PLAYER2_WIN = "player_2_win.png";
	public static final String PLAYER1_SBG = "player1_sbg.png";
	public static final String PLAYER2_SBG = "player2_sbg.png";
	public static final String PLAYERSELECTION = "player_selection.png";
	public static final String GAME_OVER = "gameover.png";
	public static final String RESTART_BUTTON = "Restart.png";
	public static final String MAIN_MENU_BUTTON = "Main_menu.png";
	public static final String RESUME_BUTTON = "Resume.png";
	public static final String PICKYOURMAP = "pick_your_map.jpg"; 
	public static final String TUTORIAL0 = "tutorial0.png"; 
	public static final String TUTORIAL1 = "tutorial1.png"; 
	public static final String TUTORIAL2 = "tutorial2.png"; 
	public static final String TUTORIAL3 = "tutorial3.png"; 
	public static final String TUTORIAL4 = "tutorial4.png"; 
	public static final String TUTORIAL5 = "tutorial5.png"; 
	public static final String TUTORIAL6 = "tutorial6.png"; 
	public static final String SETTING_BACKGROUND = "setting_background.png"; 
	public static final String PAUSE_BACKGROUND = "pause_background.png"; 



	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img=null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+fileName);
		try {
			 img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}

public static int[][] GetMapData(String mapData){
		
		BufferedImage img = GetSpriteAtlas(mapData);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		for(int j=0; j< img.getHeight();j++)
			for(int i=0; i< img.getWidth();i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if(value>=117)
					value=0;
				lvlData[j][i]=value;
			}
		return lvlData;
	}
}
