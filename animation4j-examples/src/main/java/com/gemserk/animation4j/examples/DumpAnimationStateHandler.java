package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.event.AnimationEventHandler;

public class DumpAnimationStateHandler extends AnimationEventHandler {
	public void onAnimationStarted(Animation animation) {
		System.out.println("animation started");
	}

	@Override
	public void onAnimationFinished(Animation animation) {
		System.out.println("animation finished");
	}

	@Override
	public void onIterationChanged(Animation animation) {
		System.out.println("animation iteration changed");
	}
}