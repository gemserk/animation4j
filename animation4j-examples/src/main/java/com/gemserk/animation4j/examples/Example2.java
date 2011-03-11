package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;
import com.gemserk.animation4j.timeline.sync.ObjectSynchronizer;
import com.gemserk.animation4j.timeline.sync.ReflectionObjectSynchronizer;
import com.gemserk.animation4j.timeline.sync.SynchrnonizedAnimation;
import com.gemserk.animation4j.timeline.sync.TimelineSynchronizer;
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

public class Example2 extends Java2dDesktopApplication {

	public static class Point2DInterpolator implements Interpolator<Point2D> {

		InterpolatorFunction interpolatorFunction;

		public Point2DInterpolator(InterpolatorFunction interpolatorFunction) {
			this.interpolatorFunction = interpolatorFunction;
		}

		@Override
		public Point2D interpolate(Point2D a, Point2D b, float t) {
			float x = interpolatorFunction.interpolate(t);

			double newx = (1 - x) * a.getX() + x * b.getX();
			double newy = (1 - x) * a.getY() + x * b.getY();

			Point2D interpolatedPoint = new Point2D.Float((float) newx, (float) newy);

			return interpolatedPoint;
		}

	}

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example2();
		java2dDesktopApplication.init();
		java2dDesktopApplication.start();
	}

	@Override
	public void init() {
		Injector injector = Guice.createInjector(new Java2dModule(), new BasicModule(), new ResourcesManagerModule());
		injector.getInstance(InitJava2dRenderer.class).config();
		Dimension resolution = new Dimension(640, 480);
		ExampleInternalGame game = injector.getInstance(ExampleInternalGame.class);
		createWindow("Example2", resolution, game, injector);
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

		Resource<Image> globeImageResource;

		public static class Element {

			Point2D position;

			float alpha;

			float textAlpha;

			public void setPosition(Point2D position) {
				this.position = position;
			}

			public Point2D getPosition() {
				return position;
			}

			public void setAlpha(float alpha) {
				this.alpha = alpha;
			}

			public float getAlpha() {
				return alpha;
			}

			public void setTextAlpha(float textAlpha) {
				this.textAlpha = textAlpha;
			}

			public float getTextAlpha() {
				return textAlpha;
			}

		}

		Element element = new Element();

		@Override
		public void init() {

			resourceManager.add("Globe", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("globe-256x176.png")))));
			resourceManager.add("House", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("house-128x92.png")))));
			resourceManager.add("Text", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("text-256x184.png")))));

			globeImageResource = resourceManager.get("Globe");
			houseImageResource = resourceManager.get("House");
			textImageResource = resourceManager.get("Text");

			element.position = new Point(100, 100);
			element.alpha = 0f;
			element.textAlpha = 0f;
			
			// create the synchronizers

			ObjectSynchronizer objectSynchronizer = new ReflectionObjectSynchronizer(element);
			timelineSynchronizer = new TimelineSynchronizer(objectSynchronizer);

			showGlobeAnimation = new SynchrnonizedAnimation(new TimelineAnimationBuilder() {
				{
					speed(1f);
					value("position", new TimelineValueBuilder<Point2D>().keyFrame(0, new Point(320, 320), new Point2DInterpolator(InterpolatorFunctionFactory.easeIn())) //
							.keyFrame(1000, new Point(320, 280)));
					value("alpha", new TimelineValueBuilder<Float>().keyFrame(0, 0f, new FloatInterpolator(InterpolatorFunctionFactory.easeOut())).keyFrame(1000, 1f));
					value("textAlpha", new TimelineValueBuilder<Float>().keyFrame(0, 0f, new FloatInterpolator(InterpolatorFunctionFactory.easeOut())) //
							.keyFrame(500, 0f, new FloatInterpolator(InterpolatorFunctionFactory.easeOut())) //
							.keyFrame(1500, 1f));
					// show text...
				}
			}.build(), timelineSynchronizer);

			hideGlobeAnimation = new SynchrnonizedAnimation(new TimelineAnimationBuilder() {
				{
					speed(1f);
					value("position", new TimelineValueBuilder<Point2D>().keyFrame(0, new Point(320, 280)));
					value("alpha", new TimelineValueBuilder<Float>().keyFrame(0, 1f).keyFrame(500, 1f, new FloatInterpolator(InterpolatorFunctionFactory.easeOut())).keyFrame(1000, 0f));
					value("textAlpha", new TimelineValueBuilder<Float>().keyFrame(0, 1f, new FloatInterpolator(InterpolatorFunctionFactory.easeOut())) //
							.keyFrame(500, 0f));
				}
			}.build(), timelineSynchronizer);

			currentAnimation = showGlobeAnimation;

			currentAnimation.start(1, false);

			animationHandlerManager.with(new DumpAnimationStateHandler()).handleChangesOf(currentAnimation);

		}

		@Inject
		Java2dRenderer java2dRenderer;

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		private Animation showGlobeAnimation;

		private Resource<Image> houseImageResource;

		private Animation hideGlobeAnimation;

		private Animation currentAnimation;

		private Resource<Image> textImageResource;

		private TimelineSynchronizer timelineSynchronizer;

		@Override
		public void render(Graphics2D graphics) {
			graphics.setBackground(Color.black);
			graphics.clearRect(0, 0, 800, 600);

			currentGraphicsProvider.setGraphics(graphics);

			Point2D position = element.position;

			java2dRenderer.render(new Java2dImageRenderObject(1, houseImageResource.get(), 320, 400, 1, 1, 0f));
			java2dRenderer.render(new Java2dImageRenderObject(1, globeImageResource.get(), (float) position.getX(), (float) position.getY(), 1, 1, 0f, new Color(1f, 1f, 1f, element.alpha)));
			java2dRenderer.render(new Java2dImageRenderObject(1, textImageResource.get(), (float) position.getX() + 10, (float) position.getY() + 10, 1, 1, 0f, new Color(1f, 1f, 1f, element.textAlpha)));

			graphics.setColor(Color.white);
			graphics.drawString("Press Enter to go on with the animation.", 100, 100);

		}

		@Override
		public void update(int delta) {

			currentAnimation.update(delta);

			if (keyboardInput.keyDownOnce(KeyEvent.VK_ENTER)) {

				// switch animations

				if (currentAnimation == showGlobeAnimation)
					currentAnimation = hideGlobeAnimation;
				else
					currentAnimation = showGlobeAnimation;

				currentAnimation.restart();
				animationHandlerManager.with(new DumpAnimationStateHandler()).handleChangesOf(currentAnimation);
			}

			animationHandlerManager.checkAnimationChanges();

		}

	}

}
