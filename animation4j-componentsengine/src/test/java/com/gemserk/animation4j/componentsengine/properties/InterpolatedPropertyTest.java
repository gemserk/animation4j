package com.gemserk.animation4j.componentsengine.properties;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.Transition;

public class InterpolatedPropertyTest {
	
	private Interpolator<Float> interpolator;
	
	private UpdateableTimeProvider timeProvider;
	
	@Before
	public void setUp() {
		timeProvider = new UpdateableTimeProvider();
		interpolator = new FloatInterpolator(InterpolatorFunctionFactory.linear());
	}
	
	@Test
	public void shouldReturnFirstValueWhenNoTimePassed() {
		InterpolatedProperty<Float> interpolatedProperty = new InterpolatedProperty<Float>(new Transition<Float>(100f, interpolator), 1f, timeProvider);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
		interpolatedProperty.set(200f);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
	}
	
	@Test
	public void shouldReturnSecondValueWhenTimePassed() {
		InterpolatedProperty<Float> interpolatedProperty = new InterpolatedProperty<Float>(new Transition<Float>(100f, interpolator), 1f, timeProvider);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
		interpolatedProperty.set(200f);
		timeProvider.update(10000);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(200f));
	}
	
	@Test
	public void shouldReturnInterpolatedValue() {
		InterpolatedProperty<Float> interpolatedProperty = new InterpolatedProperty<Float>(new Transition<Float>(100f, interpolator), 0.01f, timeProvider);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(100f));
		interpolatedProperty.set(200f);
		timeProvider.update(50);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(150f));
		timeProvider.update(25);
		assertThat(interpolatedProperty.get(), IsEqual.equalTo(175f));
	}

}
