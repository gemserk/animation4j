package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;

public class TimelineAnimationExample {

	public static void main(String[] args) throws InterruptedException {
		Converters.register(Vector2f.class, new Vector2fConverter());

		System.out.println("First example: animation with one iteration only");
		exampleAnimationOneIterationWithDelay();
		System.out.println("Second example: animation alternating directions");
		exampleAnimationWithAlternateDirections();
	}

	protected static void exampleAnimationOneIterationWithDelay() throws InterruptedException {

		Vector2f currentValue = new Vector2f(0, 0);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(currentValue, new Vector2fConverter()) //
						.keyFrame(0f, new Vector2f(100f, 100f)) //
						.keyFrame(0.5f, new Vector2f(500f, 500f)) //
				) //
				) //
				.delay(0.1f) //
				.build();

		// start with only one iteration
		animation.start(1);

		System.out.println("currentValue at time 0ms: " + currentValue);

		// value should not be modified because the animation has a delay of 100 units
		animation.update(0.099f);
		System.out.println("currentValue at time 99ms: " + currentValue);

		animation.update(0.201f);
		System.out.println("currentValue at time 300ms: " + currentValue);

		animation.update(0.300f);
		System.out.println("currentValue at time 600ms: " + currentValue);

		System.out.println("animation is stopped? : " + animation.isFinished());

	}

	protected static void exampleAnimationWithAlternateDirections() throws InterruptedException {
		
		Vector2f currentValue = new Vector2f(0, 0);

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(currentValue, new Vector2fConverter()) //
						.keyFrame(0f, new Vector2f(100f, 100f)) //
						.keyFrame(0.5f, new Vector2f(500f, 500f)) //
				) //
				) //
				.build();

		// start with two iterations and alternating directions.
		animation.start(2, true);

		System.out.println("currentValue at time 0: " + currentValue);
		System.out.println("current direction: " + animation.getPlayingDirection());

		// value should not be modified because the animation has a delay of 100 units
		animation.update(0.5f);
		System.out.println("currentValue at time 500: " + currentValue);

		System.out.println("animation is stopped? : " + animation.isFinished());

		animation.update(0.1f);
		System.out.println("currentValue at time 600: " + currentValue);
		System.out.println("current direction: " + animation.getPlayingDirection());

		animation.update(1f);
		System.out.println("currentValue at time 1000: " + currentValue);

		System.out.println("animation is stopped? : " + animation.isFinished());

	}

}
