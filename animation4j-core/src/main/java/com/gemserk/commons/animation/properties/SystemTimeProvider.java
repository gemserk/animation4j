package com.gemserk.commons.animation.properties;

public class SystemTimeProvider implements TimeProvider {

	public long getTime() {
		return System.currentTimeMillis();
	}

}