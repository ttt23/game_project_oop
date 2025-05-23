package entities;

import main.Game;
import utilz.LoadSave;

public class PlayerData {
	
	public String img;
	public int width;
	public int height;
	public int rowNum;
	public int columnNum;
	public int imgWidth;
	public int imgHeight;
	public int xOffset;
	public int yOffset;
	public float hitboxX, hitboxY;
	
	public int attackBox1Width;
	public int attackBox1Height;
	public int at1i;
	public int at1j;
	public int attackBox2Width;
	public int attackBox2Height;
	public int at2i;
	public int at2j;
	public int attackBox3Width;
	public int attackBox3Height;
	public int at3i;
	public int at3j;
	public int SpecialAttackBoxWidth;
	public int SpecialAttackBoxHeight;
	public int sti;
	public int stj;
	public float playerSpeed;
	public float playerScale;
	
	public int specialAttackDamage;
	public int attack1Damage;
	public int attack2Damage;
	public int attack3Damage;
	public int playerMaxHealth;
	
	public PlayerData(String string, float playerSpeed, float playerScale,int playerMaxHealth,
			int width, int height, int a, int b, int c, int d, 
			int e, int f, float hitboxX, float hitboxY,
			int attackbox1Width,int attackbox1Height,int at1i,int at1j,
			int attackbox2Width,int attackbox2Height,int at2i,int at2j,
			int attackbox3Width,int attackbox3Height,int at3i,int at3j,
			int SpecialAttackBoxWidth,int SpecialAttackBoxHeight, int sti, int stj,
			int specialAttackDamage,int attack1Damage,int attack2Damage,int attack3Damage) {
		this.img = string;
		this.playerSpeed = playerSpeed*playerScale;
		this.playerMaxHealth = playerMaxHealth;
		
		this.width = (int)(width*playerScale);
		this.height = (int)(height*playerScale);
		this.rowNum = a;
		this.columnNum = b;
		this.imgWidth = c;
		this.imgHeight = d;
		
		this.xOffset = (int)(e*playerScale);
		this.yOffset = (int)(f*playerScale);
		this.hitboxX = hitboxX;
		this.hitboxY = hitboxY;
		
		this.attackBox1Height=(int)(attackbox1Height*playerScale);
		this.attackBox1Width=(int)(attackbox1Width*playerScale);
		this.at1i=(int)(at1i*playerScale);
		this.at1j=(int)(at1j*playerScale);
		
		this.attackBox2Height=(int)(attackbox2Height*playerScale);
		this.attackBox2Width=(int)(attackbox2Width*playerScale);
		this.at2i=(int)(at2i*playerScale);
		this.at2j=(int)(at2j*playerScale);
		
		this.attackBox3Height=(int)(attackbox3Height*playerScale);
		this.attackBox3Width=(int)(attackbox3Width*playerScale);
		this.at3i=(int)(at3i*playerScale);
		this.at3j=(int)(at3j*playerScale);
		
		this.SpecialAttackBoxWidth=(int)(SpecialAttackBoxWidth*playerScale);
		this.SpecialAttackBoxHeight=(int)(SpecialAttackBoxHeight*playerScale);
		this.sti=(int)(sti*playerScale);
		this.stj=(int)(stj*playerScale);
		
		this.specialAttackDamage = specialAttackDamage;
		this.attack1Damage = attack1Damage;
		this.attack2Damage = attack2Damage;
		this.attack3Damage = attack3Damage;
	}
	public static final PlayerData SHOUNEN_SAMURAI = new PlayerData(LoadSave.SHOUNEN_SAMURAI_ATLAS,1.1f * Game.SCALE,1.2f,250,
			(int)(138*Game.SCALE),(int)(138*Game.SCALE), 11, 10, 96, 96,
			(int)(58.4*Game.SCALE),(int)(48*Game.SCALE),6.46875f,3.067f,
			59,72,38,-18,
			50,63,30,-15,
			42,85,22,-16,
			42,42,38,28,
			30,10,10,10);
	public static final PlayerData DEMON_SAMURAI = new PlayerData(LoadSave.DEMON_SAMURAI_ATLAS,1.03f * Game.SCALE,1.2f,300,
			(int)(128*Game.SCALE),(int)(108*Game.SCALE), 20, 26, 128, 108,
			(int)(53.6*Game.SCALE),(int)(48*Game.SCALE),6f,2.4f,
			34,36,14,14,
			39,48,19,5,
			39,55,18,-1,
			43,96,23,-46,
			36,13,12,15);
	public static final PlayerData WOLF_SAMURAI = new PlayerData(LoadSave.WOLF_SAMURAI_ATLAS, 1.2f * Game.SCALE,1.2f,230,
			(int)(192*Game.SCALE),(int)(64*Game.SCALE), 20, 12, 192, 64,
			(int)(77.4*Game.SCALE),(int)(26.66*Game.SCALE),5.2f,2.4f,
			25,48,-12,-18,
			24,41,-13,-23,
			23,61,-15,-23,
			25,45,-12,-15,
			30,14,13,13);
	public static final PlayerData SHOGUN_SAMURAI = new PlayerData(LoadSave.SHOGUN_SAMURAI_ATLAS, 1.01f * Game.SCALE,1.2f,270,
			(int)(172.8*Game.SCALE),(int)(69.12*Game.SCALE), 11, 11, 250, 110,
			(int)(74*Game.SCALE),(int)(21*Game.SCALE),7f,1.52f,
			49,60,25,-20,
			25,70,2,-20,
			32,50,9,0,
			75,30,52,10,
			30,10,12,11);
	public static final PlayerData MASTER_SAMURAI = new PlayerData(LoadSave.MASTER_SAMURAI_ATLAS,1.0f * Game.SCALE,1.2f,180,
			(int)(144*Game.SCALE),(int)(144*Game.SCALE), 11, 15, 96, 96,
			(int)(62*Game.SCALE),(int)(74*Game.SCALE),6.75f,3.2f,
			60,42,40,-3,
			60,75,40,-15,
			60,79,40,-20,
			38,123,18,-73,
			30,10,8,8);
	public static final PlayerData WANDERLUST_SAMURAI = new PlayerData(LoadSave.WANDERLUST_SAMURAI_ATLAS,1.02f * Game.SCALE,1.2f,240,
			(int)(159*Game.SCALE),(int)(126*Game.SCALE), 10, 10, 106, 84,
			(int)(69.4*Game.SCALE),(int)(73.5*Game.SCALE),7.44f,2.8f,
			39,84,19,-33,
			39,84,19,-33,
			39,84,19,-33,
			54,125,34,-70,
			30,13,13,13);


	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int JUMP = 2;
	public static final int FALLING = 3;
	public static final int HIT = 4;
	public static final int ATTACK_1 = 5;
	public static final int ATTACK_2 = 6;
	public static final int ATTACK_3 = 7;
	public static final int SPECIAL_ATTACK =8;
	public static final int DEATH = 9;
	public static final int SKILL = 10;

