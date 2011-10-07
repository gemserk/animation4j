package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.converters.TypeConverter;
import com.gemserk.animation4j.interpolator.function.InterpolationFunction;


public class MutableObjectTransitionTest {
	
	class AnotherTransitionBuilder {
		
		private InterpolationFunction[] functions;
		private float[] start;
		private float[] end;
		private Object mutableObject;
		private TypeConverter typeConverter;
		
		private float time = 0f;

		AnotherTransitionBuilder start(float... values) {
			this.start = values;
			return this;
		}

		AnotherTransitionBuilder end(float... values) {
			this.end = values;
			return this;
		}
		
		AnotherTransitionBuilder time(float time) {
			this.time = time;
			return this;
		}

		AnotherTransitionBuilder functions(InterpolationFunction... functions) {
			this.functions = functions;
			return this;
		}

		Transition build() {
			MutableObjectTransition mutableObjectTransition = new MutableObjectTransition(mutableObject, typeConverter);
			
			mutableObjectTransition.setFunctions(functions);
			
			if (start != null)
				mutableObjectTransition.set(start);
			
			if (end != null)
				mutableObjectTransition.set(end, time);
			
			return mutableObjectTransition;
		}
		
		@SuppressWarnings("rawtypes")
		public AnotherTransitionBuilder(Object mutableObject, TypeConverter typeConverter) {
			this.mutableObject = mutableObject;
			this.typeConverter = typeConverter;
		}

	}
	
	class AnotherTransitionBuilder2 {
		
		private MutableObjectTransition mutableObjectTransition;

		AnotherTransitionBuilder2 from(float... values) {
			mutableObjectTransition.set(values);
			return this;
		}

		AnotherTransitionBuilder2 to(float time, float... values) {
			mutableObjectTransition.set(values, time);
			return this;
		}
		
		AnotherTransitionBuilder2 functions(InterpolationFunction... functions) {
			mutableObjectTransition.setFunctions(functions);
			return this;
		}

		Transition build() {
			return mutableObjectTransition;
		}
		
		@SuppressWarnings("rawtypes")
		public AnotherTransitionBuilder2(Object mutableObject, TypeConverter typeConverter) {
			mutableObjectTransition = new MutableObjectTransition(mutableObject, typeConverter);
		}

	}
	
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
	
	@Test
	public void shouldTransitionFromCurrentValueToEndValue() {
		float b[] = {50f, 50f};
		
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
}
