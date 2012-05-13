package com.gemserk.animation4j.transitions;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.converters.TypeConverter;

public class TransitionImplTest {

	class MyObject {

		float x, y;

		public MyObject(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public MyObject() {
			this(0f, 0f);
		}

	}

	class MyObjectTypeConverter implements TypeConverter<MyObject> {

		@Override
		public int variables() {
			return 2;
		}

		@Override
		public float[] copyFromObject(MyObject object, float[] x) {
			if (x == null)
				x = new float[variables()];
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

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());

		transition.start(a);

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

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());

		transition.start(a);
	}

	@Test
	public void shouldSetOnlyFirstArrayValuesWhenSettingWithTime() {
		float a[] = { 10f };

		MyObject myObject = new MyObject();
		myObject.x = 0f;
		myObject.y = 0f;

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());

		transition.start(5f, a);
	}

	@Test
	public void shouldSetObjectValuesOnUpdateWhenSetWithTime() {
		float a[] = { 0f, 0f };
		float b[] = { 50f, 50f };

		MyObject myObject = new MyObject();
		myObject.x = 0f;
		myObject.y = 0f;

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());

		transition.start(a);
		transition.start(1f, b);

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

		assertEquals(true, transition.isStarted());
		assertEquals(true, transition.isFinished());
	}

	@Test
	public void shouldTransitionFromCurrentValueToEndValue() {
		float b[] = { 50f, 50f };

		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());

		transition.start(1f, b);

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

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());

		transition.start(1f, b);

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

		TransitionImpl transition = new TransitionImpl(myObject, new MyObjectTypeConverter());
		assertSame(myObject, transition.get());
	}

	@Test
	public void testBuilder() {
		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		Transition<MyObject> transition = Transitions.transition(myObject, new MyObjectTypeConverter()) //
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

		Transition<MyObject> transition = Transitions.transition(myObject, new MyObjectTypeConverter()) //
				.build();

		assertThat(myObject.x, IsEqual.equalTo(40f));
		assertThat(myObject.y, IsEqual.equalTo(40f));

		transition.update(1f);

		assertThat(myObject.x, IsEqual.equalTo(40f));
		assertThat(myObject.y, IsEqual.equalTo(40f));
	}

	@Test
	public void testBuilderWithCustomTypeConverter() {
		MyObject myObject = new MyObject();
		myObject.x = 40f;
		myObject.y = 40f;

		Transition<MyObject> transition = Transitions.transition(myObject, new MyObjectTypeConverter() {

			@Override
			public int variables() {
				return 3;
			}

			@Override
			public MyObject copyToObject(MyObject object, float[] x) {
				object.x = 2f * x[0] - 1f;
				object.y = -1f * x[1] - x[2] + 5f;
				return object;
			}

			@Override
			public float[] copyFromObject(MyObject object, float[] x) {
				if (x == null)
					x = new float[variables()];
				x[0] = object.x;
				x[1] = object.y;
				x[2] = object.x + object.y;
				return x;
			}

		}).start(1f, 2f, 3f) //
				.end(1f, 10f, 20f, 30f) //
				.build();

		assertThat(myObject.x, IsEqual.equalTo(1f));
		assertThat(myObject.y, IsEqual.equalTo(0f));

		transition.update(1f);

		assertThat(myObject.x, IsEqual.equalTo(19f));
		assertThat(myObject.y, IsEqual.equalTo(-45f));
	}

	@Test
	public void shouldReturnModifiedObjectWhenSetWithNoTime() {
		MyObject myObject = new MyObject(50f, 50f);
		Transition<MyObject> transition = new TransitionImpl<TransitionImplTest.MyObject>(myObject, new MyObjectTypeConverter());
		
		MyObject state = transition.get();
		
		assertEquals(50f, state.x, 0.01f);
		assertEquals(50f, state.y, 0.01f);
		
		transition.start(new MyObject(25f, 75f));

		state = transition.get();
		
		assertEquals(25f, state.x, 0.01f);
		assertEquals(75f, state.y, 0.01f);
	}
	
	@Test
	public void bugTransitionStartingFromEndWhenSetAndThenSetWithTime() {
		MyObject myObject = new MyObject(50f, 50f);
		Transition<MyObject> transition = new TransitionImpl<TransitionImplTest.MyObject>(myObject, new MyObjectTypeConverter());
		
		MyObject state = transition.get();
		
		transition.start(new MyObject(0f, 0f));
		transition.start(5f, new MyObject(200f, 200f));

		transition.update(0f);
		state = transition.get();
		
		assertEquals(0f, state.x, 0.01f);
		assertEquals(0f, state.y, 0.01f);
	}
	
	@Test
	public void shouldUpdateBasedOnNewStartingValue() {
		MyObject myObject = new MyObject(50f, 50f);
		Transition<MyObject> transition = new TransitionImpl<TransitionImplTest.MyObject>(myObject, new MyObjectTypeConverter());
		
		transition.start(new MyObject(0f, 0f));
		transition.start(5f, new MyObject(200f, 200f));
		
		transition.setStartingValue(new MyObject(100f, 100f));

		transition.update(2.5f);
		
		assertEquals(150f, myObject.x, 0.01f);
		assertEquals(150f, myObject.y, 0.01f);
	}
	
	@Test
	public void shouldUpdateBasedOnNewEndingValue() {
		MyObject myObject = new MyObject(50f, 50f);
		Transition<MyObject> transition = new TransitionImpl<TransitionImplTest.MyObject>(myObject, new MyObjectTypeConverter());
		
		transition.start(new MyObject(0f, 0f));
		transition.start(5f, new MyObject(200f, 200f));
		
		transition.setEndingValue(new MyObject(100f, 100f));

		transition.update(2.5f);
		
		assertEquals(50f, myObject.x, 0.01f);
		assertEquals(50f, myObject.y, 0.01f);
	}
	
	@Test
	public void shouldNotStartNewTransitionIfStartingOrEndingValueSet() {
		MyObject myObject = new MyObject(50f, 50f);
		Transition<MyObject> transition = new TransitionImpl<TransitionImplTest.MyObject>(myObject, new MyObjectTypeConverter());

		assertThat(transition.isFinished(), IsEqual.equalTo(true));
		transition.setStartingValue(new MyObject(100f, 100f));
		transition.update(1f);
		assertThat(transition.isFinished(), IsEqual.equalTo(true));
		transition.setEndingValue(new MyObject(100f, 100f));
		transition.update(1f);
		assertThat(transition.isFinished(), IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldWorkOverNewMutableObject() {
		MyObject myObject1 = new MyObject(50f, 50f);
		MyObject myObject2 = new MyObject(25f, 30f);
		Transition<MyObject> transition = new TransitionImpl<TransitionImplTest.MyObject>(myObject1, new MyObjectTypeConverter());
		
		transition.setObject(myObject2);

		transition.start(new MyObject(0f, 0f));
		transition.start(1f, new MyObject(200f, 200f));

		transition.update(0.5f);
		
		assertEquals(50f, myObject1.x, 0.01f);
		assertEquals(50f, myObject1.y, 0.01f);
		
		assertEquals(100f, myObject2.x, 0.01f);
		assertEquals(100f, myObject2.y, 0.01f);

	}
}
