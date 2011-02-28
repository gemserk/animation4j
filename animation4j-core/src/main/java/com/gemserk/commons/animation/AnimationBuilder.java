package com.gemserk.commons.animation;

public class AnimationBuilder {

	private final PropertyAnimation animation;

	public AnimationBuilder(String property) {
		this.animation = new PropertyAnimation(property);
	}

	public PropertyAnimation build() {
		return animation;
	}

	public void keyFrame(int time, Object value) {
		animation.addKeyFrame(time, value);
	}

}