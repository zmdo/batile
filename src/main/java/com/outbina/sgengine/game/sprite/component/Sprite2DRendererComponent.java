package com.outbina.sgengine.game.sprite.component;

import com.outbina.sgengine.game.graphics.TextureBlock;
import com.outbina.sgengine.game.sprite.SpriteComponentBase;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.canvas.Canvas;

/**
 * 2D 画面绘制组件
 * 用于将精灵绘制到画布上的组件
 */
public class Sprite2DRendererComponent extends SpriteComponentBase {

	public static final String COMPONENT_NAME = "sprite_renderer";
	public static final String[] DEPEND = {
			TransformComponent.COMPONENT_NAME
	}; 
	
	public static final String CANVAS = "canvas";
	public static final String TEXTURE_BLOCK = "texture_block";
	public static final String ORDER = "order";
	
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
		newProperty(CANVAS, Canvas.class, null);
		newProperty(TEXTURE_BLOCK, TextureBlock.class, null);
		newProperty(ORDER, Integer.class, 0);
	}

	@SuppressWarnings("unchecked")
	public ObjectProperty<Canvas> canvasProperty() {
		return (ObjectProperty<Canvas>) attributes().get(CANVAS); 
	}
	
	@SuppressWarnings("unchecked")
	public ObjectProperty<TextureBlock> textureBlockProperty() {
		return (ObjectProperty<TextureBlock>) attributes().get(TEXTURE_BLOCK); 
	}
	
	public IntegerProperty orderProperty() {
		return (IntegerProperty) attributes().get(ORDER); 
	}
	
	private TransformComponent transform;
	
	@Override
	public void start() {
		transform = getComponent(TransformComponent.class).get(0);
	}
	
	@Override
	public void update(int interval) {
		if(textureBlockProperty().get() != null) {
			Canvas canvas = canvasProperty().get();
	
			double x = transform.xProperty().get();
			double y = transform.yProperty().get();
			
			TextureBlock block = textureBlockProperty().get();
			block.draw(canvas.getGraphicsContext2D(), x, y);
		}
	}
}
