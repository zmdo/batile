package com.outbina.sgengine.game.graphics.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.outbina.sgengine.game.graphics.interfaces.ITilePlotter;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridPlotter;
import com.outbina.sgengine.game.graphics.interfaces.ITilesGridRegion;
import com.outbina.sgengine.game.map.interfaces.IMapService;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;

/**
 * 瓷砖网格绘制器
 */
public class TilesGridPlotter implements ITilesGridPlotter {
	
	private ObjectProperty<IMapService> mapServiceProperty = new SimpleObjectProperty<IMapService>();
	private ListProperty<ITilePlotter> tilePlottersProperty = new SimpleListProperty<ITilePlotter>(FXCollections.observableArrayList());

	private Map<Integer,List<TileInfo>> heightMapBuffer;
	
	public TilesGridPlotter(IMapService mapService) {
		this.mapServiceProperty.set(mapService);
		
		heightMapBuffer = new TreeMap<Integer, List<TileInfo>>();
	}
	
	@Override
	public synchronized void draw(Canvas canvas, ITilesGridRegion region) {
		
		IMapService mapService = mapServiceProperty.get();
		
		// 清除缓冲
		clearHeightMap();
		
//		System.out.println("(" + region.getStartRow() + "," + region.getStartCol() + ")" +
//				"(" + region.getEndRow() + "," + region.getEndCol() + ")");
//		
//		int dw = region.getEndRow() - region.getStartRow();
//		int dh = region.getEndCol() - region.getStartCol();
//		System.out.println(dw*dh);
		
		// 高度记录
		for(int i = region.getStartRow() ; i < region.getEndRow() ; i++) {
			for(int j = region.getStartCol() ; j < region.getEndCol() ; j++) {
				
				int eh = mapService.getEvenLayerHeightProjection(i, j);
				int oh = mapService.getOddLayerHeightProjection(i, j);
				
				if(eh != -1) {
					int n = eh/2;
					TileInfo tileInfo = mapService.getTerrain(i + n,j + n);
					List<TileInfo> tileInfos = getTileListByHeight(eh);
					tileInfos.add(tileInfo);
				}
				
				if(oh != -1) {
					int n = (oh-1)/2;
					TileInfo tileInfo = mapService.getTerrain(i + n,j + n);
					List<TileInfo> tileInfos = getTileListByHeight(oh);
					tileInfos.add(tileInfo);
				}
				
			}
		}
		
		// 根据高度绘制块
		for(Integer h : heightMapBuffer.keySet()) {
			drawTilesByHeight(h,canvas,region);
		}
		
	}
	
	private synchronized void clearHeightMap() {
		for(Integer h : heightMapBuffer.keySet()) {
			getTileListByHeight(h).clear();
		}
	}

	private List<TileInfo> getTileListByHeight(int h) {
		List<TileInfo> tileInfos ;
		if(heightMapBuffer.containsKey(h)) {
			tileInfos = heightMapBuffer.get(h);
		} else {
			tileInfos = new ArrayList<TileInfo>();
			heightMapBuffer.put(h,tileInfos);
		}
		return tileInfos;
	}
	
	private void drawTilesByHeight(int height, Canvas canvas, ITilesGridRegion region) {
		List<TileInfo> tileInfos = getTileListByHeight(height);
		if(tileInfos.isEmpty()) return; 
		for(TileInfo tileInfo : tileInfos) {
			TilePosition position = new TilePosition(region,tileInfo.getRow(),tileInfo.getCol());
			drawTile(tileInfo, canvas, position);
		}
	}
	
	private void drawTile(TileInfo tileInfo,Canvas canvas,TilePosition position) {
		// 绘制瓷砖
		ObservableList<ITilePlotter> tilePlotters =  getTerrainPlotters();
		for (ITilePlotter plotter : tilePlotters) {
			plotter.draw(tileInfo ,canvas, position);
		}
	}
	
	public ListProperty<ITilePlotter> terrainPlottersProperty() {
		return this.tilePlottersProperty;
	}
	
	public ObservableList<ITilePlotter> getTerrainPlotters() {
		return this.terrainPlottersProperty().get();
	}
	
	public void setTerrainPlotters(final ObservableList<ITilePlotter> tilePlotters) {
		this.terrainPlottersProperty().set(tilePlotters);
	}

	public ObjectProperty<IMapService> mapServiceProperty() {
		return this.mapServiceProperty;
	}
	
	public IMapService getMapService() {
		return this.mapServiceProperty().get();
	}
	
	public void setMapService(final IMapService mapService) {
		this.mapServiceProperty().set(mapService);
	}
	
	
}
