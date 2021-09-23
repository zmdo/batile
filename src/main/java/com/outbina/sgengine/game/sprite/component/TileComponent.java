package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.graphics.interfaces.ITilesGrid;
import com.outbina.sgengine.game.sprite.SpriteComponentBase;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public class TileComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "tile";
	public static final String[] DEPEND = {
			TransformComponent.COMPONENT_NAME,
	};
	
	public static final String TILE_GRID = "tile_grid";
	public static final String ROW = "row";
	public static final String COL = "col";
	
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
		newProperty(TILE_GRID, ITilesGrid.class, null);
		newProperty(ROW, Integer.class, 0);
		newProperty(COL, Integer.class, 0);
	}
	
	@SuppressWarnings("unchecked")
	public ObjectProperty<ITilesGrid> TileGridProperty() {
		return (ObjectProperty<ITilesGrid>) attributes().get(TILE_GRID);
	}
	
	public IntegerProperty rowProperty() {
		return (IntegerProperty) attributes().get(ROW);
	}
	
	public IntegerProperty colProperty() {
		return (IntegerProperty) attributes().get(COL);
	}

	@Override
	public void start() {}

	@Override
	public void update(int interval) {}

}
