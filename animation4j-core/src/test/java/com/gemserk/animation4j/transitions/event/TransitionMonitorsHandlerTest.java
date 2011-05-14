package com.gemserk.animation4j.transitions.event;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.transitions.Transition;

@RunWith(JMock.class)
public class TransitionMonitorsHandlerTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void shouldNotCallEventHandlerWhenNoChangesInTransitionDetected() {
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();

		final TransitionMonitor transitionMonitor = mockery.mock(TransitionMonitor.class);

		mockery.checking(new Expectations() {
			{
				oneOf(transitionMonitor).update();
				oneOf(transitionMonitor).wasStarted();
				will(returnValue(false));
				oneOf(transitionMonitor).wasFinished();
				will(returnValue(false));
			}
		});

		TransitionMonitorProcessor transitionMonitorProcessor = new TransitionMonitorProcessor();
		transitionMonitorProcessor.setTransitionEventHandler(mockTransitionEventHandler);
		transitionMonitorProcessor.setTransitionMonitor(transitionMonitor);
		transitionMonitorProcessor.update();

		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.transition, IsNull.nullValue());
	}

	@Test
	public void shouldCallOnStartWhenTransitionStartedDetected() {
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();

		final TransitionMonitor transitionMonitor = mockery.mock(TransitionMonitor.class);
		final Transition transition = mockery.mock(Transition.class);

		mockery.checking(new Expectations() {
			{
				oneOf(transitionMonitor).update();
				oneOf(transitionMonitor).wasStarted();
				will(returnValue(true));
				oneOf(transitionMonitor).wasFinished();
				will(returnValue(false));
				oneOf(transitionMonitor).getTransition();
				will(returnValue(transition));
			}
		});

		TransitionMonitorProcessor transitionMonitorProcessor = new TransitionMonitorProcessor();
		transitionMonitorProcessor.setTransitionEventHandler(mockTransitionEventHandler);
		transitionMonitorProcessor.setTransitionMonitor(transitionMonitor);
		transitionMonitorProcessor.update();

		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(true));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.transition, IsSame.sameInstance(transition));
	}

	@Test
	public void shouldCallOnFinishWhenTransitionFinishDetected() {
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();

		final TransitionMonitor transitionMonitor = mockery.mock(TransitionMonitor.class);
		final Transition transition = mockery.mock(Transition.class);

		mockery.checking(new Expectations() {
			{
				oneOf(transitionMonitor).update();
				oneOf(transitionMonitor).wasStarted();
				will(returnValue(false));
				oneOf(transitionMonitor).wasFinished();
				will(returnValue(true));
				oneOf(transitionMonitor).getTransition();
				will(returnValue(transition));
			}
		});

		TransitionMonitorProcessor transitionMonitorProcessor = new TransitionMonitorProcessor();
		transitionMonitorProcessor.setTransitionEventHandler(mockTransitionEventHandler);
		transitionMonitorProcessor.setTransitionMonitor(transitionMonitor);
		transitionMonitorProcessor.update();

		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(true));
		assertThat(mockTransitionEventHandler.transition, IsSame.sameInstance(transition));
	}
	
}
