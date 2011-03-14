package com.gemserk.animation4j.transitions;

public class CustomTransition<T> {

	private final Transition<T> transition;
	
	private final int time;
	
	public CustomTransition(Transition<T> transition, int time) {
		this.transition = transition;
		this.time = time;
	}

	public T get() {
		return transition.get();
	}
	
	public void set(T t) {
		transition.set(t, time);
	}

}
