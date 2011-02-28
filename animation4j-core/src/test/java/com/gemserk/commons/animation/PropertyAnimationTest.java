package com.gemserk.commons.animation;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.SimpleProperty;


public class PropertyAnimationTest {
	
	@Test
	public void propertiesHolderShouldNotBeModifiedIfAnimationDidNotStart() {
		PropertiesHolder propertiesHolder = new PropertiesHolderImpl();
		propertiesHolder.addProperty("position", new SimpleProperty<Object>(100f));

		PropertyAnimation animation = new PropertyAnimation("position");
		animation.addKeyFrame(0, 100f);
		animation.addKeyFrame(1000, 200f);
		
		assertThat((Float)Properties.getValue(propertiesHolder, "position"), IsEqual.equalTo(100f));
	}
	
	@Test
	public void propertiesHolderShouldBeOnMiddleValueBetweenTwoKeyFrames() {
		PropertiesHolder propertiesHolder = new PropertiesHolderImpl();
		propertiesHolder.addProperty("position", new SimpleProperty<Object>(100f));

		PropertyAnimation animation = new PropertyAnimation("position");
		animation.addKeyFrame(0, 100f);
		animation.addKeyFrame(1000, 200f);
		animation.play();
		animation.animate(propertiesHolder, 500);
		assertThat((Float)Properties.getValue(propertiesHolder, "position"), IsEqual.equalTo(150f));
	}
	
}
