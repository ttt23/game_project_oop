package map;

public class Map {
	private int[][]lvlData;
	public Map(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getMapData(){
		return lvlData;
	}
}
