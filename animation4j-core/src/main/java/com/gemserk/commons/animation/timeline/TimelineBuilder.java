package com.gemserk.commons.animation.timeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TimelineBuilder {

	Collection<TimelineValueBuilder> timelineValueBuilders = new ArrayList<TimelineValueBuilder>();
	
	private float delayTime = 0;

	public Timeline build() {

		Map<String, TimelineValue> timelineValues = new HashMap<String, TimelineValue>();

		float duration = 0;

		for (TimelineValueBuilder timelineValueBuilder : timelineValueBuilders) {
			timelineValues.put(timelineValueBuilder.getName(), timelineValueBuilder.build());
			duration = Math.max(duration, timelineValueBuilder.getDuration());
		}

		return new Timeline(duration, delayTime, timelineValues);
	}

	public void value(String name, TimelineValueBuilder timelineValueBuilder) {
		timelineValueBuilder.setName(name);
		timelineValueBuilders.add(timelineValueBuilder);
	}

	public void delay(float time) {
		this.delayTime = time;
	}
}