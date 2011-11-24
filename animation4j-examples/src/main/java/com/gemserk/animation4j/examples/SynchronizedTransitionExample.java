package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.sync.Synchronizer;

public class SynchronizedTransitionExample {

	public static class MyObject {

		Vector2f position = new Vector2f(0, 0);
		Vector2f size = new Vector2f(0, 0);

	}

	private static Synchronizer synchronizer = new Synchronizer();

	public static void main(String[] args) throws InterruptedException {
		Converters.register(Vector2f.class, new Vector2fConverter());
		example1();
	}

	protected static void example1() throws InterruptedException {
		MyObject myObject = new MyObject();

		synchronizer.transition(Transitions.mutableTransition(myObject.position, new Vector2fConverter()) //
				.endObject(0.5f, new Vector2f(50f, 50f)) //
				.build());

		synchronizer.transition(Transitions.mutableTransition(myObject.size, new Vector2fConverter()) //
				.endObject(0.5f, new Vector2f(1f, 1f)) //
				.build());

		synchronizer.synchronize(0);
		System.out.println("myobject.position: " + myObject.position);
		System.out.println("myobject.size: " + myObject.size);

		synchronizer.synchronize(0.25f);
		System.out.println("myobject.position: " + myObject.position);
		System.out.println("myobject.size: " + myObject.size);

		synchronizer.synchronize(0.25f);
		System.out.println("myobject.position: " + myObject.position);
		System.out.println("myobject.size: " + myObject.size);

	}

}
