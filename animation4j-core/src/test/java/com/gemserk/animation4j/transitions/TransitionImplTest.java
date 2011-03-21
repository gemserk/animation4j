package com.gemserk.animation4j.transitions;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;


public class TransitionImplTest {
	
	@Test
	public void shouldInterpolateValues() {
		
		Vector2f startValue = new Vector2f(150f, 220f);
		Vector2f endValue = new Vector2f(50f, 330f);
		
		TransitionImpl<Vector2f> transition = new TransitionImpl<Vector2f>(startValue, 1000, new Vector2fConverter());
		
		transition.set(endValue, 1000);
		assertThat(transition.get(), IsEqual.equalTo(new Vector2f(150f, 220f)));

		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(new Vector2f(100f, 275f)));
		
		transition.update(500);
		assertThat(transition.get(), IsEqual.equalTo(new Vector2f(50f, 330f)));
		
	}
	
	@Test
	public void shouldReuseInternalCreatedObjectsWhenInterpolating() {
		
		Vector2f startValue = new Vector2f(150f, 220f);
		Vector2f endValue = new Vector2f(50f, 330f);
		
		TransitionImpl<Vector2f> transition = new TransitionImpl<Vector2f>(startValue, 1000, new Vector2fConverter());
		
		transition.set(endValue, 1000);

		int instances = Vector2f.instances;
		for (int i = 0; i < 1000; i++) {
			transition.update(1);
			transition.get();
		}
		assertThat(Vector2f.instances, IsEqual.equalTo(instances+1));
	}
	
	@Test
	public void shouldNotModifyTransitionIfStartValueChanges() {
		Vector2f startValue = new Vector2f(150f, 220f);
		Vector2f endValue = new Vector2f(50f, 330f);
		TransitionImpl<Vector2f> transition = new TransitionImpl<Vector2f>(startValue, 1000, new Vector2fConverter());
		transition.set(endValue, 1000);
		startValue.set(1000f, 1000f);
		assertThat(transition.get(), IsEqual.equalTo(new Vector2f(150f, 220f)));
	}

	@Test
	public void shouldNotModifyTransitionIfEndValueChanges() {
		Vector2f startValue = new Vector2f(150f, 220f);
		Vector2f endValue = new Vector2f(50f, 330f);
		TransitionImpl<Vector2f> transition = new TransitionImpl<Vector2f>(startValue, 1000, new Vector2fConverter());
		transition.set(endValue, 1000);
		endValue.set(1500f, 1500f);
		assertThat(transition.get(), IsEqual.equalTo(new Vector2f(150f, 220f)));
		transition.update(1000);
		assertThat(transition.get(), IsEqual.equalTo(new Vector2f(50f, 330f)));
	}

}
