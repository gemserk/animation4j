package com.gemserk.animation4j.componentsengine.timeline.sync;

import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.componentsengine.properties.PropertiesHolderImpl;
import com.gemserk.componentsengine.properties.Property;
import com.gemserk.componentsengine.properties.SimpleProperty;


public class PropertiesHolderSynchronizerTest {
	
	@Test
	public void shouldSetValuesForProperties() {
		
		HashMap<String, Property<Object>> properties = new HashMap<String, Property<Object>>();
		PropertiesHolderImpl propertiesHolder = new PropertiesHolderImpl(properties);
		
		propertiesHolder.addProperty("x", new SimpleProperty(100f));
		
		PropertiesHolderSynchronizer propertiesHolderSynchronizer = new PropertiesHolderSynchronizer(propertiesHolder);
		
		propertiesHolderSynchronizer.setValue(null, "y", 200f);
		
		assertThat((Float)propertiesHolder.getProperty("x").get(), IsEqual.equalTo(100f));
		
		propertiesHolderSynchronizer.setValue(null, "x", 200f);
		
		assertThat((Float)propertiesHolder.getProperty("x").get(), IsEqual.equalTo(200f));
	}

}
