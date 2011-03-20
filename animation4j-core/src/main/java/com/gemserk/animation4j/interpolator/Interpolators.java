package com.gemserk.animation4j.interpolator;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

public class Interpolators {

	public static Interpolator<Float> floatInterpolator() {
		return interpolator(Converters.floatValue());
	}

	public static Interpolator<Float> floatInterpolator(InterpolatorFunction function) {
		return interpolator(Converters.floatValue(), function);
	}

	public static Interpolator<Point2D> point2dInterpolator() {
		return interpolator(Converters.point2d());
	}

	public static Interpolator<Point2D> point2dInterpolator(InterpolatorFunction... functions) {
		return interpolator(Converters.point2d(), functions);
	}

	public static Interpolator<Color> colorInterpolator() {
		return interpolator(Converters.color());
	}

	public static Interpolator<Color> colorInterpolator(InterpolatorFunction... functions) {
		return interpolator(Converters.color(), functions);
	}

	public static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter) {
		return new GenericInterpolator<T>(typeConverter);
	}

	public static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		return new GenericInterpolator<T>(typeConverter, functions);
	}

}