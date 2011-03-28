package com.gemserk.animation4j.examples;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.JEditorPane;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.event.AnimationEvent;
import com.gemserk.animation4j.event.AnimationEventHandler;
import com.gemserk.animation4j.event.AnimationHandlerManager;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.java2d.interpolators.Java2dInterpolators;
import com.gemserk.animation4j.states.StateMachine;
import com.gemserk.animation4j.states.StateTransition;
import com.gemserk.animation4j.states.StateTransitionCondition;
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

	public static void main(String[] args) {
		Java2dDesktopApplication java2dDesktopApplication = new Example2(){
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
		createWindow("Example2", resolution, game, injector);
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

			globeImageResource = resourceManager.get("Globe");
			houseImageResource = resourceManager.get("House");

			element.position = new Point(100, 100);
			element.alpha = 0f;
			element.textAlpha = 0f;
			
			ObjectSynchronizer objectSynchronizer = new ReflectionObjectSynchronizer(element);
			timelineSynchronizer = new TimelineSynchronizer(objectSynchronizer);

			showAnimation = new SynchrnonizedAnimation(new TimelineAnimationBuilder() {
				{
					speed(1.5f);
					value("position", new TimelineValueBuilder<Point2D>().keyFrame(0, new Point(320, 260), // 
							Java2dInterpolators.point2dInterpolator(InterpolationFunctions.easeIn(), InterpolationFunctions.easeIn())) //
							.keyFrame(1000, new Point(320, 220)));
					value("alpha", new TimelineValueBuilder<Float>().keyFrame(0, 0f, Interpolators.floatInterpolator(InterpolationFunctions.easeOut())).keyFrame(1000, 1f));
					value("textAlpha", new TimelineValueBuilder<Float>().keyFrame(0, 0f, Interpolators.floatInterpolator(InterpolationFunctions.easeOut())) //
							.keyFrame(500, 0f, Interpolators.floatInterpolator(InterpolationFunctions.easeOut())) //
							.keyFrame(1500, 1f));
					// show text...
				}
			}.build(), timelineSynchronizer);

			hideAnimation = new SynchrnonizedAnimation(new TimelineAnimationBuilder() {
				{
					speed(2f);
					value("position", new TimelineValueBuilder<Point2D>().keyFrame(0, new Point(320, 220)));
					value("alpha", new TimelineValueBuilder<Float>().keyFrame(0, 1f).keyFrame(500, 1f, Interpolators.floatInterpolator(InterpolationFunctions.easeOut())).keyFrame(1000, 0f));
					value("textAlpha", new TimelineValueBuilder<Float>().keyFrame(0, 1f, Interpolators.floatInterpolator(InterpolationFunctions.easeOut())) //
							.keyFrame(500, 0f));
				}
			}.build(), timelineSynchronizer);

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

			texts = new String[] { "<div>We need help! The enemies are near, we cannot let them conquer our lands</div>", "<div>Now is the time, gather your forces and prepare for the battle!</div>", "<div>People <strong>trust</strong> in you!</div>", "<div>You are our last hope!</div>" };

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
			} );

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

		@Inject
		Java2dRenderer java2dRenderer;

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		private Animation showAnimation;

		private Resource<Image> houseImageResource;

		private Animation hideAnimation;

		private Animation currentAnimation;

		private TimelineSynchronizer timelineSynchronizer;

		private JEditorPane creditsPane;

		private JEditorPane textPane;

		private int currentText;

		private String[] texts;

		private StateMachine<Animation> animationStateMachine;

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

			Point2D position = element.position;

			java2dRenderer.render(new Java2dImageRenderObject(1, houseImageResource.get(), 320, 340, 1, 1, 0f));
			java2dRenderer.render(new Java2dImageRenderObject(1, globeImageResource.get(), (float) position.getX(), (float) position.getY(), 1, 1, 0f, new Color(1f, 1f, 1f, element.alpha)));

			previousTransform = graphics.getTransform();

			graphics.translate(position.getX() + 10 - 110, position.getY() + 10 - 70);

			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, element.textAlpha));
			textPane.paint(graphics);

			graphics.setTransform(previousTransform);

		}

		@Override
		public void update(int delta) {

			currentAnimation.update(delta);
			
			if (keyboardInput.keyDownOnce(KeyEvent.VK_ENTER)) {
				 currentText++;
			}
			
			animationStateMachine.checkTransitionConditions();
			currentAnimation = animationStateMachine.getCurrentState();

			animationHandlerManager.checkAnimationChanges();

		}

	}

}
