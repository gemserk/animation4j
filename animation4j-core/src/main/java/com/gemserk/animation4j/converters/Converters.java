package com.gemserk.animation4j.converters;


/**
 * Provides type converters for common java2d classes like Color and Point2D.
 * @author acoppes
 */
public class Converters {
	
	private static TypeConverter<Float> floatConverter = new TypeConverter<Float>() {

		@Override
		public float[] copyFromObject(Float f, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = f.floatValue();
			return x;
		}

		@Override
		public Float copyToObject(Float f, float[] x) {
			// as Float is immutable, it will return a new float each time.
			return Float.valueOf(x[0]);
		}

		@Override
		public int variables() {
			return 1;
		}
		
	};

	/**
	 * This converter will be boxing and unboxing from float to Float and vice versa each time a method is called, so it is not recommended to use, 
	 * we recommend you to use a TypeConverter of a FloatValue mutable implementation instead.
	 */
	public static TypeConverter<Float> floatValue() {
		// not recommended to use, it will be boxing and unboxing from float to Float and vice versa.
		return floatConverter;
	}

}