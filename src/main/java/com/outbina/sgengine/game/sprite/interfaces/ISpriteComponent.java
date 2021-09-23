package com.outbina.sgengine.game.sprite.interfaces;

import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;

public interface ISpriteComponent {

	public String getName();

	public void setSprite(ISprite sprite);
	
	public ISprite getSprite();
	
	public String[] depend();
	
	public Map<String,Property<?>> attributes();
	
	public void start();
	
	public void update(int interval);
	
	public BooleanProperty availableProperty();
	
	public default boolean getAvailable() {
		return availableProperty().get();
	}
	
	public default void setAvailable(boolean available) {
		this.availableProperty().set(available);
	}
	
	public default boolean unique() {
		return true;
	};
	
}
