package com.gemserk.animation4j.animations;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class AnimationImplTest {

	@Test
	public void bugAnimationShouldBeStartAgainFrom0() {
		AnimationImpl animation = new AnimationImpl(false, false);
		animation.setDuration(10f);
		animation.start();
		animation.update(5f);
		assertThat(animation.getCurrentTime(), IsEqual.equalTo(5f));
		animation.start();
		assertThat(animation.getCurrentTime(), IsEqual.equalTo(0f));
	}

}
