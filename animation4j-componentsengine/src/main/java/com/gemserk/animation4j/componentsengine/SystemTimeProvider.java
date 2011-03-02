package com.gemserk.animation4j.componentsengine;

/**
 * Default implementation of TimeProvider using system timer.
 * @author acoppes
 */
public class SystemTimeProvider implements TimeProvider {

	public long getTime() {
		return System.currentTimeMillis();
	}

}