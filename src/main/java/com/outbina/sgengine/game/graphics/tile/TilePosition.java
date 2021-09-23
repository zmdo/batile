package com.outbina.sgengine.game.graphics.tile;

import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;

public class TilePosition {
	
	private ITilesGridRegion region;     // 区域范围
	private int row,col;                 // 所处行列
	private int offsetX ,offsetY ; // 偏移量

	public TilePosition(ITilesGridRegion region,int row,int col) {
		this.region = region;
		this.row = row;
		this.col = col;
		this.offsetX = 0;
		this.offsetY = 0;
	}
	
	public TilePosition(ITilesGridRegion region,int row,int col,int dx,int dy) {
		this.region = region;
		this.row = row;
		this.col = col;
		this.offsetX = dx;
		this.offsetY = dy;
	}
	
	public int getX(int height) {
		return region.getTileX(row, col, height) + offsetX;
	}
	
	public int getY(int height) {
		return region.getTileY(row, col, height) + offsetY;
	}

	/***********************
	 *       Getter        *
	 ***********************/
	
	/***********************/
	public ITilesGridRegion getRegion() {
		return region;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}
	
}
