package com.gemserk.commons.animation.timeline;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import javax.vecmath.Vector2f;

import org.junit.Test;

import com.gemserk.commons.animation.properties.InterpolatedProperty;
import com.gemserk.commons.animation.properties.InterpolatedPropertyTimeProvider;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.vecmath.animation.timeline.Vector2fInterpolatedValue;

public class InterpolatedPropertyTest {

	class Label {

		Property<Vector2f> position;

		public Label(Property<Vector2f> position) {
			this.position = position;
		}

		public Property<Vector2f> getPosition() {
			return position;
		}

	}

	@Test
	public void printToConsoleInterpolatedValues() {
		InterpolatedPropertyTimeProvider interpolatedPropertyTimeProvider = new InterpolatedPropertyTimeProvider();

		Property<Vector2f> interpolatedProperty = new InterpolatedProperty<Vector2f>(new Vector2fInterpolatedValue(new Vector2f(100, 100)), 0.001f, interpolatedPropertyTimeProvider);

		interpolatedProperty.set(new Vector2f(200, 200));

		System.out.println(interpolatedProperty.get());
		interpolatedPropertyTimeProvider.update(500);
		System.out.println(interpolatedProperty.get());
		interpolatedPropertyTimeProvider.update(500);
		System.out.println(interpolatedProperty.get());
	}
	
	@Test
	public void printToConsoleInterpolatedValues2() throws InterruptedException {
		Property<Vector2f> interpolatedProperty = new InterpolatedProperty<Vector2f>(new Vector2fInterpolatedValue(new Vector2f(100, 100)), 0.01f);

		interpolatedProperty.set(new Vector2f(200, 200));

		System.out.println(interpolatedProperty.get());
		Thread.sleep(50);
		System.out.println(interpolatedProperty.get());
		Thread.sleep(50);
		System.out.println(interpolatedProperty.get());
	}

	@Test
	public void shouldReturnInitialValue() {

		InterpolatedPropertyTimeProvider interpolatedPropertyTimeProvider = createMock(InterpolatedPropertyTimeProvider.class);

		expect(interpolatedPropertyTimeProvider.getTime()).andReturn(0L);
		expect(interpolatedPropertyTimeProvider.getTime()).andReturn(0L);
		expect(interpolatedPropertyTimeProvider.getTime()).andReturn(0L);
		expect(interpolatedPropertyTimeProvider.getTime()).andReturn(1000L);

		replay(interpolatedPropertyTimeProvider);

		Property<Float> interpolatedProperty = new InterpolatedProperty<Float>(new FloatInterpolatedValue(100f), // 
				0.001f, interpolatedPropertyTimeProvider);
		interpolatedProperty.set(200f);

		assertEquals(100f, (float) interpolatedProperty.get(), 0.00001);
		assertEquals(200f, (float) interpolatedProperty.get(), 0.00001);

	}

}
