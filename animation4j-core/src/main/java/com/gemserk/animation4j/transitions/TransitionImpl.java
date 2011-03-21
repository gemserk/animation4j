package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;

/**
 * Implementation of a transition based on some aspects of <a href="http://www.w3.org/TR/2009/WD-css3-transitions-20090320/">CSS3 transitions spec</a>.
 * 
 * @author acoppes
 */
public class TransitionImpl<T> implements Transition<T> {

	private int totalTime;

	private int currentTime;

	private int defaultTime;

	private Interpolator<float[]> interpolator;

	private float[] startValue;

	private float[] currentValue;

	private float[] desiredValue;

	private T currentObject;

	private final TypeConverter<T> typeConverter;

	/**
	 * @param startValue
	 *            The starting value of the transition.
	 * @param interpolator
	 *            The interpolator to use when updating the value.
	 * @param defaultTime
	 *            The default time to use when calling set without specifying the time.
	 */
	public TransitionImpl(T startValue, int defaultTime, TypeConverter<T> typeConverter) {
		this.typeConverter = typeConverter;
		this.startValue = typeConverter.copyFromObject(startValue, null);
		this.currentValue = null;
		this.interpolator = new FloatArrayInterpolator(typeConverter.variables());
		this.defaultTime = defaultTime;
	}

	@Override
	public T get() {
		if (currentValue == null)
			currentObject = typeConverter.copyToObject(currentObject, startValue);
		else
			currentObject = typeConverter.copyToObject(currentObject, currentValue);
		return currentObject;
	}

	@Override
	public void set(T t) {
		this.set(t, defaultTime);
	}

	@Override
	public void set(T t, int time) {
		this.desiredValue = typeConverter.copyFromObject(t, desiredValue);
		this.totalTime = time;
		if (currentValue != null) {
			this.startValue = currentValue;
			this.currentValue = null;
		}
		this.currentTime = 0;

		if (time == 0)
			this.currentValue = interpolator.interpolate(startValue, desiredValue, 1f);

	}

	/**
	 * Updates the current value to the new value.
	 * 
	 * @param time
	 *            The time to update the current value.
	 */
	public void update(int time) {

		if (currentTime == totalTime)
			return;

		currentTime += time;

		if (currentTime > totalTime)
			currentTime = totalTime;

		if (desiredValue == null)
			return;

		float alpha = (float) currentTime / (float) totalTime;

		currentValue = interpolator.interpolate(startValue, desiredValue, alpha);

	}

}
