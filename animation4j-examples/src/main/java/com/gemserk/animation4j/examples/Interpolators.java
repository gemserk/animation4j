package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.interpolator.ColorInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.Point2DInterpolator;

public class Interpolators {
	 
	public static Interpolator<Point2D> point2dInterpolator() {
		return new Point2DInterpolator();
	}
	
	public static Interpolator<Color> colorInterpolator() {
		return new ColorInterpolator();
	}
	
}