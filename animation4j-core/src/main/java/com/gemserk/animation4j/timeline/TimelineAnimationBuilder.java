package com.gemserk.animation4j.timeline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This builder class gives the user an easy way to build time line animations.
 * 
 * @author acoppes
 */
@SuppressWarnings("rawtypes")
@Deprecated
public class TimelineAnimationBuilder {

	Collection<TimelineValueBuilder> timelineValueBuilders = new ArrayList<TimelineValueBuilder>();

	private float delay = 0;

	private boolean started = false;

	private float speed = 1f;

	private float calculateDuration() {
		float duration = 0;
		for (TimelineValueBuilder timelineValueBuilder : timelineValueBuilders)
			duration = Math.max(duration, timelineValueBuilder.getDuration());
		return duration;
	}

	private Timeline buildTimeline() {
		ArrayList<TimelineValue> values = new ArrayList<TimelineValue>();
		// Map<String, TimelineValue> timelineValues = new HashMap<String, TimelineValue>();
		for (TimelineValueBuilder timelineValueBuilder : timelineValueBuilders)
			// timelineValues.put(timelineValueBuilder.getName(), timelineValueBuilder.build());
			values.add(timelineValueBuilder.build());
		return new Timeline(values);
	}

	/**
	 * Builds and return the time line animation built.
	 * 
	 * @return The time line animation.
	 */
	public TimelineAnimation build() {
		TimelineAnimation timelineAnimation = new TimelineAnimation(buildTimeline(), calculateDuration(), started);
		timelineAnimation.setSpeed(speed);
		timelineAnimation.setDelay(delay);
		// timelineAnimation.setDuration(calculateDuration());
		return timelineAnimation;
	}

	/**
	 * Specifies a new time line value.
	 * 
	 * @param name
	 *            The name of the value in the time line.
	 * @param timelineValueBuilder
	 *            The time line value builder in order to build the time line value.
	 * @return The builder in order to let the user make methods chaining.
	 */
	public TimelineAnimationBuilder value(String name, TimelineValueBuilder timelineValueBuilder) {
		timelineValueBuilder.setName(name);
		timelineValueBuilders.add(timelineValueBuilder);
		return this;
	}

	/**
	 * Specifies the delay to start of the animation.
	 * 
	 * @param time
	 *            The delay in seconds.
	 * @return The builder in order to let the user make methods chaining.
	 */
	public TimelineAnimationBuilder delay(float time) {
		this.delay = time;
		return this;
	}

	/**
	 * Specifies the delay to start of the animation.
	 * 
	 * @param time
	 *            The delay in ms.
	 * @return The builder in order to let the user make methods chaining.
	 */
	public TimelineAnimationBuilder delay(int time) {
		this.delay = (float) time * 0.001f;
		return this;
	}

	/**
	 * Specifies if the animation should be started or not.
	 * 
	 * @param started
	 *            true if the animation should be started, false otherwise.
	 * @return The builder in order to let the user make methods chaining.
	 */
	public TimelineAnimationBuilder started(boolean started) {
		this.started = started;
		return this;
	}

	/**
	 * Specifies the speed of the animation, by default 1x.
	 * 
	 * @param speed
	 *            The speed of the animaiton.
	 * @return The builder in order to let the user make methods chaining.
	 */
	public TimelineAnimationBuilder speed(float speed) {
		this.speed = speed;
		return this;
	}

}