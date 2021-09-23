package com.outbina.sgengine.game.graphics.tile;

import com.outbina.sgengine.game.graphics.interfaces.ITileMouseHandler;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGrid;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridPlotter;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;
import com.outbina.sgengine.game.map.interfaces.IMapService;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class TilesGridCanvas extends Canvas {

	// 瓷砖网格
	private ObjectProperty<ITilesGrid> tilesGridProperty = new SimpleObjectProperty<ITilesGrid>();
	
	private ObjectProperty<ITilesGridRegion> evenProjectionLayerRegionProperty = new SimpleObjectProperty<ITilesGridRegion>();
	private ObjectProperty<ITilesGridRegion> oddProjectionLayerRegionProperty = new SimpleObjectProperty<ITilesGridRegion>();
	
	private IMapService mapService;
	
	// 瓷砖绘制器
	private ObjectProperty<ITilesGridPlotter> tilesGridPlotterProperty = new SimpleObjectProperty<ITilesGridPlotter>();
	
	// 画布监听器
	private TilesGridCanvasMouseHandler tilesGridCanvasMouseHandler;
	
	// 界面刷新控制相关
	private ScheduledService<Integer> scheduledService;
	private ObjectProperty<Duration> periodProperty = 
			new SimpleObjectProperty<Duration>(Duration.millis(16));
	
	
	public TilesGridCanvas(int width, int height,IMapService mapService) {
		super(width,height);
		this.mapService = mapService;
		init();
	}
	
	public TilesGridCanvas(
			int width,int height,
			IMapService mapService,
			ITilesGrid tilesGrid,
			ITilesGridPlotter tilesGridPlotter) {
		
		this(width,height,mapService);
		tilesGridProperty().set(tilesGrid);
		tilesGridPlotterProperty().set(tilesGridPlotter);
		
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		
		this.tilesGridCanvasMouseHandler = new TilesGridCanvasMouseHandler(mapService);
		this.tilesGridCanvasMouseHandler.evenProjectionLayerRegionProperty().bind(evenProjectionLayerRegionProperty);
		this.tilesGridCanvasMouseHandler.oddProjectionLayerRegionProperty().bind(oddProjectionLayerRegionProperty);
		
		this.addEventHandler(MouseEvent.ANY, tilesGridCanvasMouseHandler);
		
		this.scheduledService = new ScheduledService<Integer>() {
//			int i;
//			long startTime = System.currentTimeMillis();
			@Override
			protected Task<Integer> createTask() {
				Task<Integer> task = new Task<Integer>() {

					@Override
					protected Integer call() throws Exception {
						return 0;
					}
					
					@Override
					protected void scheduled() {
						repaint();
						
//						long currentTime = System.currentTimeMillis();
//						if (currentTime - startTime > 1000) {
//							System.out.println("fps:" + i);
//							i = 0;
//							startTime = currentTime;
//						} else {
//							i ++;
//						}
					}
					
				};
				return task;
			}
		};
		
		// 限制刷新率
		this.scheduledService.periodProperty().bind(this.periodProperty);
	}
	
	public void start() {
		if(evenProjectionLayerRegionProperty().get() == null) {
			locate(0, 0, 0, 0);
		}
		scheduledService.start();
	}
	
	public void restart() {
		scheduledService.restart();
	}
	
	public void stop() {
		scheduledService.cancel();
	}
	
	
	/**
	 * 定位
	 * @param centerRow 处于中心位置的行
	 * @param centerCol 处于中心位置的列
	 * @param startX 偏移量x
	 * @param startY 偏移量y
	 */
	public void locate(int centerRow,int centerCol,int startX,int startY) {
		
		TileInfo tileInfo = mapService.getTerrain(centerRow, centerCol);
		int n = tileInfo.getHeight()/2;

		ITilesGrid grid = tilesGridProperty.get();
		ITilesGridRegion evenProjectionLayerRegion = grid.getTileGridRegion(
				centerRow - n, centerCol - n, startX, startY);
		this.evenProjectionLayerRegionProperty.set(evenProjectionLayerRegion);
		
		ITilesGridRegion oddProjectionLayerRegion = grid.getTileGridRegion(
				centerRow - n, centerCol - n, startX, startY - grid.getTileHeight()/2);
		this.oddProjectionLayerRegionProperty.set(oddProjectionLayerRegion);
	}
	
	/**
	 * 绘制网络表格
	 */
	public void repaint() {
		
		// 获取范围与绘制器
		ITilesGridRegion region = evenProjectionLayerRegionProperty.get();
		ITilesGridPlotter tilesGridPlotter = tilesGridPlotterProperty.get();
		
		// 获取画布宽高
		int width = (int)this.getWidth();
		int height = (int)this.getHeight();
		
		// 清除旧图
		GraphicsContext g2d = this.getGraphicsContext2D();
		g2d.clearRect(0, 0, width, height);
		
		// 绘制地图
		if(tilesGridPlotter != null) {
			g2d.save();
			tilesGridPlotter.draw(this, region);
			g2d.restore();	
		}
		
	}
	
	// 添加处理器
	public void addTileMouseHandler(ITileMouseHandler handler) {
		getTileMouseHandlers().add(handler);
	}
	
	public void addTileMouseHandler(int index,ITileMouseHandler handler) {
		getTileMouseHandlers().add(index,handler);
	}
	
	public void removeTileMouseHandler(ITileMouseHandler handler) {
		getTileMouseHandlers().remove(handler);
	}
	
	public void removeTileMouseHandler(int index) {
		getTileMouseHandlers().remove(index);
	}
	
	/**
	 * 获取鼠标监听处理器列表
	 * @return 鼠标监听处理器列表
	 */
	public ObservableList<ITileMouseHandler> getTileMouseHandlers() {
		return this.tilesGridCanvasMouseHandler.getTileMouseHandlers();
	}
	
	/**
	 * 获取鼠标监听处理器列表配置器
	 * @return 鼠标监听处理器列表配置器
	 */
	public final ListProperty<ITileMouseHandler> tileMouseHandlersProperty() {
		return this.tilesGridCanvasMouseHandler.tileMouseHandlersProperty();
	}

	/**
	 * 获取瓷砖网格配置器
	 * @return 瓷砖网格配置器
	 */
	public ObjectProperty<ITilesGrid> tilesGridProperty() {
		return this.tilesGridProperty;
	}
	

	/**
	 * 获取瓷砖网格
	 * @return 瓷砖网格
	 */
	public ITilesGrid getTilesGrid() {
		return this.tilesGridProperty().get();
	}
	
	/**
	 * 设置瓷砖网格
	 * @param tilesGrid 瓷砖网格
	 */
	public void setTilesGrid(final ITilesGrid tilesGrid) {
		this.tilesGridProperty().set(tilesGrid);
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
	
	/**
	 * 获取瓷砖绘制器配置器
	 * @return 瓷砖绘制器配置器
	 */
	public ObjectProperty<ITilesGridPlotter> tilesGridPlotterProperty() {
		return this.tilesGridPlotterProperty;
	}

	/**
	 * 获取瓷砖网格绘制器
	 * @return 瓷砖网格绘制器
	 */
	public ITilesGridPlotter getTilePlotter() {
		return this.tilesGridPlotterProperty().get();
	}
	
	/**
	 * 设置瓷砖网格绘制器
	 * @param tilesGridPlotter 瓷砖网格绘制器
	 */
	public void setTilePlotter(final ITilesGridPlotter tilesGridPlotter) {
		this.tilesGridPlotterProperty().set(tilesGridPlotter);
	}

	public ObjectProperty<Duration> periodProperty() {
		return this.periodProperty;
	}
	
	public Duration getPeriod() {
		return this.periodProperty().get();
	}
	
	public void setPeriod(final Duration period) {
		this.periodProperty().set(period);
	}
	
	
}
