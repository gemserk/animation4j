package com.gemserk.animation4j.examples;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JEditorPane;

import com.gemserk.animation4j.animations.events.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.componentsengine.java2d.Java2dDesktopApplication;
import com.gemserk.componentsengine.java2d.Java2dGame;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.java2d.render.Java2dImageRenderObject;
import com.gemserk.componentsengine.java2d.render.Java2dRenderer;
import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.ResourceManagerImpl;
import com.gemserk.resources.datasources.ClassPathDataSource;
import com.gemserk.resources.java2d.dataloaders.ImageLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class Example3 extends Java2dDesktopApplication {

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example3() {
			@Override
			public void stop() {
				super.stop();
				System.exit(0);
			}
		};
		java2dDesktopApplication.init();
		java2dDesktopApplication.start();
	}

	@Override
	public void init() {

		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(ResourceManager.class).to(ResourceManagerImpl.class).in(Singleton.class);
				bind(CurrentGraphicsProvider.class).in(Singleton.class);
				bind(KeyboardInput.class).in(Singleton.class);
				bind(MouseInput.class).in(Singleton.class);
			}
		});

		Dimension resolution = new Dimension(640, 480);
		ExampleInternalGame game = injector.getInstance(ExampleInternalGame.class);
		createWindow("Example3", resolution, game, injector);
	}

	static class ExampleInternalGame implements Java2dGame {

		@Inject
		KeyboardInput keyboardInput;

		@Inject
		MouseInput mouseInput;

		@Inject
		ResourceManager resourceManager;

		@Inject
		AnimationHandlerManager animationHandlerManager;

		Resource<Image> buttonImageResource;

		@Inject
		Java2dRenderer java2dRenderer;

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		private Resource<Image> houseImageResource;

		private Transition<Color> colorTransition;

		boolean mouseInside = false;

		private JEditorPane textPane;

		private JEditorPane creditsPane;

		@Override
		public void init() {

			resourceManager.add("House", new ImageLoader(new ClassPathDataSource("house-128x92.png")));

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
			InterpolationFunction[] functions = { InterpolationFunctions.linear() };

			// Creates a Color transition using a color interpolator with a linear interpolation function.

			colorTransition = Transitions.mutableTransition(new Color(0.3f, 0.3f, 0.8f, 1f), new ColorConverter()) //
					.functions(functions) //
					.build();
		}

		@Override
		public void render(Graphics2D graphics) {
			graphics.setBackground(java.awt.Color.black);
			graphics.clearRect(0, 0, 800, 600);
			currentGraphicsProvider.setGraphics(graphics);

			{
				// render the image using the color of the transition
				Color c = colorTransition.get();
				java.awt.Color color = new java.awt.Color(c.r, c.g, c.b, c.a);
				java2dRenderer.render(new Java2dImageRenderObject(1, houseImageResource.get(), 320, 340, 1, 1, 0f, color));
			}

			{
				// render texts in the screen
				AffineTransform previousTransform = graphics.getTransform();
				graphics.translate(40, 20);
				textPane.paint(graphics);
				graphics.setTransform(previousTransform);

				previousTransform = graphics.getTransform();
				graphics.translate(20, 400);
				creditsPane.paint(graphics);
				graphics.setTransform(previousTransform);
			}
		}

		@Override
		public void update(int delta) {

			colorTransition.update(0.001f * (float) delta);

			Point mousePosition = mouseInput.getPosition();
			if (new Rectangle(320 - 64, 340 - 46, 128, 92).contains(mousePosition.x, mousePosition.y)) {
				if (!mouseInside) {
					mouseInside = true;

					// when the mouse is over the image, we set the color to white
					colorTransition.set(new Color(1f, 1f, 1f, 1f), 0.6f);
				}
			} else {
				if (mouseInside) {
					mouseInside = false;

					// when the mouse left the image, we set again the color to the previous color.
					colorTransition.set(new Color(0.3f, 0.3f, 0.8f, 1f), 0.6f);
				}
			}

		}

	}

}
