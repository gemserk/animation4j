package com.gemserk.animation4j.examples;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage load(String file) {
		try {
			return ImageIO.read(ImageUtils.class.getClassLoader().getResourceAsStream(file));
		} catch (Exception e) {
			throw new RuntimeException("failed to get image from " + file, e);
		}
	}

}
