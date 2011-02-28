package com.gemserk.commons.animation;

import java.awt.Point;
import java.util.HashMap;

import org.junit.Test;

import com.gemserk.commons.animation.timeline.Timeline;
import com.gemserk.commons.animation.timeline.TimelineAnimation;
import com.gemserk.commons.animation.timeline.TimelineValue;


public class BuilderTest {

	
	static class KeyFrame {
		
	}
	
	static class KeyFrameBuilder {
		
		static float FROM = 0f;
		
		static float TO = 1f;
		
		public KeyFrameBuilder(float start) {
			
		}

		KeyFrame build() {
			return new KeyFrame();
		}
		
		void property(String name, Object value) {
			
		}
		
	}
	
	static class AnimationBuilder {
		
		final String id;
		
		final float duration;
		
		public AnimationBuilder(String id, float duration) {
			this.id = id;
			this.duration = duration;
		}
		
		TimelineAnimation build() {
			
			HashMap<String, TimelineValue> timelineValues = new HashMap<String, TimelineValue>();
			
			return new TimelineAnimation(new Timeline(duration, 0, timelineValues));
		}
	
		void keyFrame(KeyFrame keyFrame) {
			
		}
		
	}
	
	@Test
	public void nothing() {
		
		TimelineAnimation animation = new AnimationBuilder("bounce", 1500) {{
			
			keyFrame(new KeyFrameBuilder(KeyFrameBuilder.FROM) {{
				property("position", new Point(100, 100));
				property("alpha", 0f);
			}}.build());

			keyFrame(new KeyFrameBuilder(KeyFrameBuilder.TO) {{
				property("position", new Point(200, 200));
				property("alpha", 1f);
			}}.build());

		}}.build();
		
	}
	
}
