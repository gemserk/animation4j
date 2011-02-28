package com.gemserk.commons.animation.components;

import com.gemserk.commons.animation.properties.InterpolatedPropertyTimeProvider;
import com.gemserk.componentsengine.components.FieldsReflectionComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;

public class UpdateTimeProviderComponent extends FieldsReflectionComponent {

	@EntityProperty
	InterpolatedPropertyTimeProvider timeProvider;

	public UpdateTimeProviderComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		Integer delta = Properties.getValue(message, "delta");
		timeProvider.update(delta);
	}

}