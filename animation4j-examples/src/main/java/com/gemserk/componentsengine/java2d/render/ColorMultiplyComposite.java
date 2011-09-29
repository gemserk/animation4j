package com.gemserk.componentsengine.java2d.render;

import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class ColorMultiplyComposite implements Composite {

	private final Color color;

	public ColorMultiplyComposite(Color color) {
		this.color = color;
	}

	public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
		return new BlendingContext(color);
	}

	static class BlendingContext implements CompositeContext {

		private final Color color;

		public BlendingContext(Color color) {
			this.color = color;
		}

		public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {

			int width = Math.min(src.getWidth(), dstIn.getWidth());
			int height = Math.min(src.getHeight(), dstIn.getHeight());

			float[] dstOutColors = new float[4];
			float[] srcColors = new float[4];
			float[] dstInColors = new float[4];

			if (color.getAlpha() == 0) {
				dstOut.setRect(dstIn);
				return;
			}

			float colorAlpha = ((float) color.getAlpha()) / 255f;
			float colorRed = ((float) color.getRed()) / 255f;
			float colorGreen = ((float) color.getGreen()) / 255f;
			float colorBlue = ((float) color.getBlue()) / 255f;

			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					src.getPixel(x, y, srcColors);
					dstIn.getPixel(x, y, dstInColors);

					float a = colorAlpha * (srcColors[3] / 255f);
					float r = colorRed;
					float g = colorGreen;
					float b = colorBlue;

					dstOutColors[0] = srcColors[0] * a * r + dstInColors[0] * (1 - a);
					dstOutColors[1] = srcColors[1] * a * g + dstInColors[1] * (1 - a);
					dstOutColors[2] = srcColors[2] * a * b + dstInColors[2] * (1 - a);
					dstOutColors[3] = srcColors[3] * a; 

					dstOut.setPixel(x, y, dstOutColors);
				}
			}
		}

		public void dispose() {

		}
	}
}
