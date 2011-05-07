package com.gemserk.animation4j.transitions.event;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.Transition;

public class TransitionMonitorTest {

	class MockTransition<T> implements Transition<T> {

		boolean transitioning;

		public void setTransitioning(boolean transitioning) {
			this.transitioning = transitioning;
		}

		@Override
		public T get() {
			// TODO Auto-generated function stub
			return null;
		}

		@Override
		public void set(T t) {
			// TODO Auto-generated function stub

		}

		@Override
		public void set(T t, int time) {
			// TODO Auto-generated function stub

		}

		@Override
		public boolean isTransitioning() {
			// TODO Auto-generated function stub
			return transitioning;

		}

	}

	@SuppressWarnings("rawtypes")
	public static class TransitionMonitor {

		private Transition transition;

		private boolean wasStarted = false;

		private boolean wasTransitioning = false;

		public void monitor(Transition transition) {
			this.transition = transition;
		}

		public boolean wasStarted() {
			return wasStarted;
		}

		public void update() {
			boolean transitioning = transition.isTransitioning();
			wasStarted = !wasTransitioning && transitioning;
			wasTransitioning = transitioning;
		}

	}

	@Before
	public void startUp() {
		Converters.register(Vector2f.class, new Vector2fConverter());
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

}
