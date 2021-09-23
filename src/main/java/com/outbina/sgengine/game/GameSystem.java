package com.outbina.sgengine.game;

import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

public class GameSystem {

	/**
	 * 启动时间
	 */
	public static final long startTime = System.currentTimeMillis();
	
	public static final SnapshotParameters DEFAULT_SNAPSHOT_PARAMETERS;
	static {
		DEFAULT_SNAPSHOT_PARAMETERS = new SnapshotParameters();
		DEFAULT_SNAPSHOT_PARAMETERS.setFill(Color.web("#00000000"));
		DEFAULT_SNAPSHOT_PARAMETERS.setTransform(Transform.scale(1, 1));
	}
}
