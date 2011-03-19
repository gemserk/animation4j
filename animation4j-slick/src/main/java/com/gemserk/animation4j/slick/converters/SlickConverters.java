package com.gemserk.animation4j.slick.converters;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

import com.gemserk.animation4j.converters.TypeConverter;

public class SlickConverters {
	
	public static TypeConverter<Vector2f> vector2f() {
		
		return new TypeConverter<Vector2f>() {
			
			Vector2f tmp = new Vector2f();
			
			@Override
			public float[] copyFromObject(Vector2f v, float[] x) {
				if (x == null)
					x = new float[2];
				x[0] = v.x;
				x[1] = v.y;
				return x;
			}
			
			@Override
			public Vector2f copyToObject(Vector2f v, float[] x) {
				if (v == null)
					v = tmp;
				v.x = x[0];
				v.y = x[1];
				return v;
			}
		};
		
	}
	
	public static TypeConverter<Color> color() {

		return new TypeConverter<Color>() {
			
			Color tmp = new Color(0);
			
			@Override
			public float[] copyFromObject(Color color, float[] x) {
				if (x == null)
					x = new float[4];
				x[0] = color.r;
				x[1] = color.g;
				x[2] = color.b;
				x[3] = color.a;
				return x;
			}

			@Override
			public Color copyToObject(Color color, float[] x) {
				if (color == null)
					color = tmp;
				color.r = x[0];
				color.g = x[1];
				color.b = x[2];
				color.a = x[3];
				return color;
			}
			
		};
		
	}

}
