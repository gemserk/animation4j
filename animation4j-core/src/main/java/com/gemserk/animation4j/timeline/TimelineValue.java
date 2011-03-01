package com.gemserk.animation4j.timeline;

import java.util.Comparator;
import java.util.LinkedList;

import com.gemserk.animation4j.interpolators.Interpolator;

@SuppressWarnings("rawtypes")
public class TimelineValue<T> {

	/**
	 * Determines a way to compare key frames based on the key frame time.
	 * 
	 * @author acoppes
	 */
	static class KeyFrameComparator implements Comparator<TimelineValue.KeyFrame> {
		@Override
		public int compare(TimelineValue.KeyFrame o1, TimelineValue.KeyFrame o2) {
			return (int) (o1.time - o2.time);
		}
	}

	/**
	 * A key frame specifies a specific value the variable should have in a specific time.
	 * 
	 * @param <T>
	 *            the type of the variable
	 * @author acoppes
	 */
	static class KeyFrame<T> {

		private float time;

		private T value;

		public float getTime() {
			return time;
		}

		public T getValue() {
			return value;
		}

		public KeyFrame(float time, T value) {
			this.time = time;
			this.value = value;
		}

	}

	LinkedList<TimelineValue.KeyFrame<T>> keyFrames = new LinkedList<TimelineValue.KeyFrame<T>>();

	Interpolator<T> interpolator;

	public void setInterpolator(Interpolator<T> interpolator) {
		this.interpolator = interpolator;
	}

	public Interpolator<T> getInterpolator() {
		return interpolator;
	}

	public void addKeyFrame(float time, T value) {
		keyFrames.add(new TimelineValue.KeyFrame<T>(time, value));
	}

	public T getValue(float time) {

		KeyFrame<T> firstKeyFrame = keyFrames.getFirst();

		if (time <= firstKeyFrame.time) {
			if (keyFrames.size() == 1)
				return firstKeyFrame.getValue();

			KeyFrame<T> secondKeyFrame = keyFrames.get(1);

			return interpolator.interpolate(firstKeyFrame.getValue(), secondKeyFrame.getValue(), 0f);
		}

		for (int i = 0; i < keyFrames.size(); i++) {

			TimelineValue.KeyFrame<T> currentKeyFrame = keyFrames.get(i);

			if (currentKeyFrame.time > time) {

				TimelineValue.KeyFrame<T> previousKeyFrame = keyFrames.get(i - 1);

				if (previousKeyFrame == null)
					throw new RuntimeException("invalid time " + time);

				float interval = currentKeyFrame.getTime() - previousKeyFrame.getTime();

				float weight = (time - previousKeyFrame.getTime()) / interval;

				return interpolator.interpolate(previousKeyFrame.getValue(), currentKeyFrame.getValue(), weight);

			}

		}

		return keyFrames.getLast().getValue();

		// if (keyFrames.size() == 1)
		// return keyFrames.getLast().getValue();
		//
		// KeyFrame<T> secondLastFrame = keyFrames.get(keyFrames.size() - 2);
		// KeyFrame<T> lastFrame = keyFrames.getLast();
		//
		// return interpolator.interpolate(secondLastFrame.getValue(), lastFrame.getValue(), 1f);
	}

}