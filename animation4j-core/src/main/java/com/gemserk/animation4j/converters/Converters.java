package com.gemserk.animation4j.converters;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Provides type converters for common classes like Color and Point2D.
 * @author acoppes
 */
public class Converters {
	
	private static TypeConverter<Float> floatConverter = new TypeConverter<Float>() {

		@Override
		public float[] copyFromObject(Float f, float[] x) {
			if (x == null)
				x = new float[1];
			x[0] = f.floatValue();
			return x;
		}

		@Override
		public Float copyToObject(Float f, float[] x) {
			// as Float is immutable, it will return a new float each time.
			return Float.valueOf(x[0]);
		}
		
	};
	private static TypeConverter<Point2D> point2dConverter = new TypeConverter<Point2D>() {

		@Override
		public float[] copyFromObject(Point2D p, float[] x) {
			if (x == null)
				x = new float[2];
			x[0] = (float) p.getX();
			x[1] = (float) p.getY();
			return x;
		}

		@Override
		public Point2D copyToObject(Point2D p, float[] x) {
			if (p == null)
				p = new Point2D.Float(0f, 0f);
			p.setLocation(x[0], x[1]);
			return p;
		}

	};
	private static TypeConverter<Color> colorConverter = new TypeConverter<Color>() {

		@Override
		public float[] copyFromObject(Color object, float[] x) {
			if (x == null)
				x = new float[4];
			object.getRGBComponents(x);
			return x;
		}

		@Override
		public Color copyToObject(Color object, float[] x) {
			// as color is immutable, it will return a new color each time.
			return new Color(x[0], x[1], x[2], x[3]);
		}

	};

	/**
	 * This converter will be boxing and unboxing from float to Float and vice versa each time a method is called, so it is not recommended to use, 
	 * we recommend you to use a TypeConverter of a FloatValue mutable implementation instead.
	 */
	public static TypeConverter<Float> floatConverter() {
		// not recommended to use, it will be boxing and unboxing from float to Float and vice versa.
		return floatConverter;
	}

	public static TypeConverter<Point2D> point2d() {
		return point2dConverter;
	}

	public static TypeConverter<Color> color() {
		return colorConverter;
	}

}