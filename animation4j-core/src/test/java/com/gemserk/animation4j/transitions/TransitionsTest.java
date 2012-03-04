package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;

public class TransitionsTest {

	Vector2fConverter vector2fConverter = new Vector2fConverter();

	@Test(expected = RuntimeException.class)
	public void shouldFailToInferConverterFromStartValueIfConverterNotRegistered() {
		Converters.unregister(Vector2f.class);
		Transitions.transition(new Vector2f(100f, 100f)).build();
	}

	@Test
	public void shouldInferConverterFromStartValueIfConverterRegistered() {
		Converters.register(Vector2f.class, new Vector2fConverter());
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(100f, 100f)).build();
		assertNotNull(transition);
		Converters.unregister(Vector2f.class);
	}

	@Test
	public void testCreateTransitionThroughTransitionBuilderUsingFloatArrays() {
		Vector2f v = new Vector2f(50, 50);
		Transition<Vector2f> transition = Transitions.transition(v, vector2fConverter) //
				.start(20f, 20f) //
				.end(0.5f, 100f, 100f) //
				.build();
		Vector2f value = transition.get();
	}

}
