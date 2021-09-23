package com.outbina.sgengine.game.graphics.tile;

import java.util.HashMap;

public class TileInfo {
	
	/* 基本属性 */
	private int row,col;               // 位置
	private int height;                // 高度
	
	private TiledBoundary boundary;    // 平铺范围 ,画其中一块
	private Integer type;
	
	/* 其他不定属性 */
	private HashMap<String,Object> attributes;

	public TileInfo(int row, int col, int height , int type ,TiledBoundary boundary) {
		super();
		this.row = row;
		this.col = col;
		this.height = height;
		this.type = type;
		this.boundary = boundary;
		this.attributes = new HashMap<String, Object>();
	}
	
	public HashMap<String,Object> attributes() {
		return this.attributes;
	}
	
	/***********************
	 *  Getter and Setter  *
	 ***********************/

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getType() {
		return type;
	}

	public TiledBoundary getBoundary() {
		return boundary;
	}


}
