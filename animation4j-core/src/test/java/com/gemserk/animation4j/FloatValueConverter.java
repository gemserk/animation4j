package com.gemserk.animation4j;

import com.gemserk.animation4j.converters.TypeConverter;

public class FloatValueConverter implements TypeConverter<FloatValue> {

	@Override
	public int variables() {
		return 1;
	}

	@Override
	public float[] copyFromObject(FloatValue object, float[] x) {
		x[0] = object.value;
		return x;
	}

	@Override
	public FloatValue copyToObject(FloatValue object, float[] x) {
		object.value = x[0];
		return object;
	}
	
}