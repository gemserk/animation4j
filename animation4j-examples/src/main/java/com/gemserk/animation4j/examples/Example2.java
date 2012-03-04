package com.gemserk.animation4j.examples;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JEditorPane;

import com.gemserk.animation4j.animations.Animation;
import com.gemserk.animation4j.animations.events.AnimationEvent;
import com.gemserk.animation4j.animations.events.AnimationEventHandler;
import com.gemserk.animation4j.animations.events.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.states.StateMachine;
import com.gemserk.animation4j.states.StateTransition;
import com.gemserk.animation4j.states.StateTransitionCondition;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.componentsengine.java2d.Java2dGameAdapter;
import com.gemserk.componentsengine.java2d.input.KeyboardInput;
import com.gemserk.componentsengine.java2d.input.MouseInput;
import com.gemserk.componentsengine.java2d.render.CurrentGraphicsProvider;
import com.gemserk.componentsengine.java2d.render.Java2dImageRenderObject;
import com.gemserk.componentsengine.java2d.render.Java2dRenderer;
import com.google.inject.Inject;

public class Example2 extends Java2dGameAdapter {

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

	private Animation showAnimation;

	private Animation hideAnimation;

	private Animation currentAnimation;

	private JEditorPane creditsPane;

	private JEditorPane textPane;

	private int currentText;

	private String[] texts;

	private StateMachine<Animation> animationStateMachine;

	BufferedImage globeImage;
	BufferedImage houseImage;

	public static class Element {

		Vector2f position;

		FloatValue alpha;

		FloatValue textAlpha;

	}

	Example2.Element element = new Element();

	@Override
	public void init() {
		globeImage = ImageUtils.load("globe-256x176.png");
		houseImage = ImageUtils.load("house-128x92.png");

		element.position = new Vector2f(100f, 100f);
		element.alpha = new FloatValue(0f);
		element.textAlpha = new FloatValue(0f);

		showAnimation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(element.position, new Vector2fConverter()) //
						.keyFrame(0f, new Vector2f(320, 260), InterpolationFunctions.easeIn(), InterpolationFunctions.easeIn()) //
						.keyFrame(1f, new Vector2f(320, 220)) //
				) //
				.value(Builders.timelineValue(element.alpha, new FloatValueConverter()) //
						.keyFrame(0f, new FloatValue(0f), InterpolationFunctions.easeOut()) //
						.keyFrame(1f, new FloatValue(1f)) //
				) //
				.value(Builders.timelineValue(element.textAlpha, new FloatValueConverter()) //
						.keyFrame(0f, new FloatValue(0f), InterpolationFunctions.easeOut()) //
						.keyFrame(0.5f, new FloatValue(0f)) //
						.keyFrame(1.5f, new FloatValue(1f)) //
				) //
				) //
				.speed(1.5f)//
				.build();

		hideAnimation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(element.position, new Vector2fConverter()) //
						.keyFrame(0f, new Vector2f(320, 220)) //
				) //
				.value(Builders.timelineValue(element.alpha, new FloatValueConverter()) //
						.keyFrame(0f, new FloatValue(1f), InterpolationFunctions.easeOut()) //
						.keyFrame(0.5f, new FloatValue(1f)) //
						.keyFrame(1f, new FloatValue(0f)) //
				) //
				.value(Builders.timelineValue(element.textAlpha, new FloatValueConverter()) //
						.keyFrame(0f, new FloatValue(1f), InterpolationFunctions.easeOut()) //
						.keyFrame(0.5f, new FloatValue(0f)) //
				) //
				) //
				.speed(2f)//
				.build();

		currentAnimation = showAnimation;

		currentAnimation.start(1, false);

		animationHandlerManager.with(new DumpAnimationStateHandler("show")).handleChangesOf(showAnimation);
		animationHandlerManager.with(new DumpAnimationStateHandler("hide")).handleChangesOf(hideAnimation);

		String html = new FileHelper("license-lostgarden.html").read();

		creditsPane = new JEditorPane("text/html", html) {
			{
				setSize(600, 40);
				setEditable(false);
				setOpaque(false);
			}
		};

		textPane = new JEditorPane("text/html", "") {
			{
				setSize(226, 156);
				setEditable(false);
				setOpaque(false);
			}
		};

		texts = new String[] { "<div>We need help! The enemies are near, we cannot let them conquer our lands</div>", //
				"<div>Now is the time, gather your forces and prepare for the battle!</div>", //
				"<div>People <strong>trust</strong> in you!</div>", //
				"<div>You are our last hope!</div>" };

		currentText = 0;

		animationHandlerManager.with(new AnimationEventHandler() {
			@Override
			public void onAnimationStarted(AnimationEvent e) {
				if (currentText < texts.length)
					textPane.setText(texts[currentText]);
				else
					textPane.setText("");
			}
		}).handleChangesOf(showAnimation);

		animationStateMachine = new StateMachine<Animation>(showAnimation);

		animationStateMachine.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return keyboardInput.keyDownOnce(KeyEvent.VK_ENTER) && currentText < texts.length;
			}
		}, showAnimation, showAnimation) {
			@Override
			protected void afterTransition(Animation sourceState, Animation targetState) {
				targetState.restart();
			}
		});

		animationStateMachine.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return keyboardInput.keyDownOnce(KeyEvent.VK_ENTER) && currentText >= texts.length;
			}
		}, showAnimation, hideAnimation) {
			@Override
			protected void afterTransition(Animation sourceState, Animation targetState) {
				targetState.restart();
			}
		});

		animationStateMachine.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return keyboardInput.keyDownOnce(KeyEvent.VK_ENTER);
			}
		}, hideAnimation, showAnimation) {
			@Override
			public void afterTransition(Animation sourceState, Animation targetState) {
				targetState.restart();
				currentText = 0;
			}
		});

	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.setBackground(Color.black);
		graphics.clearRect(0, 0, 800, 600);

		currentGraphicsProvider.setGraphics(graphics);

		graphics.setColor(Color.white);
		graphics.drawString("Press Enter to go on with the animation.", 20, 50);

		AffineTransform previousTransform = graphics.getTransform();

		graphics.translate(20, 400);

		// graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, element.textAlpha));
		creditsPane.paint(graphics);

		graphics.setTransform(previousTransform);

		Vector2f position = element.position;

		java2dRenderer.render(new Java2dImageRenderObject(1, houseImage, 320, 340, 1, 1, 0f));
		java2dRenderer.render(new Java2dImageRenderObject(1, globeImage, (float) position.getX(), (float) position.getY(), 1, 1, 0f, new Color(1f, 1f, 1f, element.alpha.value)));

		previousTransform = graphics.getTransform();

		graphics.translate(position.getX() + 10 - 110, position.getY() + 10 - 70);

		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, element.textAlpha.value));
		textPane.paint(graphics);

		graphics.setTransform(previousTransform);

	}

	@Override
	public void update(int delta) {

		currentAnimation.update((float) delta * 0.001f);

		if (keyboardInput.keyDownOnce(KeyEvent.VK_ENTER)) {
			currentText++;
		}

		animationStateMachine.checkTransitionConditions();
		currentAnimation = animationStateMachine.getCurrentState();

		animationHandlerManager.checkAnimationChanges();

	}

}