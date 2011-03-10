package com.gemserk.animation4j.timeline;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;

import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
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
	
	
	public static interface SynchronizerIterator {
		
		<T> TimelineValue<T> next();
		
		boolean hasNext(); 
		
	}
	
	public static class SynchronizerIteratorImpl implements SynchronizerIterator {

		private final Timeline timeline;
		
		private Iterator<String> iterator;

		public SynchronizerIteratorImpl(Timeline timeline) {
			this.timeline = timeline;
			iterator = timeline.getTimelineValues().keySet().iterator();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <T> TimelineValue<T> next() {
			return timeline.getTimelineValues().get(iterator.next());
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}
		
	}
	
	@Test
	public void hasNextShouldBeFalseWithEmptyValues() {
		
		final Timeline timeline = mockery.mock(Timeline.class);
		final HashMap<String, TimelineValue> values = new HashMap<String, TimelineValue>();
		
		mockery.checking(new Expectations() {
			{
				ignoring(timeline).getTimelineValues();
				will(returnValue(values));
			}
		});
		
		SynchronizerIterator synchronizerIterator = new SynchronizerIteratorImpl(timeline);
		
		assertFalse(synchronizerIterator.hasNext());
		
	}
	
	@Test
	public void hasNextShouldBeTrueWhenNotEmptyValues() {
		
		final Timeline timeline = mockery.mock(Timeline.class);
		final HashMap<String, TimelineValue> values = new HashMap<String, TimelineValue>();
		
		values.put("x", new TimelineValue());
		
		mockery.checking(new Expectations() {
			{
				ignoring(timeline).getTimelineValues();
				will(returnValue(values));
			}
		});
		
		SynchronizerIterator synchronizerIterator = new SynchronizerIteratorImpl(timeline);
		
		assertTrue(synchronizerIterator.hasNext());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void nextShouldReturnFirstElement() {
		
		final Timeline timeline = mockery.mock(Timeline.class);
		final HashMap<String, TimelineValue> values = new HashMap<String, TimelineValue>();
		
		TimelineValue firstTimelineValue = new TimelineValue();
		TimelineValue secondTimelineValue = new TimelineValue();
		
		values.put("a", firstTimelineValue);
		values.put("b", secondTimelineValue);
		
		mockery.checking(new Expectations() {
			{
				ignoring(timeline).getTimelineValues();
				will(returnValue(values));
			}
		});
		
		SynchronizerIterator synchronizerIterator = new SynchronizerIteratorImpl(timeline);
		
		assertTrue(synchronizerIterator.hasNext());
		TimelineValue<Object> currentElement = synchronizerIterator.next();
		
		assertThat(currentElement, IsNull.notNullValue());
		assertThat(currentElement, AnyOf.anyOf(IsSame.sameInstance(firstTimelineValue), IsSame.sameInstance(secondTimelineValue)));
		
		assertTrue(synchronizerIterator.hasNext());

		currentElement = synchronizerIterator.next();
		
		assertThat(currentElement, IsNull.notNullValue());
		assertThat(currentElement, AnyOf.anyOf(IsSame.sameInstance(firstTimelineValue), IsSame.sameInstance(secondTimelineValue)));
		
		assertFalse(synchronizerIterator.hasNext());
		
	}
	
	public static interface ObjectSynchronizer {
		
		void setValue(String name, Object value);
		
	}
	
	// TODO: reflection implementation of ObjectSynchronizer.
	
	// TODO: PropertiesHolder implementation of ObjectSynchronizer.
	
}
