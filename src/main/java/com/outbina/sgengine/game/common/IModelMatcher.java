package com.outbina.sgengine.game.common;

import java.io.IOException;

import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.tile.TileInfo;

/**
 * 模型匹配器
 */
public interface IModelMatcher {
	
	/**
	 * 加载配置文件
	 * @param string
	 * @throws IOException
	 */
	public void load(String string) throws IOException;
	
	/**
	 * 根据tile单元获取模型
	 * @param tileInfo 
	 * @return 模型
	 */
	public IModel2D getModel(TileInfo tileInfo);
	
}
