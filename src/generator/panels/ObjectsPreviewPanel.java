package generator.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import generator.models.result.GeneratedObject;

public class ObjectsPreviewPanel extends PreviewPanel {
	private static final long serialVersionUID = -9061500843600921283L;
	private List<GeneratedObject> generatedObjects;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			drawImage(g);
			g.setColor(Color.red);
			g.drawLine(width / 2 + currentPoint.x, currentPoint.y - 1000, width / 2 + currentPoint.x,
					height + currentPoint.y + 1000);
			g.drawLine(currentPoint.x - 1000, height / 2 + currentPoint.y, width + currentPoint.x + 1000,
					height / 2 + currentPoint.y);
			if (generatedObjects != null) {
				for (GeneratedObject i : generatedObjects) {
					g.setColor(i.getColor());
					g.fillRect((int) ((i.getBasic().getX() + image.getWidth() / 2) * (1 + zoom) + currentPoint.x),
							(int) ((image.getHeight() / 2 - i.getBasic().getZ()) * (1 + zoom) + currentPoint.y), 4, 4);
				}
			}
		}

	}

	public void setDefaultZoom() {
		double dw = image.getWidth() - getWidth();
		double dh = image.getHeight() - getHeight();
		zoom = -Math.max(dw / image.getWidth(), dh / image.getHeight());
		currentPoint = new Point((getWidth() - getResizedWidth()) / 2, (getHeight() - getResizedHeight()) / 2);
	}

	public ObjectsPreviewPanel(BufferedImage image) throws IOException {
		super(image);
	}

	public ObjectsPreviewPanel() {
		super();
	}

	public void setResultObject(List<GeneratedObject> list) {
		generatedObjects = list;
	}

	public List<GeneratedObject> getGeneratedObjects() {
		return generatedObjects;
	}

}