package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.sprite.SpriteComponentBase;

public class TileGridRendererComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "tile_grid_renderer";
	public static final String[] DEPEND = {
			TransformComponent.COMPONENT_NAME,
			TileGridComponent.COMPONENT_NAME,
	};
	
	@Override
	public String getName() {
		return COMPONENT_NAME;
	}

	@Override
	public String[] depend() {
		return DEPEND;
	}

	private TileGridComponent tileGridComponent;
	
	@Override
	public void start() {
		tileGridComponent = getComponent(TileGridComponent.class).get(0);
	}

	@Override
	public void update(int interval) {
		
		
		
		
		
	}

	@Override
	protected void initProperties() {
		// TODO Auto-generated method stub
		
	}

}
