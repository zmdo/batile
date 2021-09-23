package com.outbina.sgengine.game;

public class Size {
	
	private double width ; 
	private double height; 
	
	public Size(double width,double height) {
		this.width = width;
		this.height = height;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public Size scale(double scaleX,double scaleY) {
		return new Size(width*scaleX, height*scaleY);
	}
	
	public Size mul(double n) {
		return new Size(width*n, height*n);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Size) {
			Size dSize = (Size)obj;
			if (dSize.getWidth() == this.width &&
				dSize.getHeight() == this.height) {
				return true;
			}
		} 
		return false;
	}

	@Override
	public String toString() {
		return "Size [width=" + width + ", height=" + height + "]";
	}
	
}
