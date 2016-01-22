package generator.panels;

import java.awt.Point;
import java.awt.image.BufferedImage;

public class TexturePreveiwPanel extends PreviewPanel {

	private static final long serialVersionUID = -4662072342848367858L;

	public TexturePreveiwPanel(BufferedImage pic) {
		super(pic);
	}

	@Override
	protected int getResizedWidth() {
		return (int) (image.getWidth() + image.getWidth() * zoom);
	}

	@Override
	public void setDefaultZoom() {
		double h = image.getWidth();
		double dw = h - getWidth();
		double dh = image.getHeight() - getHeight();
		zoom = -Math.max(dw / h, dh / image.getHeight());
		currentPoint = new Point((getWidth() - getResizedWidth()) / 2, (getHeight() - getResizedHeight()) / 2);
	}
}
