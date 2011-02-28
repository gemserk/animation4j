package com.gemserk.commons.animation.properties;

public class InterpolatedPropertyTimeProvider implements TimeProvider {

	long time = 0;

	public long getTime() {
		return time;
	}

	public void update(long time) {
		this.time += time;
	}

}