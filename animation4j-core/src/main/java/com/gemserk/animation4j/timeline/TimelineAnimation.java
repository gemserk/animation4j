package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.Animation;

public class TimelineAnimation implements Animation {

	float currentTime = 0f;

	float speed = 1f;

	boolean playing = false;

	Timeline timeline;

	private int iteration = 1;

	private int iterations = 1;

	enum PlayingDirection {
		Normal, Reverse;
	}

	boolean alternateDirection = false;

	PlayingDirection direction = PlayingDirection.Normal;

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getDuration() {
		return timeline.getDuration() + timeline.getDelay();
	}

	public TimelineAnimation(Timeline timeline) {
		this(timeline, false);
	}

	public TimelineAnimation(Timeline timeline, boolean started) {
		this.timeline = timeline;
		this.playing = started;
	}

	public void stop() {
		currentTime = 0;
		iteration = 1;
		pause();
	}

	public void resume() {
		playing = true;
	}

	public void pause() {
		playing = false;
	}

	public void update(float time) {
		if (!playing)
			return;

		if (direction.equals(PlayingDirection.Normal)) {
			currentTime += time * speed;

			if (isTimelineFinished()) {

				iteration++;

				if (iteration > iterations) {
					// if (!loop) {
					currentTime = getDuration();
					pause();
				} else {

					if (alternateDirection) {
						direction = PlayingDirection.Reverse;
					} else {
						currentTime = 0;
					}
				}

			}

		} else if (direction.equals(PlayingDirection.Reverse)) {
			currentTime -= time * speed;

			if (isTimelineFinished()) {

				iteration++;

				if (iteration > iterations) {
					pause();
				} else {
					if (alternateDirection)
						direction = PlayingDirection.Normal;
				}
			}

		}

	}

	protected boolean isTimelineFinished() {
		if (direction.equals(PlayingDirection.Normal))
			return currentTime >= timeline.getDuration() + timeline.getDelay();
		return currentTime + timeline.getDelay() <= 0;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String name) {
		return (T) timeline.getValue(currentTime, name);
	}

	public Timeline getTimeline() {
		return timeline;
	}

	@Override
	public boolean isFinished() {
		return isTimelineFinished() && (iteration > iterations);
	}

	@Override
	public boolean isStarted() {
		if (iteration > 1)
			return true;
		return currentTime >= timeline.getDelay();
	}

	@Override
	public void restart() {
		stop();
		resume();
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
	public void start() {
		start(1);
	}

	@Override
	public int getIteration() {
		return iteration;
	}

	public void alternateDirection() {
		alternateDirection = true;
	}

}