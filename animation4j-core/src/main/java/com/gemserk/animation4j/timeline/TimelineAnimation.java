package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.animations.Animation;
import com.gemserk.animation4j.animations.AnimationImpl;

/**
 * An implementation of the Animation interface using a time line to animate values.
 * 
 * @author acoppes
 */
public class TimelineAnimation implements Animation {

	private Timeline timeline;
	private AnimationImpl animation;

	public void setDuration(float duration) {
		animation.setDuration(duration);
	}

	public void setSpeed(float speed) {
		animation.setSpeed(speed);
	}

	public void setAlternateDirection(boolean alternateDirection) {
		animation.setAlternateDirection(alternateDirection);
	}

	public float getCurrentTime() {
		return animation.getCurrentTime();
	}

	@Override
	public PlayingDirection getPlayingDirection() {
		return animation.getPlayingDirection();
	}

	public void setDelay(float delay) {
		animation.setDelay(delay);
	}

	public float getDelay() {
		return animation.getDelay();
	}

	public TimelineAnimation(Timeline timeline) {
		this(timeline, false);
	}

	public TimelineAnimation(Timeline timeline, boolean started) {
		this(timeline, started, false);
	}

	public TimelineAnimation(Timeline timeline, boolean started, boolean alternateDirection) {
		this.timeline = timeline;
		this.animation = new AnimationImpl(started, alternateDirection);
	}

	/**
	 * Returns true when the time line for the current iteration is finished, could be normal or reverse playing direction.
	 */
	protected boolean isIterationFinished() {
		return animation.isIterationFinished();
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String name) {
		return (T) timeline.getValue(getCurrentTime() - getDelay(), name);
	}

	public Timeline getTimeline() {
		return timeline;
	}

	@Override
	public void start() {
		start(1);
	}

	@Override
	public void start(int iterationCount) {
		animation.start(iterationCount);
	}

	@Override
	public void start(int iterationCount, boolean alternateDirection) {
		animation.start(iterationCount, alternateDirection);
	}

	@Override
	public void restart() {
		animation.restart();
	}

	public void stop() {
		animation.stop();
	}

	public void pause() {
		animation.pause();
	}

	public void resume() {
		animation.resume();
	}

	@Override
	public int getIteration() {
		return animation.getIteration();
	}

	@Override
	public boolean isFinished() {
		return animation.isFinished();
	}

	@Override
	public boolean isStarted() {
		return animation.isStarted();
	}

	public void update(float time) {
		animation.update(time);
	}

	public void nextIteration() {
		animation.nextIteration();
	}

	public void switchDirection() {
		animation.switchDirection();
	}

}