package com.gemserk.animation4j.states;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsSame;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.MockAnimation;

@RunWith(JMock.class)
public class AnimationStateTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	@Test
	public void shouldReturnFirstAddedState() {
		Animation animation1 = new MockAnimation();
		AnimationState<String, Animation> animationState = new AnimationState<String, Animation>();
		animationState.addState("show", animation1);
		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation1));
	}

	@Test
	public void shouldReturnCurrentSelectedState() {
		Animation animation1 = new MockAnimation();
		Animation animation2 = new MockAnimation();
		AnimationState<String, Animation> animationState = new AnimationState<String, Animation>();
		animationState.addState("show", animation1);
		animationState.addState("hide", animation2);
		animationState.addTransition("transition1", "show", "hide");
		animationState.handleTransitionCondition("transition1");
		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation2));
	}
	
	@Test
	public void shouldKeepOnCurrentStateIfTransitionDoesntExists() {
		Animation animation1 = new MockAnimation();
		Animation animation2 = new MockAnimation();
		AnimationState<String, Animation> animationState = new AnimationState<String, Animation>();
		animationState.addState("show", animation1);
		animationState.addState("hide", animation2);
		animationState.addTransition("transition1", "show", "hide");
		animationState.handleTransitionCondition("unexpectedTransition");
		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation1));
	}

}
