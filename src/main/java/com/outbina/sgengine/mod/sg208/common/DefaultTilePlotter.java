package com.outbina.sgengine.mod.sg208.common;

import com.outbina.sgengine.game.common.IModelMatcher;
import com.outbina.sgengine.game.graphics.TextureBlock;
import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.interfaces.ITilePlotter;
import com.outbina.sgengine.game.graphics.tile.TileInfo;
import com.outbina.sgengine.game.graphics.tile.TilePosition;
import com.outbina.sgengine.game.graphics.tile.TiledBoundary;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

// TODO
public class DefaultTilePlotter implements ITilePlotter {
	
	private IModelMatcher modelMatcher;
	
	public DefaultTilePlotter(IModelMatcher modelMatcher) {
		this.modelMatcher = modelMatcher;
	}
	
	@Override
	public void draw(TileInfo tileInfo, Canvas canvas, TilePosition position) {
		
		TiledBoundary boundary = tileInfo.getBoundary();
		int dRow = tileInfo.getRow() - boundary.getStartRow();
		int dCol = tileInfo.getCol() - boundary.getStartCol();
		
		IModel2D model2D = modelMatcher.getModel(tileInfo);
		TextureBlock textureBlock = model2D.getTileShell().getTextureBlock(
				dRow, 
				dCol);
		
		if(boundary.canDraw(tileInfo.getRow(), tileInfo.getCol())) {
			
			textureBlock.draw(canvas.getGraphicsContext2D(), position.getX(tileInfo.getHeight()), position.getY(tileInfo.getHeight()));
			
			if(tileInfo.attributes().containsKey("touch") && (boolean) tileInfo.attributes().get("touch")) {
				textureBlock.drawMask(canvas.getGraphicsContext2D(), Color.web("88ff00a0") , position.getX(tileInfo.getHeight()), position.getY(tileInfo.getHeight()));
				// TODO
				int x = position.getX(tileInfo.getHeight());
			    int y = position.getY(tileInfo.getHeight());
				canvas.getGraphicsContext2D().fillText("("+ tileInfo.getRow() +  ", "+ tileInfo.getCol() + "," + tileInfo.getHeight() +  ")",x - 20,y);
				
			} else {
				//textureBlock.drawMask(canvas.getGraphicsContext2D(), Color.web("00000030") , position.getX(), position.getY());
			}
			
		}
		
	}

}
