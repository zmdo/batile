package com.outbina.sgengine.game.graphics.tile;

import java.util.Arrays;

import com.outbina.sgengine.game.Rectangle;
import com.outbina.sgengine.game.graphics.TextureBlock;
import com.outbina.sgengine.game.graphics.interfaces.ITexture;
import com.outbina.sgengine.game.graphics.interfaces.ITileShell;

public class TileShell implements ITileShell {

	private static final long serialVersionUID = -7813451784722520311L;
	
	private ITexture texture;
	private Rectangle region;
	private TextureBlock[][] textureBlocks;
	private int rows,cols;
	
	public TileShell(
			ITexture texture, 
			Rectangle region ,
			TextureBlock[][] textureBlocks,
			int rows,int cols) {
		this.texture = texture;
		this.region = region;
		this.textureBlocks = textureBlocks;
		this.rows = rows;
		this.cols = cols;
	}
	
	public TileShell(ITexture texture, TextureBlock[][] textureBlocks) {
		this(texture,
			new Rectangle(0, 0, texture.getImage().getWidth(), texture.getImage().getHeight()),
			textureBlocks,textureBlocks.length,
			textureBlocks[0].length);
	}
	
	@Override
	public ITexture getTexture() {
		return texture;
	}

	@Override
	public Rectangle getRegion() {
		return region;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public String toString() {
		return "TileShell [texture=" + texture + ", regions=" + Arrays.toString(textureBlocks) + ", rows=" + rows + ", cols="
				+ cols + "]";
	}

	@Override
	public TextureBlock getTextureBlock(int row, int col) {
		return textureBlocks[row][col];
	}
	
}
