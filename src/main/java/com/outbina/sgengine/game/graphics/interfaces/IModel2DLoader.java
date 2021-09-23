package com.outbina.sgengine.game.graphics.interfaces;

import java.io.IOException;

public interface IModel2DLoader<T extends IModel2D> {

	/**
	 * 加载配置文件
	 * @param string
	 * @throws IOException
	 */
	public void load(String string) throws IOException ;
	
	/**
	 * 通过模型id获取模型
	 * @param id
	 * @return
	 */
	public T findModelById(int id);
	
	/**
	 * 通过模型名称获取模型
	 * @param name
	 * @return
	 */
	public T findModelByName(String name);
	
}
