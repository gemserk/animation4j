package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.event.AnimationEventHandler;
import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.timeline.Timeline;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;

public class AlternateDirectionExample extends JFrame {

	private final class DumpAnimationStateHandler extends AnimationEventHandler {
		public void onAnimationStarted(Animation animation) {
			System.out.println("animation started");
		}

		@Override
		public void onAnimationFinished(Animation animation) {
			System.out.println("animation finished");
		}

		@Override
		public void onIterationChanged(Animation animation) {
			System.out.println("animation iteration changed");
		}
	}

	private static final long serialVersionUID = -730040543509574215L;

	public static void main(String[] args) {
		AlternateDirectionExample testApplication1 = new AlternateDirectionExample();
		testApplication1.setSize(640, 480);
		testApplication1.setVisible(true);
		testApplication1.createBufferStrategy(2);
		testApplication1.gameLoop();
	}

	private TimelineAnimation timelineAnimation;

	private long currentTime;

	private boolean done = false;

	private AnimationHandlerManager animationHandlerManager;

	public AlternateDirectionExample() {

		animationHandlerManager = new AnimationHandlerManager();

		Timeline timeline = new TimelineBuilder() {
			{
				value("x", new TimelineValueBuilder<Float>().keyFrame(0, 150f, new FloatInterpolator(InterpolatorFunctionFactory.easeIn())).keyFrame(1000, 350f));
				value("y", new TimelineValueBuilder<Float>().keyFrame(0, 150f));
			}
		}.build();

		timelineAnimation = new TimelineAnimation(timeline);
		timelineAnimation.setSpeed(1f);
		timelineAnimation.start(2, true);

		animationHandlerManager.with(new DumpAnimationStateHandler()).handleChangesOf(timelineAnimation);

		currentTime = System.currentTimeMillis();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("closing");
				done = true;
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				System.out.println("closed");
				done = true;
				System.exit(0);
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					timelineAnimation.restart();
					animationHandlerManager.with(new DumpAnimationStateHandler()).handleChangesOf(timelineAnimation);
				}
				
				if (e.getKeyCode() == KeyEvent.VK_D) {

					System.out.println("direction: " + timelineAnimation.getDirection());
					System.out.println("currentTime: " + timelineAnimation.getCurrentTime());
					System.out.println("iteration: " + timelineAnimation.getIteration());
					System.out.println("duration: " + timelineAnimation.getDuration());

				}

			}

		});

	}

	public void gameLoop() {
		while (!done) {
			long delta = System.currentTimeMillis() - currentTime;
			timelineAnimation.update(delta);
			animationHandlerManager.checkAnimationChanges();
			drawStuff();
			currentTime = System.currentTimeMillis();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void drawStuff() {
		BufferStrategy bf = this.getBufferStrategy();
		Graphics g = null;
		g = bf.getDrawGraphics();

		try {

			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setBackground(Color.black);
			graphics2d.clearRect(0, 0, 640, 480);

			graphics2d.setColor(Color.white);

			Float xvalue = timelineAnimation.getValue("x");
			Float yvalue = timelineAnimation.getValue("y");

			int x = xvalue.intValue();
			int y = yvalue.intValue();

			graphics2d.fillOval(x, y, 10, 10);

		} finally {
			// It is best to dispose() a Graphics object when done with it.
			g.dispose();
		}

		// Shows the contents of the backbuffer on the screen.
		bf.show();

		// Tell the System to do the Drawing now, otherwise it can take a few extra ms until
		// Drawing is done which looks very jerky
		Toolkit.getDefaultToolkit().sync();

	}

}
