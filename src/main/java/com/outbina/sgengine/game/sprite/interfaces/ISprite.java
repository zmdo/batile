package com.outbina.sgengine.game.sprite.interfaces;

import javafx.collections.ObservableList;

public interface ISprite {
	
	public boolean addComponent(ISpriteComponent component);
	
	public boolean removeComponent(ISpriteComponent component);
	
	public ObservableList<ISpriteComponent> getComponentByName(String name);
	
	public <T extends ISpriteComponent> ObservableList<T> getComponent(Class<T> clazz);
	
	public void init();
	
	public void update(int interval);
	
}
