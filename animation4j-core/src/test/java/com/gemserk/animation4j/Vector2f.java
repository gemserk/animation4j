package com.gemserk.animation4j;

public class Vector2f {
	
	public static int instances;
	
	public float x,y;
	
	public Vector2f(float x, float y) {
		set(x,y);
		System.out.println("vector2f.constructor(" + x + "," + y + ") called");
		Vector2f.instances++;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "vector2f(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2f other = (Vector2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
}