package com.outbina.sgengine.game.graphics.tile;

import java.util.List;

import com.outbina.sgengine.game.graphics.interfaces.ITileMouseHandler;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;
import com.outbina.sgengine.game.map.interfaces.IMapService;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * 瓷砖画布鼠标监听处理器
 */
public class TilesGridCanvasMouseHandler implements EventHandler<MouseEvent>{
	
	private ObjectProperty<ITilesGridRegion> evenProjectionLayerRegionProperty = new SimpleObjectProperty<ITilesGridRegion>();
	private ObjectProperty<ITilesGridRegion> oddProjectionLayerRegionProperty = new SimpleObjectProperty<ITilesGridRegion>();
	
	private IMapService mapService;
	
	private int recordRow,recordCol;
	private int pressedRow,pressedCol;
	
	private ListProperty<ITileMouseHandler> tileMouseHandlersProperty = 
			new SimpleListProperty<ITileMouseHandler>(FXCollections.observableArrayList());;
	
	public TilesGridCanvasMouseHandler (IMapService mapService) {
		this.mapService = mapService;
	}
	
	@Override
	public void handle(MouseEvent event) {
		
		// 获取区域
		ITilesGridRegion evenRegion = evenProjectionLayerRegionProperty.get();
		ITilesGridRegion oddRegion = oddProjectionLayerRegionProperty.get();
		
		// 获取瓷砖监听器
		List<ITileMouseHandler> tileMouseHandlers = tileMouseHandlersProperty.get();
		
		// 获取当前鼠标指示的行和列
		int evenPos[] = evenRegion.getTilePosition((int)event.getX(), (int)event.getY());
		int oddPos[] = oddRegion.getTilePosition((int)event.getX(), (int)event.getY());
		
		int eh = mapService.getEvenLayerHeightProjection(evenPos[0],evenPos[1]);
		int oh = mapService.getOddLayerHeightProjection(oddPos[0],oddPos[1]);

//		System.out.println(oddPos[0] + " " + oddPos[1]);
//		System.out.println(eh + " " + oh);
		
		int currentRow = 0;
		int currentCol = 0;
		
		if (eh == -1 && oh == -1) {
			return;
		}
		
		if(eh >= oh) {
			currentRow = evenPos[0] + eh/2;
			currentCol = evenPos[1] + eh/2;
		} else {
			currentRow = oddPos[0] + (oh-1)/2;
			currentCol = oddPos[1] + (oh-1)/2;
		}
		
		// 判断记录的行列与现在的行列是否相同
		TileMouseEvent exitedEvent = null;
		TileMouseEvent enteredEvent = null;

		// 所有事件类型的处理
		TileMouseEvent tileMouseEvent = null;
		if(event.getEventType().equals(MouseEvent.MOUSE_MOVED)) {
			if( !(recordRow == currentRow && recordCol == currentCol) ) {
				// 产生两个事件,一个事件是离开旧瓷砖，一个进入新瓷砖
				exitedEvent = 
						new TileMouseEvent(recordRow, recordCol, evenRegion, event, TileMouseEventType.MOUSE_EXITED);
				enteredEvent =
						new TileMouseEvent(currentRow, currentCol, evenRegion, event, TileMouseEventType.MOUSE_ENTERED);
			}
			tileMouseEvent = new TileMouseEvent(currentRow, currentCol, evenRegion, event, TileMouseEventType.MOUSE_MOVED);
		} else if(event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
			tileMouseEvent = new TileMouseEvent(currentRow, currentCol, evenRegion, event, TileMouseEventType.MOUSE_PRESSED);
			this.pressedRow = currentRow;
			this.pressedCol = currentCol;
		} else if(event.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
			tileMouseEvent = new TileMouseEvent(pressedRow, pressedCol, evenRegion, event, TileMouseEventType.MOUSE_DRAGGED);
		} else if(event.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {	
			tileMouseEvent = new TileMouseEvent(pressedRow, pressedCol, evenRegion, event, TileMouseEventType.MOUSE_RELEASED);
		} else if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
			tileMouseEvent = new TileMouseEvent(currentRow, currentCol, evenRegion, event, TileMouseEventType.MOUSE_CLICKED);
		} else if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
			
		} else if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED_TARGET)) {
			
		} else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
			
		} else if(event.getEventType().equals(MouseEvent.MOUSE_EXITED_TARGET)) {
			
		} 
		
		// 记录刷新
		this.recordRow = currentRow;
		this.recordCol = currentCol;
		
		// 事件处理
		for (ITileMouseHandler tileMouseHandler : tileMouseHandlers) {
			if (exitedEvent != null) {
				tileMouseHandler.handle(exitedEvent);
			}
			if (enteredEvent != null) {
				tileMouseHandler.handle(enteredEvent);
			}
			if (tileMouseEvent != null) {
				tileMouseHandler.handle(tileMouseEvent);
			}
		}
		

	}

	/**
	 * 获取瓷砖区域配置器
	 * @return 瓷砖区域配置器
	 */
	public ObjectProperty<ITilesGridRegion> evenProjectionLayerRegionProperty() {
		return this.evenProjectionLayerRegionProperty;
	}
	
	public ObjectProperty<ITilesGridRegion> oddProjectionLayerRegionProperty() {
		return this.oddProjectionLayerRegionProperty;
	}
	
	public final ListProperty<ITileMouseHandler> tileMouseHandlersProperty() {
		return this.tileMouseHandlersProperty;
	}
	
	public final ObservableList<ITileMouseHandler> getTileMouseHandlers() {
		return this.tileMouseHandlersProperty().get();
	}
	
	public final void setTileMouseHandlersProperty(final ObservableList<ITileMouseHandler> tileMouseHandlers) {
		this.tileMouseHandlersProperty().set(tileMouseHandlers);
	}
	
	
}
