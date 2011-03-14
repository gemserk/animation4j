package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.interpolator.FloatInterpolator;
import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

@RunWith(JMock.class)
public class UpdateableTransitionTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void shouldReturnInitialWhenCreatedAndNotUpdated() {
		float initialValue = 100f;
		InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);
		Transition<Float> transition = new UpdateableTransition<Float>(initialValue, new FloatInterpolator(interpolatorFunction));
		float value = transition.get();
		assertThat(value, IsEqual.equalTo(initialValue));
	}

	@Test
	public void shouldNotModifyValueWhenSetIfNotUpdateCalled() {
		float initialValue = 100f;
		InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);
		Transition<Float> transition = new UpdateableTransition<Float>(initialValue, new FloatInterpolator(interpolatorFunction));
		float newValue = 200f;
		int time = 1000;
		transition.set(newValue, time);
		float value = transition.get();
		assertThat(value, IsEqual.equalTo(initialValue));
	}

	@Test
	public void shouldModifyValueUsingInterpolatorWhenUpdate() {
		float initialValue = 100f;
		final InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);

		mockery.checking(new Expectations() {
			{
				oneOf(interpolatorFunction).interpolate(0.25f);
				will(returnValue(0.25f));
				
				oneOf(interpolatorFunction).interpolate(0.5f);
				will(returnValue(0.5f));
			}
		});

		UpdateableTransition<Float> transition = new UpdateableTransition<Float>(initialValue, new FloatInterpolator(interpolatorFunction));
		transition.set(200f, 1000);
		transition.update(250);
		assertThat(transition.get(), IsEqual.equalTo(125f));

		transition.update(250);
		assertThat(transition.get(), IsEqual.equalTo(150f));
	}
	
	@Test
	public void shouldReturnNewValueWhenUpdateTimeGreaterThanTransitionTime() {
		float initialValue = 100f;
		final InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);

		mockery.checking(new Expectations() {
			{
				oneOf(interpolatorFunction).interpolate(1f);
				will(returnValue(1f));
			}
		});

		UpdateableTransition<Float> transition = new UpdateableTransition<Float>(initialValue, new FloatInterpolator(interpolatorFunction));
		transition.set(200f, 1000);
		transition.update(1001);
		assertThat(transition.get(), IsEqual.equalTo(200f));
		
		transition.update(1001);
		assertThat(transition.get(), IsEqual.equalTo(200f));
	}
	
	@Test
	public void shouldInterpolateBetweenCurrentValueAndDesiredValueOnSet() {
		float initialValue = 100f;
		final InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);

		mockery.checking(new Expectations() {
			{
				oneOf(interpolatorFunction).interpolate(0.5f);
				will(returnValue(0.5f));
				oneOf(interpolatorFunction).interpolate(0.5f);
				will(returnValue(0.5f));
			}
		});

		UpdateableTransition<Float> transition = new UpdateableTransition<Float>(initialValue, new FloatInterpolator(interpolatorFunction));
		transition.set(200f, 1000);
		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		
		transition.set(300f, 1000);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		
		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(225f));
	}

	@Test
	public void shouldNotInterpolateWhenDesiredValueNotSetOrNull() {
		float initialValue = 100f;
		final InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);

		UpdateableTransition<Float> transition = new UpdateableTransition<Float>(initialValue, new FloatInterpolator(interpolatorFunction));
		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(initialValue));

		transition.set(null, 100);
		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(initialValue));
	}
	
}
