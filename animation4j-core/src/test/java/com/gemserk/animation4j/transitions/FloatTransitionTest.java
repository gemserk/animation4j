package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class FloatTransitionTest {

	@Test
	public void shouldSetInstantlyIfNoTimeSpecified() {
		FloatTransition transition = new FloatTransition();
		assertThat(transition.get(), IsEqual.equalTo(0f));
		transition.set(50f);
		assertThat(transition.get(), IsEqual.equalTo(50f));
	}

	@Test
	public void shouldDoNothingOnUpdateIfNoNextValueSpecified() {
		FloatTransition transition = new FloatTransition();
		assertThat(transition.get(), IsEqual.equalTo(0f));
		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(0f));
	}

	@Test
	public void shouldSetInSpecifiedTime() {
		FloatTransition transition = new FloatTransition();
		assertThat(transition.get(), IsEqual.equalTo(0f));
		transition.set(50f, 500);
		assertThat(transition.get(), IsEqual.equalTo(0f));
		transition.update(250);
		assertThat(transition.get(), IsEqual.equalTo(25f));
		transition.update(250);
		assertThat(transition.get(), IsEqual.equalTo(50f));
	}

	@Test
	public void shouldNotBeStartedNorFinished() {
		FloatTransition transition = new FloatTransition();
		assertThat(transition.isStarted(), IsEqual.equalTo(false));
		assertThat(transition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldBeStartedWhenSetWithDelayButNotFinishedBeforeUpdated() {
		FloatTransition transition = new FloatTransition();
		transition.set(50f, 100);
		assertThat(transition.isStarted(), IsEqual.equalTo(false));
		assertThat(transition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldBeStartedAndFinishedAfterUpdate() {
		FloatTransition transition = new FloatTransition();
		transition.set(50f, 100);
		transition.update(50);
		assertThat(transition.isStarted(), IsEqual.equalTo(true));
		assertThat(transition.isFinished(), IsEqual.equalTo(false));
		transition.update(50);
		assertThat(transition.isStarted(), IsEqual.equalTo(true));
		assertThat(transition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldNotBeStartedAfterSecondSetWithTime() {
		FloatTransition transition = new FloatTransition();
		transition.set(50f, 100);
		transition.update(100);
		transition.set(50f, 100);
		assertThat(transition.isStarted(), IsEqual.equalTo(false));
		assertThat(transition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotBeStartedAfterSecondSet() {
		FloatTransition transition = new FloatTransition();
		transition.set(50f, 100);
		transition.update(100);
		transition.set(50f);
		assertThat(transition.isStarted(), IsEqual.equalTo(false));
		assertThat(transition.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldSetValueInNoTime() {
		FloatTransition transition = new FloatTransition();
		transition.set(100f);
		transition.update(1000);
		assertThat(transition.get(), IsEqual.equalTo(100f));
	}
}
