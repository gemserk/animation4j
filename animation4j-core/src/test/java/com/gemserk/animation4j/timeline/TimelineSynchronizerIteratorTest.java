package com.gemserk.animation4j.timeline;

import static org.junit.Assert.*;

import java.util.HashMap;

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
public class TimelineSynchronizerIteratorTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	
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
		
		TimelineSynchronizerIterator timelineSynchronizerIterator = new TimelineSynchronizerIterator(timeline);
		
		assertFalse(timelineSynchronizerIterator.hasNext());
		
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
		
		TimelineSynchronizerIterator timelineSynchronizerIterator = new TimelineSynchronizerIterator(timeline);
		
		assertTrue(timelineSynchronizerIterator.hasNext());
		
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
		
		TimelineSynchronizerIterator timelineSynchronizerIterator = new TimelineSynchronizerIterator(timeline);
		
		assertTrue(timelineSynchronizerIterator.hasNext());
		TimelineValue<Object> currentElement = timelineSynchronizerIterator.next();
		
		assertThat(currentElement, IsNull.notNullValue());
		assertThat(currentElement, AnyOf.anyOf(IsSame.sameInstance(firstTimelineValue), IsSame.sameInstance(secondTimelineValue)));
		
		assertTrue(timelineSynchronizerIterator.hasNext());

		currentElement = timelineSynchronizerIterator.next();
		
		assertThat(currentElement, IsNull.notNullValue());
		assertThat(currentElement, AnyOf.anyOf(IsSame.sameInstance(firstTimelineValue), IsSame.sameInstance(secondTimelineValue)));
		
		assertFalse(timelineSynchronizerIterator.hasNext());
		
	}
	
	// TODO: implement iterator remove 
	
}
