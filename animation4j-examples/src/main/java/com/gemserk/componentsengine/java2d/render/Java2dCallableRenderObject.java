package com.gemserk.componentsengine.java2d.render;

import java.awt.Graphics2D;

public abstract class Java2dCallableRenderObject {

	int layer;

	public abstract void execute(Graphics2D graphics, Graphics2dHelper graphicsHelper);

	public Java2dCallableRenderObject(int layer) {
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}

}