package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.Size;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGrid;
import com.outbina.sgengine.game.graphics.tile.TilesGrid;
import com.outbina.sgengine.game.sprite.SpriteComponentBase;

public class TileGridComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "tile_grid";
	public static final String[] DEPEND = {
			TransformComponent.COMPONENT_NAME
	};
	
	public static final String TILE_GRID = "tile_grid";
	public static final String TILE_GRID_SIZE = "tile_grid_size";
	public static final String TILE_SIZE = "tile_size";
	public static final String TILE_GRID_MAX_ROWS = "tile_grid_rows";
	public static final String TILE_GRID_MAX_COLS = "tile_grid_cols";
	public static final String X_PADDING = "x_padding";
	public static final String Y_PADDING = "y_Padding";
	
	@Override
	public String getName() {
		return COMPONENT_NAME;
	}

	@Override
	public String[] depend() {
		return DEPEND;
	}
	
	@Override
	protected void initProperties() {
		TilesGrid tilesGrid = new TilesGrid(
				new Size(1600, 900), new Size(100, 50), 255, 255);
		newProperty(TILE_GRID, ITilesGrid.class, tilesGrid);
		attributes().put(TILE_GRID_SIZE, tilesGrid.gridSizeProperty());
		attributes().put(TILE_SIZE, tilesGrid.tileSizeProperty());
		attributes().put(TILE_GRID_MAX_ROWS, tilesGrid.maxRowsProperty());
		attributes().put(TILE_GRID_MAX_COLS, tilesGrid.maxColsProperty());
		attributes().put(X_PADDING, tilesGrid.xPaddingProperty());
		attributes().put(Y_PADDING, tilesGrid.yPaddingProperty());
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update(int interval) {
		
		
		
	}

}
