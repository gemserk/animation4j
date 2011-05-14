package com.gemserk.animation4j.transitions.event;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.MockTransition;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

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
		transition.setStarted(false);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldReturnTransitionStartedIfTransitionDidStart() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);
		transition.setStarted(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldReturnWasStartedOnlyOnce() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setTransitioning(true);
		transition.setStarted(true);

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
		transition.setFinished(false);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
		transition.setFinished(true);
		transitionMonitor.update();
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldReturnTransitionFinishedIfTransitionDidFinishOnlyOnce() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setFinished(false);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
		transition.setFinished(true);
		transitionMonitor.update();
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(true));
		transitionMonitor.update();
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldNotPreservePreviousStatusWhenMonitorNewTransition() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
		transition.setStarted(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		MockTransition<Vector2f> transition2 = new MockTransition<Vector2f>();
		transitionMonitor.monitor(transition2);

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(false));
	}
	
	@Test
	public void testMonitorChangesWhenUpdateTimeIsGreaterThanTransitionTime() {
		UpdateableTimeProvider updateableTimeProvider = new UpdateableTimeProvider();

		final TransitionMonitor transitionMonitor = new TransitionMonitor();
		final Transition<Vector2f> transition = Transitions.transitionBuilder(new Vector2f(50, 50)).end(new Vector2f(100, 100)).time(500).timeProvider(updateableTimeProvider).build();
		
		transitionMonitor.monitor(transition);

		updateableTimeProvider.update(1000);
		transitionMonitor.update();
		
		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(true));
	}

}
