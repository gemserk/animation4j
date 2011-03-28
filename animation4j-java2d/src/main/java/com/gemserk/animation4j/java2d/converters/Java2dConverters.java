package com.gemserk.animation4j.java2d.converters;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;

/**
 * Provides type converters for common java2d classes like Color and Point2D.
 * @author acoppes
 */
public class Java2dConverters {

	private static TypeConverter<Point2D> point2dConverter = new TypeConverter<Point2D>() {

		@Override
		public float[] copyFromObject(Point2D p, float[] x) {
			if (x == null)
				x = new float[variables()];
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

		@Override
		public int variables() {
			return 2;
		}

	};
	private static TypeConverter<Color> colorConverter = new TypeConverter<Color>() {

		@Override
		public float[] copyFromObject(Color object, float[] x) {
			if (x == null)
				x = new float[variables()];
			object.getRGBComponents(x);
			return x;
		}

		@Override
		public Color copyToObject(Color object, float[] x) {
			// as color is immutable, it will return a new color each time.
			return new Color(x[0], x[1], x[2], x[3]);
		}

		@Override
		public int variables() {
			return 4;
		}

	};

	public static TypeConverter<Point2D> point2d() {
		return point2dConverter;
	}

	public static TypeConverter<Color> color() {
		return colorConverter;
	}
	
	/**
	 * Registers common converters for Java2d classes to Converters class.
	 */
	public static void init() {
		Converters.register(Point.class, point2dConverter);
		Converters.register(Point2D.class, point2dConverter);
		Converters.register(Color.class, colorConverter);
	}

}