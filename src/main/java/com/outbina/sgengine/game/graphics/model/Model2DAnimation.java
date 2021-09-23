package com.outbina.sgengine.game.graphics.model;

import com.outbina.sgengine.game.graphics.interfaces.IModel2D;
import com.outbina.sgengine.game.graphics.interfaces.IModel2DAnimation;

public class Model2DAnimation implements IModel2DAnimation{

	private String name;
	
	private int[] durations;
	private IModel2D[] models;
	private int currentFrameIndex;
	private int dt;
	private boolean loop;
	private boolean paused;
	
	public Model2DAnimation(String name,IModel2D[] models,int[] durations,boolean loop) {
		this.name = name;
		this.models = models;
		this.durations = durations;
		this.loop = loop;
		this.paused = true;
	}
	
	public Model2DAnimation(String name,IModel2D[] models,int[] durations) {
		this(name,models,durations,false);
	}
	
	@Override
	public void start() {
		paused = false;
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void stop() {
		currentFrameIndex = 0;
		dt = 0;
		paused = true;
	}

	@Override
	public void restart() {
		stop();
		start();
	}

	@Override
	public synchronized void tick(int interval) {
		if(!paused) {
			int now = dt + interval;
			int duration = durations[currentFrameIndex];
			int frameIndex = currentFrameIndex;
			if(duration > now) {
				do {
					frameIndex += 1;
					now = now - duration;
					if (frameIndex < durations.length) {
						duration = durations[frameIndex];
					} else if (loop) {
						frameIndex = 0;
						duration = durations[0];
					} else {
						frameIndex = durations.length - 1;
						now = 0;
						break;
					}
				} while(now < duration);
			}
			currentFrameIndex = frameIndex;
			dt = now;
		}
	}
	
	@Override
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	@Override
	public boolean getLoop() {
		return loop;
	}

	@Override
	public int getCurrentFrameIndex() {
		return currentFrameIndex;
	}

	@Override
	public int getTotalFrames() {
		return models.length;
	}

	@Override
	public IModel2D getCurrentModel() {
		return models[currentFrameIndex];
	}

	@Override
	public IModel2D getModelByIndex(int index) {
		return models[index];
	}

	@Override
	public IModel2DAnimation copy() {
		Model2DAnimation animation = new Model2DAnimation(name,models, durations, loop);
		animation.dt = dt;
		animation.currentFrameIndex = currentFrameIndex;
		animation.paused = paused;
		return animation;
	}

	@Override
	public String getName() {
		return name;
	}

}
