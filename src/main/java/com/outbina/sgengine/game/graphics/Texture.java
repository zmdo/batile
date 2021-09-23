package com.outbina.sgengine.game.graphics;

import java.util.HashMap;
import java.util.Map;

import com.outbina.sgengine.game.GameSystem;
import com.outbina.sgengine.game.Size;
import com.outbina.sgengine.game.graphics.interfaces.ITexture;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Paint;

public class Texture implements ITexture {
	
	private static final long serialVersionUID = -3241116497077934452L;
	
	private Integer          id;         // 纹理Id
	private String           name;       // 纹理名
	private Size             size;       // 纹理大小
	private Image            image;      // 图片
	private Map<Paint,Image> maskImages; // 蒙版映射
	
	public Texture(Integer id,String name,Image image) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.maskImages = new HashMap<Paint, Image>();
		this.size = new Size((int)image.getWidth(),(int)image.getHeight());
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Image getImage() {
		return image;
	}

	public Size getSize() {
		return this.size;
	}
	
	public Image getMaskImage(Paint paint) {
		
		Image returnImage = maskImages.get(paint);
		
		if(returnImage != null) {
			// 如果存在该颜色的蒙版，那么直接返回
			return returnImage;
		} else {
			Canvas canvas = new Canvas(image.getWidth(),image.getHeight());
		
			// 裁剪蒙版
			canvas.setClip(new ImageView(image));
			
			// 绘制蒙版颜色
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFill(paint);
			gc.fillRect(0, 0, image.getWidth(), image.getHeight());
			
			// 转换为图片 
			WritableImage maskImage = new WritableImage(
					image.getPixelReader(), 
					(int)image.getWidth(), 
					(int)image.getHeight());
			canvas.snapshot(GameSystem.DEFAULT_SNAPSHOT_PARAMETERS, maskImage);
			
			maskImages.put(paint, maskImage);
			return maskImage;
		}
		
	}

}
