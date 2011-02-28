package com.gemserk.commons.animation;

public class MockAnimation implements Animation {

	boolean started;
	
	boolean finished;

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	@Override
	public void play() {

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
	}

	@Override
	public void stop() {
		started = false;
		finished = false;
	}

}