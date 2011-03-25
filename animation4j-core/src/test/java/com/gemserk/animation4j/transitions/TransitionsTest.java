package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;


public class TransitionsTest {
	
	@Test(expected=RuntimeException.class)
	public void shouldFailToInferConverterFromStartValueIfConverterNotRegistered() {
		Transitions.transition(new Vector2f(100, 100));
	}

	@Test
	public void shouldInferConverterFromStartValueIfConverterRegistered() {
		Converters.register(Vector2f.class, new Vector2fConverter());
		Transition<Vector2f> transition = Transitions.transition(new Vector2f(100, 100));
		assertNotNull(transition);
	}

}
