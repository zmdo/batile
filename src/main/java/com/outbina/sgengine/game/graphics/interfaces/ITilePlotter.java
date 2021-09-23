package com.outbina.sgengine.game.graphics.interfaces;

import com.outbina.sgengine.game.graphics.tile.TileInfo;
import com.outbina.sgengine.game.graphics.tile.TilePosition;

import javafx.scene.canvas.Canvas;

/**
 * 用来绘制单个地形的绘制器
 */
public interface ITilePlotter {
	
	/**
	 * 根据地形信息绘制瓷砖
	 * @param tileInfo 瓷砖信息
	 * @param canvas    画布
	 * @param position  地形所处位置
	 */
	public void draw(TileInfo tileInfo,Canvas canvas,TilePosition position);

}
