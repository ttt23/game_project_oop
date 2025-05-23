package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import utilz.LoadSave;

public class MapManager {

	private Game game;
	private BufferedImage[] mapSprite,flag;
	private Map map;
	private BufferedImage treesImg,tree1,tree2,tree3,objectsImage,templeGate,well,props,lamppost,
	backgroundImg1,backgroundImg2,backgroundImg3,backgroundImg4,backgroundImg5,flagSprite,
	flower1,flower2,bush,rock,paddy,grass;
	private int flagI = 0;
	private int tick=0;

	public MapManager(Game game) {
		this.game = game;
		importOutSideSprites();
		backgroundImg1 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_IMG1);
		backgroundImg2 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_IMG2);
		backgroundImg3 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_IMG3);
		backgroundImg4 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_IMG4);
		backgroundImg5 = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_IMG5);
		treesImg = LoadSave.GetSpriteAtlas(LoadSave.TREES);
		props = LoadSave.GetSpriteAtlas(LoadSave.PROPS);
		objectsImage = LoadSave.GetSpriteAtlas(LoadSave.OBJECTS);
		tree1 = treesImg.getSubimage(0, 0, 144, 160);
		tree2 = treesImg.getSubimage(144, 0, 112, 160);
		tree3 = treesImg.getSubimage(256, 0, 144, 160);
		templeGate = objectsImage.getSubimage(31, 0, 98, 96);
		well = objectsImage.getSubimage(136, 0, 88, 96);
		lamppost= objectsImage.getSubimage(0, 0, 32, 96);
		flagSprite = LoadSave.GetSpriteAtlas(LoadSave.FLAG);
		flag = new BufferedImage[6];
		flower1 = props.getSubimage(0, 0, 64, 32);
		grass = props.getSubimage(64, 0, 32, 32);
		bush = props.getSubimage(96, 0, 64, 32);
		rock = props.getSubimage(160, 0, 32, 32);
		paddy = props.getSubimage(192, 0, 32, 32);
		flower2 = props.getSubimage(224, 0, 32, 32);
		for(int i =0; i<6; i++)
			flag[i] = flagSprite.getSubimage(i*29, 0, 29, 64);
	}

	private void importOutSideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAP_ATLAS);
		mapSprite = new BufferedImage[117];
		for(int j=0;j<9;j++)
			for(int i=0;i<13;i++) {
				int index = j*13+i;
				mapSprite[index]=img.getSubimage(i*32, j*32, 32, 32);
			}
	}
	public void draw(Graphics g, int lvlOffset,Map map) {
		this.map = map;
		switch(game.getBeforePlaying().index) {
		case 0:
		case 2:
		g.drawImage(backgroundImg3, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT , null);
		g.drawImage(backgroundImg2, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT , null);
		g.drawImage(backgroundImg1, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT , null);
		break;
		case 1:
		case 3:
		g.drawImage(backgroundImg4, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT , null);
		break;
		case 4:
		g.drawImage(backgroundImg5, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT , null);
		break;
		}
		for(int j=0;j< Game.TILES_IN_HEIGHT;j++)
			for(int i=0;i< map.getMapData()[0].length;i++) {
				int index = map.getSpriteIndex(i, j);
				g.drawImage(mapSprite[index], Game.TILES_SIZE*i - lvlOffset,Game.TILES_SIZE*j,Game.TILES_SIZE,Game.TILES_SIZE, null);
				if(index==13)
					g.drawImage(lamppost, Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j-2)+(int)(3.34*Game.SCALE), (int)(36*Game.SCALE), (int)(108*Game.SCALE), null);
				if(index==24)
					g.drawImage(templeGate, Game.TILES_SIZE*i - lvlOffset - (int)(64*Game.SCALE), Game.TILES_SIZE*j-(int)(17*Game.SCALE), 4*Game.TILES_SIZE+(int)(20*Game.SCALE),4*Game.TILES_SIZE+(int)(23*Game.SCALE), null);
				if(index==25)
					g.drawImage(well, Game.TILES_SIZE*i+(int)(10*Game.SCALE), Game.TILES_SIZE*j-(int)(35*Game.SCALE), (int)(136*Game.SCALE), (int)(148*Game.SCALE), null);
				if(index==26)
					g.drawImage(tree1,Game.TILES_SIZE*i - lvlOffset-(int)(34*Game.SCALE), Game.TILES_SIZE*j-(int)(95*Game.SCALE), (int)(217.8*Game.SCALE), (int)(242*Game.SCALE), null);
				if(index==38)
					g.drawImage(tree2,Game.TILES_SIZE*i - lvlOffset-(int)(34*Game.SCALE), Game.TILES_SIZE*j-(int)(95*Game.SCALE), (int)(169.4*Game.SCALE), (int)(242*Game.SCALE), null);
				if(index==39)
					g.drawImage(tree3,Game.TILES_SIZE*i - lvlOffset-(int)(34*Game.SCALE), Game.TILES_SIZE*j-(int)(95*Game.SCALE), (int)(217.8*Game.SCALE), (int)(242*Game.SCALE), null);
				if(index==51)
					g.drawImage(flag[flagI], Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j-1)-(int)(3.34*Game.SCALE), (int)(36*Game.SCALE), (int)(79.5*Game.SCALE), null);
				if(index==52)
					g.drawImage(flower1, Game.TILES_SIZE*(i-2)- lvlOffset, Game.TILES_SIZE*(j)+(int)(3.34*Game.SCALE), (int)(72*Game.SCALE), (int)(36*Game.SCALE), null);
				if(index==53)
					g.drawImage(grass, Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j)+(int)(3.34*Game.SCALE), (int)(36*Game.SCALE), (int)(36*Game.SCALE), null);
				if(index==54)
					g.drawImage(bush, Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j)+(int)(3.34*Game.SCALE), (int)(72*Game.SCALE), (int)(36*Game.SCALE), null);
				if(index==55)
					g.drawImage(rock, Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j)+(int)(3.34*Game.SCALE), (int)(36*Game.SCALE), (int)(36*Game.SCALE), null);
				if(index==56)
					g.drawImage(paddy, Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j)+(int)(3.34*Game.SCALE), (int)(36*Game.SCALE), (int)(36*Game.SCALE), null);
				if(index==62)
					g.drawImage(flower2, Game.TILES_SIZE*i- lvlOffset, Game.TILES_SIZE*(j)+(int)(3.34*Game.SCALE), (int)(36*Game.SCALE), (int)(36*Game.SCALE), null);
			}
	}

	public void update() {
		tick++;
		if(tick>25) {
			tick = 0;
			flagI++;
			if(flagI>=6)
				flagI = 0;
		}
	}
	public Map getCurrentMap() {
		return map;
	}
}
