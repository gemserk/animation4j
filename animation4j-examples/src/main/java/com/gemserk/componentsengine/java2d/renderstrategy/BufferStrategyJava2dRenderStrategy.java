package com.gemserk.componentsengine.java2d.renderstrategy;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.gemserk.componentsengine.java2d.Java2dGame;

/**
 * @deprecated it is not working right (on linux at least).
 */
public class BufferStrategyJava2dRenderStrategy implements Java2dRenderStrategy {

	private final Canvas canvas;

	public BufferStrategyJava2dRenderStrategy(Canvas canvas) {
		this.canvas = canvas;
		this.canvas.createBufferStrategy(2);
	}

	@Override
	public void render(Java2dGame game) {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics2D graphics2d = (Graphics2D) bufferStrategy.getDrawGraphics();

		game.render(graphics2d);

		bufferStrategy.show();
		graphics2d.dispose();
		graphics2d = null;
	}

}