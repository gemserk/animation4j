package com.gemserk.animation4j.componentsengine.properties;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.componentsengine.UpdateableTimeProvider;
import com.gemserk.animation4j.values.FloatInterpolatedValue;


public class InterpolatedPropertyTest {
	
	@Test
	public void shouldReturnFirstValueWhenNoTimePassed() {
		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		InterpolatedProperty<Float> interpolatedProperty = new InterpolatedProperty<Float>(new FloatInterpolatedValue(100f), 1f, timeProvider);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
		interpolatedProperty.set(200f);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
	}
	
	@Test
	public void shouldReturnSecondValueWhenTimePassed() {
		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		InterpolatedProperty<Float> interpolatedProperty = new InterpolatedProperty<Float>(new FloatInterpolatedValue(100f), 1f, timeProvider);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
		interpolatedProperty.set(200f);
		timeProvider.update(10000);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(200f));
	}
	
	@Test
	public void shouldReturnInterpolatedValue() {
		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		InterpolatedProperty<Float> interpolatedProperty = new InterpolatedProperty<Float>(new FloatInterpolatedValue(100f), 0.01f, timeProvider);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
		interpolatedProperty.set(200f);
		timeProvider.update(50);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(150f));
	}

}
