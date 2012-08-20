package com.gemserk.animation4j.transitions.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.transitions.MockTransition;
import com.gemserk.animation4j.transitions.event.MockTransitionEventHandler;

public class TransitionHandlersManagerTest {

	@Test
	public void usageTest() {
		@SuppressWarnings("rawtypes")
		MockTransition transition = new MockTransition();
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();

		TransitionHandlersManager transitionHandlersManager = new TransitionHandlersManager();

		transitionHandlersManager.handle(transition, mockTransitionEventHandler);
		
		transitionHandlersManager.update();
		
		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(false));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(false));
		
		// I assume internal implementation here :S
		
//		transition.setTransitioning(true);
		transition.setStarted(true);
		transition.setFinished(false);
		transitionHandlersManager.update();

		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(true));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(false));

		transition.setStarted(true);
		transition.setFinished(true);
//		transition.setTransitioning(false);
		transitionHandlersManager.update();

		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(true));
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void checkSameProcessorUsedTwiceShouldNotBeFinished() {
		MockTransition transition = new MockTransition();
		MockTransitionEventHandler mockTransitionEventHandler = new MockTransitionEventHandler();

		TransitionHandlersManager transitionHandlersManager = new TransitionHandlersManager();
		transitionHandlersManager.handle(transition, mockTransitionEventHandler);
		
		transition.setFinished(true);
		transitionHandlersManager.update();

		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(true));
		
		transition = new MockTransition();
		mockTransitionEventHandler = new MockTransitionEventHandler();
		
		transitionHandlersManager.handle(transition, mockTransitionEventHandler);

		transition.setStarted(true);
		transition.setFinished(true);
		transitionHandlersManager.update();

		assertThat(mockTransitionEventHandler.onTransitionStartedCalled, IsEqual.equalTo(true));
		assertThat(mockTransitionEventHandler.onTransitionFinishedCalled, IsEqual.equalTo(true));

	}

}
