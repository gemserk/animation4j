package com.gemserk.animation4j.transitions.event;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.transitions.MockTransition;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

public class TransitionMonitorTest {
	
	Vector2fConverter vector2fConverter = new Vector2fConverter();

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
		transition.setStarted(true);

		TransitionMonitor transitionMonitor = new TransitionMonitor();
		transitionMonitor.monitor(transition);
		transitionMonitor.update();

		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldReturnWasStartedOnlyOnce() {
		MockTransition<Vector2f> transition = new MockTransition<Vector2f>();
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

		final TransitionMonitor transitionMonitor = new TransitionMonitor();
		final Transition<Vector2f> transition = Transitions.mutableTransition(new Vector2f(0f, 0f), new Vector2fConverter()) //
			.start(50f, 50f) //
			.end(0.5f, 100f, 100f) //
			.build();
		
		transitionMonitor.monitor(transition);

		transition.update(1f);
		transitionMonitor.update();
		
		assertThat(transitionMonitor.wasStarted(), IsEqual.equalTo(true));
		assertThat(transitionMonitor.wasFinished(), IsEqual.equalTo(true));
	}

}
