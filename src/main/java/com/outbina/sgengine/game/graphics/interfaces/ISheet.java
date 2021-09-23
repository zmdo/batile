package com.outbina.sgengine.game.graphics.interfaces;

import com.outbina.sgengine.game.Rectangle;

public interface ISheet {
	
	public ITexture getTexture();
	
	public Rectangle getRegionByIndex(int index);
	
	public int getTotalRegions();
}
