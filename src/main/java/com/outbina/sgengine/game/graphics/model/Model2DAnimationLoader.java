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

import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DAnimation;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DAnimationLoader;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DLoader;

public class Model2DAnimationLoader implements IModel2DAnimationLoader{

	public static final String ANIMATION_FILES = "animation_files";
	public static final String ANIMATION_FILES_PATH = "path";
	
	public static final String ANIMATION_CONFIG = "animation_config";
	public static final String ANIMATION_CONFIG_NAME = "animation_name";
	public static final String ANIMATION_CONFIG_LOOP = "loop";
	public static final String ANIMATION_CONFIG_DEFAULT_DURATION = "default_duration";
	
	public static final String ANIMATION_FRAMES = "frames";
	public static final String ANIMATION_FRAMES_MODEL_ID = "model_id";
	public static final String ANIMATION_FRAMES_DURATION = "duration";
	
	private IModel2DLoader<IModel2D> modelLoader;
	private Map<String,IModel2DAnimation> animations;
	private Class<?> clazz;
	
	public static Model2DAnimationLoader getInstance(Class<?> clazz,IModel2DLoader<IModel2D> modelLoader) {
		return new Model2DAnimationLoader(clazz, modelLoader);
	}
	
	private Model2DAnimationLoader(Class<?> clazz,IModel2DLoader<IModel2D> modelLoader) {
		this.clazz = clazz;
		this.modelLoader = modelLoader;
		animations = new HashMap<String, IModel2DAnimation>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void load(String path) throws IOException {
		URL modelListUrl = clazz.getResource(path);
		String dir = new File(modelListUrl.getFile()).getParentFile().getPath();
		
		// 加载 yml 文件
		Yaml yml = new Yaml();
		InputStream reader = new FileInputStream(modelListUrl.getFile());
		
		Map<String,Object> animationFilesMap = yml.loadAs(reader, Map.class);
		List<Map<String,Object>> animationFilesList = (List<Map<String, Object>>) animationFilesMap.get(ANIMATION_FILES);
		for(Map<String,Object> animationFile : animationFilesList) {
			String modelFilePath = animationFile.get(ANIMATION_FILES_PATH).toString();
			readModelAnimationFile(dir + "/" + modelFilePath);
		}
	}

	@SuppressWarnings("unchecked")
	private void readModelAnimationFile(String path) throws IOException {
		// 加载 yml 文件
		Yaml yml = new Yaml();
		InputStream reader = new FileInputStream(path);
		
		// 加载数据
		Map<String,Object> animationMap = yml.loadAs(reader, Map.class);
		
		// 加载配置
		Map<String,Object> animationConfig = (Map<String, Object>) animationMap.get(ANIMATION_CONFIG);
		
		String animationName = (String) animationConfig.get(ANIMATION_CONFIG_NAME);
		boolean loop = (boolean) animationConfig.get(ANIMATION_CONFIG_LOOP);
		int defaultDuration = 0;
		if(animationConfig.containsKey(ANIMATION_CONFIG_DEFAULT_DURATION)) {
			defaultDuration = (int) animationConfig.get(ANIMATION_CONFIG_DEFAULT_DURATION);
		}
		
		// 加载帧
		List<Map<String,Object>> frames =  (List<Map<String, Object>>) animationMap.get(ANIMATION_FRAMES);
		int size = frames.size();
		IModel2D[] models = new IModel2D[size];
		int[] durations = new int[size];
		for(int i = 0; i < size; i++ ) {
			Map<String,Object> frame = frames.get(i);
			int modelId= (int) frame.get(ANIMATION_FRAMES_MODEL_ID);
			models[i] = modelLoader.findModelById(modelId);
			if(frame.containsKey(ANIMATION_FRAMES_DURATION)) {
				durations[i] = (int) frame.get(ANIMATION_FRAMES_DURATION);
			} else {
				durations[i] = defaultDuration;
			}
		}
		
		Model2DAnimation animation = new Model2DAnimation(animationName, models, durations,loop);
		animations.put(animationName, animation);
	}

	@Override
	public IModel2DAnimation createAnimationByName(String name) {
		if(animations.containsKey(name)) {
			return animations.get(name).copy();
		}
		return null;
	}
	
}
