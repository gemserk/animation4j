package com.gemserk.animation4j.componentsengine.components;

import com.gemserk.animation4j.time.UpdateableTimeProvider;
import com.gemserk.componentsengine.components.ReferencePropertyComponent;
import com.gemserk.componentsengine.components.annotations.EntityProperty;
import com.gemserk.componentsengine.components.annotations.Handles;
import com.gemserk.componentsengine.messages.Message;
import com.gemserk.componentsengine.properties.Properties;
import com.gemserk.componentsengine.properties.Property;

/**
 * Component to update an UpdateableTimeProvider by handle update message.
 * @author acoppes
 */
public class UpdateTimeProviderComponent extends ReferencePropertyComponent {

	@EntityProperty
	Property<UpdateableTimeProvider> timeProvider;

	public UpdateTimeProviderComponent(String id) {
		super(id);
	}

	@Handles
	public void update(Message message) {
		Integer delta = Properties.getValue(message, "delta");
		timeProvider.get().update(delta);
	}

}