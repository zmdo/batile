package com.outbina.sgengine.game.graphics;

import java.util.List;

import com.outbina.sgengine.game.graphics.interfaces.IAtlas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Atlas implements IAtlas {

	private ObservableList<TextureBlock> textureBlocks;
	
	public Atlas(List<TextureBlock> textureBlocks) {
		this.textureBlocks = FXCollections.observableList(textureBlocks);
	}
	
	public Atlas(TextureBlock...textureBlocks) {
		this.textureBlocks = FXCollections.observableArrayList(textureBlocks);
	}
	
	public Atlas() {
		this.textureBlocks = FXCollections.emptyObservableList();
	}
	
	@Override
	public ObservableList<TextureBlock> textureBlocks() {
		return textureBlocks;
	}

	@Override
	public TextureBlock getTextureBlockByIndex(int index) {
		return textureBlocks.get(index);
	}

	@Override
	public int getTotalTextureBlocks() {
		return textureBlocks.size();
	}

}
