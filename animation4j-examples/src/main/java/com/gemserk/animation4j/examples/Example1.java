package com.gemserk.animation4j.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import com.gemserk.animation4j.animations.Animation;
import com.gemserk.animation4j.animations.events.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.componentsengine.java2d.Java2dGameAdapter;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.java2d.render.Java2dImageRenderObject;
import com.gemserk.componentsengine.java2d.render.Java2dRenderer;
import com.google.inject.Inject;

public class Example1 extends Java2dGameAdapter {

	@Inject
	KeyboardInput keyboardInput;

	@Inject
	MouseInput mouseInput;

	@Inject
	AnimationHandlerManager animationHandlerManager;
	
	@Inject
	Java2dRenderer java2dRenderer;

	@Inject
	CurrentGraphicsProvider currentGraphicsProvider;

	Animation animation;

	JPanel panel;

	BufferedImage critterImage;

	FloatValue x = new FloatValue(0f);
	FloatValue y = new FloatValue(0f);
	FloatValue angle = new FloatValue(0f);

	@Override
	public void init() {

		critterImage = ImageUtils.load("critter.png");

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

	@Override
	public void render(Graphics2D graphics) {
		graphics.setBackground(Color.blue);
		graphics.clearRect(0, 0, 800, 600);

		currentGraphicsProvider.setGraphics(graphics);
		java2dRenderer.render(new Java2dImageRenderObject(1, critterImage, x.value, y.value, 1, 1, angle.value));

		panel.paint(graphics);
	}

	@Override
	public void update(int delta) {
		animation.update((float) delta * 0.001f);

		if (keyboardInput.keyDownOnce(KeyEvent.VK_ENTER)) 
			animation.restart();

		animationHandlerManager.checkAnimationChanges();
	}

}