package com.outbina.sgengine.game.graphics.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.outbina.sgengine.game.graphics.interfaces.ITextureLoader;
import com.outbina.sgengine.game.graphics.interfaces.ITileShell;
import com.outbina.sgengine.game.graphics.interfaces.ITileShellBuilder;
import com.outbina.sgengine.game.Rectangle;
import com.outbina.sgengine.game.Size;
import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DLoader;
import com.outbina.sgengine.game.graphics.interfaces.ITexture;
import com.outbina.sgengine.game.graphics.tile.TiledArea;

public class Model2DLoader implements IModel2DLoader<IModel2D> {

	public static final String MODEL_FILES = "model_files";
	public static final String MODEL_FILES_PATH = "path";
	
	public static final String MODELS_CONFIG = "models_config";
	public static final String MODELS_CONFIG_NAME = "name";
	public static final String MODELS_CONFIG_TILE_SIZE = "tile_size";
	
	public static final String MODELS = "models";
	public static final String MODEL_ID = "id";
	public static final String MODEL_NAME = "name";
	public static final String MODEL_OCCUPIED_ROWS = "occupied_rows";
	public static final String MODEL_OCCUPIED_COLS = "occupied_cols";
	public static final String MODEL_TERRAIN_HEIGHT = "model_height";
	public static final String MODEL_OCCUPIED_MATRIX = "occupied_matrix";
	public static final String MODEL_DRAWABLE_MATRIX = "drawable_matrix";
	public static final String TEXTURE_ID = "texture_id";
	public static final String TEXTURE_REGION = "texture_region";
	
	private Map<Integer,IModel2D> modelIdMap;
	private Map<String,IModel2D>  modelNameMap;
	private Class<?> clazz;
	
	private ITileShellBuilder tileShellBuilder;
	private ITextureLoader textureLoader;
	private Size stdTileSize;
	
	// 获取一个实例
	public static Model2DLoader getInstance(
			Class<?> clazz,
			ITextureLoader textureLoader,
			ITileShellBuilder tileShellBuilder,
			Size stdTileSize) {
		return new Model2DLoader(clazz,textureLoader,tileShellBuilder,stdTileSize);
	}
	
	// 私有化构造方法，使用 静态方法方法获取实例
	private Model2DLoader(
			Class<?> clazz,
			ITextureLoader textureLoader,
			ITileShellBuilder tileShellBuilder,
			Size stdTileSize) {
		
		this.clazz = clazz;
		this.tileShellBuilder = tileShellBuilder;
		this.textureLoader = textureLoader;
		this.stdTileSize = stdTileSize;
		
		modelIdMap = new HashMap<Integer, IModel2D>();
		modelNameMap = new HashMap<String, IModel2D>();
		
	}
	
