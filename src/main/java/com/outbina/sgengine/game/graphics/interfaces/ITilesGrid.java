package com.outbina.sgengine.game.graphics.interfaces;

/**
 * 瓷砖网格的接口
 * <p>用于处理绘制瓷砖时的绘制区域问题。
 */
public interface ITilesGrid {

	/**
	 * 获取瓷砖网格范围。
	 * @param centerRow 中心行
	 * @param centerCol 中心列
	 * @param startX 距离左上角的X轴方向的像素距离
	 * @param startY 距离左上角的Y轴方向的像素距离
	 * @return 瓷砖网格范围
	 */
	public ITilesGridRegion getTileGridRegion(int centerRow,int centerCol,int startX,int startY);
	
	/**
	 * 从中心行列出发，X轴方向移动dx像素距离，Y轴方向移动dy像素距离后，根据其所处的位置，获取其瓷砖网格范围。
	 * @param centerRow 中心行
	 * @param centerCol 中心列
	 * @param dx X轴方向移动dx像素距离
	 * @param dy Y轴方向移动dy像素距离
	 * @return 瓷砖网格范围
	 */
	public ITilesGridRegion relocation(int centerRow,int centerCol,int dx,int dy);
	
	/**
	 * 判断行列是否在范围内：
	 * <p> return (0 <= row && row < maxRows) && (0 <= col && col < maxCols)
	 * @param row 当前行
	 * @param col 当前列
	 * @return 在范围内返回true，否则返回false
	 */
	public boolean isInRegion(int row,int col);
	
//	public ObjectProperty<Size> gridSizeProperty();
//	public ObjectProperty<Size> tileSizeProperty();
//	public IntegerProperty maxRowsProperty();
//	public IntegerProperty maxColsProperty();
//	public IntegerProperty xPaddingProperty();
//	public IntegerProperty yPaddingProperty();
	
	/**
	 * 获取单块瓷砖的宽度
	 * @return 单块瓷砖的宽度
	 */
	public int getTileWidth();
	
	/**
	 * 获取单块瓷砖的高度
	 * @return 单块瓷砖的高度
	 */
	public int getTileHeight();
	
}
