package com.gemserk.componentsengine.java2d.render;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.google.inject.Inject;

public class Java2dRenderer {

	@Inject
	CurrentGraphicsProvider currentGraphicsProvider;

	@Inject
	Graphics2dHelper graphicsHelper;

	public void render(Java2dRenderObject renderObject) {

		currentGraphicsProvider.getGraphics();

		if (renderObject instanceof Java2dImageRenderObject) {
			imageRenderer.render((Java2dImageRenderObject) renderObject);
		}

	}

	@Inject
	Java2dImageRenderer imageRenderer;

	static class Java2dImageRenderer {

		@Inject
		CurrentGraphicsProvider currentGraphicsProvider;

		@Inject
		Graphics2dHelper graphicsHelper;

		public void render(Java2dImageRenderObject renderObject) {

			Graphics2D graphics = currentGraphicsProvider.getGraphics();
			Point2D position = renderObject.getPosition();
			Point2D size = renderObject.getSize();
			Image image = renderObject.getImage();
			double theta = renderObject.getTheta();
			final Color color = renderObject.getColor();

			graphicsHelper.pushTransform();
			{
				// TODO: render object parameters....
				RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				renderingHints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				graphics.setRenderingHints(renderingHints);

				AffineTransform tx = new AffineTransform();

				int width = image.getWidth(null);
				int height = image.getHeight(null);

				float x = (float) (position.getX() + ((float) -width) / 2f);
				float y = (float) (position.getY() + ((float) -height) / 2f);

				tx.concatenate(AffineTransform.getTranslateInstance(x + width / 2, y + height / 2));
				tx.concatenate(AffineTransform.getRotateInstance(theta));
				tx.concatenate(AffineTransform.getScaleInstance(size.getX(), size.getY()));
				tx.concatenate(AffineTransform.getTranslateInstance(-width / 2, -height / 2));

				graphics.transform(tx);

				Composite previousComposite = graphics.getComposite();
				// maybe if color is white avoid using the composite to improve performance.
				if (!Color.white.equals(color))
					graphics.setComposite(new ColorMultiplyComposite(color));
				graphics.drawImage(image, 0, 0, null);
				graphics.setComposite(previousComposite);

			}
			graphicsHelper.popTransform();

		}
	}

}