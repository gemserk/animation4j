package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JEditorPane;

import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.ColorInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.transitions.Transition;
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
import com.gemserk.resources.datasources.ClassPathDataSource;
import com.gemserk.resources.java2d.dataloaders.ImageLoader;
import com.gemserk.resources.resourceloaders.CachedResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class Example3 extends Java2dDesktopApplication {

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example3();
		java2dDesktopApplication.init();
		java2dDesktopApplication.start();
	}

	@Override
	public void init() {
		Injector injector = Guice.createInjector(new Java2dModule(), new BasicModule(), new ResourcesManagerModule());
		injector.getInstance(InitJava2dRenderer.class).config();
		Dimension resolution = new Dimension(640, 480);
		ExampleInternalGame game = injector.getInstance(ExampleInternalGame.class);
		createWindow("Example3", resolution, game, injector);
	}

	public void stop() {
		System.exit(0);
	}

	static class ExampleInternalGame implements Java2dGame {

		@Inject
		KeyboardInput keyboardInput;

		@Inject
		MouseInput mouseInput;

		@SuppressWarnings("rawtypes")
		@Inject
		ResourceManager resourceManager;

		@Inject
		AnimationHandlerManager animationHandlerManager;

		Resource<Image> buttonImageResource;

		@Override
		public void init() {

			resourceManager.add("House", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("house-128x92.png")))));
			houseImageResource = resourceManager.get("House");
			creditsPane = new JEditorPane("text/html", new FileHelper("license-lostgarden.html").read()) {
				{
					setSize(600, 40);
					setEditable(false);
					setOpaque(false);
				}
			};
			textPane = new JEditorPane("text/html", new FileHelper("example3.html").read()) {
				{
					setSize(600, 240);
					setEditable(false);
					setOpaque(false);
				}
			};

			// Creates a Color transition using a color interpolator with a linear interpolation function.
			InterpolatorFunction linearInterpolationFunction = InterpolatorFunctionFactory.linear();
			colorTransition = new Transition<Color>(new Color(0.3f, 0.3f, 0.8f, 1f), new ColorInterpolator(linearInterpolationFunction));
		}

		@Inject
		Java2dRenderer java2dRenderer;

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		private Resource<Image> houseImageResource;

		private Transition<Color> colorTransition;

		@Override
		public void render(Graphics2D graphics) {
			graphics.setBackground(Color.black);
			graphics.clearRect(0, 0, 800, 600);

			currentGraphicsProvider.setGraphics(graphics);

			Color color = colorTransition.get();

			java2dRenderer.render(new Java2dImageRenderObject(1, houseImageResource.get(), 320, 340, 1, 1, 0f, color));

			AffineTransform previousTransform = graphics.getTransform();
			graphics.translate(40, 20);
			textPane.paint(graphics);
			graphics.setTransform(previousTransform);

			previousTransform = graphics.getTransform();
			graphics.translate(20, 400);
			creditsPane.paint(graphics);
			graphics.setTransform(previousTransform);
		}

		boolean mouseInside = false;

		private JEditorPane textPane;

		private JEditorPane creditsPane;

		@Override
		public void update(int delta) {

			colorTransition.update(delta);

			Point mousePosition = mouseInput.getPosition();

			if (new Rectangle(320 - 128, 340 - 88, 256, 176).contains(mousePosition.x, mousePosition.y)) {
				if (!mouseInside) {
					mouseInside = true;
					colorTransition.set(new Color(1f, 1f, 1f, 1f), 400);
				}
			} else {
				if (mouseInside) {
					mouseInside = false;
					colorTransition.set(new Color(0.3f, 0.3f, 0.8f, 1f), 400);
				}
			}

		}

	}

}
