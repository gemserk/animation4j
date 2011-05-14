package com.gemserk.animation4j.transitions;


public class MockTransition<T> implements Transition<T> {

	boolean transitioning;
	
	boolean started = false;
	
	boolean finished = false;

	public void setTransitioning(boolean transitioning) {
		this.transitioning = transitioning;
	}

	@Override
	public T get() {
		return null;
	}

	@Override
	public void set(T t) {

	}

	@Override
	public void set(T t, int time) {

	}

	@Override
	public boolean isTransitioning() {
		return transitioning;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public boolean isFinihsed() {
		return finished;
	}

}