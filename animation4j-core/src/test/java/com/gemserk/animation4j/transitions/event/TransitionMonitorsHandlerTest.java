package com.gemserk.animation4j.transitions.event;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
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
	
	public static class TransitionEventHandler {
		
		public void onTransitionStarted() {
			
		}

		public void onTransitionFinished() {
			
		}

	}
	
	static class TransitionMonitorEventImpl {
		
		private TransitionEventHandler transitionEventHandler;
		
		private TransitionMonitor transitionMonitor;
		
		public void setTransitionEventHandler(TransitionEventHandler transitionEventHandler) {
			this.transitionEventHandler = transitionEventHandler;
		}
		
		public void setTransitionMonitor(TransitionMonitor transitionMonitor) {
			this.transitionMonitor = transitionMonitor;
		}

		public void update() {
			transitionMonitor.update();
			if (transitionMonitor.wasStarted())
				transitionEventHandler.onTransitionStarted();
			if (transitionMonitor.wasFinished())
				transitionEventHandler.onTransitionFinished();
		}
		
	}

	@SuppressWarnings("rawtypes")
	static class TransitionMonitorBuilder {
		
		public TransitionMonitorBuilder with(TransitionEventHandler transitionEventHandler) {
			return this;
		}
		
		public TransitionMonitorBuilder monitor(Transition transition) {
			return this;
		}

	}
	
	static class MockTransitionEventHandler extends TransitionEventHandler {
		
		boolean onTransitionFinishedCalled = false;
		
		boolean onTransitionStartedCalled = false;
		
		@Override
		public void onTransitionFinished() {
			onTransitionFinishedCalled = true;
		}
		
		@Override
		public void onTransitionStarted() {
			onTransitionStartedCalled = true;
		}
		
	}
	
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
		
		TransitionMonitorEventImpl transitionMonitorEventImpl = new TransitionMonitorEventImpl();
		transitionMonitorEventImpl.setTransitionEventHandler(mockTransitionEventHandler);
		transitionMonitorEventImpl.setTransitionMonitor(transitionMonitor);
		transitionMonitorEventImpl.update();
		
		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldCallOnStartWhenTransitionStartedDetected() {
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();
		
		final TransitionMonitor transitionMonitor = mockery.mock(TransitionMonitor.class);
		
		mockery.checking(new Expectations() {
			{
				oneOf(transitionMonitor).update();
				oneOf(transitionMonitor).wasStarted();
				will(returnValue(true));
				oneOf(transitionMonitor).wasFinished();
				will(returnValue(false));
			}
		});
		
		TransitionMonitorEventImpl transitionMonitorEventImpl = new TransitionMonitorEventImpl();
		transitionMonitorEventImpl.setTransitionEventHandler(mockTransitionEventHandler);
		transitionMonitorEventImpl.setTransitionMonitor(transitionMonitor);
		transitionMonitorEventImpl.update();
		
		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(true));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldCallOnFinishWhenTransitionFinishDetected() {
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();
		
		final TransitionMonitor transitionMonitor = mockery.mock(TransitionMonitor.class);
		
		mockery.checking(new Expectations() {
			{
				oneOf(transitionMonitor).update();
				oneOf(transitionMonitor).wasStarted();
				will(returnValue(false));
				oneOf(transitionMonitor).wasFinished();
				will(returnValue(true));
			}
		});
		
		TransitionMonitorEventImpl transitionMonitorEventImpl = new TransitionMonitorEventImpl();
		transitionMonitorEventImpl.setTransitionEventHandler(mockTransitionEventHandler);
		transitionMonitorEventImpl.setTransitionMonitor(transitionMonitor);
		transitionMonitorEventImpl.update();
		
		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(true));
	}
	
}
