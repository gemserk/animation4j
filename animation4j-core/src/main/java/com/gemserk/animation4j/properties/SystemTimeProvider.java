package com.gemserk.animation4j.properties;

public class SystemTimeProvider implements TimeProvider {

	public long getTime() {
		return System.currentTimeMillis();
	}

}