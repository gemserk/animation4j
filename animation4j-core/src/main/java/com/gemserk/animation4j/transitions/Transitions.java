package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

/**
 * Provides an easy way to build transitions.
 * 
 * @author acoppes
 */
public class Transitions {

	/**
	 * Used to build transitions when no speed is specified.
	 */
	public static float defaultTransitionSpeed = 1f;

	public static TimeProvider timeProvider = new SystemTimeProvider();

	/**
	 * Provides an easy way to build a transition.
	 */
	public static class TransitionBuilder<T> {

		private T startValue;

		private T endValue;

		private int time;

		private float speed = 1f;

		private InterpolationFunction[] functions;

		private TypeConverter<T> typeConverter;

		private TimeProvider timeProvider = new SystemTimeProvider();

		TransitionBuilder<T> start(T startValue) {
			this.startValue = startValue;
			return this;
		}

		public TransitionBuilder<T> end(T endValue) {
			this.endValue = endValue;
			return this;
		}

		public TransitionBuilder<T> time(int time) {
			this.time = time;
			return this;
		}

		public TransitionBuilder<T> speed(float speed) {
			this.speed = speed;
			return this;
		}

		public TransitionBuilder<T> functions(InterpolationFunction... functions) {
			this.functions = functions;
			return this;
		}

		public TransitionBuilder<T> converter(TypeConverter<T> typeConverter) {
			this.typeConverter = typeConverter;
			return this;
		}

		public TransitionBuilder<T> timeProvider(TimeProvider timeProvider) {
			this.timeProvider = timeProvider;
			return this;
		}

		public TransitionBuilder<T> typeConverter(TypeConverter typeConverter) {
			this.typeConverter = typeConverter;
			return this;
		}

		/**
		 * Builds and returns the Transition<T> using the specified builder parameters.
		 */
		public Transition<T> build() {
			if (typeConverter == null)
				typeConverter = (TypeConverter<T>) Converters.converter(startValue.getClass());

			Transition<T> transition;

			InternalTransition<T> internalTransition = new InternalTransition<T>(startValue, typeConverter, getInterpolator());
			transition = new TransitionImpl<T>(internalTransition, speed, timeProvider);

			if (endValue != null)
				transition.set(endValue, time);
			// else
			// throw new RuntimeException("cant create a transition without end value, you must call end() method");

			return transition;
		}

		private Interpolator<float[]> getInterpolator() {
			Interpolator<float[]> interpolator = null;

			if (functions != null) {
				interpolator = new FloatArrayInterpolator(typeConverter.variables(), functions);
			} else {
				interpolator = new FloatArrayInterpolator(typeConverter.variables());
			}
			return interpolator;
		}

	}

	/**
	 * Creates a new transition builder to let the user create a transition in an easy way.
	 */
	public static <T> TransitionBuilder<T> transitionBuilder(T startValue) {
		// if we need to create a pool of builders or something, this is where to do that.
		TransitionBuilder<T> transitionBuilder = new TransitionBuilder<T>();
		transitionBuilder.timeProvider(timeProvider);
		transitionBuilder.speed(defaultTransitionSpeed);
		return transitionBuilder.start(startValue).speed(defaultTransitionSpeed).time(1);
	}

}