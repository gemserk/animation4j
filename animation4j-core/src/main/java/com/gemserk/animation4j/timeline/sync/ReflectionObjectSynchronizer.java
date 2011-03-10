package com.gemserk.animation4j.timeline.sync;

import org.apache.commons.beanutils.BeanUtils;

public class ReflectionObjectSynchronizer implements ObjectSynchronizer {

	private final Object object;

	public ReflectionObjectSynchronizer(Object object) {
		this.object = object;
	}

	@Override
	public void setValue(String name, Object value) {
		try {
			BeanUtils.setProperty(object, name, value);
		} catch (Exception e) {
			throw new RuntimeException("failed to set value " + value.toString() + " to field " + name, e);
		}
	}

}
