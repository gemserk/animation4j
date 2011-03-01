package com.gemserk.animation4j;

/**
 * Provides an abstraction of an animation with most common methods.
 * @author acoppes
 */
public interface Animation {

	/**
	 * Starts the animation.
	 */
	void start();
	
	/**
	 * Starts the animation and repeats it the specified number of times.
	 * @param iterationCount - The number of times to repeat the animation. Use 0 or negative number to repeat the Animation an infinite number of times. 
	 */
	void start(int iterationCount);
	
	void stop();
	
	/**
	 * Pauses the animation in the current time.
	 */
	void pause();
	
	/**
	 * Resumes the animation from the current time.
	 */
	void resume();
	
	/**
	 * Returns true if the animation is started.
	 */
	boolean isStarted();
	
	/**
	 * Returns true if the animation is finished.
	 */
	boolean isFinished();

	void restart();
	
	
	
	void update(float delta);
}