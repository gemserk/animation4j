package com.gemserk.animation4j.timeline;

/**
 * Iterates over the values of a time line.
 */
public interface TimelineSynchronizerIterator  {
	
	<T> TimelineValue<T> next();
	
	boolean hasNext(); 
	
	// TODO: integrate with iterator interface.
	
}