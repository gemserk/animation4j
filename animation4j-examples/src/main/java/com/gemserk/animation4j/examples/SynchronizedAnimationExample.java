package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.timeline.Builders;
import com.gemserk.animation4j.timeline.TimelineAnimation;

public class SynchronizedAnimationExample {

	public static class MyEntity {

		Vector2f position = new Vector2f(0f, 0f);
		Vector2f size = new Vector2f(1f, 1f);

	}

	public static void main(String[] args) throws InterruptedException {
		Converters.register(Vector2f.class, new Vector2fConverter());
		System.out.println("First example: animation with one iteration only");
		exampleAnimationOneIterationWithDelay();
	}

	protected static void exampleAnimationOneIterationWithDelay() throws InterruptedException {

		// One problem with time line animations is that you have to get values by hand and setting them to your objects

		MyEntity myEntity = new MyEntity();

		TimelineAnimation animation = Builders.animation(Builders.timeline() //
				.value(Builders.timelineValue(myEntity.position, new Vector2fConverter()) //
						.keyFrame(0f, new Vector2f(100f, 100f)) //
						.keyFrame(0.5f, new Vector2f(500f, 500f)) //
				) //
				.value(Builders.timelineValue(myEntity.size, new Vector2fConverter()) //
						.keyFrame(0f, new Vector2f(1f, 1f)) //
						.keyFrame(0.5f, new Vector2f(2f, 2f)) //
				) //
				) //
				.build();

		animation.start(1);

		System.out.println("myEntity.position at time 0ms: " + myEntity.position);
		System.out.println("myEntity.size at time 0ms: " + myEntity.size);

		animation.update(0.25f);

		System.out.println("myEntity.position at time 250ms: " + myEntity.position);
		System.out.println("myEntity.size at time 250ms: " + myEntity.size);

		animation.update(0.25f);

		System.out.println("myEntity.position at time 500ms: " + myEntity.position);
		System.out.println("myEntity.size at time 500ms: " + myEntity.size);

	}

}
