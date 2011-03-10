package com.gemserk.animation4j.timeline;

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
	
	public static interface SynchronizerIterator {
		
		<T> TimelineValue<T> next();
		
		boolean hasNext(); 
		
	}
	
	public static class TimelineSynchronizer {
		
		private final SynchronizerIterator synchronizerIterator;
		
		private final ObjectSynchronizer objectSynchronizer;

		public TimelineSynchronizer(SynchronizerIterator synchronizerIterator, ObjectSynchronizer objectSynchronizer) {
			this.synchronizerIterator = synchronizerIterator;
			this.objectSynchronizer = objectSynchronizer;
		}

		/**
		 * Synchronizes the values of the time line with the current object. 
		 * @param time
		 */
		public void syncrhonize(int time) {
			while(synchronizerIterator.hasNext()) {
				TimelineValue<Object> timelineValue = synchronizerIterator.next();
				String name = timelineValue.getName();
				Object value = timelineValue.getValue(time);
				objectSynchronizer.setValue(name, value);
			}
		}

	}
	
	public static interface ObjectSynchronizer {
		
		void setValue(String name, Object value);
		
	}
	
	// TODO: reflection implementation of ObjectSynchronizer.
	
	// TODO: PropertiesHolder implementation of ObjectSynchronizer.
	
	@Test
	@SuppressWarnings("rawtypes")
	public void shouldGetValuesFromIteratorAndSetToObject() {
		
		final SynchronizerIterator synchronizerIterator = mockery.mock(SynchronizerIterator.class);
		final TimelineValue timelineValue = mockery.mock(TimelineValue.class);
		final ObjectSynchronizer objectSynchronizer = mockery.mock(ObjectSynchronizer.class);
		
		mockery.checking(new Expectations() {
			{
				oneOf(synchronizerIterator).hasNext();
				will(returnValue(true));
				
				oneOf(synchronizerIterator).next();
				will(returnValue(timelineValue));
				
				oneOf(timelineValue).getName();
				will(returnValue("x"));
				
				oneOf(timelineValue).getValue(10);
				will(returnValue(100f));
				
				oneOf(objectSynchronizer).setValue("x", 100f);
				
				oneOf(synchronizerIterator).hasNext();
				will(returnValue(false));
			}
		});
		
		TimelineSynchronizer timelineSynchronizer = new TimelineSynchronizer(synchronizerIterator, objectSynchronizer);
		
		timelineSynchronizer.syncrhonize(10);
		
	}
	
}
