package com.gemserk.animation4j.interpolator;

import java.awt.geom.Point2D;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;

public class Point2DInterpolator implements Interpolator<Point2D> {

	private final InterpolatorFunction interpolatorFunction;

	public Point2DInterpolator() {
		this(InterpolatorFunctionFactory.linear());
	}

	public Point2DInterpolator(InterpolatorFunction interpolatorFunction) {
		this.interpolatorFunction = interpolatorFunction;
	}

	@Override
	public Point2D interpolate(Point2D a, Point2D b, Point2D out, float t) {
		float x = interpolatorFunction.interpolate(t);
		double newx = (1 - x) * a.getX() + x * b.getX();
		double newy = (1 - x) * a.getY() + x * b.getY();
		return new Point2D.Float((float) newx, (float) newy);
	}

}