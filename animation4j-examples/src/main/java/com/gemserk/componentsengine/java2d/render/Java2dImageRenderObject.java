package com.gemserk.componentsengine.java2d.render;

import java.awt.Color;
import java.awt.Image;
import java.awt.geom.Point2D;

public class Java2dImageRenderObject extends Java2dRenderObject {

	private final Image image;

	private final Point2D position;

	private final double theta;

	private final Point2D size;
	
	private final Color color;

	public Point2D getPosition() {
		return position;
	}

	public Point2D getSize() {
		return size;
	}

	public Image getImage() {
		return image;
	}

	public double getTheta() {
		return theta;
	}
	
	public Color getColor() {
		return color;
	}

	/**
	 * @param layer
	 * @param image
	 * @param x
	 * @param y
	 * @param sx horizontal scale value
	 * @param sy vertical scale value
	 * @param theta
	 */
	public Java2dImageRenderObject(int layer, Image image, float x, float y, float sx, float sy, double theta) {
		this(layer, image, x, y, sx, sy, theta, Color.white);
	}
	
	/**
	 * @param layer
	 * @param image
	 * @param x
	 * @param y
	 * @param sx horizontal scale value
	 * @param sy vertical scale value
	 * @param theta
	 */
	public Java2dImageRenderObject(int layer, Image image, float x, float y, float sx, float sy, double theta, Color color) {
		super(layer);
		this.image = image;
		this.position = new Point2D.Float(x, y);
		this.size = new Point2D.Float(sx, sy);
		this.theta = theta;
		this.color = color;
	}
}