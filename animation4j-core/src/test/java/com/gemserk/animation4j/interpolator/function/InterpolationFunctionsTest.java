package com.gemserk.animation4j.interpolator.function;

import org.junit.Test;

import com.gemserk.animation4j.easing.EasingFunctions;

public class InterpolationFunctionsTest {

	@Test
	public void test() {

		float t = 0f;

		System.out.println("cubicInOut");
		for (int i = 0; i <= 10; i++) {
			System.out.println("f(" + t + ")=" + InterpolationFunctions.cubicEaseInOut.interpolate(t));
			t += 0.1f;
		}

	}

	@Test
	public void test2() {

		float t = 0f;

		System.out.println("cubicOut");
		for (int i = 0; i <= 10; i++) {
			System.out.println("f(" + t + ")=" + InterpolationFunctions.cubicEaseOut.interpolate(t) * 16f);
			System.out.println("g(" + t + ")=" + EasingFunctions.Cubic.out.calculate(t, 0f, 16f, 1f));
			t += 0.1f;
		}

	}

	@Test
	public void test3() {

		float t = 0f;

		System.out.println("cubicIn");
		for (int i = 0; i <= 10; i++) {
			System.out.println("f(" + t + ")=" + InterpolationFunctions.cubicEaseIn.interpolate(t));
			t += 0.1f;
		}

	}

}
