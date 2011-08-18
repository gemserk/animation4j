package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.reflection.ReflectionUtils;
import com.gemserk.animation4j.time.SystemTimeProvider;
import com.gemserk.animation4j.time.TimeProvider;

/**
 * Transitions factory provides a TransitionBuilder to specify Transitions in an easy way.
 * 
 * @author acoppes
 */
public class Transitions {

	private static final TransitionBuilder transitionBuilder = new TransitionBuilder();

	/**
	 * Provides an easy way to specify all the different values of a transition and to build it.
	 */
	public static class TransitionBuilder<T> {

		private static final TimeProvider systemTypeProvider = new SystemTimeProvider();

		private static final float defaultTransitionSpeed = 1f;

		private T startValue;

		private T endValue;

		private float time;

		private float speed;

		private InterpolationFunction[] functions;

		private TypeConverter typeConverter;

		private TimeProvider timeProvider;

		TransitionBuilder() {
			reset();
		}

		private void reset() {
			functions = null;
			speed = defaultTransitionSpeed;
			startValue = null;
			endValue = null;
			time = 0;
			timeProvider = systemTypeProvider;
			typeConverter = null;
		}

		public TransitionBuilder<T> start(T startValue) {
			this.startValue = startValue;
			return this;
		}

		public TransitionBuilder<T> end(T endValue) {
			this.endValue = endValue;
			return this;
		}

		public TransitionBuilder<T> time(int time) {
			return time((float) time * 0.001f);
		}
		
		public TransitionBuilder<T> time(float time) {
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
			if (this.timeProvider == systemTypeProvider)
				this.timeProvider = timeProvider;
			return this;
		}

		public TransitionBuilder<T> typeConverter(TypeConverter typeConverter) {
			this.typeConverter = typeConverter;
			return this;
		}

		public boolean isStartValueSet() {
			return startValue != null;
		}

		/**
		 * Builds and returns the Transition<T> using the specified builder parameters.
		 */
		public Transition<T> build() {
			if (typeConverter == null)
				typeConverter = Converters.converter(startValue.getClass());

			InternalTransition<T> internalTransition = new InternalTransition<T>(startValue, typeConverter, getInterpolator());
			Transition<T> transition = new TransitionImpl<T>(internalTransition, speed, timeProvider);

			if (endValue != null)
				transition.set(endValue, time);
			// else
			// throw new RuntimeException("cant create a transition without end value, you must call end() method");

			reset();
			return transition;
		}

		private Interpolator<float[]> getInterpolator() {
			if (functions != null)
				return new FloatArrayInterpolator(typeConverter.variables(), functions);
			else
				return new FloatArrayInterpolator(typeConverter.variables());
		}

	}

	/**
	 * Creates a new transition builder to let the user create a transition in an easy way.
	 */
	public static TransitionBuilder transitionBuilder() {
		return transitionBuilder;
	}

	/**
	 * Creates a new transition builder to let the user create a transition in an easy way.
	 */
	public static <T> TransitionBuilder<T> transitionBuilder(T startValue) {
		return transitionBuilder.start(startValue);
	}

	/**
	 * Creates a new transition builder to let the user create a transition in an easy way, it uses as start value the current value of the field of the specified object.
	 */
	public static <T> TransitionBuilder<T> transitionBuilder(Object object, String field) {
		return (TransitionBuilder<T>) transitionBuilder(ReflectionUtils.getFieldValue(object, field));
	}

}