package com.outbina.sgengine.game.map;

import com.outbina.sgengine.game.graphics.tile.TileInfo;

public class MapBlock {
	
	private int blockId;
	
	private int startRow,startCol;
	private TileInfo tileInfos[][];
	
	public MapBlock(int id,int startRow,int startCol,TileInfo tiles[][]) {
		this.blockId =id;
		this.startRow = startRow;
		this.startCol = startCol;
		this.tileInfos = tiles;
	}
	
	public TileInfo getCell(int row, int col) {
		return tileInfos[row - startRow][col - startCol];
	}
	
	public TileInfo[][] getCells() {
		return tileInfos;
	}
	
	public int getSideRows() {
		return tileInfos.length;
	}
	
	public int getBlockId() {
		return blockId;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getStartCol() {
		return startCol;
	}


	
}
