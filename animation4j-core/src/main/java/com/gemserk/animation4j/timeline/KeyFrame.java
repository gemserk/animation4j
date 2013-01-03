package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * A key frame specifies a specific value a variable should have in a specific time.
 * 
 * @author acoppes
 */
public class KeyFrame {

	/**
	 * Time in seconds of the key frame.
	 */
	private final float time;
	private final float[] value;
	private final InterpolationFunction[] functions;

	/**
	 * The internal index inside the {@link TimelineValue}, used to improve speed when calculating the 
	 * previous {@link KeyFrame}.
	 */
	int index;

	public float getTime() {
		return time;
	}

	public float[] getValue() {
		return value;
	}

	public InterpolationFunction[] getFunctions() {
		return functions;
	}

	public KeyFrame(float time, float[] value, InterpolationFunction... functions) {
		this.time = time;
		this.value = value;
		this.functions = functions;
	}

	public float[] interpolate(float[] b, float[] out, float weight) {
		FloatArrayInterpolator.interpolate(getValue(), b, out, weight, functions);
		return out;
	}

	/**
	 * Interpolates values between the current key frame and the specified key frame using the specified functions for this key frame, outputs result in out array.
	 */
	public float[] interpolate(KeyFrame keyFrame, float[] out, float weight) {
		return interpolate(keyFrame.getValue(), out, weight);
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append("time: ");
		stringBuffer.append(time);
		stringBuffer.append(", values: ");
		for (int i = 0; i < value.length; i++) {
			stringBuffer.append(value[i]);
			stringBuffer.append(", ");
		}
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

}