package com.outbina.sgengine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;

import com.outbina.sgengine.game.Size;
import com.outbina.sgengine.game.common.IModelMatcher;
import com.outbina.sgengine.game.common.TerrainModelMatcher;
import com.outbina.sgengine.game.graphics.interfaces.ITextureLoader;
import com.outbina.sgengine.game.graphics.interfaces.ITileMouseHandler;
import com.outbina.sgengine.game.graphics.TextureLoader;
import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DAnimationLoader;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DLoader;
import com.outbina.sgengine.game.graphics.interfaces.ITilePlotter;
import com.outbina.sgengine.game.graphics.interfaces.ITileShellBuilder;
import com.outbina.sgengine.game.graphics.model.Model2D;
import com.outbina.sgengine.game.graphics.model.Model2DAnimationLoader;
import com.outbina.sgengine.game.graphics.model.Model2DLoader;
import com.outbina.sgengine.game.graphics.tile.TileInfo;
import com.outbina.sgengine.game.graphics.tile.TileMouseEvent;
import com.outbina.sgengine.game.graphics.tile.TileMouseEventType;
import com.outbina.sgengine.game.graphics.tile.TileShellBuilder;
import com.outbina.sgengine.game.graphics.tile.TiledBoundary;
import com.outbina.sgengine.game.graphics.tile.TilesGrid;
import com.outbina.sgengine.game.graphics.tile.TilesGridPlotter;
import com.outbina.sgengine.game.map.MapService;
import com.outbina.sgengine.mod.sg208.common.DefaultMapPanel;
import com.outbina.sgengine.mod.sg208.common.DefaultTilePlotter;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

    	int rows = 200;
    	int cols = 200;
		int xPadding = 0;//500;
		int yPadding = 0;//200;
    	
		Size gridSize = new Size(1200, 750);
		Size tileSize = new Size(107, 54);
		// Size tileSize = new Size(60, 30);
		/****************************************/
		
		// TODO 1 加载本地图片/纹理
		ITextureLoader textureLoader = TextureLoader.getInstance(App.class);
		textureLoader.load("/mod/sg/textures.yml");
		
		/****************************************/
		
		// TODO 2 构建图集工具
		ITileShellBuilder shellBuilder = new TileShellBuilder();
		
		/****************************************/
		
		// TODO 3 加载模型
    	IModel2DLoader<IModel2D> modelLoader = 
    			Model2DLoader.getInstance(App.class,textureLoader,shellBuilder,tileSize);
    	modelLoader.load("/mod/sg/model_files.yml");
    	
		/****************************************/
    	
		// TODO 4 加载模型动画
    	IModel2DAnimationLoader animationLoader = 
    			Model2DAnimationLoader.getInstance(App.class, modelLoader);
    	animationLoader.load("/mod/sg/animation_files.yml");
    	
		/****************************************/
    	
    	// TODO 5 构建网格
    	TilesGrid tilesGrid = new TilesGrid(gridSize, tileSize, rows, cols);
		tilesGrid.setXPadding(xPadding);
		tilesGrid.setYPadding(yPadding);
    	
		/****************************************/
		
    	// TODO 6构建模型匹配器
    	TerrainModelMatcher terrainModelMatcher = 
    			TerrainModelMatcher.getInstance(App.class,modelLoader);
    	terrainModelMatcher.load("/mod/sg/map/cell_model.yml");
		
    	/****************************************/

    	// TODO 7 构建单个节点绘制器
    	ITilePlotter defaultTilePlotter = new DefaultTilePlotter(terrainModelMatcher);
    	
    	/****************************************/
		
		// TODO 8启动地图服务
		MapService mapService = new MapService(rows,cols,terrainModelMatcher);
		
		/****************************************/
		
    	// TODO 9构建网格绘制器
    	TilesGridPlotter tilesGridPlotter = new TilesGridPlotter(mapService);
    	tilesGridPlotter.getTerrainPlotters().add(defaultTilePlotter);
    	
		/****************************************/
		
		// TODO 10 地图面板创建
		DefaultMapPanel mapPanel = new DefaultMapPanel(
				(int)gridSize.getWidth(), (int)gridSize.getHeight(),mapService, tilesGrid, tilesGridPlotter);
		
		// 添加网格监听器
		mapPanel.addTileMouseHandler(new ITileMouseHandler() {
			
			private TileInfo selectedTerrain;
			
			@Override
			public void handle(TileMouseEvent event) {
				if(event.getEventType() == TileMouseEventType.MOUSE_ENTERED ) {
					
					TileInfo tileInfo = mapService.getTerrain(event.getTileRow(), event.getTileCol());

					// 取消旧的
					if(selectedTerrain != null) {
						TiledBoundary boundary = selectedTerrain.getBoundary();
						
						for(int i = boundary.getStartRow() ; i < boundary.getEndRow() ; i++) {
							for(int j = boundary.getStartCol() ; j < boundary.getEndCol() ; j++) {
								if(boundary.isOccupied(i,j)) {
									TileInfo innerTile = mapService.getTerrain(i,j);
									innerTile.attributes().put("touch",false);
								}								
							}
						}
					}
					
					// 点亮新的
					if(tileInfo != null) {
						TiledBoundary boundary = tileInfo.getBoundary();

						for(int i = boundary.getStartRow() ; i < boundary.getEndRow() ; i++) {
							for(int j = boundary.getStartCol() ; j < boundary.getEndCol() ; j++) {
								if(boundary.isOccupied(i,j)) {
									TileInfo innerTile = mapService.getTerrain(i,j);
									innerTile.attributes().put("touch",true);
								}
							}
						}
					}
					
					selectedTerrain = tileInfo;
					
				} else if(event.getEventType() == TileMouseEventType.MOUSE_CLICKED) {
					
				}
			}
		});
		
		// 面板开始
		mapPanel.start(100, 50, 0, 0);
		// System.out.println(Paint.valueOf("radial-gradient(red, green, blue)"));
		/****************************************/
		
    	// TODO End
    	Pane pane = new Pane();
    	
		Camera camera = new PerspectiveCamera(); 
		//Camera camera = new ParallelCamera();  
        camera.getTransforms().addAll (
        		// new Rotate(-10, Rotate.Y_AXIS),
                // new Rotate(10, Rotate.X_AXIS),  
                new Translate(0, 0, 0));
		
		
        mapPanel.getPane().setTranslateY(0);
        
		pane.getChildren().add(mapPanel.getPane());
		
		SubScene subScene = new SubScene(pane,gridSize.getWidth(),gridSize.getHeight(),true,SceneAntialiasing.BALANCED);
		subScene.setFill(Color.ALICEBLUE);  
		subScene.setCamera(camera);  
		
		Group group = new Group();  
        group.getChildren().add(subScene);
        
    	scene = new Scene(group,-1,-1,true,SceneAntialiasing.BALANCED);
    	
    	stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    	
    }

}