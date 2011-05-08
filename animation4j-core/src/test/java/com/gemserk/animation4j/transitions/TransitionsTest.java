package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;


public class TransitionsTest {

	@Test(expected=RuntimeException.class)
	public void shouldFailToInferConverterFromStartValueIfConverterNotRegistered() {
		Converters.unregister(Vector2f.class);
		Transitions.transition(new Vector2f(100, 100));
	}

	@Test
	public void shouldInferConverterFromStartValueIfConverterRegistered() {
		Converters.register(Vector2f.class, new Vector2fConverter());
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(100, 100));
		assertNotNull(transition);
		Converters.unregister(Vector2f.class);
	}

	@Test
	public void testCreateTransitionThroughTransitionBuilder() {
		Converters.register(Vector2f.class, new Vector2fConverter());
		Vector2f startValue = new Vector2f(50, 50);
		Transition<Vector2f> transition = Transitions.transitionBuilder(startValue).end(new Vector2f(100, 100)).time(500).build();
		Vector2f value = transition.get();
		Converters.unregister(Vector2f.class);
	}
	
}
