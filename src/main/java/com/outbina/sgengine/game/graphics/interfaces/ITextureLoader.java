package com.outbina.sgengine.game.graphics.interfaces;

import java.io.IOException;

/**
 * 纹理加载器
 */
public interface ITextureLoader {
	
	/**
	 * 加载配置文件
	 * @param string
	 * @throws IOException
	 */
	public void load(String string) throws IOException ;
	
	/**
	 * 通过模型id获取纹理
	 * @param id
	 * @return
	 */
	public ITexture findTextureById(int id);
	
	/**
	 * 通过模型名称获取纹理
	 * @param name
	 * @return
	 */
	public ITexture findTextureByName(String name);
	
}
