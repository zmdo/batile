package com.outbina.sgengine.game.graphics.interfaces;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

/**
 * 可绘制接口
 * <p> 可用于在画布上绘制一些图案
 */
public interface IDrawable {
	
	/**
	 * 根据区域范围绘制网格
	 * @param gc 画布画笔
	 * @param x 绘制的x坐标
	 * @param y 绘制的y坐标
	 */
	public void draw(GraphicsContext gc,double x,double y);
	
	/**
	 * 根据区域范围绘制网格蒙版
	 * @param gc 画布画笔
	 * @param paint 蒙版填充属性
	 * @param x 绘制的x坐标
	 * @param y 绘制的y坐标
	 */
	public void drawMask(GraphicsContext gc,Paint paint,double x,double y);
	
}
