package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.timeline.TimelineAnimation;
import com.gemserk.animation4j.timeline.TimelineAnimationBuilder;
import com.gemserk.animation4j.timeline.TimelineValueBuilder;
import com.gemserk.animation4j.timeline.sync.ReflectionObjectSynchronizer;
import com.gemserk.animation4j.timeline.sync.SynchrnonizedAnimation;
import com.gemserk.animation4j.timeline.sync.TimelineSynchronizer;

public class SynchronizedAnimationExample {
	
	public static class MyEntity {
		
		Vector2f position = new Vector2f(0f, 0f);
		
		Vector2f size = new Vector2f(1f, 1f);
		
		// you will have to make getters and setters as we are using apache-beanutils to set your fields using reflection.
		
		public void setPosition(Vector2f position) {
			this.position = position;
		}
		
		public Vector2f getPosition() {
			return position;
		}
		
		public void setSize(Vector2f size) {
			this.size = size;
		}
		
		public Vector2f getSize() {
			return size;
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("First example: animation wiht one iteration only");
		exampleAnimationOneIterationWithDelay();
	}

	protected static void exampleAnimationOneIterationWithDelay() throws InterruptedException {
		final Vector2fConverter vector2fConverter = new Vector2fConverter();

		// One problem with time line animations is that you have to get values by hand and setting them to your objects
		TimelineAnimation timelineAnimation = new TimelineAnimationBuilder() {{
			
			value("position", new TimelineValueBuilder<Vector2f>(){{
				keyFrame(0, new Vector2f(100f, 100f));
				keyFrame(500, new Vector2f(500f, 500f));
			}});
			
			value("size", new TimelineValueBuilder<Vector2f>(){{
				keyFrame(0, new Vector2f(1f, 1f));
				keyFrame(500, new Vector2f(2f, 2f));
			}});

			
		}}.build();
		
		// one solution for that (by now) is using an automatic synchronization 
		
		MyEntity myEntity = new MyEntity();
		
		TimelineSynchronizer timelineSynchronizer = new TimelineSynchronizer(new ReflectionObjectSynchronizer(myEntity));
		
		Animation animation = new SynchrnonizedAnimation(timelineAnimation, timelineSynchronizer);
		animation.start(1);
		
		// using the synchronized animation you shouldn't call the get() method, but for now you still have to call the update method.
		
		System.out.println("myEntity.position at time 0: " + myEntity.position);
		System.out.println("myEntity.size at time 0: " + myEntity.size);
		
		animation.update(250);

		System.out.println("myEntity.position at time 250: " + myEntity.position);
		System.out.println("myEntity.size at time 250: " + myEntity.size);
		
		animation.update(250);

		System.out.println("myEntity.position at time 500: " + myEntity.position);
		System.out.println("myEntity.size at time 500: " + myEntity.size);

	}

}
