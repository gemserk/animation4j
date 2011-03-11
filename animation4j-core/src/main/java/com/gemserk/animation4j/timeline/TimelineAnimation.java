package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.Animation;

public class TimelineAnimation implements Animation {

	float currentTime = 0f;

	float speed = 1f;

	boolean playing = false;

	Timeline timeline;

	private int iteration = 1;

	private int iterations = 1;

	boolean alternateDirection = false;

	PlayingDirection playingDirection = PlayingDirection.Normal;

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setAlternateDirection(boolean alternateDirection) {
		this.alternateDirection = alternateDirection;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	@Override
	public PlayingDirection getPlayingDirection() {
		return playingDirection;
	}

	public float getDuration() {
		return timeline.getDuration() + timeline.getDelay();
	}

	public TimelineAnimation(Timeline timeline) {
		this(timeline, false);
	}

	public TimelineAnimation(Timeline timeline, boolean started) {
		this(timeline, started, false);
	}

	public TimelineAnimation(Timeline timeline, boolean started, boolean alternateDirection) {
		this.timeline = timeline;
		this.playing = started;
		this.alternateDirection = alternateDirection;
	}

	/**
	 * Returns true when the time line for the current iteration is finished, could be normal or reverse playing direction.
	 */
	protected boolean isIterationFinished() {
		float delay = timeline.getDelay();
		if (playingDirection.equals(PlayingDirection.Normal))
			return currentTime >= timeline.getDuration() + delay;
		return currentTime + delay <= 0;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String name) {
		return (T) timeline.getValue(currentTime, name);
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
		this.iterations = iterationCount;
		if (this.iterations <= 0)
			this.iterations = Integer.MAX_VALUE;
		this.iteration = 1;
		resume();
	}

	@Override
	public void start(int iterationCount, boolean alternateDirection) {
		start(iterationCount);
		this.alternateDirection = alternateDirection;
	}

	@Override
	public void restart() {
		stop();
		resume();
	}

	public void stop() {
		currentTime = 0;
		iteration = 1;
		pause();
	}

	public void pause() {
		playing = false;
	}

	public void resume() {
		playing = true;
	}

	@Override
	public int getIteration() {
		return iteration;
	}

	@Override
	public boolean isFinished() {
		if (iteration > iterations)
			return true;
		return isIterationFinished();
	}

	@Override
	public boolean isStarted() {
		if (iteration > 1)
			return true;
		return currentTime > timeline.getDelay();
	}

	public void update(float time) {
		if (!playing)
			return;
		moveCurrentTime(time);
		if (isIterationFinished()) {
			iteration++;
			if (iteration > iterations)
				finishIterations();
			else
				nextIteration();
		}
	}

	public void nextIteration() {
		if (playingDirection.equals(PlayingDirection.Normal)) {
			if (alternateDirection)
				switchDirection();
			else
				currentTime = 0 + timeline.getDelay();
		} else {
			if (alternateDirection)
				switchDirection();
		}
	}

	public void switchDirection() {
		if (playingDirection == PlayingDirection.Normal)
			playingDirection = PlayingDirection.Reverse;
		else 
			playingDirection = PlayingDirection.Normal;
	}

	protected void finishIterations() {
		if (playingDirection.equals(PlayingDirection.Normal)) {
			currentTime = getDuration();
			pause();
		} else {
			currentTime = 0f;
			pause();
			switchDirection();
		}
	}

	protected void moveCurrentTime(float time) {
		if (playingDirection.equals(PlayingDirection.Normal))
			currentTime += time * speed;
		else
			currentTime -= time * speed;
	}

}