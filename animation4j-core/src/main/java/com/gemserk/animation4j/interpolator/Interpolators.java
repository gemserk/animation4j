package com.gemserk.animation4j.interpolator;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

public class Interpolators {

	public static Interpolator<Float> floatInterpolator() {
		return interpolator(1, Converters.floatValue());
	}

	public static Interpolator<Float> floatInterpolator(InterpolatorFunction function) {
		return interpolator(1, Converters.floatValue(), function);
	}

	public static Interpolator<Point2D> point2dInterpolator() {
		return interpolator(2, Converters.point2d());
	}

	public static Interpolator<Point2D> point2dInterpolator(InterpolatorFunction... functions) {
		return interpolator(2, Converters.point2d(), functions);
	}

	public static Interpolator<Color> colorInterpolator() {
		return interpolator(4, Converters.color());
	}

	public static Interpolator<Color> colorInterpolator(InterpolatorFunction... functions) {
		return interpolator(4, Converters.color(), functions);
	}

	public static <T> Interpolator<T> interpolator(int variables, TypeConverter<T> typeConverter) {
		Interpolator<float[]> variablesInterpolator = new FloatArrayInterpolator(variables);
		return new GenericInterpolator<T>(typeConverter, variablesInterpolator);
	}

	public static <T> Interpolator<T> interpolator(int variables, TypeConverter<T> typeConverter, InterpolatorFunction... functions) {
		Interpolator<float[]> variablesInterpolator = new FloatArrayInterpolator(variables, functions);
		return new GenericInterpolator<T>(typeConverter, variablesInterpolator);
	}
	
}