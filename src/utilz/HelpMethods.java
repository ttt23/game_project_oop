package utilz;

import java.awt.geom.Rectangle2D;

import entities.PlayerData;
import main.Game;

public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float witdth, float height, int[][] lvlData) {
		if(!IsSolid(x, y, lvlData))
			if(!IsSolid(x+witdth, y+height, lvlData))
				if(!IsSolid(x+witdth, y, lvlData))
					if(!IsSolid(x, y+height, lvlData))
						if(!IsSolid(x+witdth, y+height/2, lvlData))
							if(!IsSolid(x, y+height/2, lvlData))
								if(!IsSolid(x+witdth, y+height/4, lvlData))
									if(!IsSolid(x, y+height/4, lvlData))
										if(!IsSolid(x+witdth, y+3*height/4, lvlData))
											if(!IsSolid(x, y+3*height/4, lvlData))
												if(!IsSolid(x+witdth/2, y+height, lvlData))
													if(!IsSolid(x+witdth/2, y, lvlData))
														return true;
		return false;
					
	}
	
	public static boolean IsSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if(x < 0 || x >= maxWidth)
			return true;
		if(y>=Game.GAME_HEIGHT)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		if(yIndex<0)
			yIndex = 0;
		int value = lvlData[(int)yIndex][(int)xIndex];

		switch(value) {
		case 0:
		case 11:
		case 13:
		case 24:
		case 25:
		case 26:
		case 38:
		case 39:
		case 51:
		case 52:
		case 53:
		case 54:	
		case 55:
		case 56:	
		case 62:	
		case 76:
		case 77:
		case 90:
			return false;
		}
		return true;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0) {
			int tilePos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE-hitbox.width%Game.TILES_SIZE);
			return tilePos + xOffset -1;
		}
		if(xSpeed<0)
			return
				currentTile * Game.TILES_SIZE + hitbox.x%Game.TILES_SIZE;
		else 
			return currentTile * Game.TILES_SIZE;
	}
	public static float GetEntityYPosUnderRoofOrAboveFlorr(Rectangle2D.Float hitbox, float airSpeed, PlayerData playerData) {

		int currentTile = (int)((hitbox.y+hitbox.height)/Game.TILES_SIZE);
		if(airSpeed > 0) {
			int tileYPos = currentTile * Game.TILES_SIZE;
				return tileYPos + Game.TILES_SIZE -hitbox.height - 1;
		} else
			return (int)((hitbox.y)/Game.TILES_SIZE) * Game.TILES_SIZE;
		
	}
	public static boolean IsTilse12(float x, float y, int[][] lvlData) {
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		if(yIndex<0)
			yIndex = 0;
		int value = lvlData[(int)yIndex][(int)xIndex];
		if(value==12)
			return true;
		else 
			return false;
	}
	public static boolean CheckOnObject(Rectangle2D.Float hitbox, int[][] lvlData, float airspeed) {
		if(hitbox.height+hitbox.y+airspeed<Game.GAME_HEIGHT) {
		if(airspeed<0)
			if((IsTilse12(hitbox.x, hitbox.y+airspeed, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+hitbox.height, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+hitbox.height, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+hitbox.height/4, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+hitbox.height/4, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+hitbox.height/2, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+hitbox.height/2, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+3*hitbox.height/4, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+3*hitbox.height/4, lvlData))
				&&(!(IsSolid(hitbox.x, hitbox.y+airspeed, lvlData)&&(!IsTilse12(hitbox.x, hitbox.y+airspeed, lvlData)))
				&&!(IsSolid(hitbox.x+hitbox.width, hitbox.y+airspeed, lvlData)&&!IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed, lvlData))
				&&!(IsSolid(hitbox.x, hitbox.y+hitbox.height/4+airspeed, lvlData)&&!IsTilse12(hitbox.x, hitbox.y+hitbox.height/4+airspeed, lvlData))
				&&!(IsSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height/4+airspeed, lvlData)&&!IsTilse12(hitbox.x+hitbox.width, hitbox.y+hitbox.height/4+airspeed, lvlData))))
			return true;
		 if(airspeed>=0) 
			if((IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+hitbox.height/4, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+hitbox.height/4, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+hitbox.height/2, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+hitbox.height/2, lvlData)||
			   IsTilse12(hitbox.x, hitbox.y+airspeed+3*hitbox.height/4, lvlData)||
			   IsTilse12(hitbox.x+hitbox.width, hitbox.y+airspeed+3*hitbox.height/4, lvlData))
				&&(!(IsSolid(hitbox.x, hitbox.y+hitbox.height+airspeed, lvlData)&&(!IsTilse12(hitbox.x, hitbox.y+hitbox.height+airspeed, lvlData)))
				&&!(IsSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height+airspeed, lvlData)&&!IsTilse12(hitbox.x+hitbox.width, hitbox.y+hitbox.height+airspeed, lvlData))
				&&!(IsSolid(hitbox.x, hitbox.y+3*hitbox.height/4+airspeed, lvlData)&&!IsTilse12(hitbox.x, hitbox.y+3*hitbox.height/4+airspeed, lvlData))
				&&!(IsSolid(hitbox.x+hitbox.width, hitbox.y+3*hitbox.height/4+airspeed, lvlData)&&!IsTilse12(hitbox.x+hitbox.width, hitbox.y+3*hitbox.height/4+airspeed, lvlData))))
				return true;
		}
		return false;
	}
	public static boolean CheckNextToObject(Rectangle2D.Float hitbox, int[][] lvlData, float xSpeed) {
		if((0<hitbox.x+xSpeed)&&(hitbox.x+xSpeed+hitbox.width<Game.GAME_WIDTH)) {
		if(IsSolid(hitbox.x+xSpeed, hitbox.y+99*hitbox.height/100, lvlData)&&!IsTilse12(hitbox.x+xSpeed, hitbox.y+99*hitbox.height/100, lvlData))
			return false;
		if(IsSolid(hitbox.x+xSpeed+hitbox.width, hitbox.y+99*hitbox.height/100, lvlData)&&!IsTilse12(hitbox.x+xSpeed+hitbox.width, hitbox.y+99*hitbox.height/100, lvlData))
			return false;
		if((IsTilse12(hitbox.x+xSpeed, hitbox.y, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width, hitbox.y, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width, hitbox.y+hitbox.height, lvlData)||
		   IsTilse12(hitbox.x+xSpeed, hitbox.y+hitbox.height, lvlData)||
		   IsTilse12(hitbox.x+xSpeed, hitbox.y+hitbox.height/4, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width, hitbox.y+hitbox.height/4, lvlData)||
		   IsTilse12(hitbox.x+xSpeed, hitbox.y+hitbox.height/2, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width, hitbox.y+hitbox.height/2, lvlData)||
		   IsTilse12(hitbox.x+xSpeed, hitbox.y+3*hitbox.height/4, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width, hitbox.y+3*hitbox.height/4, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width/2, hitbox.y, lvlData)||
		   IsTilse12(hitbox.x+xSpeed+hitbox.width/2, hitbox.y+hitbox.height, lvlData)))
			return true;
		}
		return false;
	}
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if(!IsSolid(hitbox.x, hitbox.y+hitbox.height+1, lvlData))
				if(!IsSolid(hitbox.x +hitbox.width, hitbox.y+hitbox.height+1, lvlData))
					return false;
		return true;
	}
	public static boolean traped(Rectangle2D.Float hitbox, int[][] lvlData) {
		float xIndex1 = hitbox.x / Game.TILES_SIZE;
		float yIndex1 = (hitbox.y+hitbox.height) / Game.TILES_SIZE;
		float xIndex2 = (hitbox.x+hitbox.width) / Game.TILES_SIZE;
		float yIndex2 = (hitbox.y+hitbox.height) / Game.TILES_SIZE;
		if(yIndex1<0)
			yIndex1 = 0;
		if(yIndex2<0)
			yIndex2 = 0;
		int value1 = lvlData[(int)yIndex1][(int)xIndex1];
		int value2 = lvlData[(int)yIndex2][(int)xIndex2];
		if(value1==0||value2==0)
			return true;
		else 
			return false;
	}
	public static boolean spiked(Rectangle2D.Float hitbox, int[][] lvlData) {
		float xIndex1 = hitbox.x / Game.TILES_SIZE;
		float yIndex1 = (hitbox.y+hitbox.height) / Game.TILES_SIZE;
		float xIndex2 = (hitbox.x+hitbox.width) / Game.TILES_SIZE;
		float yIndex2 = (hitbox.y+hitbox.height) / Game.TILES_SIZE;
		if(yIndex1<0)
			yIndex1 = 0;
		if(yIndex2<0)
			yIndex2 = 0;
		int value1 = lvlData[(int)yIndex1][(int)xIndex1];
		int value2 = lvlData[(int)yIndex2][(int)xIndex2];
		if(value1==76||value2==76||value1==77||value2==77||value1==90||value2==90)
			return true;
		else 
			return false;
	}
}
