package com.gemserk.animation4j;

import com.gemserk.animation4j.converters.TypeConverter;

public class Vector2fConverter implements TypeConverter<Vector2f> {
	
	public static int arrayInstances = 0;

	@Override
	public float[] copyFromObject(Vector2f v, float[] x) {
		if (x == null) {
			x = new float[variables()];
			arrayInstances++;
		}
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