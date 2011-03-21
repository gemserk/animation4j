package com.gemserk.animation4j.timeline;

import java.util.ArrayList;
import java.util.Map;

/**
 * Provides a way to iterate through the values of a time line.
 * 
 * @author acoppes
 */
@SuppressWarnings("rawtypes")
public class TimelineIterator {

	private final Map<String, TimelineValue> values;

	private final ArrayList<String> keys = new ArrayList<String>();

	private int currentKey;

	TimelineIterator(Map<String, TimelineValue> values) {
		this.values = values;
		keys.addAll(values.keySet());
		currentKey = 0;
	}

	/**
	 * Returns the current element being iterated and move the iterator ahead.
	 */
	@SuppressWarnings("unchecked")
	public TimelineValue<Object> next() {
		return values.get(keys.get(currentKey++));
	}

	/**
	 * Returns true if there are more elements to iterate, false otherwise.
	 */
	public boolean hasNext() {
		return currentKey < keys.size();
	}

	/**
	 * Restarts the iteration from the first element.
	 */
	public void restart() {
		currentKey = 0;
	}

}