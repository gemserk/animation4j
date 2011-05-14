package com.gemserk.animation4j.transitions.event;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.MockTransition;

public class TransitionMonitorTest {

	@Before
	public void startUp() {
		Converters.register(Vector2f.class, new Vector2fConverter());
	}
	
	@After
	public void after() {
		Converters.unregister(Vector2f.class);
	}

	@Test
	public void shouldReturnTransitionNotStartedIfTransitionDidntStart() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldReturnTransitionStartedIfTransitionDidStart() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldReturnWasStartedOnlyOnce() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));
		transitionMonitor.update();
		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldReturnTransitionNotFinishedIfTransitionDidntFinish() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldReturnTransitionFinishedIfTransitionDidFinish() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
		transition.setTransitioning(false);
		transitionMonitor.update();
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldReturnTransitionFinishedIfTransitionDidFinishOnce() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
		transition.setTransitioning(false);
		transitionMonitor.update();
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(true));
		transitionMonitor.update();
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotPreservePreviousStatusWhenMonitorNewTransition() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));

		MockTransition<Vector2f> transition2 = new MockTransition<Vector2f>();
		transitionMonitor.monitor(transition2);

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(false));
	}

}
