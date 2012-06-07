package com.gemserk.animation4j.animations.events;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.animations.AnimationManager;
import com.gemserk.animation4j.animations.MockAnimation;

public class AnimationManagerTest {

	@Test
	public void shouldCallAllHandlersEvenIfOneOfThemRemovesTheAnimation() {
		final AnimationManager animationManager = new AnimationManager();

		MockAnimation animation = new MockAnimation();

		animation.setStarted(true);
		animation.setFinished(true);

		animationManager.add(animation);
		
		animationManager.handleAnimationChanges(animation, new AnimationEventHandler() {
			@Override
			public void onAnimationFinished(AnimationEvent e) {
				animationManager.remove(e.getAnimation());
			}
		});
		
		MockAnimationEventHandler animationEventHandler2 = new MockAnimationEventHandler();
		animationEventHandler2.onFinishCalled = false;

		animationManager.handleAnimationChanges(animation,animationEventHandler2);
		
		animationManager.update(1f);
		
		assertThat(animationEventHandler2.onFinishCalled, IsEqual.equalTo(true));

	}

}
