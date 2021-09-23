package com.outbina.sgengine.game.graphics.tile;

import java.io.Serializable;

/**
 * 平铺区域大小
 */
public class TiledArea implements Serializable {

	private static final long serialVersionUID = -1533575867634122021L;
	
	private int occupiedRows;
	private int occupiedCols;
	private int height;
	
	private boolean[][] occupiedMatrix;
	private boolean[][] drawableMatrix;
	
	public TiledArea(int rows,int cols,int height) {
		this(rows,cols,height,null,null);
	}
	
	public TiledArea(int rows,int cols,int height,boolean[][] occupiedMatrix,boolean[][] drawableMatrix) {
		this.occupiedRows = rows;
		this.occupiedCols = cols;
		this.height = height;
		if (occupiedMatrix != null) {
			this.occupiedMatrix = occupiedMatrix;
		} else {
			this.occupiedMatrix = new boolean[rows][cols];
			for(int i = 0; i < rows; i ++) {
				for(int j = 0; j< cols; j++ ) {
					this.occupiedMatrix[i][j] = true;
				}
			}
		}
		
		if (drawableMatrix != null) {
			this.drawableMatrix = drawableMatrix;
		} else {
			this.drawableMatrix = new boolean[rows][cols];
			for(int i = 0; i < rows; i ++) {
				for(int j = 0; j< cols; j++ ) {
					this.drawableMatrix[i][j] = true;
				}
			}
		}
		
	}

	public int getOccupiedRows() {
		return occupiedRows;
	}

	public int getOccupiedCols() {
		return occupiedCols;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * 判断行块是否是被占据的
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOccupied(int row,int col) {
		return occupiedMatrix[row][col];
	}
	
	/**
	 * 判断行块是否可以绘制
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean canDraw(int row,int col) {
		return drawableMatrix[row][col];
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TiledArea) {
			TiledArea size = (TiledArea)obj;
			if (size.getOccupiedRows() == this.occupiedRows &&
				size.getOccupiedCols() == this.occupiedCols &&
				size.getHeight() == this.height) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "TiledArea [occupiedRows=" + occupiedRows + ", occupiedCols=" + occupiedCols + ", height="
				+ height + "]";
	}

	
}
