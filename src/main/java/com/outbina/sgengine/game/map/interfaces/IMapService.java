package com.outbina.sgengine.game.map.interfaces;

import com.outbina.sgengine.game.graphics.tile.TileInfo;

public interface IMapService {
	
	public TileInfo getTerrain(int row,int col);

	public int getEvenLayerHeightProjection(int row,int col);
	
	public int getOddLayerHeightProjection(int row,int col);
	
}
