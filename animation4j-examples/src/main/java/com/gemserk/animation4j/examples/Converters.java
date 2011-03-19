package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.TypeConverter;

public class Converters {

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