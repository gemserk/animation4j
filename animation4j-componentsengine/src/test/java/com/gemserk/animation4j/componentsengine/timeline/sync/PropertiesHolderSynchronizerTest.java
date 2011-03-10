package com.gemserk.animation4j.componentsengine.timeline.sync;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.SimpleProperty;


public class PropertiesHolderSynchronizerTest {
	
	@Test
	public void shouldSetValuesForProperties() {
		
		PropertiesHolderImpl propertiesHolder = new PropertiesHolderImpl();
		
		propertiesHolder.addProperty("x", new SimpleProperty(100f));
		
		PropertiesHolderSynchronizer propertiesHolderSynchronizer = new PropertiesHolderSynchronizer(propertiesHolder);
		
		propertiesHolderSynchronizer.setValue("y", 200f);
		
		assertThat((Float)propertiesHolder.getProperty("x").get(), IsEqual.equalTo(100f));
		
		propertiesHolderSynchronizer.setValue("x", 200f);
		
		assertThat((Float)propertiesHolder.getProperty("x").get(), IsEqual.equalTo(200f));
	}

}
