package com.gemserk.commons.animation.timeline;

import com.gemserk.commons.animation.Animation;

public class TimelineAnimation implements Animation {

	float currentTime = 0f;

	float speed = 1f;

	boolean playing = false;

	boolean loop = false;

	Timeline timeline;

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
		pause();
	}

	public void play() {
		play(false);
	}

	public void play(boolean loop) {
		this.playing = true;
		this.loop = loop;
	}

	public void pause() {
		playing = false;
	}

	public void update(float time) {
		if (!playing)
			return;

		currentTime += time * speed;

		if (isFinished()) {
			if (!loop) {
				currentTime = getDuration();
				pause();
			} else {
				stop();
				play(true);
			}
		}
	}

	public Object getValue(String name) {
		return timeline.getValue(currentTime, name);
	}

	public Timeline getTimeline() {
		return timeline;
	}

	@Override
	public boolean isFinished() {
		return currentTime >= timeline.getDuration() + timeline.getDelay();
	}

	@Override
	public boolean isStarted() {
		return currentTime >= timeline.getDelay();
	}

	@Override
	public void restart() {
		currentTime = 0;
		play();
	}

}