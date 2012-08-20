package com.gemserk.animation4j.examples;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.gemserk.animation4j.animations.events.AnimationHandlerManager;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.componentsengine.java2d.Java2dGameAdapter;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.java2d.render.Java2dImageRenderObject;
import com.gemserk.componentsengine.java2d.render.Java2dRenderer;
import com.google.inject.Inject;

public class Example4 extends Java2dGameAdapter {
	
	private static ColorConverter colorConverter = new ColorConverter();

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

	BufferedImage backgroundImage;
	BufferedImage buttonImage;
	BufferedImage buttonGlowImage;

	Transition<Color> backgroundColor;
	
	class Button {

		Transition<Vector2f> position;

		Transition<Color> color;

		Transition<Color> glowColor;

		Transition<Vector2f> size;

		Boolean mouseInside = false;

	}

	ArrayList<Button> buttons = new ArrayList<Button>();

	@Override
	public void init() {
		
		backgroundImage = ImageUtils.load("example4/background.jpg");
		buttonImage = ImageUtils.load("example4/settings-button.png");
		buttonGlowImage = ImageUtils.load("example4/settings-button-glow.png");

		final Vector2fConverter vector2fConverter = new Vector2fConverter();

		buttons.add(new Button() {
			{
				position = Transitions.transition(new Vector2f(320, 625), vector2fConverter) //
						.speed(1f) //
						.end(1f, 320f, 125f) //
						.build();

				size = Transitions.transition(new Vector2f(1f, 1f), vector2fConverter) //
						.speed(1f) //
						.build();

				color = Transitions.transition(new Color(1f, 1f, 1f, 1f), Example4.colorConverter) //
						.speed(5f) //
						.build();
				glowColor = Transitions.transition(new Color(1f, 0f, 0f, 0f), Example4.colorConverter) //
						.speed(2f) //
						.build();

			}
		});

		buttons.add(new Button() {
			{
				position = Transitions.transition(new Vector2f(320, 725), vector2fConverter) //
						.speed(1f) //
						.end(1f, 320f, 225f) //
						.build();

				size = Transitions.transition(new Vector2f(1f, 1f), vector2fConverter) //
						.speed(1f) //
						.build();

				color = Transitions.transition(new Color(1f, 1f, 1f, 1f), Example4.colorConverter) //
						.speed(5f) //
						.build();
				glowColor = Transitions.transition(new Color(1f, 0f, 0f, 0f), Example4.colorConverter) //
						.speed(2f) //
						.build();
				
			}
		});

		buttons.add(new Button() {
			{
				position = Transitions.transition(new Vector2f(320, 825), vector2fConverter) //
						.speed(1f) //
						.end(1f, 320f, 325f) //
						.build();

				size = Transitions.transition(new Vector2f(1f, 1f), vector2fConverter) //
						.speed(1f) //
						.build();

				color = Transitions.transition(new Color(1f, 1f, 1f, 1f), Example4.colorConverter) //
						.speed(5f) //
						.build();

				glowColor = Transitions.transition(new Color(1f, 0f, 0f, 0f), Example4.colorConverter) //
						.speed(2f) //
						.build();

			}
		});

		backgroundColor = Transitions.transition(new Color(0.4f, 0.4f, 0.4f, 0f), Example4.colorConverter) //
				.speed(1f) //
				.build();

		backgroundColor.start(1f, new Color(0.4f, 0.4f, 0.4f, 0.6f));
	}

	java.awt.Color getColor(Transition<Color> transition) {
		Color color = transition.get();
		return new java.awt.Color(color.r, color.g, color.b, color.a);
	}

	@Override
	public void render(Graphics2D graphics) {

		currentGraphicsProvider.setGraphics(graphics);

		java2dRenderer.render(new Java2dImageRenderObject(0, backgroundImage, 320, 240, 1, 1, 0f));

		graphics.setColor(getColor(backgroundColor));
		graphics.fillRect(0, 0, 640, 480);

		// render the image using the color of the transition

		for (Button button : buttons) {
			Vector2f position = button.position.get();

			Vector2f size = button.size.get();

			float x = (float) position.getX();
			float y = (float) position.getY();

			float sx = (float) size.getX();
			float sy = (float) size.getY();

			java.awt.Color color = getColor(button.color);
			java.awt.Color glowColor = getColor(button.glowColor);
			
			java2dRenderer.render(new Java2dImageRenderObject(1, buttonGlowImage, x, y, sx, sy, 0f, glowColor));
			java2dRenderer.render(new Java2dImageRenderObject(1, buttonImage, x, y, sx, sy, 0f, color));

		}

	}

	@Override
	public void update(int delta) {
		
		float deltaF = 0.001f * (float) delta;

		backgroundColor.update(0.001f * (float) delta);

		Point mousePosition = mouseInput.getPosition();

		int height = buttonImage.getHeight();
		int width = buttonImage.getWidth();

		for (Button button : buttons) {

			button.color.update(deltaF);
			button.glowColor.update(deltaF);
			button.position.update(0.001f * (float) delta);
			button.size.update(0.001f * (float) delta);

			Vector2f position = button.position.get();

			int x = (int) position.getX();
			int y = (int) position.getY();

			if (new Rectangle(x - width / 2, y - height / 2, width, height).contains(mousePosition.x, mousePosition.y)) {
				if (!button.mouseInside) {
					button.mouseInside = true;
					// when the mouse is over the image, we set the color to white
					button.color.start(0.25f, new Color(1f, 1f, 1f, 1f));
					button.glowColor.start(1f, new Color(1f, 0f, 0f, 1f));
					button.size.start(0.25f, new Vector2f(1.05f, 1.05f));
				}
			} else {
				if (button.mouseInside) {
					button.mouseInside = false;
					// when the mouse left the image, we set again the color to the previous color.
					button.color.start(0.25f, new Color(1f, 1f, 1f, 1f));
					button.glowColor.start(0.25f, new Color(1f, 0f, 0f, 0f));
					button.size.start(0.25f, new Vector2f(1f, 1f));
				}
			}

		}

	}

}