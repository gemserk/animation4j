package com.gemserk.animation4j.timeline;

import java.util.ArrayList;

/**
 * Implementation of TimelineValueInterface which works modifying a float[].
 * 
 * @author acoppes
 * 
 */
class TimelineValueFloatArrayImpl {

	float[] x;
	ArrayList<KeyFrame> keyFrames;

	/**
	 * Creates a new instance using the specified float[] as the array to be modified.
	 * 
	 * @param x
	 *            The float array to be modified on setTime().
	 */
	public TimelineValueFloatArrayImpl(float[] x) {
		this.x = x;
		keyFrames = new ArrayList<KeyFrame>();
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

//		if (weight >= 1f)
//			weight = 1f;

		previousKeyFrame.interpolate(keyFrame, x, weight);
	}

	KeyFrame getKeyFrame(float time) {
		if (keyFrames.size() == 1)
			return keyFrames.get(0);

		KeyFrame firstKeyFrame = keyFrames.get(0);

		if (time < firstKeyFrame.getTime())
			return firstKeyFrame;

		for (int i = 0; i < keyFrames.size(); i++) {
			KeyFrame currentKeyFrame = keyFrames.get(i);
			if (currentKeyFrame.getTime() > time)
				return currentKeyFrame;
		}

		return keyFrames.get(keyFrames.size() - 1);
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