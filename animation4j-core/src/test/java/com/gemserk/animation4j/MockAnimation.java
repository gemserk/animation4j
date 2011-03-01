package com.gemserk.animation4j;


public class MockAnimation implements Animation {

	boolean started;
	
	boolean finished;

	int iteration;

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
	}

	@Override
	public void stop() {
		started = false;
		finished = false;
	}

	@Override
	public void start(int iterationCount) {
		this.iteration = iterationCount;
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void start() {
		
	}

}