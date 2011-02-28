package com.gemserk.componentsengine.java2d;

import java.awt.Canvas;

public class Java2dWindow {

	KeyboardInput keyboardInput;

	MouseInput mouseInput;
	
	Canvas canvas;
	
	Java2dRenderStrategy renderStrategy;
	
	public void setKeyboardInput(KeyboardInput keyboardInput) {
		this.keyboardInput = keyboardInput;
	}
	
	public void setMouseInput(MouseInput mouseInput) {
		this.mouseInput = mouseInput;
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void setRenderStrategy(Java2dRenderStrategy renderStrategy) {
		this.renderStrategy = renderStrategy;
	}

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public KeyboardInput getKeyboardInput() {
		return keyboardInput;
	}

	private Java2dGame java2dGame;

	public Java2dWindow(Java2dGame java2dGame) {
		this.java2dGame = java2dGame;
	}

	long fps = 0;

	private Boolean done;

	public long getFrameTime() {
		return 1000 / getFramesPerSecond();
	}

	public long getFramesPerSecond() {
		return fps;
	}
	
	public void start() {

		java2dGame.init();

		done = false;

		long frames = 0;
		long time = 0;

		double t = 0.0;
		final double dt = 0.01;

		double currentTime = 0.001 * System.currentTimeMillis();
		double accumulator = dt;

		while (!done) {

			double newTime = 0.001 * System.currentTimeMillis();
			double frameTime = newTime - currentTime;
			if (frameTime > 0.25)
				frameTime = 0.25;
			currentTime = newTime;

			accumulator += frameTime;

			while (accumulator >= dt) {

				// ¿should input polling be inside the while of the accumulator?
				keyboardInput.poll();
				mouseInput.poll();

				// previousState = currentState;
				// integrate ( currentState, t, dt )
				java2dGame.update((int) (dt * 1000));

				t += dt;
				accumulator -= dt;
			}

			// double alpha = accumulator / dt;

			// State state = currentState*alpha + previousState * ( 1.0 - alpha );

			// render( state ); using interpolated state

			renderStrategy.render(java2dGame);

			frames++;
			time += frameTime * 1000;

			if (time >= 1000) {
				time -= 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}

		}

	}

	public void stop() {
		done = true;
	}

}