package com.gemserk.animation4j;

import com.gemserk.animation4j.interpolator.function.InterpolationFunction;
import com.gemserk.animation4j.interpolator.function.InterpolationFunctions;
import com.gemserk.animation4j.transitions.Transitions;
import com.gemserk.animation4j.transitions.sync.Synchronizers;

public class AnimationBuilderTest {
	
	class Timeline {
		
		class Keyframe {
			
			float[] value;
			
			int time;
			
			InterpolationFunction[] functions;
			
		}
		
		Keyframe[] keyframes;
		
		public Timeline(Keyframe[] keyframes) {
			
		}
		
		Keyframe getKeyframe(int time) {
			return null;
		}
		
	}

	class AnimationBuilder {

		private ValueBuilder valueBuilder;

		private Object object;

		public AnimationBuilder() {
			valueBuilder = new ValueBuilder(this);
		}

		AnimationBuilder animate(Object object) {
			this.object = object;
			valueBuilder.targetObject = null;
			return this;
		}

		ValueBuilder to(Object object) {
			valueBuilder.to(object);
			return valueBuilder;
		}

	}

	class ValueBuilder {

		private final AnimationBuilder animationBuilder;

		private InterpolationFunction[] functions;

		private int time;

		private Object targetObject;

		ValueBuilder(AnimationBuilder animationBuilder) {
			this.animationBuilder = animationBuilder;
		}

		ValueBuilder time(int time) {
			this.time = time;
			return this;
		}

		ValueBuilder functions(InterpolationFunction... functions) {
			this.functions = functions;
			return this;
		}

		ValueBuilder to(Object value) {
			if (targetObject != null)
				Synchronizers.transition(animationBuilder.object, Transitions.transitionBuilder(animationBuilder.object).end(targetObject).time(time).functions(functions));
			targetObject = value;
			return this;
		}

	}

	public void test() {

		Vector2f v = new Vector2f(50f, 50f);

		AnimationBuilder animationBuilder = new AnimationBuilder();

		animationBuilder.animate(v) //
				.to(v).time(500).functions(InterpolationFunctions.ease()) //
				.to(v).time(100);

	}

}
