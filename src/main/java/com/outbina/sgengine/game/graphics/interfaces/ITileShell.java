package com.outbina.sgengine.game.graphics.interfaces;

import java.io.Serializable;

import com.outbina.sgengine.game.Rectangle;
import com.outbina.sgengine.game.graphics.TextureBlock;

public interface ITileShell extends Serializable {
	
	public ITexture getTexture();
	
	public Rectangle getRegion();
	
	public TextureBlock getTextureBlock(int row, int col);
	
	public int getRows();
	
	public int getCols();
	
}