	public static int GetSpriteAmount(PlayerData playerData, int playerAction) {
		if(playerData == SHOUNEN_SAMURAI)
			switch (playerAction) {
			case IDLE:
				return 5;
			case RUNNING:
				return 8;
			case JUMP:
				return 3;
			case HIT:
				return 4;
			case ATTACK_1:
				return 6;
			case ATTACK_2:
			case ATTACK_3:
				return 5;
			case SPECIAL_ATTACK:
				return 10;
			case DEATH:
				return 10;
			case FALLING:
				return 1;
			case SKILL:
				return 7;	
		}
		if(playerData == DEMON_SAMURAI)
			switch (playerAction) {
		case IDLE:
			return 6;
		case RUNNING:
			return 8;
		case JUMP:
			return 3;
		case HIT:
			return 4;
		case ATTACK_1:
		case ATTACK_3:
			return 7;
		case ATTACK_2:
			return 5;
		case SPECIAL_ATTACK:
			return 12;
		case DEATH:
			return 26;
		case SKILL:
			return 17;
		case FALLING:
			return 1;
		}
		if(playerData == WOLF_SAMURAI)
			switch (playerAction) {
		case IDLE:
		case RUNNING:
		case ATTACK_1:
		case HIT:
			return 6;
		case JUMP:
			return 3;
		case ATTACK_2:
			return 4;
		case ATTACK_3:
			return 5;
		case SPECIAL_ATTACK:
			return 12;
		case DEATH:
			return 8;
		case FALLING:
			return 1;
		}
		if(playerData == SHOGUN_SAMURAI)
			switch (playerAction) {
			case IDLE:
				return 5;
			case RUNNING:
				return 7;
			case JUMP:
				return 3;
			case HIT:
				return 3;
			case ATTACK_1:
			case ATTACK_2:
				return 5;
			case ATTACK_3:
				return 10;
			case SPECIAL_ATTACK:
				return 11;
			case DEATH:
				return 10;
			case FALLING:
				return 1;
			case SKILL:
				return 8;	
		}
		if(playerData == MASTER_SAMURAI)
			switch (playerAction) {
			case IDLE:
				return 5;
			case RUNNING:
				return 8;
			case JUMP:
				return 3;
			case HIT:
				return 4;
			case ATTACK_1:
			case ATTACK_2:
			case ATTACK_3:
				return 5;
			case SPECIAL_ATTACK:
				return 14;
			case DEATH:
				return 10;
			case FALLING:
				return 1;
			case SKILL:
				return 15;	
		}
		if(playerData == WANDERLUST_SAMURAI)
			switch (playerAction) {
			case IDLE:
				return 4;
			case RUNNING:
				return 8;
			case JUMP:
				return 3;
			case HIT:
				return 3;
			case ATTACK_1:
			case ATTACK_2:
			case ATTACK_3:
				return 5;
			case SPECIAL_ATTACK:
				return 9;
			case DEATH:
				return 10;
			case FALLING:
				return 1;
		}
		return 1;
	}	
}
