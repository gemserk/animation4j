package com.gemserk.animation4j.timeline;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.timeline.sync.ObjectSynchronizer;



@RunWith(JMock.class)
public class TimelineSynchronizerTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
	@Test
	@SuppressWarnings("rawtypes")
	public void shouldGetValuesFromIteratorAndSetToObject() {
		
		final TimelineSynchronizerIteratorImpl timelineSynchronizerIterator = mockery.mock(TimelineSynchronizerIteratorImpl.class);
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
		
		TimelineSynchronizer timelineSynchronizer = new TimelineSynchronizer(objectSynchronizer);
		
		timelineSynchronizer.syncrhonize(timelineSynchronizerIterator,  10);
		
	}
	
}
