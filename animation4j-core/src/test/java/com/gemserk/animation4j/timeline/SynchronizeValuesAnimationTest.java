package com.gemserk.animation4j.timeline;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.timeline.TimelineSynchronizerIteratorTest.TimelineSynchronizerIterator;


@RunWith(JMock.class)
public class SynchronizeValuesAnimationTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	public static class TimelineSynchronizer {
		
		private final TimelineSynchronizerIterator timelineSynchronizerIterator;
		
		private final ObjectSynchronizer objectSynchronizer;

		public TimelineSynchronizer(TimelineSynchronizerIterator timelineSynchronizerIterator, ObjectSynchronizer objectSynchronizer) {
			this.timelineSynchronizerIterator = timelineSynchronizerIterator;
			this.objectSynchronizer = objectSynchronizer;
		}

		/**
		 * Synchronizes the values of the time line with the current object. 
		 * @param time
		 */
		public void syncrhonize(int time) {
			while(timelineSynchronizerIterator.hasNext()) {
				TimelineValue<Object> timelineValue = timelineSynchronizerIterator.next();
				String name = timelineValue.getName();
				Object value = timelineValue.getValue(time);
				objectSynchronizer.setValue(name, value);
			}
		}

	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void shouldGetValuesFromIteratorAndSetToObject() {
		
		final TimelineSynchronizerIterator timelineSynchronizerIterator = mockery.mock(TimelineSynchronizerIterator.class);
		final TimelineValue timelineValue = mockery.mock(TimelineValue.class);
		final ObjectSynchronizer objectSynchronizer = mockery.mock(ObjectSynchronizer.class);
		
		mockery.checking(new Expectations() {
			{
				oneOf(timelineSynchronizerIterator).hasNext();
				will(returnValue(true));
				
				oneOf(timelineSynchronizerIterator).next();
				will(returnValue(timelineValue));
				
				oneOf(timelineValue).getName();
				will(returnValue("x"));
				
				oneOf(timelineValue).getValue(10);
				will(returnValue(100f));
				
				oneOf(objectSynchronizer).setValue("x", 100f);
				
				oneOf(timelineSynchronizerIterator).hasNext();
				will(returnValue(false));
			}
		});
		
		TimelineSynchronizer timelineSynchronizer = new TimelineSynchronizer(timelineSynchronizerIterator, objectSynchronizer);
		
		timelineSynchronizer.syncrhonize(10);
		
	}
	
	public static interface ObjectSynchronizer {
		
		void setValue(String name, Object value);
		
	}
	
	// TODO: reflection implementation of ObjectSynchronizer.
	
	// TODO: PropertiesHolder implementation of ObjectSynchronizer.
	
}
