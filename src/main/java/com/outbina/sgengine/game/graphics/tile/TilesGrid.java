package com.outbina.sgengine.game.graphics.tile;

import com.outbina.sgengine.game.Size;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGrid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * 瓷砖网格的默认实现类
 * <p>用于处理绘制瓷砖时的绘制区域问题。本实现类仅仅适用于 宽高比为2:1的瓷砖绘制区域处理问题。
 */
public class TilesGrid implements ITilesGrid {
	
	public static final String TILE_GRID = "tile_grid";
	public static final String TILE_GRID_SIZE = "tile_grid_size";
	public static final String TILE_SIZE = "tile_size";
	public static final String TILE_GRID_MAX_ROWS = "tile_grid_rows";
	public static final String TILE_GRID_MAX_COLS = "tile_grid_cols";
	public static final String X_PADDING = "x_padding";
	public static final String Y_PADDING = "y_Padding";
	
	private ObjectProperty<Size> gridSizeProperty = new SimpleObjectProperty<Size>(this, TILE_GRID_SIZE, null);               // 网格整体的宽和高
	private ObjectProperty<Size> tileSizeProperty = new SimpleObjectProperty<Size>(this, TILE_SIZE, null);               // 地板的宽和高
	private IntegerProperty maxRowsProperty = new SimpleIntegerProperty(this,TILE_GRID_MAX_ROWS,255);         // 最大行数，最大列数
	private IntegerProperty maxColsProperty = new SimpleIntegerProperty(this,TILE_GRID_MAX_COLS,255);
	private IntegerProperty xPaddingProperty = new SimpleIntegerProperty(this,X_PADDING,0) ;       // x方向内间距，y方向内间距
	private IntegerProperty yPaddingProperty = new SimpleIntegerProperty(this,Y_PADDING,0) ;
	
	public TilesGrid(
			int gridWidth, int gridHeight, 
			int tileWidth, int tileHeight,
			int maxRows, int maxCols) {
		this(new Size(gridWidth, gridHeight),
			 new Size(tileWidth, tileHeight),
			 maxRows,maxCols);
	}
	
	public TilesGrid(
			Size gridSize,
			Size tileSize,
			int maxRows,int maxCols) {
		this.gridSizeProperty.set(gridSize);
		this.tileSizeProperty.set(tileSize);
		this.maxRowsProperty.set(maxRows);
		this.maxColsProperty.set(maxCols);
	}
	
	/**
	 * 获取瓷砖网格范围。
	 * @param centerRow 中心行
	 * @param centerCol 中心列
	 * @param startX 距离左上角的X轴方向的像素距离
	 * @param startY 距离左上角的Y轴方向的像素距离
	 * @return 瓷砖网格范围
	 */
	@Override
	public TilesGridRegion getTileGridRegion(int centerRow,int centerCol,int startX,int startY) {
		
		int width = (int) gridSizeProperty.get().getWidth();
		int height = (int) gridSizeProperty.get().getHeight();
		
		int tileWidth = (int) tileSizeProperty.get().getWidth();
		int tileHeight = (int) tileSizeProperty.get().getHeight();

		int xPadding = this.xPaddingProperty.get();
		int yPadding = this.yPaddingProperty.get();
		
		// 去下内边距
		int tWidth = width - xPadding*2;
		int tHeight = height - yPadding*2;
		
		// 计算中心点位置
		int centerX = width/2 ;
		int centerY = height/2 ;
		
		// 计算补偿行大小
		int offsetRows = tWidth/(tileWidth/2);
		int offsetCols = tHeight/(tileHeight) ;
		
		// 取其中的最大值
		if(offsetCols > offsetRows) {
			offsetRows = offsetCols;
		} else {
			offsetCols = offsetRows;
		}
		
		// 计算开始行、开始列 以及 结束行,结束列
		int startRow = centerRow - offsetRows +1;
		int startCol = centerCol - offsetCols +1;
		int endRow = centerRow + offsetRows ;
		int endCol = centerCol + offsetCols ;
		
		// 最大行限定
		int maxRows = maxRowsProperty.get();
		int maxCols = maxColsProperty.get();
		if(endRow > maxRows) {
			endRow = maxRows;
		}
		if(endCol > maxCols) {
			endCol = maxCols;
		}
		
		return new TilesGridRegion(
				startX,startY,
				xPadding,yPadding,
				tileWidth, tileHeight,
				startRow, startCol, 
				endRow, endCol, 
				centerX, centerY, 
				centerRow, centerCol);
	}

