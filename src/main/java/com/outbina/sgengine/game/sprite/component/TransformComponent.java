package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.sprite.SpriteComponentBase;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Point3D;

public class TransformComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "transform";
	public static final String[] DEPEND = null;
			
	public static final String X = "x";
	public static final String Y = "y";
	public static final String Z = "z";
	
	public static final String SCALE_X = "scale_x";
	public static final String SCALE_Y = "scale_y";
	public static final String SCALE_Z = "scale_z";
	
	public static final String ROTATE_X = "rotate_x";
	public static final String ROTATE_Y = "rotate_y";
	public static final String ROTATE_Z = "rotate_z";
	
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
		newProperty(X, Double.class, 0d);
		newProperty(Y, Double.class, 0d);
		newProperty(Z, Double.class, 0d);
		
		newProperty(SCALE_X, Double.class, 1d);
		newProperty(SCALE_Y, Double.class, 1d);
		newProperty(SCALE_Z, Double.class, 1d);
		
		newProperty(ROTATE_X, Double.class, 0d);
		newProperty(ROTATE_Y, Double.class, 0d);
		newProperty(ROTATE_Z, Double.class, 0d);
	}
	
	public DoubleProperty xProperty() {
		return (DoubleProperty) attributes().get(X);
	}
	
	public DoubleProperty yProperty() {
		return (DoubleProperty) attributes().get(Y);
	}
	
	public DoubleProperty zProperty() {
		return (DoubleProperty) attributes().get(Z);
	}
	
	public DoubleProperty scaleXProperty() {
		return (DoubleProperty) attributes().get(SCALE_X);
	}
	
	public DoubleProperty scaleYProperty() {
		return (DoubleProperty) attributes().get(SCALE_Y);
	}
	
	public DoubleProperty scaleZProperty() {
		return (DoubleProperty) attributes().get(SCALE_Z);
	}
	
	public DoubleProperty rotateXProperty() {
		return (DoubleProperty) attributes().get(ROTATE_X);
	}
	
	public DoubleProperty rotateYProperty() {
		return (DoubleProperty) attributes().get(ROTATE_Y);
	}
	
	public DoubleProperty rotateZProperty() {
		return (DoubleProperty) attributes().get(ROTATE_Z);
	}
	
	public void setPosition(double x,double y,double z) {
		xProperty().set(x);
		yProperty().set(y);
		zProperty().set(z);
	}
	
	public void setScale(double x,double y,double z) {
		scaleXProperty().set(x);
		scaleYProperty().set(y);
		scaleZProperty().set(z);
	}
	
	public void setRotate(double x,double y,double z) {
		rotateXProperty().set(x);
		rotateYProperty().set(y);
		rotateZProperty().set(z);
	}
	
	public static final Point3D X_AXIS = new Point3D(1, 0, 0);
	public static final Point3D Y_AXIS = new Point3D(0, 1, 0);
	public static final Point3D Z_AXIS = new Point3D(0, 0, 1);
	
	@Override
	public void start() { }
	
	@Override
	public void update(int interval) {}
	
	@Override
	public boolean unique() {
		return true;
	}


}
