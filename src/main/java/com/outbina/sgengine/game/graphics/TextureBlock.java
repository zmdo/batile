package com.outbina.sgengine.game.graphics;

import java.io.Serializable;

import com.outbina.sgengine.game.GameSystem;
import com.outbina.sgengine.game.Rectangle;
import com.outbina.sgengine.game.graphics.interfaces.IDrawable;
import com.outbina.sgengine.game.graphics.interfaces.ITexture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Paint;

public class TextureBlock implements IDrawable,Serializable {
	
	private static final long serialVersionUID = -1527244427001900352L;

	private WritableImage image;
	
	private ITexture texture;
	private Rectangle region;
	private double scaleX,scaleY;
	private double pivotX,pivotY;
	
	public TextureBlock(
			ITexture texture, Rectangle region,
			double scaleX,double scaleY,
			double pivotX,double pivotY) {
		this.texture = texture;
		this.region = region;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.pivotX = pivotX;
		this.pivotY = pivotY;
	}

	public TextureBlock(ITexture texture, Rectangle region) {
		this(texture,region,1d,1d,0d,0d);
	}
	
	/**
	 * 绘制该图块
	 * @param gc 画笔
	 * @param x 水平方向上的位置坐标
	 * @param y 垂直方向上的位置坐标
	 */
	public void draw(GraphicsContext gc,double x,double y) {
		drawImage(texture.getImage(),gc, x, y);
	}
	
	/**
	 * 绘制该图块蒙版图
	 * @param gc 画笔
	 * @param x 水平方向上的位置坐标
	 * @param y 垂直方向上的位置坐标
	 */
	public void drawMask(GraphicsContext gc,Paint paint,double x,double y) {
		drawImage(texture.getMaskImage(paint),gc,x,y);
	}
	
	private void drawImage(Image image,GraphicsContext gc,double x,double y) {
		gc.save();
		
		gc.translate(x - pivotX, y - pivotY);
		gc.scale(getScaleX(), getScaleY());
		
		gc.drawImage(image,
				region.getMinX(),region.getMinY(),
				region.getWidth(),region.getHeight(),
				0,0,
				region.getWidth(),region.getHeight());
		
		gc.restore();
	}
	
	/**
	 * 返回一张图
	 * @return
	 */
	public Image asImage() {
		if(image == null) {
			int width = (int)(region.getWidth() * scaleX);
			int height = (int)(region.getHeight() * scaleY);
			image = new WritableImage(width,height);
			Canvas canvas = new Canvas();
			draw(canvas.getGraphicsContext2D(),pivotX,pivotY);
			canvas.snapshot(GameSystem.DEFAULT_SNAPSHOT_PARAMETERS, image);
		}
		return image;
	}
	
	public ITexture getTexture() {
		return texture;
	}

	public Rectangle getRegion() {
		return region;
	}

	public double getWidth() {
		return region.getWidth();
	}
	
	public double getHeight() {
		return region.getHeight();
	}
	
	public double getScaleX() {
		return scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public double getPivotX() {
		return pivotX;
	}

	public double getPivotY() {
		return pivotY;
	}

	public void setPivotX(double pivotX) {
		this.pivotX = pivotX;
	}

	public void setPivotY(double pivotY) {
		this.pivotY = pivotY;
	}
	
	public TextureBlock copy() {
		return new TextureBlock(texture, region,scaleX,scaleY,pivotX,pivotY);
	}
	
}
