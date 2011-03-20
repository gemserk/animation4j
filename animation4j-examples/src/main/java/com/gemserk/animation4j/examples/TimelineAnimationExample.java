package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;

public class TimelineAnimationExample {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("First example: animation wiht one iteration only");
		exampleAnimationOneIterationWithDelay();
		System.out.println("Second example: animation alternating directions");
		exampleAnimationWithAlternateDirections();
	}

	protected static void exampleAnimationOneIterationWithDelay() throws InterruptedException {
		// as converters are stateless, you could reuse it in several interpolators.
		final Vector2fConverter vector2fConverter = new Vector2fConverter();

		TimelineAnimation animation = new TimelineAnimationBuilder() {{
			
			delay(100f);
			
			value("position", new TimelineValueBuilder<Vector2f>(){{
				keyFrame(0, new Vector2f(100f, 100f), Interpolators.interpolator(vector2fConverter));
				keyFrame(500, new Vector2f(500f, 500f));
			}});
			
		}}.build();
		
		// start with only one iteration
		animation.start(1);

		Vector2f currentValue = animation.getValue("position");
		System.out.println("currentValue at time 0: " + currentValue);
		
		// value should not be modified because the animation has a delay of 100 units
		animation.update(99);
		currentValue = animation.getValue("position");
		System.out.println("currentValue at time 99: " + currentValue);
		
		animation.update(201);
		currentValue = animation.getValue("position");
		System.out.println("currentValue at time 300: " + currentValue);

		animation.update(300);
		currentValue = animation.getValue("position");
		System.out.println("currentValue at time 600: " + currentValue);
		
		System.out.println("animation is stopped? : " + animation.isFinished());
		
	}
	
	protected static void exampleAnimationWithAlternateDirections() throws InterruptedException {
		// as converters are stateless, you could reuse it in several interpolators.
		final Vector2fConverter vector2fConverter = new Vector2fConverter();

		TimelineAnimation animation = new TimelineAnimationBuilder() {{
			
			// note: we haven't defined delay, so delay will be 0 then.
			
			value("position", new TimelineValueBuilder<Vector2f>(){{
				keyFrame(0, new Vector2f(100f, 100f), Interpolators.interpolator(vector2fConverter));
				keyFrame(500, new Vector2f(500f, 500f));
			}});
			
		}}.build();
		
		// start with two iterations and alternating directions.
		animation.start(2, true);

		Vector2f currentValue = animation.getValue("position");
		System.out.println("currentValue at time 0: " + currentValue);
		System.out.println("current direction: " + animation.getPlayingDirection());
		
		// value should not be modified because the animation has a delay of 100 units
		animation.update(500);
		currentValue = animation.getValue("position");
		System.out.println("currentValue at time 500: " + currentValue);

		System.out.println("animation is stopped? : " + animation.isFinished());
		
		animation.update(100);
		currentValue = animation.getValue("position");
		System.out.println("currentValue at time 600: " + currentValue);
		System.out.println("current direction: " + animation.getPlayingDirection());

		animation.update(1000);
		currentValue = animation.getValue("position");
		System.out.println("currentValue at time 1000: " + currentValue);
		
		System.out.println("animation is stopped? : " + animation.isFinished());
		
	}

}
