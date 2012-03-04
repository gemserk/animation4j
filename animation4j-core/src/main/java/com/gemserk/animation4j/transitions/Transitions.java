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

	private static final TransitionBuilder transitionBuilder = new TransitionBuilder();

	public static class TransitionBuilder<T> {

		private TypeConverter<T> typeConverter;
		private TransitionImpl<T> transitionImpl;

		private void setMutableObjectTransition(TransitionImpl<T> mutableObjectTransition) {
			this.transitionImpl = mutableObjectTransition;
		}

		private void setTypeConverter(TypeConverter<T> typeConverter) {
			this.typeConverter = typeConverter;
		}
		
		public TransitionBuilder<T> speed(float speed) {
			transitionImpl.setSpeed(speed);
			return this;
		}

		public TransitionBuilder<T> start(float... values) {
			transitionImpl.start(values);
			return this;
		}

		public TransitionBuilder<T> startObject(T start) {
			transitionImpl.start(typeConverter.copyFromObject(start, null));
			return this;
		}

		public TransitionBuilder<T> end(float time, float... values) {
			transitionImpl.start(time, values);
			return this;
		}

		public TransitionBuilder<T> endObject(float time, T end) {
			transitionImpl.start(time, typeConverter.copyFromObject(end, null));
			return this;
		}

		public TransitionBuilder functions(InterpolationFunction... functions) {
			transitionImpl.setFunctions(functions);
			return this;
		}

		public Transition<T> build() {
			return transitionImpl;
		}

		private TransitionBuilder() {

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
	public static <T> TransitionBuilder<T> transition(T mutableObject, TypeConverter typeConverter) {
		transitionBuilder.setMutableObjectTransition(new TransitionImpl(mutableObject, typeConverter));
		transitionBuilder.setTypeConverter(typeConverter);
		return transitionBuilder;
	}

	/**
	 * Returns a new TransitionBuilder to build mutable object transitions, it internally uses the TypeConverter registered on Converters for the object class.
	 * 
	 * @param mutableObject
	 *            The mutable object to be used in the transition.
	 * @return The TransitionBuilder to build the new transition.
	 */
	public static <T> TransitionBuilder<T> transition(T mutableObject) {
		return transition(mutableObject, (TypeConverter) Converters.converter(mutableObject.getClass()));
	}

}