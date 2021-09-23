package com.outbina.sgengine.game.graphics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.outbina.sgengine.game.graphics.interfaces.ITexture;
import com.outbina.sgengine.game.graphics.interfaces.ITextureLoader;

import javafx.scene.image.Image;

public class TextureLoader implements ITextureLoader{
	
	public static final String TEXTURES = "textures";
	public static final String TEXTURE_ID = "id";
	public static final String TEXTURE_NAME = "name";
	public static final String TEXTURE_PATH = "path";
	
	private Map<Integer,ITexture> textureIdMap;
	private Map<String,ITexture>  textureNameMap;
	private Class<?> clazz;
	
	// 获取一个实例
	public static TextureLoader getInstance(Class<?> clazz) {
		return new TextureLoader(clazz);
	}
	
	// 私有化构造方法，使用 静态方法方法获取实例
	private TextureLoader(Class<?> clazz) {
		this.clazz = clazz;
		textureIdMap = new HashMap<Integer, ITexture>();
		textureNameMap = new HashMap<String, ITexture>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void load(String path) throws IOException {
		
		URL textureConfUrl = clazz.getResource(path);
		
		// 加载 yml 文件
		Yaml yml = new Yaml();
		InputStream reader = new FileInputStream(textureConfUrl.getFile());
		
		// 加载模型数据列表
		Map<String,Object> modelMap = yml.loadAs(reader, Map.class);
		List<Map<String,Object>> modelList = (List<Map<String, Object>>) modelMap.get(TEXTURES);
		reader.close();
		
		// 根据数据列表生成模型
		String dir = new File(textureConfUrl.getFile()).getParentFile().getPath();
		for(Map<String,Object> modelInfo : modelList) {
			
			Integer textureId = (Integer)modelInfo.get(TEXTURE_ID);
			String textureName = modelInfo.get(TEXTURE_NAME).toString();
			String texturePath = modelInfo.get(TEXTURE_PATH).toString();

			Image image = loadImage(dir,texturePath);
			ITexture texture = new Texture(textureId,textureName,image);
			
			textureIdMap.put(textureId, texture);
			textureNameMap.put(textureName,texture);
			
		}
	}

	// 加载图片
	private Image loadImage(String dir , String name) {
		File imageFile = new File(dir+"/" + name.toString());
		Image image = null;
		try {
			image = new Image(new FileInputStream(imageFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	@Override
	public ITexture findTextureById(int id) {
		return textureIdMap.get(id);
	}

	@Override
	public ITexture findTextureByName(String name) {
		return textureNameMap.get(name);
	}

}
