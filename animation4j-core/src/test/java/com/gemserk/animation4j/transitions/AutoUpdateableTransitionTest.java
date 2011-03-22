package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.time.UpdateableTimeProvider;


public class AutoUpdateableTransitionTest {

	private UpdateableTimeProvider timeProvider;
	
	private TransitionImpl<Float> transitionImpl;
	
	private TypeConverter<Float> typeConverter = Converters.floatValue();
	
	@Before
	public void setUp() {
		timeProvider = new UpdateableTimeProvider();
		transitionImpl = new TransitionImpl<Float>(100f, typeConverter, new FloatArrayInterpolator(typeConverter.variables()));
	}
	
	@Test
	public void shouldReturnFirstValueWhenNoTimePassed() {
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(transitionImpl, 1f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		assertThat(transition.get(), IsEqual.equalTo(100f));
	}
	
	@Test
	public void shouldReturnSecondValueWhenTimePassed() {
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(transitionImpl, 1f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(10000);
		assertThat(transition.get(), IsEqual.equalTo(200f));
	}
	
	@Test
	public void shouldReturnInterpolatedValue() {
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(transitionImpl, 0.01f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(50);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(175f));
	}
	
	@Test
	public void testSeveralSetAndUpdates() {
		AutoUpdateableTransition<Float> transition = new AutoUpdateableTransition<Float>(transitionImpl, 0.01f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(50);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(175f));
		
		transition.set(0f);
		assertThat(transition.get(), IsEqual.equalTo(175f));
		timeProvider.update(100);
		
		assertThat(transition.get(), IsEqual.equalTo(0f));

		transition.set(100f);
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(25f));
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(50f));
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(75f));
		timeProvider.update(25);
		assertThat(transition.get(), IsEqual.equalTo(100f));
	}

	
}
