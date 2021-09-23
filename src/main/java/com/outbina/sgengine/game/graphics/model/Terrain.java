package com.outbina.sgengine.game.graphics.model;

import com.outbina.sgengine.game.graphics.interfaces.ITileShell;
import com.outbina.sgengine.game.graphics.tile.TiledArea;

public class Terrain extends Model2D {
	
	private static final long serialVersionUID = 678872832245175517L;

	public Terrain(Integer id, String name, TiledArea tiledArea, ITileShell tileShell) {
		super(id, name, tiledArea, tileShell);
	}

}
