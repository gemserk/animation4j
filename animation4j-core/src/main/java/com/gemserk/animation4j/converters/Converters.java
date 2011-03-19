package com.gemserk.animation4j.converters;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Provides type converters for common classes like Color and Point2D. 
 * @author acoppes
 */
public class Converters {
	
	public static TypeConverter<Float> floatConverter() {
		
		// not recommended to use, it will be boxing and unboxing from float to Float and vice versa. 
		
		return new TypeConverter<Float>() {
			@Override
			public float[] copyFromObject(Float f, float[] x) {
				x[0] = f.floatValue();
				return x;
			}

			@Override
			public Float copyToObject(Float f, float[] x) {
				return Float.valueOf(x[0]);
			}
		};
		
	}

	public static TypeConverter<Point2D> point2d() {

		// the type converter could be reused if it returns a new point each call or if it uses a pool of objects, else
		// use a new type converter for each new transition

		return new TypeConverter<Point2D>() {

			Point2D tmp = new Point2D.Float(0f, 0f);

			@Override
			public float[] copyFromObject(Point2D p, float[] x) {
				x[0] = (float) p.getX();
				x[1] = (float) p.getY();
				return x;
			}

			@Override
			public Point2D copyToObject(Point2D p, float[] x) {
				if (p == null)
					p = tmp;
				p.setLocation(x[0], x[1]);
				return p;
			}

		};
	}

	public static TypeConverter<Color> color() {
		
		// in this case, as color is not mutable, we have to return a new color each time. 
		
		return new TypeConverter<Color>() {

			@Override
			public float[] copyFromObject(Color object, float[] x) {
				object.getRGBComponents(x);
				return x;
			}

			@Override
			public Color copyToObject(Color object, float[] x) {
				return new Color(x[0], x[1], x[2], x[3]);
			}
			
		};
	}

}