package com.gemserk.animation4j.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import com.gemserk.animation4j.animations.events.AnimationHandlerManager;
import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.java2d.converters.Java2dConverters;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;
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

public class Example1 extends Java2dDesktopApplication {

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example1() {
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
		createWindow("Example1", resolution, game, injector);
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

		Resource<Image> critterImageResource;
		
		FloatValue x = new FloatValue(0f);
		FloatValue y = new FloatValue(0f);
		FloatValue angle = new FloatValue(0f);

		@Override
		public void init() {

			Converters.init();
			Java2dConverters.init();

			resourceManager.add("Critter", new ImageLoader(new ClassPathDataSource("critter.png")));

			critterImageResource = resourceManager.get("Critter");
			
			animation = Builders.animation(Builders.timeline() //
					.value(Builders.timelineValue(x, new FloatValueConverter()) //
							.keyFrame(0f, new FloatValue(150f), InterpolationFunctions.easeIn()) //
							.keyFrame(1f, new FloatValue(350f)) //
					) //
					.value(Builders.timelineValue(y, new FloatValueConverter()) //
							.keyFrame(0f, new FloatValue(325f)) //
					) //
					.value(Builders.timelineValue(angle, new FloatValueConverter()) //
							.keyFrame(0f, new FloatValue(0f), InterpolationFunctions.easeIn()) //
							.keyFrame(1f, new FloatValue((float) Math.PI / 2)) //
					) //
					) //
					.build();

			animation.start(2, true);

			String html = new FileHelper("example1.html").read();

			panel = new JPanel();
			panel.setSize(640, 480);
			panel.setLayout(new BorderLayout());
			panel.setIgnoreRepaint(true);
			panel.setOpaque(false);

			JEditorPane comp = new JEditorPane("text/html", html);
			comp.setLocation(10, 10);
			comp.setSize(610, 420);
			comp.setForeground(Color.white);
			comp.setOpaque(false);
			comp.setEditable(false);

			panel.add(comp);

			animationHandlerManager.with(new DumpAnimationStateHandler()).handleChangesOf(animation);
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

			currentGraphicsProvider.setGraphics(graphics);
			java2dRenderer.render(new Java2dImageRenderObject(1, critterImageResource.get(), x.value, y.value, 1, 1, angle.value));

			panel.paint(graphics);

		}

		@Override
		public void update(int delta) {

			animation.update((float) delta * 0.001f);
			
			if (keyboardInput.keyDownOnce(KeyEvent.VK_ENTER)) {
				animation.restart();
			}

			animationHandlerManager.checkAnimationChanges();

		}

	}

}
