package com.gemserk.animation4j.timeline.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;
import com.gemserk.animation4j.converters.Converters;


public class MutableObjectSynchronizerTest {
	
	public static class MyTestObject {
		
		Vector2f position;

		public Vector2f getPosition() {
			return position;
		}

		public void setPosition(Vector2f position) {
			this.position = position;
		}
		
	}
	
	@Test
	public void test() {
		
		Converters.register(Vector2f.class, new Vector2fConverter());

		Vector2f instance = new Vector2f(50, 50);
		
		MyTestObject object = new MyTestObject();
		object.position = instance;
		
		MutableObjectSynchronizer mutableObjectSynchronizer = new MutableObjectSynchronizer(object);
		mutableObjectSynchronizer.setValue("position", new Vector2f(500, 500));
		
		assertThat(object.position, IsEqual.equalTo(new Vector2f(500, 500)));
		assertThat(object.position, IsSame.sameInstance(instance));
		
		Converters.unregister(Vector2f.class);
		
	}
	
	@Test
	public void test2() {
		
		Converters.register(Vector2f.class, new Vector2fConverter());

		Vector2f instance = new Vector2f(50, 50);
		
		MyTestObject object = new MyTestObject();
		object.position = instance;
		
		MutableObjectSynchronizer mutableObjectSynchronizer = new MutableObjectSynchronizer(object);
		
		Vector2fConverter.arrayInstances = 0;
		
		for (int i = 0; i < 50; i++) {
			mutableObjectSynchronizer.setValue("position", new Vector2f(500, 500));
		}
		
		assertThat(Vector2fConverter.arrayInstances, IsEqual.equalTo(1));
		
		Converters.unregister(Vector2f.class);
		
	}

}
