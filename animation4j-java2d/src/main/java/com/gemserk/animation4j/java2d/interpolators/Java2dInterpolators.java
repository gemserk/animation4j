package com.gemserk.animation4j.java2d.interpolators;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.java2d.converters.Java2dConverters;

public class Java2dInterpolators {

	public static Interpolator<Point2D> point2dInterpolator() {
		return interpolator(Java2dConverters.point2d());
	}

	public static Interpolator<Point2D> point2dInterpolator(InterpolatorFunction... functions) {
		return interpolator(Java2dConverters.point2d(), functions);
	}

	public static Interpolator<Color> colorInterpolator() {
		return interpolator(Java2dConverters.color());
	}

	public static Interpolator<Color> colorInterpolator(InterpolatorFunction... functions) {
		return interpolator(Java2dConverters.color(), functions);
	}

	private static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter) {
		return com.gemserk.animation4j.interpolator.Interpolators.interpolator(typeConverter);
	}

	private static <T> Interpolator<T> interpolator(TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		return com.gemserk.animation4j.interpolator.Interpolators.interpolator(typeConverter, functions);
	}

}