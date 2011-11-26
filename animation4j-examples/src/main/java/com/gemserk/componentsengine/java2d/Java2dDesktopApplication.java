package com.gemserk.componentsengine.java2d;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.renderstrategy.Java2dRenderStrategy;
import com.gemserk.componentsengine.java2d.renderstrategy.VolatileImageJava2dRenderStrategy;
import com.google.inject.Inject;

public class Java2dDesktopApplication {

	Java2dWindow java2dWindow;
	
	@Inject
	KeyboardInput keyboardInput;
	
	@Inject
	MouseInput mouseInput;
	
	@Inject
	Java2dGame java2dGame;

	boolean exitOnClose;

	public void init(String title, Dimension dimension, boolean exitOnClose) {
		this.exitOnClose = exitOnClose;
		createWindow(title, dimension, java2dGame);
	}
	
	public void start() {
		java2dWindow.start();
	}
	
	public void stop() {
		java2dWindow.stop();
		if (exitOnClose)
			System.exit(0);
	}

	protected void createWindow(String title, Dimension resolution, Java2dGame game) {
		Canvas canvas = new Canvas();

		canvas.addKeyListener(keyboardInput);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		canvas.addMouseWheelListener(mouseInput);

		final JFrame frame = new JFrame(title);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Java2dDesktopApplication.this.stop();
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
