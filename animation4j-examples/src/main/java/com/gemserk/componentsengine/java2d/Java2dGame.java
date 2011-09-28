package com.gemserk.componentsengine.java2d;

import java.awt.Graphics2D;

public interface Java2dGame {
	
	void init();

	void render(Graphics2D graphics2d);

	void update(int delta);
	
}