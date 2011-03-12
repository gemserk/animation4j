package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.interpolator.function.InterpolatorFunction;

@RunWith(JMock.class)
public class FloatTransitionTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void shouldReturnInitialWhenCreatedAndNotUpdated() {
		float initialValue = 100f;
		InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);
		FloatTransition floatTransition = new FloatTransition(initialValue, interpolatorFunction);
		float value = floatTransition.getValue();
		assertThat(value, IsEqual.equalTo(initialValue));
	}

	@Test
	public void shouldNotModifyValueWhenSetIfNotUpdateCalled() {
		float initialValue = 100f;
		InterpolatorFunction interpolatorFunction = mockery.mock(InterpolatorFunction.class);
		FloatTransition floatTransition = new FloatTransition(initialValue, interpolatorFunction);
		float newValue = 200f;
		int time = 1000;
		floatTransition.set(newValue, time);
		float value = floatTransition.getValue();
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

		FloatTransition floatTransition = new FloatTransition(initialValue, interpolatorFunction);
		floatTransition.set(200f, 1000);
		floatTransition.update(250);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(125f));

		floatTransition.update(250);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(150f));
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

		FloatTransition floatTransition = new FloatTransition(initialValue, interpolatorFunction);
		floatTransition.set(200f, 1000);
		floatTransition.update(1001);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(200f));
		
		floatTransition.update(1001);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(200f));
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

		FloatTransition floatTransition = new FloatTransition(initialValue, interpolatorFunction);
		floatTransition.set(200f, 1000);
		floatTransition.update(500);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(150f));
		
		floatTransition.set(300f, 1000);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(150f));
		
		floatTransition.update(500);
		assertThat(floatTransition.getValue(), IsEqual.equalTo(225f));
	}

}
