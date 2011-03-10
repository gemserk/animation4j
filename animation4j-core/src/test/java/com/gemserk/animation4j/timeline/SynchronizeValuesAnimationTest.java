package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JMock.class)
public class SynchronizeValuesAnimationTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	public static class TestObject {

		private float x;
		
		public void setX(float x) {
			this.x = x;
		}
		
		public float getX() {
			return x;
		}
		
	}
	
	public static interface SynchronizerIterator {
		
		<T> TimelineValue<T> next();
		
		boolean hasNext(); 
		
	}
	
	public static class Synchronizer {
		
		private final SynchronizerIterator synchronizerIterator;
		
		private final TestObject object;

		public Synchronizer(SynchronizerIterator synchronizerIterator, TestObject object) {
			this.synchronizerIterator = synchronizerIterator;
			this.object = object;
		}

		/**
		 * Synchronizes the values of the time line with the current object. 
		 * @param time
		 */
		public void syncrhonize(int time) {
			while(synchronizerIterator.hasNext()) {
				TimelineValue<Float> timelineValue = synchronizerIterator.next();
				object.setX(timelineValue.getValue(time));
			}
		}
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void shouldGetValuesFromIteratorAndSetToObject() {
		
		final SynchronizerIterator synchronizerIterator = mockery.mock(SynchronizerIterator.class);
		final TimelineValue timelineValue = mockery.mock(TimelineValue.class);
		
		TestObject testObject = new TestObject();
		
		mockery.checking(new Expectations() {
			{
				oneOf(synchronizerIterator).hasNext();
				will(returnValue(true));
				
				oneOf(synchronizerIterator).next();
				will(returnValue(timelineValue));
				
				oneOf(timelineValue).getValue(10);
				will(returnValue(100f));
				
				oneOf(synchronizerIterator).hasNext();
				will(returnValue(false));

			}
		});
		
		Synchronizer synchronizer = new Synchronizer(synchronizerIterator, testObject);
		
		synchronizer.syncrhonize(10);
		
		assertThat(testObject.getX(), IsEqual.equalTo(100f));
		
	}
	
}
