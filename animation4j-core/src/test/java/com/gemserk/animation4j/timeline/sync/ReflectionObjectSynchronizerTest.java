package com.gemserk.animation4j.timeline.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class ReflectionObjectSynchronizerTest {
	
	public static class TestObject {
		
		float x;
		
		float y;

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
		
	}
	
	
	@Test
	public void shouldSetValueForFieldName() {
		TestObject object = new TestObject();
		object.setX(200f);
		object.setY(150f);
		
		ReflectionObjectSynchronizer reflectionObjectSynchronizer = new ReflectionObjectSynchronizer(object);
		
		reflectionObjectSynchronizer.setValue("x", 100f);
		
		assertThat(object.getX(), IsEqual.equalTo(100f));
		assertThat(object.getY(), IsEqual.equalTo(150f));
		
		reflectionObjectSynchronizer.setValue("y", 500f);

		assertThat(object.getX(), IsEqual.equalTo(100f));
		assertThat(object.getY(), IsEqual.equalTo(500f));
		
	}
	
	@Test
	public void shouldDoNothingIfValueDontExist() {
		TestObject object = new TestObject();
		
		object.setX(200f);
		object.setY(150f);
		
		ReflectionObjectSynchronizer reflectionObjectSynchronizer = new ReflectionObjectSynchronizer(object);
		
		reflectionObjectSynchronizer.setValue("z", 100f);
		
		assertThat(object.getX(), IsEqual.equalTo(200f));
		assertThat(object.getY(), IsEqual.equalTo(150f));
		
	}

}
