package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.TypeConverter;

public class Vector2fConverter implements TypeConverter<Vector2f> {
	
	// Example of a converter you have to create for your own mutable class if you want it to be interpolated.
	
	@Override
	public float[] copyFromObject(Vector2f v, float[] x) {
		if (x == null)
			x = new float[variables()];
		x[0] = v.x;
		x[1] = v.y;
		return x;

	}

	@Override
	public Vector2f copyToObject(Vector2f v, float[] x) {
		if (v == null)
			v = new Vector2f(0, 0);
		v.x = x[0];
		v.y = x[1];
		return v;
	}

	@Override
	public int variables() {
		return 2;
	}

}