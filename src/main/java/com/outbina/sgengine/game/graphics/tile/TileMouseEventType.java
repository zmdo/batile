package com.outbina.sgengine.game.graphics.tile;

public enum TileMouseEventType {
	/**
	 * 鼠标在瓷砖范围内移动
	 */
	MOUSE_MOVED,
	/**
	 * 鼠标按下
	 */
	MOUSE_PRESSED,
	/**
	 * 鼠标释放
	 */
	MOUSE_RELEASED,
	/**
	 * 鼠标拖动
	 */
	MOUSE_DRAGGED,
	/**
	 * 鼠标点击
	 */
	MOUSE_CLICKED,
	/**
	 * 鼠标进入瓷砖范围
	 */
	MOUSE_ENTERED,
	/**
	 * 鼠标离开瓷砖范围
	 */
	MOUSE_EXITED,
}