	/**
	 * 从中心行列出发，X轴方向移动dx像素距离，Y轴方向移动dy像素距离后，根据其所处的位置，获取其瓷砖网格范围。
	 * @param centerRow 中心行
	 * @param centerCol 中心列
	 * @param dx X轴方向移动dx像素距离
	 * @param dy Y轴方向移动dy像素距离
	 * @return 瓷砖网格范围
	 */
	@Override
	public TilesGridRegion relocation(int centerRow,int centerCol,int dx,int dy) {

		int tileWidth = (int) tileSizeProperty.get().getWidth();
		int tileHeight = (int) tileSizeProperty.get().getHeight();
		
		int[] PositionIncrements = TilesGridHelper.computePositionIncrements(tileWidth, tileHeight, dx, dy);
		int addedRows = PositionIncrements[0];
		int addedCols = PositionIncrements[1];
		
		int newRow = centerRow - addedRows;
		int newCol = centerCol - addedCols;
		
		int nowX = dx - (addedRows - addedCols)*(tileWidth/2);
		int nowY = dy - (addedRows + addedCols)*(tileHeight/2);
		
		return getTileGridRegion(newRow, newCol, nowX, nowY);
		
	}
	
	/**
	 * 判断行列是否在范围内：
	 * <p> return (0 <= row && row < maxRows) && (0 <= col && col < maxCols)
	 * @param row 当前行
	 * @param col 当前列
	 * @return 在范围内返回true，否则返回false
	 */
	@Override
	public boolean isInRegion(int row,int col) {
		return 
			(0 <= row && row < maxRowsProperty.get())&&
			(0 <= col && col < maxColsProperty.get());
	}
	
	/***********************
	 *  Getter and Setter  *
	 ***********************/
	
	/***********************/
	
	public ObjectProperty<Size> gridSizeProperty(){
		return gridSizeProperty;
	}
	public ObjectProperty<Size> tileSizeProperty(){
		return tileSizeProperty;
	}
	public IntegerProperty maxRowsProperty() {
		return maxRowsProperty;
	}
	public IntegerProperty maxColsProperty() {
		return maxColsProperty;
	}
	public IntegerProperty xPaddingProperty() {
		return xPaddingProperty;
	}
	public IntegerProperty yPaddingProperty() {
		return yPaddingProperty;
	}
	
	public void setGridSize(Size gridSize) {
		this.gridSizeProperty.set(gridSize);
	}
	
	public int getGridWidth() {
		return (int) gridSizeProperty.get().getWidth();
	}

	public int getGridHeight() {
		return (int) gridSizeProperty.get().getHeight();
	}

	public int getMaxRows() {
		return maxRowsProperty.get();
	}

	public void setMaxRows(int maxRows) {
		this.maxRowsProperty.set(maxRows);
	}

	public int getMaxCols() {
		return maxColsProperty.get();
	}

	public void setMaxCols(int maxCols) {
		this.maxColsProperty.set(maxCols);
	}

	public int getXPadding() {
		return xPaddingProperty.get();
	}

	public void setXPadding(int xPadding) {
		this.xPaddingProperty.set(xPadding);
	}

	public int getYPadding() {
		return yPaddingProperty.get();
	}

	public void setYPadding(int yPadding) {
		this.yPaddingProperty.set(yPadding);
	}

	public void setTileSize(Size tileSize) {
		this.tileSizeProperty.set(tileSize);
	}
	
	public int getTileWidth() {
		return (int) tileSizeProperty.get().getWidth();
	}

	public int getTileHeight() {
		return (int) tileSizeProperty.get().getHeight();
	}

	@Override
	public String toString() {
		return "TilesGrid [gridSize=" + gridSizeProperty + ", tileSize=" + tileSizeProperty + ", maxRows=" + maxRowsProperty + ", maxCols="
				+ maxColsProperty + ", xPadding=" + xPaddingProperty + ", yPadding=" + yPaddingProperty + "]";
	}

}
