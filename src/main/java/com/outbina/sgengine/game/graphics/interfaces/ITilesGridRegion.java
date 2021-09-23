package com.outbina.sgengine.game.graphics.interfaces;

/**
 * 瓷砖网格范围接口
 */
public interface ITilesGridRegion {

	/**
	 * 根据行列返回瓷砖中心的X坐标
	 * @param row 行
	 * @param col 列
	 * @param height 高度
	 * @return X坐标
	 */
	public int getTileX(int row,int col,int height);
	
	/**
	 * 根据行列返回瓷砖中心的Y坐标
	 * @param row 行
	 * @param col 列
	 * @param height 高度
	 * @return Y坐标
	 */
	public int getTileY(int row,int col,int height);
	
	/**
	 * 判断在坐标(x,y)位置上，是第几行第几列
	 * @param x X轴坐标
	 * @param y Y轴坐标
	 * @return 一个二维数组，[0]为行，[1]为列
	 */
	public int[] getTilePosition(int x,int y);
	
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
	
	/**
	 * 获取X轴方向(水平方向)上的偏移量
	 * @return X轴方向(水平方向)上的偏移量
	 */
	public int getStartX();
	
	/**
	 * 获取Y轴方向(垂直方向)上的偏移量
	 * @return Y轴方向(垂直方向)上的偏移量
	 */
	public int getStartY();
	
	/**
	 * 获取绘制时处于中心位置上的行
	 * <p><b>注意</b>: 处于中心位置上的行需要忽略其在水平方向上的偏移量
	 * @return 绘制时处于中心位置上的行
	 */
	public int getCenterRow();
	
	/**
	 * 获取绘制时处于中心位置上的列
	 * <p><b>注意</b>: 处于中心位置上的列需要忽略其在垂直方向上的偏移量
	 * @return 绘制时处于中心位置上的列
	 */
	public int getCenterCol();
	
	/**
	 * 获取开始行
	 * @return 开始行
	 */
	public int getStartRow();
	
	/**
	 * 获取结束行
	 * @return 结束行
	 */
	public int getEndRow();
	
	/**
	 * 获取开始列
	 * @return 开始列
	 */
	public int getStartCol();
	
	/**
	 * 获取结束列
	 * @return 结束列
	 */
	public int getEndCol();
	
}