package com.gemserk.animation4j.timeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TimelineAnimationBuilder {

	Collection<TimelineValueBuilder> timelineValueBuilders = new ArrayList<TimelineValueBuilder>();
	
	private float delayTime = 0;

	private boolean started = false;

	private float speed = 1f;

	protected Timeline build() {

		Map<String, TimelineValue> timelineValues = new HashMap<String, TimelineValue>();

		float duration = 0;

		for (TimelineValueBuilder timelineValueBuilder : timelineValueBuilders) {
			timelineValues.put(timelineValueBuilder.getName(), timelineValueBuilder.build());
			duration = Math.max(duration, timelineValueBuilder.getDuration());
		}

		return new Timeline(duration, delayTime, timelineValues);
	}
	
	public TimelineAnimation buildAnimation() {
		TimelineAnimation timelineAnimation = new TimelineAnimation(build(), started);
		timelineAnimation.setSpeed(speed);
		return timelineAnimation;
	}

	public TimelineAnimationBuilder value(String name, TimelineValueBuilder timelineValueBuilder) {
		timelineValueBuilder.setName(name);
		timelineValueBuilders.add(timelineValueBuilder);
		return this;
	}

	public TimelineAnimationBuilder delay(float time) {
		this.delayTime = time;
		return this;
	}
	
	public TimelineAnimationBuilder started(boolean started) {
		this.started = started;
		return this;
	}
	
	public TimelineAnimationBuilder speed(float speed) {
		this.speed = speed;
		return this;
	}
	
}