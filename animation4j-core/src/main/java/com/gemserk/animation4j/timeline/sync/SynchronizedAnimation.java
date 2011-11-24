package com.gemserk.animation4j.timeline.sync;

import com.gemserk.animation4j.animations.Animation;
import com.gemserk.animation4j.timeline.TimelineAnimation;

/**
 * Animation implementation which decorates the TimelineAnimation using a synchronizer to synchronize time line values with an object.
 * 
 * @author acoppes
 */
@Deprecated
public class SynchronizedAnimation implements Animation {

	private final TimelineAnimation animation;

	private final TimelineSynchronizer timelineSynchronizer;

	// private final TimelineIterator iterator;

	public SynchronizedAnimation(TimelineAnimation animation, TimelineSynchronizer timelineSynchronizer) {
		this.animation = animation;
		this.timelineSynchronizer = timelineSynchronizer;

		// iterator = animation.getTimeline().getIterator();
	}

	public void start() {
		animation.start();
	}

	public void start(int iterationCount) {
		animation.start(iterationCount);
	}

	public void start(int iterationCount, boolean alternateDirection) {
		animation.start(iterationCount, alternateDirection);
	}

	public void restart() {
		animation.restart();
	}

	public void stop() {
		animation.stop();
	}

	public void pause() {
		animation.pause();
	}

	public void resume() {
		animation.resume();
	}

	public boolean isStarted() {
		return animation.isStarted();
	}

	public boolean isFinished() {
		return animation.isFinished();
	}

	public int getIteration() {
		return animation.getIteration();
	}

	public PlayingDirection getPlayingDirection() {
		return animation.getPlayingDirection();
	}

	@Deprecated
	public void update(float delta) {
		animation.update(delta);
		// iterator.restart();
		// float time = animation.getCurrentTime();
		// timelineSynchronizer.syncrhonize(iterator, time);
	}

}