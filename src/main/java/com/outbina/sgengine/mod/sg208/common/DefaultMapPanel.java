package com.outbina.sgengine.mod.sg208.common;

import com.outbina.sgengine.game.graphics.interfaces.ITileMouseHandler;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGrid;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridPlotter;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;
import com.outbina.sgengine.game.graphics.tile.TilesGridCanvas;
import com.outbina.sgengine.game.map.interfaces.IMapService;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DefaultMapPanel {
	
	// 绘制图层面板
	private Pane pane;
	private TilesGridCanvas canvas;
	
	// 瓷砖网格
	private ObjectProperty<ITilesGrid> tilesGridProperty = new SimpleObjectProperty<ITilesGrid>();
	private ObjectProperty<ITilesGridRegion> regionProperty = new SimpleObjectProperty<ITilesGridRegion>();
	
	public DefaultMapPanel(int width,int height,IMapService mapservice,ITilesGrid tilesGrid,ITilesGridPlotter tilesGridPlotter) {
		
		// 初始化图层管理页面
		pane = new Pane();
		
		// 画布
		canvas = new TilesGridCanvas(width, height, mapservice,tilesGrid, tilesGridPlotter);
		this.tilesGridProperty.bind(canvas.tilesGridProperty());
		this.regionProperty.bind(canvas.evenProjectionLayerRegionProperty());
		
		// 添加移动控制监听器
		canvas.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
			
    		private double startX,startY;
    		
			@Override
			public void handle(MouseEvent event) {
				
				ITilesGridRegion region = canvas.evenProjectionLayerRegionProperty().get();
				if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
					
					startX = event.getX();
					startY = event.getY();
					
				} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
					
					double tx = event.getX() - startX;
					double ty = event.getY() - startY;
					
 					int dx = (int) (region.getStartX() + tx);
 					int dy = (int) (region.getStartY() + ty);
 					
 					ITilesGridRegion evenProjectionLayerRegion = canvas.tilesGridProperty().get().relocation(
 							region.getCenterRow(), 
 							region.getCenterCol(), 
 							dx ,dy);
 					
 					ITilesGridRegion oddProjectionLayerRegion = 
 					canvas.tilesGridProperty().get().getTileGridRegion(
 							evenProjectionLayerRegion.getCenterRow(), 
 							evenProjectionLayerRegion.getCenterCol(), 
 							evenProjectionLayerRegion.getStartX(), 
 							evenProjectionLayerRegion.getStartY() - region.getTileHeight()/2);
 					
// 							canvas.tilesGridProperty().get().relocation(
// 							region.getCenterRow(), 
// 							region.getCenterCol(), 
// 							dx,dy - region.getTileHeight()/2);
 					
					startX = event.getX() ;
					startY = event.getY() ;
					
					canvas.evenProjectionLayerRegionProperty().set(evenProjectionLayerRegion);
					canvas.oddProjectionLayerRegionProperty().set(oddProjectionLayerRegion);
				}
			}
    		
    	});
		
		pane.getChildren().add(canvas);
	}
	
	public void start() {
		// 画布开始刷新运行
		canvas.start();
	}
	
	public void start(int centerRow,int centerCol,int startX,int startY) {
		// 初始定位
		canvas.locate(centerRow, centerCol, startX, startY);
		// 画布开始刷新运行
		canvas.start();
	}
	
	public Pane getPane() {
		return pane;
	}

	public void addTileMouseHandler(ITileMouseHandler handler) {
		canvas.addTileMouseHandler(handler);
	}

	public void removeTileMouseHandler(ITileMouseHandler handler) {
		canvas.removeTileMouseHandler(handler);
	}
}
