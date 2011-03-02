package com.gemserk.animation4j.componentsengine.properties;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gemserk.animation4j.componentsengine.TimeProvider;
import com.gemserk.animation4j.componentsengine.UpdateableTimeProvider;
import com.gemserk.componentsengine.properties.Property;


public class DelayedPropertyTest {

	@Test
	public void shouldReturnOriginalValue() {
		TimeProvider timeProvider = createMock(TimeProvider.class);
		Property<Integer> delayedProperty = new DelayedProperty<Integer>(100, 1000, timeProvider);
		assertEquals(100, delayedProperty.get().intValue());
	}
	
	@Test
	public void shouldReturnStartValueWhileBeforeDelay() {
		TimeProvider timeProvider = createMock(TimeProvider.class);
		
		expect(timeProvider.getTime()).andReturn(0L);
		expect(timeProvider.getTime()).andReturn(0L);
		replay(timeProvider);
		
		Property<Integer> delayedProperty = new DelayedProperty<Integer>(100, 1000, timeProvider);
		delayedProperty.set(200);
		assertEquals(100, delayedProperty.get().intValue());

		verify(timeProvider);
	}
	
	@Test
	public void shouldReturnNewValueAfterDelay() {
		TimeProvider timeProvider = createMock(TimeProvider.class);
		
		expect(timeProvider.getTime()).andReturn(0L);
		expect(timeProvider.getTime()).andReturn(1000L);
		replay(timeProvider);
		
		Property<Integer> delayedProperty = new DelayedProperty<Integer>(100, 1000, timeProvider);
		delayedProperty.set(200);
		assertEquals(200, delayedProperty.get().intValue());
		
		verify(timeProvider);
	}
	
	@Test
	public void useTest() {
		UpdateableTimeProvider timeProvider = new UpdateableTimeProvider();
		
		Property<Integer> delayedProperty = new DelayedProperty<Integer>(100, 1000, timeProvider);
		assertEquals(100, delayedProperty.get().intValue());
		
		delayedProperty.set(200);

		timeProvider.update(100);
		assertEquals(100, delayedProperty.get().intValue());
		timeProvider.update(900);
		assertEquals(200, delayedProperty.get().intValue());
		
		delayedProperty.set(100);
		assertEquals(200, delayedProperty.get().intValue());
		timeProvider.update(999);
		assertEquals(200, delayedProperty.get().intValue());
		timeProvider.update(1);
		assertEquals(100, delayedProperty.get().intValue());
		
	}
	
}
