package com.gemserk.animation4j.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import com.gemserk.componentsengine.java2d.Java2dDesktopApplication;
import com.gemserk.componentsengine.java2d.Java2dGame;
import com.gemserk.componentsengine.java2d.Java2dModule;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.java2d.render.InitJava2dRenderer;
import com.gemserk.componentsengine.modules.BasicModule;
import com.gemserk.componentsengine.modules.ResourcesManagerModule;
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
		
		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		@Override
		public void init() {
			
		}

		@Override
		public void render(Graphics2D graphics) {
			graphics.setBackground(Color.blue);
			graphics.clearRect(0, 0, 800, 600);
		}

		@Override
		public void update(int delta) {
			
		}
		
	}
	
}
