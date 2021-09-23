package com.outbina.sgengine.game.graphics.interfaces;

import com.outbina.sgengine.game.graphics.TextureBlock;

import javafx.collections.ObservableList;

public interface IAtlas {
	
	public ObservableList<TextureBlock> textureBlocks();
	
	public TextureBlock getTextureBlockByIndex(int index);

	public int getTotalTextureBlocks();
	
}
