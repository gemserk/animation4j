package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.sync.Synchronizers;

public class SynchronizedTransitionExample {
	
	public static class MyObject {
		
		Vector2f position = new Vector2f(0, 0);
		
		Vector2f size = new Vector2f(0, 0);

		public Vector2f getPosition() {
			return position;
		}

		public void setPosition(Vector2f position) {
			this.position = position;
		}

		public Vector2f getSize() {
			return size;
		}

		public void setSize(Vector2f size) {
			this.size = size;
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		Converters.register(Vector2f.class, new Vector2fConverter());
		example1();
	}
	
	protected static void example1() throws InterruptedException {
		
		MyObject myObject = new MyObject();
		
		Synchronizers.transition(myObject.position, Transitions.transitionBuilder(myObject.position)
				.end(new Vector2f(50, 50))
				.time(500)
				.build());

		Synchronizers.transition(myObject.size, Transitions.transitionBuilder(myObject.size)
				.end(new Vector2f(1, 1))
				.time(500)
				.build());
		
		Synchronizers.synchronize();
		
		System.out.println("myobject.position: " + myObject.position);
		System.out.println("myobject.size: " + myObject.size);
		
		Thread.sleep(250);
		
		Synchronizers.synchronize();

		System.out.println("myobject.position: " + myObject.position);
		System.out.println("myobject.size: " + myObject.size);
		
		Thread.sleep(250);
		
		Synchronizers.synchronize();

		System.out.println("myobject.position: " + myObject.position);
		System.out.println("myobject.size: " + myObject.size);

	}

}
