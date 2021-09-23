package com.outbina.sgengine.game.graphics.tile;

/**
 * 平铺边界
 */
public class TiledBoundary {
	
	private int startRow,startCol;
	private int endRow,endCol;
	private TiledArea tiledArea;
	
	public TiledBoundary(int startRow,int startCol,TiledArea tiledArea) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.endRow = startRow + tiledArea.getOccupiedRows();
		this.endCol = startCol + tiledArea.getOccupiedCols();
		this.tiledArea = tiledArea;
	}
	
	/**
	 * 判断行块是否是被占据的,被占据的行块处不能绘制新的行块
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOccupied(int row,int col) {
		return tiledArea.isOccupied(row - startRow, col - startCol);
	}
	
	/**
	 * 判断行块是否可以绘制
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean canDraw(int row,int col) {
		return tiledArea.canDraw(row - startRow, col - startCol);
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

	public TiledArea getTiledArea() {
		return tiledArea;
	}
	
}
