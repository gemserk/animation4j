package com.gemserk.animation4j.transitions;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.converters.TypeConverter;

public class MutableObjectTransitionTest {

	class MyObject {

		float x, y;

	}

	class MyObjectTypeConverter implements TypeConverter<MyObject> {

		@Override
		public int variables() {
			return 2;
		}

		@Override
		public float[] copyFromObject(MyObject object, float[] x) {
			x[0] = object.x;
			x[1] = object.y;
			return x;
		}

		@Override
		public MyObject copyToObject(MyObject object, float[] x) {
			object.x = x[0];
			object.y = x[1];
			return object;
		}

	}

	@Test
	public void shouldSetObjectOnSetWithNoTime() {
		float a[] = { 10f, 20f };

		MyObject myObject = new MyObject();
		myObject.x = 0f;
		myObject.y = 0f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());

		transition.set(a);

		assertThat(myObject.x, IsEqual.equalTo(10f));
		assertThat(myObject.y, IsEqual.equalTo(20f));

		assertEquals(false, transition.isStarted());
		assertEquals(true, transition.isFinished());
	}

	@Test
	public void shouldSetOnlyFirstArrayValues() {
		float a[] = { 10f };

		MyObject myObject = new MyObject();
		myObject.x = 0f;
		myObject.y = 0f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());

		transition.set(a);
	}

	@Test
	public void shouldSetOnlyFirstArrayValuesWhenSettingWithTime() {
		float a[] = { 10f };

		MyObject myObject = new MyObject();
		myObject.x = 0f;
		myObject.y = 0f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());

		transition.set(a, 5f);
	}

	@Test
	public void shouldSetObjectValuesOnUpdateWhenSetWithTime() {
		float a[] = { 0f, 0f };
		float b[] = { 50f, 50f };

		MyObject myObject = new MyObject();
		myObject.x = 0f;
		myObject.y = 0f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());

		transition.set(a);
		transition.set(b, 1f);

		assertEquals(true, transition.isStarted());
		assertEquals(false, transition.isFinished());

		transition.update(0.5f);

		assertThat(myObject.x, IsEqual.equalTo(25f));
		assertThat(myObject.y, IsEqual.equalTo(25f));

		assertEquals(true, transition.isStarted());
		assertEquals(false, transition.isFinished());

		transition.update(0.5f);

		assertThat(myObject.x, IsEqual.equalTo(50f));
		assertThat(myObject.y, IsEqual.equalTo(50f));

		assertEquals(false, transition.isStarted());
		assertEquals(true, transition.isFinished());
	}

	@Test
	public void shouldTransitionFromCurrentValueToEndValue() {
		float b[] = { 50f, 50f };

		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());

		transition.set(b, 1f);

		transition.update(0f);

		assertThat(myObject.x, IsEqual.equalTo(40f));
		assertThat(myObject.y, IsEqual.equalTo(40f));

		transition.update(1f);

		assertThat(myObject.x, IsEqual.equalTo(50f));
		assertThat(myObject.y, IsEqual.equalTo(50f));
	}
	
	@Test
	public void shouldReturnCurrentValueWhenGet() {
		float b[] = { 50f, 50f };

		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());

		transition.set(b, 1f);

		transition.update(0.5f);

		float x[] = transition.getValue();
		
		assertNotNull(x);
		assertThat(x[0], IsEqual.equalTo(45f));
		assertThat(x[1], IsEqual.equalTo(45f));
	}
	
	@Test
	public void shouldReturnMutableObject() {
		float b[] = { 50f, 50f };

		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		MutableObjectTransition transition = new MutableObjectTransition(myObject, new MyObjectTypeConverter());
		assertSame(myObject, transition.get());
	}

	@Test
	public void testBuilder() {
		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		Transition<MyObject> transition = Transitions.mutableTransition(myObject, new MyObjectTypeConverter()) //
				.start(0f, 0f) //
				.end(2f, 20f) //
				.build();
		
		assertThat(myObject.x, IsEqual.equalTo(0f));
		assertThat(myObject.y, IsEqual.equalTo(0f));

		transition.update(2f);

		assertThat(myObject.x, IsEqual.equalTo(20f));
		assertThat(myObject.y, IsEqual.equalTo(0f));
	}
	
	@Test
	public void testBuilder2() {
		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		Transition<MyObject> transition = Transitions.mutableTransition(myObject, new MyObjectTypeConverter()) //
				.build();
		
		assertThat(myObject.x, IsEqual.equalTo(40f));
		assertThat(myObject.y, IsEqual.equalTo(40f));
		
		transition.update(1f);
		
		assertThat(myObject.x, IsEqual.equalTo(40f));
		assertThat(myObject.y, IsEqual.equalTo(40f));
	}
}
