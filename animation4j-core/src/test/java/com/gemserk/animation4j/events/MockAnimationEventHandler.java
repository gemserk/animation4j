package com.gemserk.animation4j.events;

import com.gemserk.animation4j.Animation;

public class MockAnimationEventHandler extends AnimationEventHandler {

	public boolean onStartCalled = false;

	public boolean onFinishCalled = false;
	
	public boolean onIterationChangedCalled = false;

	@Override
	public void onAnimationStarted(Animation animation) {
		onStartCalled = true;
	}

	@Override
	public void onAnimationFinished(Animation animation) {
		onFinishCalled = true;
	}
	
	@Override
	public void onIterationChanged(Animation animation) {
		onIterationChangedCalled = true;
	}
	
	public void reset() {
		onStartCalled = false;
		onFinishCalled = false;
		onIterationChangedCalled = false;
	}
}