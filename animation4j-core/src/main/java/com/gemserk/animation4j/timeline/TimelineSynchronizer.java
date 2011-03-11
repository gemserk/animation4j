package com.gemserk.animation4j.timeline;

import com.gemserk.animation4j.timeline.sync.ObjectSynchronizer;

public class TimelineSynchronizer {
	
	/**
	 * Synchronizes the values of the time line with the current object. 
	 * @param time
	 */
	public void syncrhonize(TimelineSynchronizerIterator timelineSynchronizerIterator, ObjectSynchronizer objectSynchronizer, float time) {
		while(timelineSynchronizerIterator.hasNext()) {
			TimelineValue<Object> timelineValue = timelineSynchronizerIterator.next();
			String name = timelineValue.getName();
			Object value = timelineValue.getValue(time);
			objectSynchronizer.setValue(name, value);
		}
	}

}