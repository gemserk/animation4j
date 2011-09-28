package com.gemserk.componentsengine.java2d.renderstrategy;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.VolatileImage;

import com.gemserk.componentsengine.java2d.Java2dGame;

public class VolatileImageJava2dRenderStrategy implements Java2dRenderStrategy {

	private final Canvas canvas;

	private VolatileImage backBufferImage = null;

	public VolatileImageJava2dRenderStrategy(Canvas canvas) {
		this.canvas = canvas;
		GraphicsConfiguration gc = this.canvas.getGraphicsConfiguration();
		createBackBufferImage(gc);
	}

	@Override
	public void render(Java2dGame game) {
		GraphicsConfiguration gc = canvas.getGraphicsConfiguration();

		int validated = backBufferImage.validate(gc);
		if (validated == VolatileImage.IMAGE_INCOMPATIBLE) {
			createBackBufferImage(gc);
		}

		game.render((Graphics2D) backBufferImage.getGraphics());

		Graphics graphics = canvas.getGraphics();
		if (graphics!=null)
			graphics.drawImage(backBufferImage, 0, 0, null);
	}

	private void createBackBufferImage(GraphicsConfiguration gc) {
		if (backBufferImage == null)
			backBufferImage = gc.createCompatibleVolatileImage(canvas.getWidth(), canvas.getHeight());
	}

}