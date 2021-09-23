package com.outbina.sgengine.game.map;

import java.util.Random;

import com.outbina.sgengine.game.common.TerrainModelMatcher;
import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.tile.TileInfo;
import com.outbina.sgengine.game.graphics.tile.TiledArea;
import com.outbina.sgengine.game.graphics.tile.TiledBoundary;
import com.outbina.sgengine.game.map.interfaces.IMapService;

public class MapService implements IMapService {
	
	private TileInfo tileInfos[][];
	
	public static final int NONE = -1;
	
	private int expand ;
	private TileInfo evenLayerHeightProjections[][]; // 偶数高度投影
	private TileInfo oddLayerHeightProjections[][];  // 奇数高度投影
	
	public MapService(int rows,int cols,TerrainModelMatcher modelMatcher) {
		tileInfos = new TileInfo[rows][cols];
		
		boolean[][] land = new boolean[rows][cols];
		Random random = new Random();

		for (int i = 0 ; i < rows ; i ++) {
			for (int j = 0 ; j < cols ; j ++) {
				
				// 如果已经占用，那么继续下一个
				if (land[i][j]) {
					continue;
				}
				
				int type;
				TiledBoundary boundary ;
				do {
					type = random.nextInt(15-3) + 3;
					IModel2D model = modelMatcher.getModel(type);
					TiledArea tiledArea = model.getTiledArea();
					boundary = new TiledBoundary(i, j, tiledArea);
				} while(!isBuildable(land,boundary));
				
				build(tileInfos, land, type ,boundary);
			}
		}
		
		buildHeightProjections(rows,cols);
	}
	
	private boolean isBuildable(boolean[][] land,TiledBoundary boundary) {
		
		if (boundary.getEndRow() > land.length ||
			boundary.getEndCol() > land[0].length) {
				return false;
		}
		
		for(int i = boundary.getStartRow() ; i < boundary.getEndRow() ; i ++) {
			for(int j = boundary.getStartCol() ; j < boundary.getEndCol() ; j ++) {
				if(land[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void build(TileInfo[][] tiles,boolean[][] land, int type,TiledBoundary boundary) {
		for(int i = boundary.getStartRow() ; i < boundary.getEndRow() ; i ++) {
			for(int j = boundary.getStartCol() ; j < boundary.getEndCol() ; j ++) {
				
				//TODO 
				int h = 0;
				if(i == 100 && j == 50) {
					h = 1;
				} else {
					h = 0;
				}
				
				tiles[i][j] = new TileInfo(i, j, h,type, boundary);
				land[i][j] = 
						boundary.getTiledArea().isOccupied(i - boundary.getStartRow(), j - boundary.getStartCol());
			
			}
		}
	}
	
	public TileInfo getTerrain(int row,int col) {
		if(row < 0 || col < 0
				|| row > tileInfos.length || col > tileInfos[0].length) {
			return null;
		}
		return tileInfos[row][col];
	}

	@Override
	public int getEvenLayerHeightProjection(int row, int col) {
		return getHeightProjection(true,row,col);
	}

	@Override
	public int getOddLayerHeightProjection(int row, int col) {
		return getHeightProjection(false,row,col);
	}
	
	/**********/
	
	private int getHeightProjection(boolean even,int row, int col) {
		int tRow = expand + row;
		int tCol = expand + col;
		if(row < tileInfos.length && col < tileInfos[0].length &&
		   tRow >= 0 && tCol >= 0) {
			TileInfo[][] heightProjections;
			if(even) {
				heightProjections = evenLayerHeightProjections;
			} else {
				heightProjections = oddLayerHeightProjections;
			}
			TileInfo info = heightProjections[expand + row][ expand +col];
			if(info != null) {
				return info.getHeight();
			} else {
				return NONE;
			}
		} else {
			return NONE;
		}
	}
	
	public void buildHeightProjections(int rows,int cols) {
		expand = 1;
		for (int i = 0 ; i < rows ; i ++) {
			for (int j = 0 ; j < cols ; j ++) {
				int height = tileInfos[i][j].getHeight();
				int col = tileInfos[i][j].getCol();
				int n = height/2 - col;
				if(n > expand) {
					expand = n;
				}
			}
		}
		
		evenLayerHeightProjections = new TileInfo[expand + rows][expand + cols];
		oddLayerHeightProjections = new TileInfo[expand + rows][expand + cols];
		
		for (int i = 0 ; i < rows ; i ++) {
			for (int j = 0 ; j < cols ; j ++) {
				int height = tileInfos[i][j].getHeight();
				int row = tileInfos[i][j].getRow();
				int col = tileInfos[i][j].getCol();
				
				if(height%2 == 0) {
					int n = height/2 ;
					int tRow = expand + row - n;
					int tCol = expand + col - n;
					
					TileInfo dTileInfo = evenLayerHeightProjections[tRow][tCol];
					if(dTileInfo == null || dTileInfo.getHeight() < height) {
						evenLayerHeightProjections[tRow][tCol]=tileInfos[i][j];
					}
					
				} else {
					int n = (height - 1)/2;
					int tRow = expand + row - n;
					int tCol = expand + col - n;
					
					TileInfo dTileInfo = oddLayerHeightProjections[tRow][tCol];
					if(dTileInfo == null || dTileInfo.getHeight() < height) {
						oddLayerHeightProjections[tRow][tCol]=tileInfos[i][j];
					}
				}
				
			}
		}
	}
	
}
