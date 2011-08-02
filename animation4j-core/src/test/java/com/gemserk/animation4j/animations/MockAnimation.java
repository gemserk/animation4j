package com.gemserk.animation4j.animations;

import com.gemserk.animation4j.animations.Animation;

public class MockAnimation implements Animation {

	boolean started;

	boolean finished;

	int iteration;

	int iterations;

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	@Override
	public void resume() {

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public void restart() {
		started = false;
		finished = false;
		this.iteration = 1;
	}

	@Override
	public void stop() {
		started = false;
		finished = false;
	}

	@Override
	public void start(int iterationCount) {
		this.iterations = iterationCount;
		this.iteration = 1;
	}

	@Override
	public void pause() {

	}

	@Override
	public void start() {

	}

	@Override
	public int getIteration() {
		return iteration;
	}

	@Override
	public void start(int iterationCount, boolean alternateDirection) {
		start(iterationCount);
	}

	PlayingDirection playingDirection = PlayingDirection.Normal;

	public void setPlayingDirection(PlayingDirection playingDirection) {
		this.playingDirection = playingDirection;
	}

	public PlayingDirection getPlayingDirection() {
		return playingDirection;
	}

}