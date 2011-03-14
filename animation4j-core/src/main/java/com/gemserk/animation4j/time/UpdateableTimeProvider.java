package com.gemserk.animation4j.time;

public class UpdateableTimeProvider implements TimeProvider {

	long time = 0;

	public long getTime() {
		return time;
	}

	public void update(long time) {
		this.time += time;
	}

}