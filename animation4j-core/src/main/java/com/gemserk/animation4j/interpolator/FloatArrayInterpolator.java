package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;

/**
 * Interpolates an array of floats using multiple interpolator functions (linear by default).
 * 
 * @author acoppes
 */
public class FloatArrayInterpolator implements Interpolator<float[]> {

	private final InterpolationFunction[] functions;

	private final float[] x;

	/**
	 * Creates a new interpolator with an internal array of float with the length specified.
	 * 
	 * @param length
	 *            The length of the float array to interpolate.
	 */
	public FloatArrayInterpolator(int length) {
		this(new float[length]);
	}

	/**
	 * Creates a new interpolator with an internal array of float with the length specified.
	 * 
	 * @param length
	 *            The length of the float array to interpolate.
	 * @param functions
	 *            The interpolator functions to be used to interpolate each variable of the array.
	 */
	public FloatArrayInterpolator(int length, InterpolationFunction... functions) {
		this(new float[length], functions);
	}

	/**
	 * Creates a new interpolator using the float array specified.
	 * 
	 * @param x
	 *            The float array to be used.
	 * @param functions
	 *            The interpolator functions to be used to interpolate each variable of the array.
	 */
	public FloatArrayInterpolator(float[] x, InterpolationFunction... functions) {
		this.x = x;
		this.functions = new InterpolationFunction[x.length];

		int i = 0;
		for (i = 0; i < functions.length; i++)
			this.functions[i] = functions[i];
		for (; i < this.functions.length; i++)
			this.functions[i] = InterpolationFunctions.linear();
	}

	@Override
	public float[] interpolate(float[] a, float[] b, float t) {
		for (int i = 0; i < x.length; i++) {
			t = functions[i].interpolate(t);
			x[i] = a[i] * (1 - t) + b[i] * t;
		}
		return x;
	}

}