package com.gemserk.animation4j.timeline;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsNull;
import org.hamcrest.core.IsSame;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.animation4j.converters.Converters;

@RunWith(JMock.class)
@SuppressWarnings("rawtypes")
public class TimelineIteratorTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void hasNextShouldBeFalseWithEmptyValues() {
		final HashMap<String, TimelineValue> values = new HashMap<String, TimelineValue>();
		TimelineIterator timelineIterator = new TimelineIterator(values);
		assertFalse(timelineIterator.hasNext());
	}

	@Test
	public void hasNextShouldBeTrueWhenNotEmptyValues() {
		final HashMap<String, TimelineValue> values = new HashMap<String, TimelineValue>();
		values.put("x", new TimelineValue(null, Converters.floatValue()));
		TimelineIterator timelineIterator = new TimelineIterator(values);
		assertTrue(timelineIterator.hasNext());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void nextShouldReturnFirstElement() {
		final HashMap<String, TimelineValue> values = new HashMap<String, TimelineValue>();

		final TimelineValue firstTimelineValue = new TimelineValue(null, Converters.floatValue());
		final TimelineValue secondTimelineValue = new TimelineValue(null, Converters.floatValue());

		values.put("a", firstTimelineValue);
		values.put("b", secondTimelineValue);

		TimelineIterator timelineIterator = new TimelineIterator(values);

		assertTrue(timelineIterator.hasNext());
		TimelineValue<Object> currentElement = timelineIterator.next();

		assertThat(currentElement, IsNull.notNullValue());
		assertThat(currentElement, AnyOf.anyOf(IsSame.sameInstance(firstTimelineValue), IsSame.sameInstance(secondTimelineValue)));

		assertTrue(timelineIterator.hasNext());

		currentElement = timelineIterator.next();

		assertThat(currentElement, IsNull.notNullValue());
		assertThat(currentElement, AnyOf.anyOf(IsSame.sameInstance(firstTimelineValue), IsSame.sameInstance(secondTimelineValue)));

		assertFalse(timelineIterator.hasNext());
	}

}
