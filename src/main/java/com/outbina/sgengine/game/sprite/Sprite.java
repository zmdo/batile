package com.outbina.sgengine.game.sprite;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import com.outbina.sgengine.game.sprite.component.TransformComponent;
import com.outbina.sgengine.game.sprite.interfaces.ISprite;
import com.outbina.sgengine.game.sprite.interfaces.ISpriteComponent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Sprite implements ISprite {

	private Map<String,ObservableList<ISpriteComponent>> components;
	private List<ISpriteComponent> invorkQueue;
	
	public Sprite() {
		components = FXCollections.emptyObservableMap();
		invorkQueue = FXCollections.emptyObservableList();
		addComponent(new TransformComponent());
	}
	
	@Override
	public boolean addComponent(ISpriteComponent component) {
		if(components.containsKey(component.getName())) {
			if(component.unique()) {
				return false;
			} else {
				components.get(component.getName()).add(0,component);
				int index = invorkQueue.indexOf(components.get(component.getName()).get(0));
				invorkQueue.add(index, component);
				return true;
			}
		} else {
			// 判断依赖关系
			if (component.depend() == null) {
				invorkQueue.add(0, component);
			} else {
				int last = 0;
				for (String dependComponentName : component.depend()) {
					// 如果包含依赖
					if(components.containsKey(dependComponentName)) {
						for(ISpriteComponent dc : invorkQueue) {
							if(dc.getName().equals(dependComponentName)) {
								int index = invorkQueue.indexOf(dc);
								if (index > last) {
									last = index;
								}
								break;
							}
						}
					} else {
						return false;
					}
				}
				invorkQueue.add(last+1, component);
			}
			
			// 加入组件系统
			components.put(component.getName(), FXCollections.observableArrayList(component));
			component.setSprite(this);
			return true;
		}
	}

	@Override
	public ObservableList<ISpriteComponent> getComponentByName(String name) {
		return FXCollections.unmodifiableObservableList(components.get(name));
	}

	@Override
	public <T extends ISpriteComponent> ObservableList<T> getComponent(Class<T> clazz) {
		
		T instance = null;
		
		try {
			instance = clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		if(instance == null) {
			return null;
		}
		
		String name = instance.getName();
		if(name != null && components.containsKey(name)) {
			ObservableList<ISpriteComponent> components = this.getComponentByName(name);
			ObservableList<T> result = FXCollections.emptyObservableList();
			for(ISpriteComponent component : components) {
				result.add(clazz.cast(component));
			}
			return result;
		}

		return null;
	}
	
	@Override
	public void update(int interval) {
		for(ISpriteComponent component : invorkQueue) {
			if(component.getAvailable()) {
				component.update(interval);
			}
		}
	}

	@Override
	public boolean removeComponent(ISpriteComponent component) {
		
		String name = component.getName();
		
		if(name.equals(TransformComponent.COMPONENT_NAME)) {
			return false;
		}
		
		if(!components.containsKey(name)) {
			return true;
		}
		
		// 检查被依赖关系
		for(ISpriteComponent comp : invorkQueue) {
			for(String dependComponentName : comp.depend()) {
				if(dependComponentName.equals(name)) {
					return false;
				}
			}
		}
		
		List<ISpriteComponent> comList =  components.get(name);
		comList.remove(component);
		if (comList.isEmpty()) {
			components.remove(name);
		}
		invorkQueue.remove(component);
		component.setSprite(null);
		return true;
	}

	@Override
	public void init() {
		for(ISpriteComponent component : invorkQueue) {
			if(component.getAvailable()) {
				component.start();
			}
		}
	}
	
}
