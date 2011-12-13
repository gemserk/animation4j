package com.gemserk.animation4j.timeline;

import java.util.LinkedList;

/**
 * Implementation of TimelineValueInterface which works modifying a float[].
 * 
 * @author acoppes
 * 
 */
public class TimelineValueFloatArray implements TimelineValueInterface {

	float[] x;
	LinkedList<KeyFrame> keyFrames;

	/**
	 * Creates a new TimelineValueFloatArray using the specified float[] as the array to be modified.
	 * 
	 * @param x
	 *            The float array to be modified on setTime().
	 */
	public TimelineValueFloatArray(float[] x) {
		this.x = x;
		keyFrames = new LinkedList<KeyFrame>();
	}

	public void addKeyFrame(KeyFrame keyFrame) {
		if (keyFrame.getValue().length != x.length)
			throw new IllegalArgumentException("keyframe should work over a valid float[] of size " + x.length);
		keyFrames.add(keyFrame);
	}

	public void setTime(float time) {
		if (keyFrames.isEmpty())
			throw new RuntimeException("cant interpolate without keyframes");

		KeyFrame keyFrame = getKeyFrame(time);
		KeyFrame previousKeyFrame = getPreviousKeyFrame(keyFrame);

		if (previousKeyFrame == null) {
			System.arraycopy(keyFrame.getValue(), 0, x, 0, x.length);
			return;
		}

		float interval = keyFrame.getTime() - previousKeyFrame.getTime();
		float weight = (time - previousKeyFrame.getTime()) / interval;

		previousKeyFrame.interpolate(keyFrame, x, weight);
	}

	KeyFrame getKeyFrame(float time) {
		if (keyFrames.size() == 1)
			return keyFrames.getFirst();

		KeyFrame firstKeyFrame = keyFrames.getFirst();

		if (time < firstKeyFrame.getTime())
			return firstKeyFrame;

		for (int i = 0; i < keyFrames.size(); i++) {
			KeyFrame currentKeyFrame = keyFrames.get(i);
			if (currentKeyFrame.getTime() > time)
				return currentKeyFrame;
		}

		return keyFrames.getLast();
	}

	KeyFrame getPreviousKeyFrame(KeyFrame keyFrame) {
		int currentKeyFrameIndex = keyFrames.indexOf(keyFrame);
		if (currentKeyFrameIndex - 1 < 0)
			return null;
		return keyFrames.get(currentKeyFrameIndex - 1);
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append(", keyframes:");
		for (KeyFrame keyFrame : keyFrames)
			stringBuffer.append(keyFrame.toString());
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

}