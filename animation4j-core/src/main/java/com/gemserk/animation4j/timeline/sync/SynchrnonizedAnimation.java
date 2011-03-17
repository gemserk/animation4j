package com.gemserk.animation4j.timeline.sync;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineIterator;

/**
 * Animation implementation which decorates the TimelineAnimation using a synchronizer to synchronize time line values with an object. 
 * @author acoppes
 */
public class SynchrnonizedAnimation implements Animation {

	private final TimelineAnimation animation;
	
	private final TimelineSynchronizer timelineSynchronizer;

	private final TimelineIterator iterator;

	public SynchrnonizedAnimation(TimelineAnimation animation, TimelineSynchronizer timelineSynchronizer) {
		this.animation = animation;
		this.timelineSynchronizer = timelineSynchronizer;
		
		iterator = animation.getTimeline().getIterator();
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

	public void update(float delta) {
		animation.update(delta);
		iterator.restart();
		float time = animation.getCurrentTime();
		timelineSynchronizer.syncrhonize(iterator, time);
	}

}