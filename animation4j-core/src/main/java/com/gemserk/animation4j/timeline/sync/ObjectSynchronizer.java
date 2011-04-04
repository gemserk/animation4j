package com.gemserk.animation4j.timeline.sync;

/**
 * Used by the TimelineSynchronizer to set values to object in a user custom way.
 * 
 * @author acoppes
 */
public interface ObjectSynchronizer {

	/**
	 * Sets the specified value to the corresponding field identified by name.
	 * @param object The container object.
	 * @param name The name of the field to be set.
	 * @param value The value to set to the field.
	 */
	void setValue(Object object, String name, Object value);

}
