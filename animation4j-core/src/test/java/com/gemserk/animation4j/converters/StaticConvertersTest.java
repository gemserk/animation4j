package com.gemserk.animation4j.converters;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;


public class StaticConvertersTest {
	
	public static class MyClass {
		
		
		
	}
	
	public static class NotRegisteredClass {
		
		
	}
	
	public static class SomeConverter implements TypeConverter<MyClass>{

		@Override
		public int variables() {
			return 1;
		}

		@Override
		public float[] copyFromObject(MyClass object, float[] x) {
			// TODO Auto-generated function stub
			return null;
		}

		@Override
		public MyClass copyToObject(MyClass object, float[] x) {
			// TODO Auto-generated function stub
			return null;
		}
		
	}
	
	@Test(expected=RuntimeException.class)
	public void shouldFailToReturnConverterForNotRegisteredClass() {
		Converters.converter(NotRegisteredClass.class);
	}
	
	@Test
	public void shouldInferConverterFromTypeIfAlreadyRegistered() {
		Converters.registerConverter(MyClass.class, new SomeConverter());
		TypeConverter<MyClass> converter = Converters.converter(MyClass.class);
		assertNotNull(converter);
		assertThat(converter.getClass().isAssignableFrom(SomeConverter.class), IsEqual.equalTo(true));
	}
}
