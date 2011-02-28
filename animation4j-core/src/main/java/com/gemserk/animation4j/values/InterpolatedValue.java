package com.gemserk.animation4j.values;

import com.gemserk.animation4j.interpolators.Interpolator;

/**
 * Used to simplify interpolated values management. You simply create an interpolated value with an interpolator and two values, and then move the alpha between 0 and 1 to get an interpolated value.
 * @param <T> the type of the variable to interpolate
 * @author acoppes
 */
public abstract class InterpolatedValue<T>  {

	private Interpolator<T> interpolator;

	protected T a;

	protected T b;

	private float alpha;

	public void setA(T a) {
		this.a = a;
	}

	public void setB(T b) {
		this.b = b;
	}

	public T getA() {
		return a;
	}

	public T getB() {
		return b;
	}

	public float getAlpha() {
		return alpha;
	}

	/**
	 * Sets alpha between 0 and 1.
	 * @param alpha
	 */
	public void setAlpha(float alpha) {
		if (alpha < 0)
			alpha = 0f;

		if (alpha > 1f)
			alpha = 1f;

		this.alpha = alpha;
	}

	public InterpolatedValue(Interpolator<T> interpolator, T a, T b) {
		this.interpolator = interpolator;
		this.a = a;
		this.b = b;
		this.alpha = 0f;
	}

	/**
	 * @return an interpolated value between a and b, using alpha as weight.
	 */
	public T getInterpolatedValue() {
		return interpolator.interpolate(a, b, alpha);
	}

	public boolean isFinnished() {
		return alpha == 1f;
	}

	/**
	 * Sets alpha normalizing it based on distance between a and b.
	 * @param alpha
	 */
	public void setNormalizedAlpha(float alpha) {
		float length = getLength();
		if (length != 0)
			setAlpha(alpha / length);
		else
			setAlpha(0f);
	}

	/**
	 * @return The distance between the two points a and b. Depends on the variable type so must be implemented in a subclass. 
	 */
	protected abstract float getLength();
	
}