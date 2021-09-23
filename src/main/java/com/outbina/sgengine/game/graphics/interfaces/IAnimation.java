package com.outbina.sgengine.game.graphics.interfaces;

public interface IAnimation {
	
	/**
	 * 获取名字
	 */
	public String getName();
	
	/**
	 * 开始播放
	 */
	public void start();
	
	/**
	 * 暂停播放
	 */
	public void pause();
	
	/**
	 * 停止播放
	 */
	public void stop();
	
	/**
	 * 重播
	 */
	public void restart();
	
	/**
	 * 设置循环
	 * @param loop
	 */
	public void setLoop(boolean loop);
	
	/**
	 * 获取是否循环
	 * @return
	 */
	public boolean getLoop();
	
	
	/**
	 * 标记间隔时间
	 * @param interval
	 */
	public void tick(int interval);
	
	/**
	 * 获取当前帧是第几帧
	 * @return
	 */
	public int getCurrentFrameIndex();
	
	/**
	 * 获取总帧数
	 * @return
	 */
	public int getTotalFrames();
	
	/**
	 * @return
	 */
	public IModel2DAnimation copy();
}
