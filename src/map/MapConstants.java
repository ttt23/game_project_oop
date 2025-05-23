package map;

import utilz.LoadSave;

public class MapConstants {
	public static int totalMap = 4; // tổng số lượng map trong game;
	public static String[] mapInfo = {"map1.png", "map2.png", "map3.png","map4.png","map5.png"}; //mảng lưu trữ tên của từng map, VD: mapInfo[1] = map_1.png; 
	public static Map[] map = {new Map(LoadSave.GetMapData(mapInfo[0])), new Map(LoadSave.GetMapData(mapInfo[1])), 
			new Map(LoadSave.GetMapData(mapInfo[2])),new Map(LoadSave.GetMapData(mapInfo[3])),new Map(LoadSave.GetMapData(mapInfo[4]))}; 
}