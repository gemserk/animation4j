package com.gemserk.componentsengine.java2d.render;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Stack;

import com.google.inject.Inject;

public class Graphics2dHelper {

	private CurrentGraphicsProvider currentGraphicsProvider;

	private Stack<AffineTransform> transformStack = new Stack<AffineTransform>();

	@Inject
	public void setCurrentGraphicsProvider(CurrentGraphicsProvider currentGraphicsProvider) {
		this.currentGraphicsProvider = currentGraphicsProvider;
	}

	protected Graphics2D getGraphics() {
		return currentGraphicsProvider.getGraphics();
	}

	public void pushTransform() {
		transformStack.push(getGraphics().getTransform());
	}

	public void popTransform() {
		getGraphics().setTransform(transformStack.pop());
	}
}