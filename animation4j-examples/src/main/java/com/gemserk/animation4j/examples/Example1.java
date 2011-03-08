package com.gemserk.animation4j.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;
import com.gemserk.componentsengine.java2d.Java2dDesktopApplication;
import com.gemserk.componentsengine.java2d.Java2dGame;
import com.gemserk.componentsengine.java2d.Java2dModule;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.java2d.render.InitJava2dRenderer;
import com.gemserk.componentsengine.java2d.render.Java2dImageRenderObject;
import com.gemserk.componentsengine.java2d.render.Java2dRenderer;
import com.gemserk.componentsengine.modules.BasicModule;
import com.gemserk.componentsengine.modules.ResourcesManagerModule;
import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.dataloaders.DataLoader;
import com.gemserk.resources.resourceloaders.CachedResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class Example1 extends Java2dDesktopApplication {

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example1();
		java2dDesktopApplication.init();
		java2dDesktopApplication.start();
	}

	@Override
	public void init() {
		Injector injector = Guice.createInjector(new Java2dModule(), new BasicModule(), new ResourcesManagerModule());
		injector.getInstance(InitJava2dRenderer.class).config();
		Dimension resolution = new Dimension(800, 480);
		Example1Game game = injector.getInstance(Example1Game.class);
		createWindow("Example1", resolution, game, injector);
	}
	
	static class Example1Game implements Java2dGame {
		
		@Inject
		KeyboardInput keyboardInput;
		
		@Inject
		MouseInput mouseInput;
		

		@SuppressWarnings("rawtypes")
		@Inject
		ResourceManager resourceManager;

		Resource<Image> critterImageResource;

		@Override
		public void init() {
			
			resourceManager.add("Critter", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new DataLoader<Image>() {

				@Override
				public Image load() {
					try {
						return ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("critter.png"));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			})));
			
			critterImageResource = resourceManager.get("Critter");
			
			animation = new TimelineAnimationBuilder() {
				{
					speed(2f);
					started(false);

					value("x", new TimelineValueBuilder<Float>().keyFrame(0, 150f, new FloatInterpolator(InterpolatorFunctionFactory.easeIn())).keyFrame(1000, 350f));
					value("y", new TimelineValueBuilder<Float>().keyFrame(0, 300f));
					value("angle", new TimelineValueBuilder<Float>().keyFrame(0, 0f, new FloatInterpolator(InterpolatorFunctionFactory.easeIn())).keyFrame(1000, (float) Math.PI / 2));
				}
			}.build();

			animation.start(2, true);
			
			String html = new FileHelper("example1.html").read();
			
			panel = new JPanel();
			panel.setSize(800, 480);
			panel.setLayout(new BorderLayout());
			panel.setIgnoreRepaint(true);
			panel.setOpaque(false);

			JEditorPane comp = new JEditorPane("text/html", html);
			comp.setLocation(10, 10);
			comp.setSize(600, 420);
			comp.setForeground(Color.white);
			comp.setOpaque(false);
			comp.setEditable(false);
			
			panel.add(comp);
			
		}
		
		@Inject
		Java2dRenderer java2dRenderer;
		
		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;
		
		private TimelineAnimation animation;

		private JPanel panel;

		@Override
		public void render(Graphics2D graphics) {
			graphics.setBackground(Color.blue);
			graphics.clearRect(0, 0, 800, 600);
			
			Float x = animation.getValue("x");
			Float y = animation.getValue("y");
			Float angle = animation.getValue("angle");
			
			currentGraphicsProvider.setGraphics(graphics);
			java2dRenderer.render(new Java2dImageRenderObject(1, critterImageResource.get(), x, y, 1, 1, angle));
			
			panel.paint(graphics);
			
		}

		@Override
		public void update(int delta) {
			
			animation.update(delta);
			if (keyboardInput.keyDownOnce(KeyEvent.VK_ENTER)) {
				animation.restart();
			}
		}
		
	}
	
}

