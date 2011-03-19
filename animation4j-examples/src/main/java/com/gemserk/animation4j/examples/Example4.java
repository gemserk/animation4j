package com.gemserk.animation4j.examples;

import static com.gemserk.animation4j.examples.Interpolators.colorInterpolator;
import static com.gemserk.animation4j.examples.Properties.interpolatedProperty;
import static com.gemserk.animation4j.examples.Transitions.transition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.ColorInterpolator;
import com.gemserk.animation4j.interpolator.CustomInterpolator;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.interpolator.TypeConverter;
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

			resourceManager.add("Background", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("example4/background.jpg")))));

			resourceManager.add("Button", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("example4/settings-button.png")))));
			resourceManager.add("ButtonGlow", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("example4/settings-button-glow.png")))));

			buttonImageResource = resourceManager.get("Button");
			buttonGlowImageResource = resourceManager.get("ButtonGlow");

			final InterpolatorFunction linearInterpolationFunction = InterpolatorFunctionFactory.linear();

//			final CustomInterpolator<Point2D> point2dInterpolator = new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2));

			// final Interpolator<Point2D> point2dInterpolator = new Point2DInterpolator();

			buttons.add(new Button() {
				{
					position = interpolatedProperty(transition(new Point2D.Float(320, 625), new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2)), 0.001f));
					size = interpolatedProperty(transition(new Point2D.Float(1f, 1f), new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2)), 0.005f));
					color = interpolatedProperty(transition(new Color(1f, 1f, 1f, 1f), colorInterpolator(), 0.005f));
					glowColor = interpolatedProperty(transition(new Color(1f, 0f, 0f, 0f), colorInterpolator(), 0.002f));

					position.set(new Point2D.Float(320, 125));
				}
			});

			buttons.add(new Button() {
				{
					position = interpolatedProperty(transition(new Point2D.Float(320, 725), new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2)), 0.001f));
					size = interpolatedProperty(transition(new Point2D.Float(1f, 1f), new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2)), 0.005f));
					color = interpolatedProperty(transition(new Color(1f, 1f, 1f, 1f), new ColorInterpolator(), 0.005f));
					glowColor = interpolatedProperty(transition(new Color(1f, 0f, 0f, 0f), new ColorInterpolator(), 0.002f));

					position.set(new Point2D.Float(320, 225));
				}
			});

			buttons.add(new Button() {
				{
					position = interpolatedProperty(transition(new Point2D.Float(320, 825), new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2)), 0.001f));
					size = interpolatedProperty(transition(new Point2D.Float(1f, 1f), new CustomInterpolator<Point2D>(createPoint2dConverter(), new FloatArrayInterpolator(2)), 0.005f));
					color = interpolatedProperty(transition(new Color(1f, 1f, 1f, 1f), new ColorInterpolator(), 0.005f));
					glowColor = interpolatedProperty(transition(new Color(1f, 0f, 0f, 0f), new ColorInterpolator(), 0.002f));

					position.set(new Point2D.Float(320, 325));
				}
			});

			backgroundColor = transition(new Color(0.4f, 0.4f, 0.4f, 0f), colorInterpolator(), 0.001f);
			backgroundColor.set(new Color(0.4f, 0.4f, 0.4f, 0.6f), 1000);
		}

		protected TypeConverter<Point2D> createPoint2dConverter() {
			return new TypeConverter<Point2D>() {

				Point2D tmp = new Point2D.Float(0f, 0f);

				@Override
				public float[] copyFromObject(Point2D p, float[] x) {
					x[0] = (float) p.getX();
					x[1] = (float) p.getY();
					return x;
				}

				@Override
				public Point2D copyToObject(Point2D p, float[] x) {
					if (p == null) {
						System.out.println("creating new point for interpolator");
						p = tmp;
					}
					p.setLocation(x[0], x[1]);
					return p;
				}
			};
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
