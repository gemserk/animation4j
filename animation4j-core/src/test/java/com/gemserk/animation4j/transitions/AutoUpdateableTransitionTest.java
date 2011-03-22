package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.Interpolator;
import com.gemserk.animation4j.interpolator.Interpolators;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunctionFactory;
import com.gemserk.animation4j.time.UpdateableTimeProvider;


public class AutoUpdateableTransitionTest {

	private Interpolator<Float> interpolator;
	
	private UpdateableTimeProvider timeProvider;
	
	@Before
	public void setUp() {
		timeProvider = new UpdateableTimeProvider();
		interpolator = Interpolators.floatInterpolator(InterpolatorFunctionFactory.linear());
	}
	
	@Test
	public void shouldReturnFirstValueWhenNoTimePassed() {
		TypeConverter<Float> typeConverter = Converters.floatValue();
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(1f, timeProvider, new TransitionImpl<Float>(100f, typeConverter));
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		assertThat(transition.get(), IsEqual.equalTo(100f));
	}
	
	@Test
	public void shouldReturnSecondValueWhenTimePassed() {
		TypeConverter<Float> typeConverter = Converters.floatValue();
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(1f, timeProvider, new TransitionImpl<Float>(100f, typeConverter));
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(10000);
		assertThat(transition.get(), IsEqual.equalTo(200f));
	}
	
	@Test
	public void shouldReturnInterpolatedValue() {
		TypeConverter<Float> typeConverter = Converters.floatValue();
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(0.01f, timeProvider, new TransitionImpl<Float>(100f, typeConverter));
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(50);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(175f));
	}

	
}
