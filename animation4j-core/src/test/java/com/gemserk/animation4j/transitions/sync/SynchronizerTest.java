package com.gemserk.animation4j.transitions.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.event.MockTransitionEventHandler;

public class SynchronizerTest {

	@Before
	public void before() {
		Converters.register(Vector2f.class, new Vector2fConverter());
	}

	@After
	public void after() {
		Converters.unregister(Vector2f.class);
	}

	@Test
	public void differentSynchronizersInstances() {
		Synchronizer synchronizer1 = new Synchronizer();
		Synchronizer synchronizer2 = new Synchronizer();

		Vector2f object1 = new Vector2f(50, 50);
		Vector2f object2 = new Vector2f(100, 100);

		synchronizer1.transition(object1, Transitions.transitionBuilder(object1).end(new Vector2f(0f, 0f)).time(1000));
		synchronizer2.transition(object2, Transitions.transitionBuilder(object2).end(new Vector2f(250f, 250f)).time(1000));

		assertThat(object1, IsEqual.equalTo(new Vector2f(50, 50)));
		assertThat(object2, IsEqual.equalTo(new Vector2f(100, 100)));

		synchronizer1.synchronize(1000);

		assertThat(object1, IsEqual.equalTo(new Vector2f(0, 0)));
		assertThat(object2, IsEqual.equalTo(new Vector2f(100, 100)));

		synchronizer2.synchronize(1000);
		assertThat(object2, IsEqual.equalTo(new Vector2f(250, 250)));

	}

	@Test
	public void shouldUpdateUsingInternalTimeProvider() {
		Synchronizer synchronizer1 = new Synchronizer();
		Vector2f object1 = new Vector2f(50, 50);
		synchronizer1.transition(object1, Transitions.transitionBuilder(object1).end(new Vector2f(0f, 0f)).time(1000));
		synchronizer1.synchronize(0);
		assertThat(object1, IsEqual.equalTo(new Vector2f(50, 50)));
		synchronizer1.synchronize(0.5f);
		assertThat(object1, IsEqual.equalTo(new Vector2f(25, 25)));
		synchronizer1.synchronize(0.5f);
		assertThat(object1, IsEqual.equalTo(new Vector2f(0, 0)));
	}

	@Test
	public void shouldCallEventsEvenIfDeltaIsTooGreat() {
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();
		Synchronizer synchronizer1 = new Synchronizer();
		Vector2f object1 = new Vector2f(50, 50);
		synchronizer1.transition(object1, Transitions.transitionBuilder(object1).end(new Vector2f(0f, 0f)).time(100), mockTransitionEventHandler);
		synchronizer1.synchronize(200);
		assertThat(object1, IsEqual.equalTo(new Vector2f(0f, 0f)));
		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(true));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(true));
	}
	
}
