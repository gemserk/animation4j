package com.gemserk.animation4j.timeline.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;


public class ReflectionObjectSynchronizerTest {
	
	public static class TestObject {
		
		private float x;
		
		private float y;
		
		private String some;

		public float getX() {
			return x;
		}

		public void setX(float x) {
			this.x = x;
		}

		public float getY() {
			return y;
		}

		public void setY(float y) {
			this.y = y;
		}

		public String getSome() {
			return some;
		}

		public void setSome(String some) {
			this.some = some;
		}
		
	}
	
	
	@Test
	public void shouldSetValueForFieldName() {
		TestObject object = new TestObject();
		object.setX(200f);
		object.setY(150f);
		
		ReflectionObjectSynchronizer reflectionObjectSynchronizer = new ReflectionObjectSynchronizer();
		
		reflectionObjectSynchronizer.setValue(object, "x", 100f);
		
		assertThat(object.getX(), IsEqual.equalTo(100f));
		assertThat(object.getY(), IsEqual.equalTo(150f));
		
		reflectionObjectSynchronizer.setValue(object, "y", 500f);

		assertThat(object.getX(), IsEqual.equalTo(100f));
		assertThat(object.getY(), IsEqual.equalTo(500f));

		assertThat(object.getSome(), IsNull.nullValue());
		reflectionObjectSynchronizer.setValue(object, "some", "value");
		assertThat(object.getSome(), IsNull.notNullValue());
		assertThat(object.getSome(), IsEqual.equalTo("value"));

	}
	
	@Test
	public void shouldDoNothingIfValueDontExist() {
		TestObject object = new TestObject();
		
		object.setX(200f);
		object.setY(150f);
		
		ReflectionObjectSynchronizer reflectionObjectSynchronizer = new ReflectionObjectSynchronizer();
		
		reflectionObjectSynchronizer.setValue(object, "z", 100f);
		
		assertThat(object.getX(), IsEqual.equalTo(200f));
		assertThat(object.getY(), IsEqual.equalTo(150f));
	}

}
