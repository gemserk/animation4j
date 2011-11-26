package com.gemserk.animation4j.examples;

import java.awt.Dimension;

import com.gemserk.componentsengine.java2d.Java2dDesktopApplication;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
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
				bind(CurrentGraphicsProvider.class).in(Singleton.class);
				bind(KeyboardInput.class).in(Singleton.class);
				bind(MouseInput.class).in(Singleton.class);
			}
		});

		injector.injectMembers(this);

		Dimension resolution = new Dimension(640, 480);
		Example1Game game = injector.getInstance(Example1Game.class);
		createWindow("Example1", resolution, game);
	}

}
