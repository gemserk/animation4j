package com.gemserk.componentsengine.java2d.render;
import java.awt.Graphics2D;

public class CurrentGraphicsProvider {
	
	Graphics2D graphics;
	
	public Graphics2D getGraphics() {
		return graphics;
	}
	
	public void setGraphics(Graphics2D graphics) {
		this.graphics = graphics;
	}
	
}