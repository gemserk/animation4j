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
import com.gemserk.animation4j.converters.TypeConverter;
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

	@Test
	public void shouldCallRegisteredSynchronizersAndRemoveThenIfFinished() {

		final TransitionObjectSynchronizer synchronizer1 = mockery.mock(TransitionObjectSynchronizer.class, "synchronizer1");
		final TransitionObjectSynchronizer synchronizer2 = mockery.mock(TransitionObjectSynchronizer.class, "synchronizer2");

		mockery.checking(new Expectations() {
			{
				oneOf(synchronizer1).synchronize();
				oneOf(synchronizer1).isFinished();
				will(returnValue(false));

				oneOf(synchronizer2).synchronize();
				oneOf(synchronizer2).isFinished();
				will(returnValue(true));

				oneOf(synchronizer1).synchronize();
				oneOf(synchronizer1).isFinished();
				will(returnValue(false));
			}
		});

		SynchronizedTransitionManager synchronizedTransitionManager = new SynchronizedTransitionManager();

		synchronizedTransitionManager.handle(synchronizer1);
		synchronizedTransitionManager.handle(synchronizer2);

		synchronizedTransitionManager.synchronize();
		synchronizedTransitionManager.synchronize();

	}

	@Test
	public void testSynchronizers() {

		Converters.register(Vector2f.class, new Vector2fConverter());

		MyObject myObject = new MyObject();
		myObject.position = new Vector2f(50, 50);

		int time = 500;

		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		Transitions.timeProvider = timeProvider;

		Synchronizers.transition(myObject, "position", new Vector2f(100, 100), time);
		Synchronizers.synchronize();

		assertThat(myObject.position, IsEqual.equalTo(new Vector2f(50f, 50f)));

		timeProvider.update(time);
		Synchronizers.synchronize();

		assertThat(myObject.position, IsEqual.equalTo(new Vector2f(100f, 100f)));

		Converters.unregister(Vector2f.class);

	}
	
	@Test
	public void testSynchronizers2() {

		Converters.register(Vector2f.class, new Vector2fConverter());

		MyObject myObject = new MyObject();
		myObject.position = new Vector2f(50, 50);

		int time = 500;

		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		Transitions.timeProvider = timeProvider;

		Synchronizers.transition(myObject.position, new Vector2f(100, 100), time);
		Synchronizers.synchronize();

		assertThat(myObject.position, IsEqual.equalTo(new Vector2f(50f, 50f)));

		timeProvider.update(time);
		Synchronizers.synchronize();

		assertThat(myObject.position, IsEqual.equalTo(new Vector2f(100f, 100f)));

		Converters.unregister(Vector2f.class);

	}

	static class MutableObjectSynchronizer {

		private final Object object;

		private final TypeConverter typeConverter;

		public MutableObjectSynchronizer(Object object) {
			this(object, Converters.converter(object.getClass()));
		}

		public MutableObjectSynchronizer(Object object, TypeConverter typeConverter) {
			this.object = object;
			this.typeConverter = typeConverter;
		}

		public void synchronize(Object value) {
			float[] v = typeConverter.copyFromObject(value, null);
			typeConverter.copyToObject(object, v);
		}

	}
	
	static class TransitionMutableObjectSynchronizer {

		private final MutableObjectSynchronizer mutableObjectSynchronizer;

		private final Transition transition;

		public TransitionMutableObjectSynchronizer(MutableObjectSynchronizer mutableObjectSynchronizer, Transition transition) {
			this.mutableObjectSynchronizer = mutableObjectSynchronizer;
			this.transition = transition;

		}

		public void synchronize() {
			mutableObjectSynchronizer.synchronize(transition.get());
		}

	}

	@Test
	public void testSynchronizeMutableObject() {

		Vector2fConverter typeConverter = new Vector2fConverter();
		Vector2f position = new Vector2f(50, 50);

		MutableObjectSynchronizer mutableObjectSyncrhonizer = new MutableObjectSynchronizer(position, typeConverter);

		assertThat(position, IsEqual.equalTo(new Vector2f(50f, 50f)));

		mutableObjectSyncrhonizer.synchronize(new Vector2f(100, 100));

		assertThat(position, IsEqual.equalTo(new Vector2f(100, 100)));

	}

	@Test
	public void testSynchronizers3() {

		Converters.register(Vector2f.class, new Vector2fConverter());

		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		Transitions.timeProvider = timeProvider;

		Vector2f position = new Vector2f(50, 50);

		MutableObjectSynchronizer mutableObjectSynchronizer = new MutableObjectSynchronizer(position);

		Transition<Vector2f> transition = Transitions.transitionBuilder(position).build();
		transition.set(new Vector2f(100, 100), 500);

		TransitionMutableObjectSynchronizer transitionMutableObjectSynchronizer = new TransitionMutableObjectSynchronizer(mutableObjectSynchronizer, transition);
		transitionMutableObjectSynchronizer.synchronize();

		assertThat(position, IsEqual.equalTo(new Vector2f(50f, 50f)));

		timeProvider.update(250);
		transitionMutableObjectSynchronizer.synchronize();

		assertThat(position, IsEqual.equalTo(new Vector2f(75, 75)));

		Converters.unregister(Vector2f.class);

	}

}
