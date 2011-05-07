package com.gemserk.animation4j.transitions.event;

import com.gemserk.animation4j.transitions.Transition;

@SuppressWarnings("rawtypes") class MockTransitionEventHandler extends TransitionEventHandler {

	boolean onTransitionFinishedCalled = false;

	boolean onTransitionStartedCalled = false;

	Transition transition = null;

	@Override
	public void onTransitionFinished(Transition transition) {
		this.transition = transition;
		onTransitionFinishedCalled = true;
	}

	@Override
	public void onTransitionStarted(Transition transition) {
		this.transition = transition;
		onTransitionStartedCalled = true;
	}

}