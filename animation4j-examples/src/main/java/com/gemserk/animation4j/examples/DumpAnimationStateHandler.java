package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.event.AnimationEvent;
import com.gemserk.animation4j.event.AnimationEventHandler;

public class DumpAnimationStateHandler extends AnimationEventHandler {
	public void onAnimationStarted(AnimationEvent e) {
		System.out.println("animation started");
	}

	@Override
	public void onAnimationFinished(AnimationEvent e) {
		System.out.println("animation finished");
	}

	@Override
	public void onIterationChanged(AnimationEvent e) {
		System.out.println("animation iteration changed");
	}
}