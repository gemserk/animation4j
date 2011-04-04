package com.gemserk.animation4j.timeline.sync;

/**
 * Used by the TimelineSynchronizer to set values to object in a user custom way.
 * 
 * @author acoppes
 */
public interface ObjectSynchronizer {

	void setValue(Object object, String name, Object value);

}