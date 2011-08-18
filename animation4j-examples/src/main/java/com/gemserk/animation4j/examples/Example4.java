package com.gemserk.animation4j.examples;

import static com.gemserk.animation4j.examples.Properties.interpolatedProperty;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.gemserk.animation4j.animations.events.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.java2d.converters.Java2dConverters;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
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
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;
import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.datasources.ClassPathDataSource;
import com.gemserk.resources.java2d.dataloaders.ImageLoader;
import com.gemserk.resources.resourceloaders.CachedResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class Example4 extends Java2dDesktopApplication {

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example4() {
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
		Injector injector = Guice.createInjector(new Java2dModule(), new BasicModule(), new ResourcesManagerModule());
		injector.getInstance(InitJava2dRenderer.class).config();
		Dimension resolution = new Dimension(640, 480);
		ExampleInternalGame game = injector.getInstance(ExampleInternalGame.class);
		createWindow("Example4", resolution, game, injector);
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

		@Inject
		Java2dRenderer java2dRenderer;

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		private Resource<BufferedImage> buttonImageResource;

		private Resource<BufferedImage> buttonGlowImageResource;

		class Button {

			Property<Point2D> position;

			Property<Color> color;

			Property<Color> glowColor;

			Property<Point2D> size;

			Property<Boolean> mouseInside = new SimpleProperty<Boolean>(false);

		}

		ArrayList<Button> buttons = new ArrayList<Button>();

		@Override
		public void init() {
			
			Java2dConverters.init();

			resourceManager.add("Background", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("example4/background.jpg")))));

			resourceManager.add("Button", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("example4/settings-button.png")))));
			resourceManager.add("ButtonGlow", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("example4/settings-button-glow.png")))));

			buttonImageResource = resourceManager.get("Button");
			buttonGlowImageResource = resourceManager.get("ButtonGlow");

			final InterpolationFunction linearInterpolationFunction = InterpolationFunctions.linear();

			buttons.add(new Button() {
				{
					position = interpolatedProperty(Transitions.transitionBuilder(new Point2D.Float(320, 625)).speed(1f).typeConverter(Java2dConverters.point2d()).build());
					size = interpolatedProperty(Transitions.transitionBuilder(new Point2D.Float(1f, 1f)).speed(5f).typeConverter(Java2dConverters.point2d()).build());
					color = interpolatedProperty(Transitions.transitionBuilder(new Color(1f, 1f, 1f, 1f)).speed(5f).build());
					glowColor = interpolatedProperty(Transitions.transitionBuilder(new Color(1f, 0f, 0f, 0f)).speed(2f).build());

					position.set(new Point2D.Float(320, 125));
				}
			});

			buttons.add(new Button() {
				{
					position = interpolatedProperty(Transitions.transitionBuilder(new Point2D.Float(320, 725)).speed(1f).typeConverter(Java2dConverters.point2d()).build());
					size = interpolatedProperty(Transitions.transitionBuilder(new Point2D.Float(1f, 1f)).speed(5f).typeConverter(Java2dConverters.point2d()).build());
					color = interpolatedProperty(Transitions.transitionBuilder(new Color(1f, 1f, 1f, 1f)).speed(5f).build());
					glowColor = interpolatedProperty(Transitions.transitionBuilder(new Color(1f, 0f, 0f, 0f)).speed(2f).build());

					position.set(new Point2D.Float(320, 225));
				}
			});

			buttons.add(new Button() {
				{
					position = interpolatedProperty(Transitions.transitionBuilder(new Point2D.Float(320, 825)).speed(1f).typeConverter(Java2dConverters.point2d()).build());
					size = interpolatedProperty(Transitions.transitionBuilder(new Point2D.Float(1f, 1f)).speed(5f).typeConverter(Java2dConverters.point2d()).build());
					color = interpolatedProperty(Transitions.transitionBuilder(new Color(1f, 1f, 1f, 1f)).speed(5f).build());
					glowColor = interpolatedProperty(Transitions.transitionBuilder(new Color(1f, 0f, 0f, 0f)).speed(2f).build());

					position.set(new Point2D.Float(320, 325));
				}
			});

			backgroundColor = Transitions.transitionBuilder(new Color(0.4f, 0.4f, 0.4f, 0f)).speed(1f).build();
			backgroundColor.set(new Color(0.4f, 0.4f, 0.4f, 0.6f), 1f);
		}

		Transition<Color> backgroundColor;

		@Override
		public void render(Graphics2D graphics) {

			currentGraphicsProvider.setGraphics(graphics);

			Resource<BufferedImage> backgroundResource = resourceManager.get("Background");
			java2dRenderer.render(new Java2dImageRenderObject(0, backgroundResource.get(), 320, 240, 1, 1, 0f));

			graphics.setColor(backgroundColor.get());
			graphics.fillRect(0, 0, 640, 480);

			// render the image using the color of the transition

			for (Button button : buttons) {
				Point2D position = button.position.get();

				Point2D size = button.size.get();

				float x = (float) position.getX();
				float y = (float) position.getY();

				float sx = (float) size.getX();
				float sy = (float) size.getY();

				Color color = button.color.get();

				java2dRenderer.render(new Java2dImageRenderObject(1, buttonImageResource.get(), x, y, sx, sy, 0f, color));
				java2dRenderer.render(new Java2dImageRenderObject(1, buttonGlowImageResource.get(), x, y, sx, sy, 0f, button.glowColor.get()));

			}

		}

		@Override
		public void update(int delta) {

			BufferedImage image = buttonImageResource.get();

			Point mousePosition = mouseInput.getPosition();

			int height = image.getHeight();
			int width = image.getWidth();

			for (Button button : buttons) {

				Point2D position = button.position.get();

				int x = (int) position.getX();
				int y = (int) position.getY();

				if (new Rectangle(x - width / 2, y - height / 2, width, height).contains(mousePosition.x, mousePosition.y)) {
					if (!button.mouseInside.get()) {
						button.mouseInside.set(true);
						// when the mouse is over the image, we set the color to white
						button.color.set(new Color(1f, 1f, 1f, 1f));
						button.glowColor.set(new Color(1f, 0f, 0f, 1f));
						button.size.set(new Point2D.Float(1.05f, 1.05f));
					}
				} else {
					if (button.mouseInside.get()) {
						button.mouseInside.set(false);
						// when the mouse left the image, we set again the color to the previous color.
						button.color.set(new Color(1f, 1f, 1f, 1f));
						button.glowColor.set(new Color(1f, 0f, 0f, 0f));
						button.size.set(new Point2D.Float(1f, 1f));
					}
				}

			}

		}

	}

}
