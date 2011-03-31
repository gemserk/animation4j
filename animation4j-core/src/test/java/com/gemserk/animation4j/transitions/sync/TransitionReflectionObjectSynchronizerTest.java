package com.gemserk.animation4j.transitions.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.sync.TransitionReflectionObjectSynchronizer;

@RunWith(JMock.class)
public class TransitionReflectionObjectSynchronizerTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	public static class MyObject {

		Vector2f position;

		public void setPosition(Vector2f position) {
			this.position = position;
		}

		public Vector2f getPosition() {
			return position;
		}

	}

	@Test
	public void shouldSyncrhonizeValuesFromTransition() {

		final Transition transition = mockery.mock(Transition.class);

		MyObject object = new MyObject();
		object.setPosition(new Vector2f(50, 50));

		mockery.checking(new Expectations() {
			{
				oneOf(transition).get();
				will(returnValue(new Vector2f(100, 100)));
			}
		});

		TransitionReflectionObjectSynchronizer transitionReflectionObjectSynchronizer = new TransitionReflectionObjectSynchronizer(transition, object, "position");

		transitionReflectionObjectSynchronizer.synchronize();

		assertThat(object.position, IsEqual.equalTo(new Vector2f(100, 100)));

	}

}
