package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class TimeTransitionTest {

	@Test
	public void shouldStartFinished() {
		TimeTransition timeTransition = new TimeTransition();
		assertThat(timeTransition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void test() {
		TimeTransition timeTransition = new TimeTransition();
		timeTransition.start(500);
		assertThat(timeTransition.isFinished(), IsEqual.equalTo(false));
		timeTransition.update(200);
		assertThat(timeTransition.isFinished(), IsEqual.equalTo(false));
		timeTransition.update(300);
		assertThat(timeTransition.isFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void test2() {
		TimeTransition timeTransition = new TimeTransition();
		timeTransition.start(500);
		timeTransition.update(500);
		assertThat(timeTransition.isFinished(), IsEqual.equalTo(true));
		timeTransition.start(500);
		assertThat(timeTransition.isFinished(), IsEqual.equalTo(false));
	}

}
