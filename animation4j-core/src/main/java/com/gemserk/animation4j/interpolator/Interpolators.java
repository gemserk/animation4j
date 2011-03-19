package com.gemserk.animation4j.interpolator;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

public class Interpolators {

	public static Interpolator<Float> floatInterpolator() {
		return new CustomInterpolator<Float>(new TypeConverter<Float>() {

			@Override
			public float[] copyFromObject(Float f, float[] x) {
				x[0] = f;
				return x;
			}

			@Override
			public Float copyToObject(Float f, float[] x) {
				return x[0];
			}

		}, new FloatArrayInterpolator(1));
	}

	public static Interpolator<Float> floatInterpolator(InterpolatorFunction function) {

		return new CustomInterpolator<Float>(new TypeConverter<Float>() {

			@Override
			public float[] copyFromObject(Float f, float[] x) {
				x[0] = f;
				return x;
			}

			@Override
			public Float copyToObject(Float f, float[] x) {
				return x[0];
			}

		}, new FloatArrayInterpolator(1, function));

	}

	public static Interpolator<Point2D> point2dInterpolator() {
		return new CustomInterpolator<Point2D>(Converters.point2d(), new FloatArrayInterpolator(2));
	}

	public static Interpolator<Point2D> point2dInterpolator(InterpolatorFunction... functions) {
		return new CustomInterpolator<Point2D>(Converters.point2d(), new FloatArrayInterpolator(2, functions));
	}

	public static Interpolator<Color> colorInterpolator() {
		return new CustomInterpolator<Color>(Converters.color(), new FloatArrayInterpolator(4));
	}

	public static Interpolator<Color> colorInterpolator(InterpolatorFunction... functions) {
		return new CustomInterpolator<Color>(Converters.color(), new FloatArrayInterpolator(4, functions));
	}

}