package com.gemserk.animation4j.timeline;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class TimelineIterator implements Iterator<TimelineValue> {

	private final Timeline timeline;
	
	private Iterator<String> iterator;

	public TimelineIterator(Timeline timeline) {
		this.timeline = timeline;
		iterator = timeline.getTimelineValues().keySet().iterator();
	}
	
	@Override
	public TimelineValue next() {
		return timeline.getTimelineValues().get(iterator.next());
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public void remove() {
		throw new RuntimeException("not implemented yet");
	}
	
}