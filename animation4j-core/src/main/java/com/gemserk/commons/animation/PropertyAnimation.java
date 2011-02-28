package com.gemserk.commons.animation;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.gemserk.commons.animation.interpolators.Interpolator;
import com.gemserk.commons.animation.interpolators.InterpolatorProvider;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertiesHolder;

@SuppressWarnings("unchecked")
public class PropertyAnimation {

	static class KeyFrameComparator implements Comparator<KeyFrame> {
		@Override
		public int compare(KeyFrame o1, KeyFrame o2) {
			return o1.startTime - o2.startTime;
		}
	}

	private class KeyFrame {

		int startTime;

		Object value;

		public KeyFrame(int startTime, Object value) {
			this.startTime = startTime;
			this.value = value;
		}

	}

	int currentTime = 0;

	LinkedList<KeyFrame> keyFrames = new LinkedList<KeyFrame>();

	int currentIndexFrame = -1;

	LinkedList<Interpolator> currentInterpolators = new LinkedList<Interpolator>();

	final String property;

	final boolean loop;

	public PropertyAnimation(String property) {
		this(property, false);
	}

	public PropertyAnimation(String property, boolean loop) {
		this.property = property;
		this.loop = loop;
		stop();
	}

	private KeyFrame getCurrentFrame() {
		if (keyFrames.size() == 0)
			return null;
		return keyFrames.get(currentIndexFrame);
	}

	private KeyFrame getNextFrame() {
		if (currentIndexFrame + 1 < keyFrames.size())
			return keyFrames.get(currentIndexFrame + 1);
		return null;
	}
	
	public void animate(PropertiesHolder propertiesHolder, int delta) {
		
		if (paused)
			return;
		
		currentTime += delta;

		if (mustChangeFrame()) {
			proceedNextFrame();
			recalculateInterpolators();
		}

		for (Interpolator interpolator : currentInterpolators) {
			interpolator.update(delta);
			Object interpolatedValue = interpolator.getInterpolatedValue();
			Properties.setValue(propertiesHolder, property, interpolatedValue);
		}
	}

	private void recalculateInterpolators() {
		currentInterpolators.clear();

		KeyFrame currentFrame = getCurrentFrame();
		KeyFrame nextFrame = getNextFrame();

		if (currentFrame == null || nextFrame == null)
			return;

		int interpolationTime = nextFrame.startTime - currentFrame.startTime;

		Object startValue = currentFrame.value;
		Object endValue = nextFrame.value;

		currentInterpolators.add(InterpolatorProvider.getInterpolator(interpolationTime, startValue, endValue));
	}

	private void proceedNextFrame() {
		currentIndexFrame++;
	}

	private boolean mustChangeFrame() {
		if (currentIndexFrame == -1)
			return true;

		KeyFrame nextFrame = getNextFrame();

		if (nextFrame == null)
			return false;

		if (nextFrame.startTime < currentTime)
			return true;

		return false;
	}

	public void addKeyFrame(int startTime, Object value) {
		keyFrames.add(new KeyFrame(startTime, value));
		Collections.sort(keyFrames, new KeyFrameComparator());
	}

	public void restart() {
		reset();
		play();
	}

	private void reset() {
		currentTime = 0;
		currentInterpolators.clear();
		currentIndexFrame = -1;
	}

	public boolean isFinished() {
		if (!isLastFrame())
			return false;

		// TODO: precalculate is frame finished when update.

		for (Interpolator interpolator : currentInterpolators) {
			if (!interpolator.isFinished())
				return false;
		}
		return true;
	}

	private boolean isLastFrame() {
		return currentIndexFrame == keyFrames.size() - 1;
	}
	
	private boolean paused;
	
	public void play() {
		paused = false;
	}
	
	public void stop(){ 
		reset();
		pause();
	}
	
	public void pause() {
		paused = true;
	}
	
	public boolean isPaused() {
		return paused;
	}

}
