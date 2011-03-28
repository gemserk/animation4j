package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;

public class TimelineAnimationExample {

	public static void main(String[] args) throws InterruptedException {
		Converters.register(Vector2f.class, new Vector2fConverter());
		
		System.out.println("First example: animation with one iteration only");
		exampleAnimationOneIterationWithDelay();
		System.out.println("Second example: animation alternating directions");
		exampleAnimationWithAlternateDirections();
	}

	protected static void exampleAnimationOneIterationWithDelay() throws InterruptedException {

		TimelineAnimation animation = new TimelineAnimationBuilder() {{
			
			delay(100f);
			
			value("position", new TimelineValueBuilder<Vector2f>(){{
				keyFrame(0, new Vector2f(100f, 100f));
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

		TimelineAnimation animation = new TimelineAnimationBuilder() {{
			
			// note: we haven't defined delay, so delay will be 0 then.
			
			value("position", new TimelineValueBuilder<Vector2f>(){{
				keyFrame(0, new Vector2f(100f, 100f));
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
