package com.gemserk.animation4j.time;

/**
 * Provides a way to get current application time, used by InterpolatedProperty.
 * @author acoppes
 */
public interface TimeProvider {

	long getTime();

}