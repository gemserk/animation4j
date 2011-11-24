package com.gemserk.animation4j.transitions;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;

/**
 * Transitions factory provides a TransitionBuilder to specify Transitions in an easy way.
 * 
 * @author acoppes
 */
public class Transitions {

	private static final MutableObjectTransitionBuilder mutableObjectTransitionBuilder = new MutableObjectTransitionBuilder();

	public static class MutableObjectTransitionBuilder<T> {

		private TypeConverter<T> typeConverter;
		private MutableObjectTransition<T> mutableObjectTransition;

		private void setMutableObjectTransition(MutableObjectTransition<T> mutableObjectTransition) {
			this.mutableObjectTransition = mutableObjectTransition;
		}

		private void setTypeConverter(TypeConverter<T> typeConverter) {
			this.typeConverter = typeConverter;
		}
		
		public MutableObjectTransitionBuilder<T> speed(float speed) {
			mutableObjectTransition.setSpeed(speed);
			return this;
		}

		public MutableObjectTransitionBuilder<T> start(float... values) {
			mutableObjectTransition.set(values);
			return this;
		}

		public MutableObjectTransitionBuilder<T> startObject(T start) {
			mutableObjectTransition.set(typeConverter.copyFromObject(start, null));
			return this;
		}

		public MutableObjectTransitionBuilder<T> end(float time, float... values) {
			mutableObjectTransition.set(values, time);
			return this;
		}

		public MutableObjectTransitionBuilder<T> endObject(float time, T end) {
			mutableObjectTransition.set(typeConverter.copyFromObject(end, null), time);
			return this;
		}

		public MutableObjectTransitionBuilder functions(InterpolationFunction... functions) {
			mutableObjectTransition.setFunctions(functions);
			return this;
		}

		public Transition<T> build() {
			return mutableObjectTransition;
		}

		private MutableObjectTransitionBuilder() {

		}

	}

	/**
	 * Returns a new TransitionBuilder to build mutable object transitions.
	 * 
	 * @param mutableObject
	 *            The mutable object to be used in the transition.
	 * @param typeConverter
	 *            The TypeConverter used to convert between float[] to the object and vice versa.
	 * @return The TransitionBuilder to build the new transition.
	 */
	public static <T> MutableObjectTransitionBuilder<T> mutableTransition(T mutableObject, TypeConverter typeConverter) {
		mutableObjectTransitionBuilder.setMutableObjectTransition(new MutableObjectTransition(mutableObject, typeConverter));
		mutableObjectTransitionBuilder.setTypeConverter(typeConverter);
		return mutableObjectTransitionBuilder;
	}

	/**
	 * Returns a new TransitionBuilder to build mutable object transitions, it internally uses the TypeConverter registered on Converters for the object class.
	 * 
	 * @param mutableObject
	 *            The mutable object to be used in the transition.
	 * @return The TransitionBuilder to build the new transition.
	 */
	public static <T> MutableObjectTransitionBuilder<T> mutableTransition(T mutableObject) {
		return mutableTransition(mutableObject, (TypeConverter) Converters.converter(mutableObject.getClass()));
	}

}