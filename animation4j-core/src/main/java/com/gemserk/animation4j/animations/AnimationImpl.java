package com.gemserk.animation4j.animations;

/**
 * An implementation of the Animation interface using a time line to animate values.
 * 
 * @author acoppes
 */
public class AnimationImpl implements Animation {

	private float currentTime = 0f;
	private float speed = 1f;
	private boolean playing = false;
	private int iteration = 1;
	private int iterations = 1;
	private boolean alternateDirection = false;
	private PlayingDirection playingDirection = PlayingDirection.Normal;
	private float delay;
	private float duration;

	@Override
	public PlayingDirection getPlayingDirection() {
		return playingDirection;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@Override
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setAlternateDirection(boolean alternateDirection) {
		this.alternateDirection = alternateDirection;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	public float getDuration() {
		return duration + getDelay();
	}

	public void setDelay(float delay) {
		this.delay = delay;
	}

	public float getDelay() {
		return delay;
	}

	public AnimationImpl(boolean started, boolean alternateDirection) {
		this.playing = started;
		this.alternateDirection = alternateDirection;
	}

	/**
	 * Returns true when the time line for the current iteration is finished, could be normal or reverse playing direction.
	 */
	public boolean isIterationFinished() {
		float delay = getDelay();
		if (playingDirection.equals(PlayingDirection.Normal))
			return currentTime >= duration + delay;
		return currentTime + delay <= 0;
	}

	@Override
	public void start() {
		start(1);
	}

	@Override
	public void start(int iterationCount) {
		this.iterations = iterationCount;
		if (this.iterations <= 0)
			this.iterations = Integer.MAX_VALUE;
		this.currentTime = 0;
		this.iteration = 1;
		resume();
	}

	@Override
	public void start(int iterationCount, boolean alternateDirection) {
		start(iterationCount);
		this.alternateDirection = alternateDirection;
	}

	@Override
	public void restart() {
		stop();
		resume();
	}

	public void stop() {
		currentTime = 0;
		iteration = 1;
		playingDirection = PlayingDirection.Normal;
		pause();
	}

	public void pause() {
		playing = false;
	}

	public void resume() {
		playing = true;
	}

	@Override
	public int getIteration() {
		return iteration;
	}

	@Override
	public boolean isFinished() {
		if (iteration > iterations)
			return true;
		return isIterationFinished();
	}

	@Override
	public boolean isStarted() {
		if (iteration > 1)
			return true;
		return currentTime > getDelay();
	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

	public void update(float time) {
		if (!playing)
			return;
		moveCurrentTime(time);
		if (isIterationFinished()) {
			iteration++;
			if (iteration > iterations)
				finishIterations();
			else
				nextIteration();
		}
	}

	public void nextIteration() {
		if (playingDirection.equals(PlayingDirection.Normal)) {
			if (alternateDirection)
				switchDirection();
			else
				currentTime = 0 + getDelay();
		} else {
			if (alternateDirection)
				switchDirection();
		}
	}

	public void switchDirection() {
		if (playingDirection == PlayingDirection.Normal)
			playingDirection = PlayingDirection.Reverse;
		else
			playingDirection = PlayingDirection.Normal;
	}

	protected void finishIterations() {
		if (playingDirection.equals(PlayingDirection.Normal)) {
			currentTime = getDuration();
			pause();
		} else {
			currentTime = 0f;
			pause();
			switchDirection();
		}
	}

	protected void moveCurrentTime(float time) {
		if (playingDirection.equals(PlayingDirection.Normal))
			currentTime += time * speed;
		else
			currentTime -= time * speed;
	}

}