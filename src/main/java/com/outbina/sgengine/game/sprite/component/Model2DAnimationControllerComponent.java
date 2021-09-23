package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.sprite.SpriteComponentBase;

public class Model2DAnimationControllerComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "model2D_animation_controller";
	public static final String[] DEPEND = {
			TransformComponent.COMPONENT_NAME,
			Model2DRendererComponent.COMPONENT_NAME};
	
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(int interval) {
		// TODO Auto-generated method stub
		
	}



}
