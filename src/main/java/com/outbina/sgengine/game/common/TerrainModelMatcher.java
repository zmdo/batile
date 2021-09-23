package com.outbina.sgengine.game.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DLoader;
import com.outbina.sgengine.game.graphics.tile.TileInfo;

public class TerrainModelMatcher implements IModelMatcher {
	
	public static final String TYPE_MODEL = "type_model";
	public static final String TYPE_ID = "type_id";
	public static final String MODEL_ID = "model_id";
	
	private java.util.Map<Integer,Integer> typeModelMap;
	
	private Class<?> clazz;
	private IModel2DLoader<IModel2D> model2DLoader;
	
	// 获取一个实例
	public static TerrainModelMatcher getInstance(Class<?> clazz,IModel2DLoader<IModel2D> modelLoader) {
		return new TerrainModelMatcher(clazz,modelLoader);
	}
	
	// 私有化构造方法，使用 静态方法方法获取实例
	private TerrainModelMatcher(Class<?> clazz,IModel2DLoader<IModel2D> modelLoader) {
		this.clazz = clazz;
		this.model2DLoader = modelLoader;
	}
	
	@SuppressWarnings("unchecked")
	public void load(String path) throws IOException {
		URL modelConfUrl = clazz.getResource(path);
		
		typeModelMap = new HashMap<Integer, Integer>();
		
		// 加载 yml 文件
		Yaml yml = new Yaml();
		InputStream reader = new FileInputStream(modelConfUrl.getFile());
		
		// 加载模型数据列表
		Map<String,Object> map = yml.loadAs(reader, Map.class);
		List<Map<String,Object>> typeModelList = (List<Map<String, Object>>) map.get(TYPE_MODEL);
		reader.close();
		
		for(Map<String,Object> typeModelInfo : typeModelList) {
			Integer typeId = (Integer)typeModelInfo.get(TYPE_ID);
			Integer modelId = (Integer)typeModelInfo.get(MODEL_ID);
			typeModelMap.put(typeId, modelId);
		}
	}
	
	public int getModelId(int typeId) {
		return typeModelMap.get(typeId);
	}

	public IModel2D getModel(int typeId) {
		return model2DLoader.findModelById(getModelId(typeId));
	}
	
	@Override
	public IModel2D getModel(TileInfo tileInfo) {
		return model2DLoader.findModelById(getModelId(tileInfo.getType()));
	}

}
