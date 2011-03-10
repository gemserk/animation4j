package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.Animation;

public class SynchronizeValuesAnimation implements Animation {
	
	private TimelineAnimation timelineAnimation;
	
	private Object synchronizationObject;
	
	public SynchronizeValuesAnimation(TimelineAnimation timelineAnimation, Object synchronizationObject) {
		this.timelineAnimation = timelineAnimation;
		this.synchronizationObject = synchronizationObject;
	}

	public void setSpeed(float speed) {
		timelineAnimation.setSpeed(speed);
	}

	public void setAlternateDirection(boolean alternateDirection) {
		timelineAnimation.setAlternateDirection(alternateDirection);
	}

	public float getCurrentTime() {
		return timelineAnimation.getCurrentTime();
	}

	public PlayingDirection getPlayingDirection() {
		return timelineAnimation.getPlayingDirection();
	}

	public float getDuration() {
		return timelineAnimation.getDuration();
	}

	public <T> T getValue(String name) {
		return timelineAnimation.getValue(name);
	}

	public Timeline getTimeline() {
		return timelineAnimation.getTimeline();
	}

	public void start() {
		timelineAnimation.start();
	}

	public void start(int iterationCount) {
		timelineAnimation.start(iterationCount);
	}

	public void start(int iterationCount, boolean alternateDirection) {
		timelineAnimation.start(iterationCount, alternateDirection);
	}

	public void restart() {
		timelineAnimation.restart();
	}

	public void stop() {
		timelineAnimation.stop();
	}

	public void pause() {
		timelineAnimation.pause();
	}

	public void resume() {
		timelineAnimation.resume();
	}

	public int getIteration() {
		return timelineAnimation.getIteration();
	}

	public boolean isFinished() {
		return timelineAnimation.isFinished();
	}

	public boolean isStarted() {
		return timelineAnimation.isStarted();
	}

	public void update(float time) {
		timelineAnimation.update(time);
	}

	public void nextIteration() {
		timelineAnimation.nextIteration();
	}

	public void switchDirection() {
		timelineAnimation.switchDirection();
	}
	
}
