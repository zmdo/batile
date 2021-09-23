package com.outbina.sgengine.game;

import javafx.geometry.Rectangle2D;

public class Rectangle extends Rectangle2D {
	
	private Size size;
	
	public Rectangle(double minX, double minY, double width, double height) {
		super(minX, minY, width, height);
		this.size = new Size(width, height);
	}

	public Size getSize() {
		return size;
	}
	
}
