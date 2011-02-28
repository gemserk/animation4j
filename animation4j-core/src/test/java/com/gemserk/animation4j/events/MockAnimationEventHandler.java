package com.gemserk.animation4j.events;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.events.AnimationEventHandler;

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