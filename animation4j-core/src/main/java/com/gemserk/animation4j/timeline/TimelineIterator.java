package com.gemserk.animation4j.timeline;

import java.util.ArrayList;
import java.util.Map;

/**
 * Provides a way to iterate through the elements of a time line.
 * 
 * @author acoppes
 */
@SuppressWarnings("rawtypes")
public class TimelineIterator {

	private ArrayList<String> keys = new ArrayList<String>();

	private int currentKey;

	private final Map<String, TimelineValue> values;

	TimelineIterator(Map<String, TimelineValue> values) {
		this.values = values;
		keys.addAll(values.keySet());
		currentKey = 0;
	}

	@SuppressWarnings("unchecked")
	public TimelineValue<Object> next() {
		return values.get(keys.get(currentKey++));
	}

	public boolean hasNext() {
		return currentKey < keys.size();
	}

	public void restart() {
		currentKey = 0;
	}

}