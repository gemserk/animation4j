package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.gemserk.animation4j.Vector2f;
import com.gemserk.animation4j.Vector2fConverter;

@SuppressWarnings({ "rawtypes", "serial" })
public class TimelineTest {
	
	@Test
	public void shouldSetValuesForTheStartOfTheTimeline() {
		Vector2f position = new Vector2f(0f, 0f);
		
		final TimelineValue<Vector2f> timelineValue = Builders.timelineValue(position)
			.typeConverter(new Vector2fConverter()) //
			.keyFrame(0f, new Vector2f(50f, 50f)) //
			.keyFrame(1f, new Vector2f(100f, 50f)) //
			.build();
		
		Timeline timeline = new Timeline(new ArrayList<TimelineValue>(){{
			add(timelineValue);
		}});
		
		timeline.move(0f);
		
		assertEquals(50f, position.x, 0.01f);
		assertEquals(50f, position.y, 0.01f);
		
		timeline.move(-1f);
		
		assertEquals(50f, position.x, 0.01f);
		assertEquals(50f, position.y, 0.01f);
	}
	
	@Test
	public void shouldSetValuesForTheEndOfTheTimeline() {
		Vector2f position = new Vector2f(0f, 0f);
		
		final TimelineValue<Vector2f> timelineValue = Builders.timelineValue(position)
			.typeConverter(new Vector2fConverter()) //
			.keyFrame(0f, new Vector2f(50f, 50f)) //
			.keyFrame(1f, new Vector2f(100f, 75f)) //
			.build();
		
		Timeline timeline = new Timeline(new ArrayList<TimelineValue>(){{
			add(timelineValue);
		}});
		
		timeline.move(1f);
		
		assertEquals(100f, position.x, 0.01f);
		assertEquals(75f, position.y, 0.01f);
		
		timeline.move(2f);
		
		assertEquals(100f, position.x, 0.01f);
		assertEquals(75f, position.y, 0.01f);
	}
	
	@Test
	public void shouldSetValuesForTimeline() {
		Vector2f position = new Vector2f(0f, 0f);
		
		final TimelineValue<Vector2f> timelineValue = Builders.timelineValue(position)
			.typeConverter(new Vector2fConverter()) //
			.keyFrame(0f, new Vector2f(50f, 0f)) //
			.keyFrame(1f, new Vector2f(100f, 200f)) //
			.build();
		
		Timeline timeline = new Timeline(new ArrayList<TimelineValue>(){{
			add(timelineValue);
		}});
		
		timeline.move(0.5f);
		
		assertEquals(75f, position.x, 0.01f);
		assertEquals(100f, position.y, 0.01f);
	}

	@Test
	public void shouldNotGenerateGarbageWhenMovingTheTimeline() {

		// no new instances should be generated between interpolations when getting the value from the timeline.

		Vector2f position = new Vector2f(0f, 0f);
		
		final TimelineValue<Vector2f> timelineValue = Builders.timelineValue(position)
			.typeConverter(new Vector2fConverter()) //
			.keyFrame(0f, new Vector2f(50f, 0f)) //
			.keyFrame(1f, new Vector2f(100f, 200f)) //
			.build();
		

		Timeline timeline = new Timeline(new ArrayList<TimelineValue>(){{
			add(timelineValue);
		}});

		int instances = Vector2f.instances;
		int arrayInstances = Vector2fConverter.arrayInstances;

		System.out.println("vector2f.instances=" + instances);
		System.out.println("float[].instances=" + arrayInstances);

		for (int i = 0; i < 5; i++)
			timeline.move(i);

		System.out.println("==== after test ====");
		System.out.println("vector2f.instances=" + Vector2f.instances);
		System.out.println("float[].instances=" + Vector2fConverter.arrayInstances);
		
		assertEquals(instances, Vector2f.instances);
		assertEquals(arrayInstances, Vector2fConverter.arrayInstances);

	}

}
