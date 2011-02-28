package com.gemserk.animation4j;

/**
 * Provides an abstraction of an animation with most common methods.
 * @author acoppes
 */
public interface Animation {

	void play();

	void update(float delta);
	
	boolean isStarted();
	
	boolean isFinished();

	void restart();
	
	void stop();
}