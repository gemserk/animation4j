package com.gemserk.animation4j.timeline;

import java.util.Iterator;

/**
 * Provides a way to iterate through items of a time line.
 * @author acoppes
 */
public class TimelineIterator implements Iterator<TimelineValue<Object>> {

	private final Timeline timeline;
	
	private Iterator<String> iterator;

	public TimelineIterator(Timeline timeline) {
		this.timeline = timeline;
		iterator = timeline.getTimelineValues().keySet().iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TimelineValue<Object> next() {
		return timeline.getTimelineValues().get(iterator.next());
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public void remove() {
		throw new RuntimeException("not implemented yet, dunno if we should let remove items from a time line");
	}
	
}