	@SuppressWarnings("unchecked")
	public void load(String path) throws IOException {
		
		URL modelListUrl = clazz.getResource(path);
		String dir = new File(modelListUrl.getFile()).getParentFile().getPath();
		
		// 加载 yml 文件
		Yaml yml = new Yaml();
		InputStream reader = new FileInputStream(modelListUrl.getFile());
		
		Map<String,Object> modelFilesMap = yml.loadAs(reader, Map.class);
		List<Map<String,Object>> modelFilesList = (List<Map<String, Object>>) modelFilesMap.get(MODEL_FILES);
		for(Map<String,Object> modelFile : modelFilesList) {
			String modelFilePath = modelFile.get(MODEL_FILES_PATH).toString();
			readModelSetFile(dir + "/" + modelFilePath);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void readModelSetFile(String path) throws IOException {
		
		// 加载 yml 文件
		Yaml yml = new Yaml();
		InputStream reader = new FileInputStream(path);
		
		// 加载数据
		Map<String,Object> modelMap = yml.loadAs(reader, Map.class);
		
		// 加载配置
		Map<String,Object> modelsConfig = (Map<String, Object>) modelMap.get(MODELS_CONFIG);
		String tileSizeStr = modelsConfig.get(MODELS_CONFIG_TILE_SIZE).toString();
		tileSizeStr = tileSizeStr.replaceAll(" ", "");
		tileSizeStr = tileSizeStr.substring(1, tileSizeStr.length() - 1);
		String tileSizeSeg[] = tileSizeStr.split(",");
		Size tileSize = new Size(Double.valueOf(tileSizeSeg[0]), Double.valueOf(tileSizeSeg[1]));
		
		// 加载模型数据列表
		List<Map<String,Object>> modelList = (List<Map<String, Object>>) modelMap.get(MODELS);
		reader.close();
		
		// 根据数据列表生成模型
		for(Map<String,Object> modelInfo : modelList) {
			
			Integer modelId = (Integer)modelInfo.get(MODEL_ID);
			String modelName = modelInfo.get(MODEL_NAME).toString();
			
			////////////////////
			// 构建 tiledArea //
			////////////////////
			
			Integer rows = (Integer)modelInfo.get(MODEL_OCCUPIED_ROWS);
			Integer cols = (Integer)modelInfo.get(MODEL_OCCUPIED_COLS);
			Integer height = (Integer)modelInfo.get(MODEL_TERRAIN_HEIGHT);
			
			boolean[][] occupiedMatrix = null;
			if(modelInfo.containsKey(MODEL_OCCUPIED_MATRIX)) {
				String matrixStr = modelInfo.get(MODEL_OCCUPIED_MATRIX).toString();
				occupiedMatrix = readMatrix(matrixStr,rows,cols);
			}
			
			boolean[][] drawableMatrix = null;
			if(modelInfo.containsKey(MODEL_DRAWABLE_MATRIX)) {
				String matrixStr = modelInfo.get(MODEL_DRAWABLE_MATRIX).toString();
				drawableMatrix = readMatrix(matrixStr,rows,cols);
			}
			
			TiledArea tiledArea = new TiledArea(rows, cols, height, occupiedMatrix,drawableMatrix);
			
			////////////////////
			// 构建 tileSheet //
			////////////////////
			
			Integer textureId = (Integer)modelInfo.get(TEXTURE_ID);
			String textureRegion = modelInfo.get(TEXTURE_REGION).toString();
			
			String regions[] = textureRegion.replaceAll(" ", "").replace("(", "").replace(")", "").split(",");
			
			int minX = Integer.valueOf(regions[0]);
			int minY = Integer.valueOf(regions[1]);
			int dWidth = Integer.valueOf(regions[2]);
			int dHeight = Integer.valueOf(regions[3]);
			Rectangle region = new Rectangle(minX, minY, dWidth, dHeight);
			
			ITexture texture = textureLoader.findTextureById(textureId);
			ITileShell tileShell = tileShellBuilder.createTileShell(texture, region, tileSize, stdTileSize , tiledArea);
			
			////////////////////
			// 构建 model     //
			////////////////////
			
			IModel2D model2D = new Model2D(modelId, modelName, tiledArea,tileShell);
			
			modelIdMap.put(modelId, model2D);
			modelNameMap.put(modelName,model2D);
		}
	}
	
	private boolean[][] readMatrix(String matrixStr,int rows,int cols) {
		matrixStr = matrixStr.replaceAll(" ", "");
		matrixStr = matrixStr.substring(1,matrixStr.length() - 1);
		String[] tMatrix = matrixStr.split(",");
		
		boolean[][] matrix = new boolean[rows][cols];
		for (int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols; j ++) {
				if(tMatrix[j*rows+i].equals("0")) {
					matrix[i][j] = false;
				} else if(tMatrix[j*rows+i].equals("1")) {
					matrix[i][j] = true;
				}
			}
		}
		
		return matrix;
	}
	
	@Override
	public IModel2D findModelById(int id) {
		return modelIdMap.get(id);
	}
	
	@Override
	public IModel2D findModelByName(String name) {
		return modelNameMap.get(name);
	}
}
