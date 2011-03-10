package com.gemserk.animation4j.timeline;

import java.util.Iterator;

public class TimelineSynchronizerIteratorImpl implements TimelineSynchronizerIterator {

	private final Timeline timeline;
	
	private Iterator<String> iterator;

	public TimelineSynchronizerIteratorImpl(Timeline timeline) {
		this.timeline = timeline;
		iterator = timeline.getTimelineValues().keySet().iterator();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> TimelineValue<T> next() {
		return timeline.getTimelineValues().get(iterator.next());
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}
	
}