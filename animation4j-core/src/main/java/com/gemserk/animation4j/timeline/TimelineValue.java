package com.gemserk.animation4j.timeline;

import java.util.Comparator;
import java.util.LinkedList;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;

/**
 * Represents all the progress of a value inside a time line.
 * 
 * @author acoppes
 * 
 * @param <T>
 *            The type of the value.
 */
public class TimelineValue<T> {

	/**
	 * Determines a way to compare key frames based on the key frame time.
	 * 
	 * @author acoppes
	 */
	static class KeyFrameComparator implements Comparator<KeyFrame> {
		@Override
		public int compare(KeyFrame o1, KeyFrame o2) {
			return (int) (o1.time - o2.time);
		}
	}

	/**
	 * A key frame specifies a specific value the variable should have in a specific time.
	 * 
	 * @param <T>
	 *            The type of the variable
	 * @author acoppes
	 */
	static class KeyFrame {

		private float time;

		private float[] value;

		private Interpolator<float[]> interpolator;

		public float getTime() {
			return time;
		}

		public float[] getValue() {
			return value;
		}

		public Interpolator<float[]> getInterpolator() {
			return interpolator;
		}

		public KeyFrame(float time, float[] value, Interpolator<float[]> interpolator) {
			this.time = time;
			this.value = value;
			this.interpolator = interpolator;
		}

	}

	LinkedList<KeyFrame> keyFrames = new LinkedList<KeyFrame>();

	String name;

	private final TypeConverter<T> typeConverter;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	TimelineValue(TypeConverter<T> typeConverter) {
		this.typeConverter = typeConverter;
	}
	
	public void addKeyFrame(float time, T value, Interpolator<float[]> interpolator) {
		keyFrames.add(new KeyFrame(time, typeConverter.copyFromObject(value, null), interpolator));
	}

	private T currentValue;

	/**
	 * Returns the value for the given time, interpolating between corresponding key frames.
	 * 
	 * @param time
	 *            The time to use when calculating the value.
	 * @return An interpolated value based on corresponding to specified time key frames.
	 */
	public T getValue(float time) {
		float[] value = getFloatValue(time);
		currentValue = typeConverter.copyToObject(currentValue, value);
		return currentValue;
	}

	float[] getFloatValue(float time) {
		KeyFrame firstKeyFrame = keyFrames.getFirst();

		if (time <= firstKeyFrame.time) {
			if (keyFrames.size() == 1)
				return firstKeyFrame.getValue();

			KeyFrame secondKeyFrame = keyFrames.get(1);

			Interpolator<float[]> interpolator = firstKeyFrame.getInterpolator();
			return interpolator.interpolate(firstKeyFrame.getValue(), secondKeyFrame.getValue(), 0f);
		}

		for (int i = 0; i < keyFrames.size(); i++) {

			KeyFrame currentKeyFrame = keyFrames.get(i);

			if (currentKeyFrame.time > time) {

				KeyFrame previousKeyFrame = keyFrames.get(i - 1);

				if (previousKeyFrame == null)
					throw new RuntimeException("invalid time " + time);

				float interval = currentKeyFrame.getTime() - previousKeyFrame.getTime();

				float weight = (time - previousKeyFrame.getTime()) / interval;

				Interpolator<float[]> interpolator = previousKeyFrame.getInterpolator();
				return interpolator.interpolate(previousKeyFrame.getValue(), currentKeyFrame.getValue(), weight);

			}

		}

		if (keyFrames.size() == 1)
			return keyFrames.getLast().getValue();

		KeyFrame secondLastFrame = keyFrames.get(keyFrames.size() - 2);
		KeyFrame lastFrame = keyFrames.getLast();

		Interpolator<float[]> interpolator = secondLastFrame.getInterpolator();

		return interpolator.interpolate(secondLastFrame.getValue(), lastFrame.getValue(), 1f);
	}

}