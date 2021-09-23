package com.outbina.sgengine.game.graphics.interfaces;

import java.io.IOException;

public interface IModel2DAnimationLoader {
	
	/**
	 * 加载配置文件
	 * @param string
	 * @throws IOException
	 */
	public void load(String string) throws IOException ;
	
	public IModel2DAnimation createAnimationByName(String name);
	
}
