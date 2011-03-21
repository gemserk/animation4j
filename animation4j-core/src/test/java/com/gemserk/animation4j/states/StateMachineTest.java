package com.gemserk.animation4j.states;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsSame;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.Animation;
import com.gemserk.animation4j.MockAnimation;

@RunWith(JMock.class)
public class StateMachineTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void shouldReturnFirstAddedState() {
		Animation animation1 = new MockAnimation();
		AnimationStateMachine animationState = new AnimationStateMachine();
		animationState.setCurrentState(animation1);
		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation1));
	}

	@Test
	public void shouldReturnNextStateWhenTransitionConditionMatches() {
		Animation animation1 = new MockAnimation();
		Animation animation2 = new MockAnimation();
		AnimationStateMachine animationState = new AnimationStateMachine();
		animationState.setCurrentState(animation1);

		animationState.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return true;
			}
		}, animation1, animation2));

		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation1));
		animationState.checkTransitionConditions();
		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation2));
	}

	@Test
	public void shouldReturnAnotherStateWhenAnotherTransitionConditionMatches() {
		Animation animation1 = new MockAnimation();
		Animation animation2 = new MockAnimation();
		Animation animation3 = new MockAnimation();

		AnimationStateMachine animationState = new AnimationStateMachine();
		animationState.setCurrentState(animation1);

		animationState.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return false;
			}
		}, animation1, animation2));

		animationState.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return true;
			}
		}, animation1, animation3));

		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation1));
		animationState.checkTransitionConditions();
		assertThat(animationState.getCurrentState(), IsSame.sameInstance(animation3));
	}

	boolean enterStateCalled = false;

	boolean leaveStateCalled = false;

	@Test
	public void souldCallPerformWhenTransitionConditionMatches() {
		Animation animation1 = new MockAnimation();
		AnimationStateMachine animationState = new AnimationStateMachine();

		animationState.setCurrentState(animation1);

		animationState.addTransition(new StateTransition<Animation>(new StateTransitionCondition<Animation>() {
			@Override
			public boolean matches(Animation sourceState, Animation targetState) {
				return true;
			}
		}, animation1, animation1) {
			@Override
			public void afterTransition(Animation sourceState, Animation targetState) {
				enterStateCalled = true;
			}

			@Override
			public void beforeTransition(Animation sourceState, Animation targetState) {
				leaveStateCalled = true;
			}
		});

		animationState.checkTransitionConditions();
		assertThat(enterStateCalled, IsEqual.equalTo(true));
		assertThat(leaveStateCalled, IsEqual.equalTo(true));
	}

}
