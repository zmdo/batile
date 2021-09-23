package com.outbina.sgengine.game.sprite;

import java.util.List;
import java.util.Map;

import com.outbina.sgengine.game.sprite.interfaces.ISprite;
import com.outbina.sgengine.game.sprite.interfaces.ISpriteComponent;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public abstract class SpriteComponentBase implements ISpriteComponent {

	private ISprite sprite;
	private BooleanProperty availableProperty = new SimpleBooleanProperty(this,"available",true);
	
	private ObservableMap<String,Property<?>> tempMap;
	private Map<String, Property<?>> propertysMap;
	
	public SpriteComponentBase() {
		this.propertysMap = FXCollections.unmodifiableObservableMap(initMap());
	}
	
	private ObservableMap<String,Property<?>> initMap(){
		this.tempMap = FXCollections.emptyObservableMap();
		initProperties();
		return tempMap;
	}
	
	protected <T extends ISpriteComponent> List<T> getComponent(Class<T> clazz) {
		return this.sprite.getComponent(clazz);
	}
	
	protected abstract void initProperties();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <T> Property<?> newProperty(String paramName,Class<T> type,T initialValue) {
		
		Property<?> property = null;
		
		if (type.isEnum()) {
			property = new SimpleObjectProperty<T>(this, paramName, initialValue);
		} else if (type.isArray()) {
			property = new SimpleListProperty
					(this, paramName,FXCollections.observableArrayList(initialValue));
		} else {
			if(type.equals(Byte.class) || type.equals(byte.class)) {
				property = new SimpleIntegerProperty(this, paramName, (byte)initialValue);
			} else if(type.equals(Short.class) || type.equals(short.class)) {
				property = new SimpleIntegerProperty(this, paramName, (short)initialValue);
			} else if(type.equals(Integer.class) || type.equals(int.class)) {
				property = new SimpleIntegerProperty(this, paramName, (int)initialValue);
			} else if(type.equals(Long.class) || type.equals(long.class)) {
				property = new SimpleLongProperty(this, paramName, (long)initialValue);
			} else if(type.equals(Float.class) || type.equals(float.class)) {
				property = new SimpleFloatProperty(this, paramName, (float)initialValue);
			} else if(type.equals(Double.class) || type.equals(double.class)) {
				property = new SimpleDoubleProperty(this, paramName, (double)initialValue);
			} else if(type.equals(Boolean.class) || type.equals(boolean.class)) {
				property = new SimpleBooleanProperty(this, paramName, (boolean)initialValue);
			} else if(type.equals(Character.class) || type.equals(char.class)) {
				property = new SimpleIntegerProperty(this, paramName, (char)initialValue);
			} else if(type.equals(String.class)) {
				property = new SimpleStringProperty(this, paramName, (String)initialValue);
			} else if(type.equals(Map.class)) {
				property = new SimpleMapProperty
				(this, paramName, FXCollections.observableMap((Map)initialValue));
			} else if(type.equals(List.class)) {
				property = new SimpleListProperty
				(this, paramName,FXCollections.observableArrayList((List)initialValue));
			} else {
				property = new SimpleObjectProperty<T>(this, paramName, initialValue);
			}
		}
		
		tempMap.put(paramName, property);
		return property;
	}
	
	@Override
	public void setSprite(ISprite sprite) {
		this.sprite = sprite;
	}

	@Override
	public ISprite getSprite() {
		return sprite;
	}

	@Override
	public Map<String, Property<?>> attributes() {
		return propertysMap;
	}
	
	public BooleanProperty availableProperty() {
		return availableProperty;
	}
}
