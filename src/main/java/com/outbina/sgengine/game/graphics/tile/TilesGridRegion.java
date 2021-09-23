package com.outbina.sgengine.game.graphics.tile;

import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;

/**
 * 瓷砖网格范围的默认实现类
 */
public class TilesGridRegion implements ITilesGridRegion {
	
	private int tileWidth,tileHeight;           // 地板的宽和高
	private int halfTileWidth,halfTileHeight;
	private int startX,startY;
	
	private int startRow,startCol;              // 起始行，起始列
	private int endRow,endCol;                  // 结束行，结束列
	
	private int centerX,centerY;                // 中心位置坐标
	private int centerRow,centerCol;
	
	public TilesGridRegion(
			int startX,int startY,
			int xPadding,int yPadding,
			int tileWidth, int tileHeight,  
			int startRow, int startCol,
			int endRow, int endCol, 
			int centerX, int centerY, 
			int centerRow, int centerCol) {
		
		super();
		this.startX = startX;
		this.startY = startY;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = endRow;
		this.endCol = endCol;
		this.centerX = centerX;
		this.centerY = centerY;
		this.centerRow = centerRow;
		this.centerCol = centerCol;
		
		this.halfTileWidth = tileWidth/2;
		this.halfTileHeight = tileHeight/2;
	}

	/**
	 * 根据行列返回瓷砖中心的X坐标
	 * @param row 行
	 * @param col 列
	 * @return X坐标
	 */
	@Override
	public int getTileX(int row,int col,int height) {
		return ((row - col) - (centerRow - centerCol ))*halfTileWidth + (startX + centerX);
	}
	
	/**
	 * 根据行列返回瓷砖中心的Y坐标
	 * @param row 行
	 * @param col 列
	 * @return Y坐标
	 */
	@Override
	public int getTileY(int row,int col,int height) {
		return ((row + col)- (centerRow + centerCol))*halfTileHeight  + (startY + centerY) - height*halfTileHeight;
	}
	
	/**
	 * 判断在坐标(x,y)位置上，是第几行第几列
	 * @param x X轴坐标
	 * @param y Y轴坐标
	 * @return 一个二维数组，[0]为行，[1]为列
	 */
	@Override
	public int[] getTilePosition(int x,int y) {
		
		// 计算出相对偏移量
		int dx = startX + centerX - x;
		int dy = startY + centerY - y;
		
		// 根据相对偏移量计算出行数和列数增加量
		int[] PositionIncrements = TilesGridHelper.computePositionIncrements(tileWidth, tileHeight, dx, dy);
		
		// 计算出新的行列就是最终结果
		int newRow = centerRow - PositionIncrements[0];
		int newCol = centerCol - PositionIncrements[1];
		
		return new int[] {newRow,newCol};
	}
	
	/***********************
	 *  Getter and Setter  *
	 ***********************/
	
	/***********************/
	@Override
	public int getTileWidth() {
		return tileWidth;
	}
	
	@Override
	public int getTileHeight() {
		return tileHeight;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getEndCol() {
		return endCol;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getCenterRow() {
		return centerRow;
	}

	public int getCenterCol() {
		return centerCol;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	@Override
	public String toString() {
		return "TilesGridRegion [tileWidth=" + tileWidth + ", tileHeight=" + tileHeight + ", halfTileWidth="
				+ halfTileWidth + ", halfTileHeight=" + halfTileHeight + ", startX=" + startX + ", startY=" + startY
				+ ", startRow=" + startRow + ", startCol=" + startCol + ", endRow=" + endRow + ", endCol=" + endCol
				+ ", centerX=" + centerX + ", centerY=" + centerY + ", centerRow=" + centerRow + ", centerCol="
				+ centerCol + "]";
	}
}
