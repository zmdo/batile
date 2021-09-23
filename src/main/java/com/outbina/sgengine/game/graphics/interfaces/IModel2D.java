package com.outbina.sgengine.game.graphics.interfaces;

import java.io.Serializable;

import com.outbina.sgengine.game.graphics.tile.TiledArea;

/**
 * 模型接口
 */
public interface IModel2D extends Serializable{
	
	public Integer getId();
	
	public String getName();
	
	/**
	 * 获取可绘制的瓷砖块集合
	 * @return
	 */
	public ITileShell getTileShell();
	
	/**
	 * 所需绘制图形大小
	 * @return
	 */
	public TiledArea getTiledArea();
	
}
