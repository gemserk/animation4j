package com.gemserk.commons.animation.handlers;

import com.gemserk.commons.animation.Animation;

public class MockAnimationEventHandler extends AnimationEventHandler {

	public boolean onStartCalled = false;

	public boolean onFinishCalled = false;

	@Override
	public void onAnimationStarted(Animation animation) {
		onStartCalled = true;
	}

	@Override
	public void onAnimationFinished(Animation animation) {
		onFinishCalled = true;
	}
	
	public void reset() {
		onStartCalled = false;
		onFinishCalled = false;
	}
}