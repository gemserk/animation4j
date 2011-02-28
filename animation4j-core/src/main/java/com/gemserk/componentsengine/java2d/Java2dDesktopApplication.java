package com.gemserk.componentsengine.java2d;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Java2dDesktopApplication {

	private Java2dWindow java2dWindow;

	public void init() {

	}
	
	public void start() {
		java2dWindow.start();
	}
	
	public void stop() {
		java2dWindow.stop();
	}

	protected void createWindow(String title, Dimension resolution, Java2dGame game, KeyboardInput keyboardInput, MouseInput mouseInput) {

		Canvas canvas = new Canvas();

		canvas.addKeyListener(keyboardInput);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		canvas.addMouseWheelListener(mouseInput);

		Frame frame = new Frame(title);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Java2dDesktopApplication.this.stop();
				System.exit(0);
			}
		});

		frame.add(canvas);
		frame.pack();
		frame.setSize(resolution);
		frame.setVisible(true);

		frame.addKeyListener(keyboardInput);
		frame.addMouseListener(mouseInput);
		frame.addMouseMotionListener(mouseInput);
		frame.addMouseWheelListener(mouseInput);

		// canvas.createBufferStrategy(2);
		// Java2dRenderStrategy renderStrategy = new BufferStrategyJava2dRenderStrategy(canvas);

		Java2dRenderStrategy renderStrategy = new VolatileImageJava2dRenderStrategy(canvas);
		
		java2dWindow = new Java2dWindow(game);
		java2dWindow.setKeyboardInput(keyboardInput);
		java2dWindow.setMouseInput(mouseInput);
		java2dWindow.setCanvas(canvas);
		java2dWindow.setRenderStrategy(renderStrategy);

	}

}
