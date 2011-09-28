package com.gemserk.componentsengine.java2d;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JApplet;

import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.renderstrategy.Java2dRenderStrategy;
import com.gemserk.componentsengine.java2d.renderstrategy.VolatileImageJava2dRenderStrategy;
import com.google.inject.Injector;

public class Java2dAppletApplication extends JApplet {

	private static final long serialVersionUID = -5957028678575191089L;

	private Java2dWindow java2dWindow;

	protected void createWindow(String title, Dimension resolution, Java2dGame game, Injector injector) {
		KeyboardInput keyboardInput = injector.getInstance(KeyboardInput.class);
		MouseInput mouseInput = injector.getInstance(MouseInput.class);

		Canvas canvas = new Canvas();

		canvas.setSize(resolution);

		this.setLayout(new BorderLayout());
		add(canvas);

		canvas.addKeyListener(keyboardInput);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		canvas.addMouseWheelListener(mouseInput);

		Java2dRenderStrategy renderStrategy = new VolatileImageJava2dRenderStrategy(canvas);

		java2dWindow = new Java2dWindow(game);
		java2dWindow.setKeyboardInput(keyboardInput);
		java2dWindow.setMouseInput(mouseInput);
		java2dWindow.setCanvas(canvas);
		java2dWindow.setRenderStrategy(renderStrategy);

	}

	@Override
	public void start() {
		super.start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				java2dWindow.start();
			}

		}).start();
	}

	@Override
	public void stop() {
		super.stop();
		java2dWindow.stop();
	}

}
