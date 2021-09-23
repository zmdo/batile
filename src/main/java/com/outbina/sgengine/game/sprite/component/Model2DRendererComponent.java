package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.sprite.SpriteComponentBase;

import javafx.beans.property.ObjectProperty;

public class Model2DRendererComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "model2D_renderer";
	public static final String[] DEPEND = {
			TileComponent.COMPONENT_NAME,
	}; 
	
	public static final String MODEL = "model";
	
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
		newProperty(MODEL, IModel2D.class, null);
	}

	@SuppressWarnings("unchecked")
	public ObjectProperty<IModel2D> modelProperty() {
		return (ObjectProperty<IModel2D>) attributes().get(MODEL);
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
