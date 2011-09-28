package com.gemserk.componentsengine.java2d.render;

public abstract class Java2dRenderObject {

	int layer;

	public Java2dRenderObject(int layer) {
		this.layer = layer;
	}

	public int getLayer() {
		return layer;
	}

}