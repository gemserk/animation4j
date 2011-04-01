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
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;
import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.animation4j.transitions.Transition;
import com.gemserk.animation4j.transitions.Transitions;

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
	
	@Test
	public void shouldNotBeFinishedIfTransitionIsTransitioning() {
		final Transition transition = mockery.mock(Transition.class);
		MyObject object = new MyObject();
		mockery.checking(new Expectations() {
			{
				oneOf(transition).isTransitioning();
				will(returnValue(true));
			}
		});
		TransitionReflectionObjectSynchronizer synchronizer = new TransitionReflectionObjectSynchronizer(transition, object, "position");
		assertThat(synchronizer.isFinished(), IsEqual.equalTo(false));
	}

	@Test
	public void shouldBeFinishedIfTransitionIsNotTransitioning() {
		final Transition transition = mockery.mock(Transition.class);
		MyObject object = new MyObject();
		mockery.checking(new Expectations() {
			{
				oneOf(transition).isTransitioning();
				will(returnValue(false));
			}
		});
		TransitionReflectionObjectSynchronizer synchronizer = new TransitionReflectionObjectSynchronizer(transition, object, "position");
		assertThat(synchronizer.isFinished(), IsEqual.equalTo(true));
	}

	static class SyncrhonizedTransitionManager {

		private TransitionReflectionObjectSynchronizer synchronizer;

		public void transition(Object object, Object startValue, Object endValue, int time, String fieldName) {

			// maybe working directly with float[] transitions could be better to reuse stuff.

			// TypeConverter valueConverter = Converters.converter(endValue.getClass());
			// float[] endValueArray = valueConverter.copyFromObject(endValue, null);

			// get current value from object

			Transition<Object> transition = Transitions.transition(startValue);
			transition.set(endValue, time);

			synchronizer = new TransitionReflectionObjectSynchronizer(transition, object, fieldName);

		}

		public void syncrhonize() {
			synchronizer.synchronize();
		}

	}

	@Test
	public void something() {

		MyObject myObject = new MyObject();
		myObject.position = new Vector2f(10, 10);
		
		Converters.register(Vector2f.class, new Vector2fConverter());
		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		
		Transitions.timeProvider = timeProvider;

		SyncrhonizedTransitionManager syncrhonizedTransitionManager = new SyncrhonizedTransitionManager();

		int time = 500;

		syncrhonizedTransitionManager.transition(myObject, myObject.position, new Vector2f(50, 50), time, "position");

		assertThat(myObject.position, IsEqual.equalTo(new Vector2f(10, 10)));

		timeProvider.update(500);

		syncrhonizedTransitionManager.syncrhonize();

		assertThat(myObject.position, IsEqual.equalTo(new Vector2f(50, 50)));

	}

}
