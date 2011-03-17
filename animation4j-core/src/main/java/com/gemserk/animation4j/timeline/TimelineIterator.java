package com.gemserk.animation4j.timeline;

import java.util.ArrayList;

/**
 * Provides a way to iterate through items of a time line.
 * @author acoppes
 */
public class TimelineIterator {

	private final Timeline timeline;
	
	private ArrayList<String> keys = new ArrayList<String>();

	private int currentKey;

	public TimelineIterator(Timeline timeline) {
		this.timeline = timeline;
		keys.addAll(timeline.getTimelineValues().keySet());
		currentKey = 0;
	}
	
	@SuppressWarnings("unchecked")
	public TimelineValue<Object> next() {
		return timeline.getTimelineValue(keys.get(currentKey++));
	}

	public boolean hasNext() {
		return currentKey < keys.size();
	}

}