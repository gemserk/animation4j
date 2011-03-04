package com.gemserk.animation4j;

/**
 * Provides an abstraction of an animation with most common methods. It is based in some ideas from <a href="http://www.w3.org/TR/css3-animations/">http://www.w3.org/TR/css3-animations/</a>
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
	
	/**
	 * Starts the animation and repeats it the specified number of times.
	 * @param iterationCount - The number of times to repeat the animation. Use 0 or negative number to repeat the Animation an infinite number of times.
	 * @param alternateDirection - Specify if the animation should alternate between normal and reverse playing direction each time an iteration ends. 
	 */
	void start(int iterationCount, boolean alternateDirection);
	
	/**
	 * Restarts the animation from the beginning.
	 */
	void restart();
	
	/**
	 * Stops the animation.
	 */
	void stop();
	
	/**
	 * Pauses the animation in the current time.
	 */
	void pause();
	
	/**
	 * Resumes the animation from the current time.
	 */
	void resume();
	
	/// used by the monitor
	
	/**
	 * Returns true if the animation is started.
	 */
	boolean isStarted();
	
	/**
	 * Returns true if the animation is finished.
	 */
	boolean isFinished();

	/**
	 * Returns the current iteration number.
	 * @return The current iteration number.
	 */
	int getIteration();
	
	/// used to update the animation (should be in the subclass only)
	
	void update(float delta);
}