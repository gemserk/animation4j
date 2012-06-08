package com.gemserk.animation4j.animations;

/**
 * Provides an abstraction of an animation with most common methods. It is based in some aspects from <a href="http://www.w3.org/TR/css3-animations/">http://www.w3.org/TR/css3-animations/</a>
 * 
 * @author acoppes
 */
public interface Animation {

	enum PlayingDirection {
		Normal, Reverse;
	}

	/**
	 * Starts the animation with the default values of one iteration and no alternate directions.
	 */
	void start();

	/**
	 * Starts the animation and repeats it the specified number of times.
	 * 
	 * @param iterationCount
	 *            The number of times to repeat the animation. Use 0 or negative number to repeat the Animation an infinite number of times.
	 */
	void start(int iterationCount);

	/**
	 * Starts the animation and repeats it the specified number of times.
	 * 
	 * @param iterationCount
	 *            The number of times to repeat the animation. Use 0 or negative number to repeat the Animation an infinite number of times.
	 * @param alternateDirection
	 *            Specify if the animation should alternate between normal and reverse playing direction each time an iteration ends.
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
	 * 
	 * @return The current iteration number.
	 */
	int getIteration();

	/**
	 * Returns the current playing direction of the animation, could it be normal or reverse;
	 */
	PlayingDirection getPlayingDirection();

	/**
	 * Updates the animation.
	 * 
	 * @param delta
	 *            The time to update the animation in Seconds.
	 */
	void update(float delta);

	/**
	 * Sets the animation speed.
	 * 
	 * @param speed
	 *            The new speed of the animation.
	 */
	void setSpeed(float speed);

	/**
	 * Returns the current speed of the animation.
	 */
	float getSpeed();
	
}