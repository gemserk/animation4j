package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.FloatArrayInterpolator;
import com.gemserk.animation4j.time.UpdateableTimeProvider;

public class TransitionImplTest {

	private UpdateableTimeProvider timeProvider;

	private InternalTransition<Float> internalTransition;

	private TypeConverter<Float> typeConverter = Converters.floatValue();

	@Before
	public void setUp() {
		timeProvider = new UpdateableTimeProvider();
		internalTransition = new InternalTransition<Float>(100f, typeConverter, new FloatArrayInterpolator(typeConverter.variables()));
	}

	@Test
	public void shouldReturnFirstValueWhenNoTimePassed() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 1f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		assertThat(transition.get(), IsEqual.equalTo(100f));
	}

	@Test
	public void shouldReturnSecondValueWhenTimePassed() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 1f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(10000);
		assertThat(transition.get(), IsEqual.equalTo(200f));
	}

	@Test
	public void shouldReturnInterpolatedValue() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(0.05f);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		timeProvider.update(0.025f);
		assertThat(transition.get(), IsEqual.equalTo(175f));
	}

	@Test
	public void testSeveralSetAndUpdates() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		assertThat(transition.get(), IsEqual.equalTo(100f));
		transition.set(200f);
		timeProvider.update(0.05f);
		assertThat(transition.get(), IsEqual.equalTo(150f));
		timeProvider.update(0.025f);
		assertThat(transition.get(), IsEqual.equalTo(175f));

		transition.set(0f);
		assertThat(transition.get(), IsEqual.equalTo(175f));
		timeProvider.update(0.1f);

		assertThat(transition.get(), IsEqual.equalTo(0f));

		transition.set(100f);
		timeProvider.update(0.025f);
		assertEquals(25f, transition.get(), 0.01f);
		timeProvider.update(0.025f);
		assertEquals(50f, transition.get(), 0.01f);
		timeProvider.update(0.025f);
		assertEquals(75f, transition.get(), 0.01f);
		timeProvider.update(0.025f);
		assertEquals(100f, transition.get(), 0.01f);
	}

	@Test
	public void shouldNotBeStartedWhenCreated() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		assertThat(transition.isStarted(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldBeFinishedWhenCreated() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		assertThat(transition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldBeStartedWhenTimeHasNotPassedButSetCalled() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		transition.set(500f, 2000);
		assertThat(transition.isStarted(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotBeFinishedWhenTimeHasNotPassedButSetCalled() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		transition.set(500f, 2000);
		assertThat(transition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldKeepBeingInTransitionWhenTwoOrMoreSetCalled() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		transition.set(500f, 1000);
		timeProvider.update(500);
		transition.set(250f, 1000);
		assertThat(transition.isFinished(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldNotBeInTransitionWhenTimeHasPassed() {
		TransitionImpl<Float> transition = new TransitionImpl<Float>(internalTransition, 10f, timeProvider);
		transition.set(500f, 1000);
		timeProvider.update(1000);
		assertThat(transition.isFinished(), IsEqual.equalTo(true));
	}

}
