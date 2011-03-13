package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.FloatInterpolator;
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

			resourceManager.add("Button", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("globe-256x176.png")))));
			resourceManager.add("ButtonGlow", new CachedResourceLoader<Image>(new ResourceLoaderImpl<Image>(new ImageLoader(new ClassPathDataSource("house-128x92.png")))));

			buttonImageResource = resourceManager.get("Button");
			buttonGlowImageResource = resourceManager.get("ButtonGlow");

			colorTransition = new Transition<Float>(0.3f, new FloatInterpolator(InterpolatorFunctionFactory.linear()));

		}

		@Inject
		Java2dRenderer java2dRenderer;

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		private Resource<Image> buttonGlowImageResource;

		private Transition<Float> colorTransition;

		@Override
		public void render(Graphics2D graphics) {
			graphics.setBackground(Color.black);
			graphics.clearRect(0, 0, 800, 600);

			currentGraphicsProvider.setGraphics(graphics);

			Color color = new Color(colorTransition.getValue(), colorTransition.getValue(), colorTransition.getValue(), 1f);

			// java2dRenderer.render(new Java2dImageRenderObject(1, buttonImageResource.get(), 320, 240, 1, 1, 0f));
			java2dRenderer.render(new Java2dImageRenderObject(1, buttonGlowImageResource.get(), 320, 240, 2, 2, 0f, color));

		}

		boolean mouseInside = false;

		@Override
		public void update(int delta) {

			colorTransition.update(delta);

			Point mousePosition = mouseInput.getPosition();

			if (new Rectangle(320 - 128, 240 - 88, 256, 176).contains(mousePosition.x, mousePosition.y)) {
				if (!mouseInside) {
					mouseInside = true;
					colorTransition.set(1f, 500);
				}
			} else {
				if (mouseInside) {
					mouseInside = false;
					colorTransition.set(0.3f, 500);
				}
			}

		}

	}

}
