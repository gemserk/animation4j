package com.gemserk.animation4j.examples;

public class Vector2f {

	// Example of a mutable class used in the examples when making interpolations.

	float x, y;

	public Vector2f(float x, float y) {
		set(x, y);
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "vector(" + x + ", " + y + ")";
	}

}