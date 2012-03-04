package com.gemserk.animation4j.examples;

import com.gemserk.animation4j.converters.TypeConverter;

public class ColorConverter implements TypeConverter<Color>{

	@Override
	public int variables() {
		return 4;
	}

	@Override
	public float[] copyFromObject(Color c, float[] x) {
		if (x == null)
			x = new float[variables()];
		x[0] = c.r;
		x[1] = c.g;
		x[2] = c.b;
		x[3] = c.a;
		return x;
	}

	@Override
	public Color copyToObject(Color c, float[] x) {
		if (c == null)
			c = new Color();
		c.r = x[0];
		c.g = x[1];
		c.b = x[2];
		c.a = x[3];
		return c;
	}

}
