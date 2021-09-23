package com.outbina.sgengine.game.graphics.interfaces;

import com.outbina.sgengine.game.Rectangle;
import com.outbina.sgengine.game.Size;
import com.outbina.sgengine.game.graphics.tile.TiledArea;

public interface ITileShellBuilder {

	/**
	 * 
	 * @param texture   纹理
	 * @param region    需要处理的纹理范围
	 * @param tileSize  纹理中对应的单个瓷砖的大小
	 * @param currentTileSize 当前使用的瓷砖大小
	 * @param tiledArea 平铺的区域
	 * @return
	 */
	public ITileShell createTileShell(ITexture texture,Rectangle region,Size tileSize,Size currentTileSize ,TiledArea tiledArea);
	
}
