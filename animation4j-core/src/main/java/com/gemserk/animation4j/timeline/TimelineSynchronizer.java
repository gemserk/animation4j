package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.timeline.sync.ObjectSynchronizer;

public class TimelineSynchronizer {
	
	private final ObjectSynchronizer objectSynchronizer;

	public TimelineSynchronizer(ObjectSynchronizer objectSynchronizer) {
		this.objectSynchronizer = objectSynchronizer;
	}
	
	/**
	 * Synchronizes the values of the time line with the current object. 
	 * @param time
	 */
	public void syncrhonize(TimelineIterator timelineIterator, float time) {
		while(timelineIterator.hasNext()) {
			TimelineValue<Object> timelineValue = timelineIterator.next();
			String name = timelineValue.getName();
			Object value = timelineValue.getValue(time);
			objectSynchronizer.setValue(name, value);
		}
	}

}