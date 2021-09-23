package com.outbina.sgengine.game.map;

public class TileSlope {
	
	/*                                     NW N NE W C E SW S SE */
	public static final int[] SLOPE_FLAT = {0,0,0, 0,0,0, 0,0,0};
	public static final int[] SLOPE_N    = {0,0,0, 0,0,0, 0,1,0};
	public static final int[] SLOPE_W    = {0,0,0, 0,0,1, 0,0,0};
	public static final int[] SLOPE_E    = {0,0,0, 1,0,0, 0,0,0};
	public static final int[] SLOPE_S    = {0,1,0, 0,0,0, 0,0,0};
	
	public static final int[] SLOPE_NW   = {0,0,0, 0,0,0, 0,0,1};
	public static final int[] SLOPE_NE   = {0,0,0, 0,0,0, 1,0,0};
	public static final int[] SLOPE_SW   = {0,0,1, 0,0,0, 0,0,0};
	public static final int[] SLOPE_SE   = {1,0,0, 0,0,0, 0,0,0};
	
	public static final int[] SLOPE_NWS  = {0,0,0, 0,0,0, 0,0,0};
//	public static final int[] SLOPE_NWS  = {0,0,0, 0,0,0, 0,0,0};
//	public static final int[] SLOPE_NWS  = {0,0,0, 0,0,0, 0,0,0};
//	public static final int[] SLOPE_NWS  = {0,0,0, 0,0,0, 0,0,0};
	
	private TileSlope() {
		
	}
	
	public boolean match(int[][] martix) {
		return false;
	}
}
