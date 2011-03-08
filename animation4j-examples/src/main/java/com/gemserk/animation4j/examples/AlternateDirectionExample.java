package com.gemserk.animation4j.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

	private Font textFont;

	private JPanel panel;

	private JEditorPane comp;

	private BufferedImage bufferedImage;
	
	public AlternateDirectionExample() {

		textFont = new Font("Arial", Font.PLAIN, 16);

		animationHandlerManager = new AnimationHandlerManager();
		
		bufferedImage = getImage("critter.png");

		Timeline timeline = new TimelineBuilder() {
			{
				value("x", new TimelineValueBuilder<Float>().keyFrame(0, 150f, new FloatInterpolator(InterpolatorFunctionFactory.easeIn())).keyFrame(1000, 350f));
				value("y", new TimelineValueBuilder<Float>().keyFrame(0, 150f));
				
				value("angle", new TimelineValueBuilder<Float>().keyFrame(0, 0f, new FloatInterpolator(InterpolatorFunctionFactory.easeIn())).keyFrame(1000, (float) Math.PI / 2));
			}
		}.build();

		timelineAnimation = new TimelineAnimation(timeline);
		timelineAnimation.setSpeed(2f);
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

					System.out.println("direction: " + timelineAnimation.getPlayingDirection());
					System.out.println("currentTime: " + timelineAnimation.getCurrentTime());
					System.out.println("iteration: " + timelineAnimation.getIteration());
					System.out.println("duration: " + timelineAnimation.getDuration());

				}

			}

		});

		// panel = new Container() {
		// public void paint(Graphics g) {
		// Component[] components = this.getComponents();
		// for (Component component : components) {
		// component.paint(g);
		// }
		// }
		// };

		String html = getExampleDescription("example1.html");

		panel = new JPanel();
		panel.setSize(640, 120);
		panel.setLayout(new BorderLayout());
		panel.setIgnoreRepaint(true);
		panel.setOpaque(false);

		comp = new JEditorPane("text/html", html);
		comp.setLocation(50, 50);
		comp.setSize(250, 120);
		comp.setForeground(Color.white);
		comp.setOpaque(false);
		comp.setEditable(false);

		panel.add(comp);

		// this.add(comp);

		this.setLayout(new BorderLayout());
		// this.add(panel);

		setIgnoreRepaint(true);

	}

	protected BufferedImage getImage(String imageFileName) {
		try {
			return ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream(imageFileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getExampleDescription(String file) {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);

		StringBuilder stringBuilder = new StringBuilder();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			char[] buffer = new char[8192];

			int read;
			while ((read = bufferedReader.read(buffer, 0, buffer.length)) > 0) {
				stringBuilder.append(buffer, 0, read);
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {

			}
		}

		return "";
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

		if (bf == null)
			return;

		g = bf.getDrawGraphics();

		try {

			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setBackground(Color.black);
			graphics2d.clearRect(0, 0, 640, 480);

			// graphics2d.setFont(textFont);
			// graphics2d.drawString("a", 100, 100);
			try {
				panel.paint(graphics2d);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			
			graphics2d.setColor(Color.white);

			Float xvalue = timelineAnimation.getValue("x");
			Float yvalue = timelineAnimation.getValue("y");

			int x = xvalue.intValue();
			int y = yvalue.intValue();
			
			Float angle = timelineAnimation.getValue("angle");

			AffineTransform pushTransform = graphics2d.getTransform();
			
			AffineTransform tx = new AffineTransform();

			int width = bufferedImage.getWidth(null);
			int height = bufferedImage.getHeight(null);

			float x1 = (float) (x + ((float) -width) / 2f);
			float y1 = (float) (y + ((float) -height) / 2f);

			tx.concatenate(AffineTransform.getTranslateInstance(x1 + width / 2, y1 + height / 2));
			tx.concatenate(AffineTransform.getRotateInstance(angle.doubleValue()));
			tx.concatenate(AffineTransform.getScaleInstance(1, 1));
			tx.concatenate(AffineTransform.getTranslateInstance(-width / 2, -height / 2));
			
			RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2d.setRenderingHints(renderingHints);

			graphics2d.transform(tx);

			graphics2d.drawImage(bufferedImage, 0, 0, null);
			
			graphics2d.setTransform(pushTransform);
			
			// graphics2d.fillOval(x, y, 10, 10);

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
