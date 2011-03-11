package com.gemserk.animation4j.event;

import com.gemserk.animation4j.event.AnimationEventHandler;

public class MockAnimationEventHandler extends AnimationEventHandler {

	public boolean onStartCalled = false;

	public boolean onFinishCalled = false;
	
	public boolean onIterationChangedCalled = false;

	@Override
	public void onAnimationStarted(AnimationEvent e) {
		onStartCalled = true;
	}

	@Override
	public void onAnimationFinished(AnimationEvent e) {
		onFinishCalled = true;
	}
	
	@Override
	public void onIterationChanged(AnimationEvent e) {
		onIterationChangedCalled = true;
	}
	
	public void reset() {
		onStartCalled = false;
		onFinishCalled = false;
		onIterationChangedCalled = false;
	}
}