package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.converters.TypeConverter;


public class MutableObjectTransitionTest {
	
	class MyObject {
		
		float x,y;
		
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
		float a[] = {10f, 20f};
		
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
	public void shouldSetObjectValuesOnUpdateWhenSetWithTime() {
		float a[] = {0f, 0f};
		float b[] = {50f, 50f};
		
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
}
