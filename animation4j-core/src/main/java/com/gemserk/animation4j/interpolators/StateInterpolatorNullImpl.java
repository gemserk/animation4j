package com.gemserk.animation4j.interpolators;

public class StateInterpolatorNullImpl implements StateInterpolator<Object> {
	
	private final int time;
	
	private int currentTime;

	public StateInterpolatorNullImpl(int time) {
		this.time = time;
		currentTime = time;
	}

	@Override
	public Object getInterpolatedValue() {
		return null;
	}

	@Override
	public void reset() {
		currentTime = time;
	}

	@Override
	public void update(int delta) {
		currentTime -= delta;
		if (currentTime < 0)
			currentTime = 0;
	}

	@Override
	public boolean isFinished() {
		return currentTime == 0;
	}

}
