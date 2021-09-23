package com.outbina.sgengine.game.graphics.tile;

/**
 * 瓷砖网格的帮助类
 */
public final class TilesGridHelper {

	public static final double HYPOTENUSE_BASE = Math.sqrt(5);
	
	private TilesGridHelper() {}
	
	/**
	 * 根据相对X轴的偏移量dx与Y轴的偏移量dy计算出行数和列数增加量
	 * @param tileWidth  瓷砖宽度
	 * @param tileHeight 瓷砖高度
	 * @param dx 相对X轴的偏移量
	 * @param dy 相对Y轴的偏移量
	 * @return 行数和列数的增加量，其中[0]为增加的行数，[1]为增加的列数
	 */
	public static final int[] computePositionIncrements(int tileWidth ,int tileHeight ,int dx,int dy) {
		
//		int zbX  = (int) ((dy/2 + dx/4)*HYPOTENUSE_BASE);
//		int zbY  = (int) ((dy/2 - dx/4)*HYPOTENUSE_BASE);
//		
//		double len = Math.sqrt(tileWidth*tileWidth + tileHeight*tileHeight)/4; 
		
		// 以下是对上面的注释部分的简化算法
		//////////////////////////////
		double zbX  = dy + dx/2.0;
		double zbY  = dy - dx/2.0;
		
		double len = tileHeight/2.0; 
		//////////////////////////////
		
		int addedRows = (int)(zbX/len);
		if(addedRows != 0) {
			addedRows = ( 1 + (Math.abs(addedRows)-1)/2 )*(Math.abs(addedRows)/addedRows);
		}
		int addedCols = (int)(zbY/len);
		if(addedCols != 0) {
			addedCols = ( 1 + (Math.abs(addedCols)-1)/2 )*(Math.abs(addedCols)/addedCols);
		}
		
		return new int[] {addedRows,addedCols};
	}
	
}
