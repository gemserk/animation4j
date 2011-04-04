package com.gemserk.animation4j.componentsengine.timeline.sync;

import com.gemserk.animation4j.timeline.sync.ObjectSynchronizer;
import com.gemserk.componentsengine.properties.PropertiesHolder;
import com.gemserk.componentsengine.properties.Property;

/**
 * PropertiesHolder implementation of the ObjectSynchronier, setting values to the PropetiesHolder properties.
 * @author acoppes
 */
public class PropertiesHolderSynchronizer implements ObjectSynchronizer {

	private final PropertiesHolder propertiesHolder;

	public PropertiesHolderSynchronizer(PropertiesHolder propertiesHolder) {
		this.propertiesHolder = propertiesHolder;
	}

	@Override
	public void setValue(Object object, String name, Object value) {
		Property<Object> property = propertiesHolder.getProperty(name);
		if (property != null)
			property.set(value);
	}

}
