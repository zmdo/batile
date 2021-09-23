package com.outbina.sgengine.game.graphics.interfaces;

import javafx.scene.canvas.Canvas;

/**
 * 瓷砖网格绘制器
 * 用于绘制整个范围内的瓷砖网格绘制器
 */
public interface ITilesGridPlotter {
	
	/**
	 * 绘制区域范围内的图形
	 * @param canvas
	 * @param region
	 */
	public void draw(Canvas canvas,ITilesGridRegion region);
	
}
