package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.CustomInterpolator;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;

public class Interpolators {

	public static Interpolator<Point2D> point2dInterpolator() {

		// the type converter could be reused if it returns a new point each call or if it uses a pool of objects, else
		// use a new type converter for each new transition

		return new CustomInterpolator<Point2D>(new TypeConverter<Point2D>() {

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

		}, new FloatArrayInterpolator(2));
	}

	public static Interpolator<Color> colorInterpolator() {
		return new CustomInterpolator<Color>(new TypeConverter<Color>() {

			@Override
			public float[] copyFromObject(Color object, float[] x) {
				object.getRGBComponents(x);
				return x;
			}

			@Override
			public Color copyToObject(Color object, float[] x) {
				return new Color(x[0], x[1], x[2], x[3]);
			}
			
		}, new FloatArrayInterpolator(4));
	}

}