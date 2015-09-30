package generator.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import generator.Mediator;
import generator.models.result.GeneratedObject;

public class ObjectsPreviewPanel extends PreviewPanel {
	private static final long serialVersionUID = -9061500843600921283L;
	private List<GeneratedObject> generatedObjects;
	private double posX, posY, posZ;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.setColor(Color.red);
			g.drawLine(width / 2 + currentPoint.x, currentPoint.y - 1000, width / 2 + currentPoint.x,
					height + currentPoint.y + 1000);
			g.drawLine(currentPoint.x - 1000, height / 2 + currentPoint.y, width + currentPoint.x + 1000,
					height / 2 + currentPoint.y);
			if (generatedObjects != null) {
				for (GeneratedObject i : generatedObjects) {
					g.setColor(i.getColor());
					g.fillRect(getObjectX(i), getObjectY(i), 4, 4);
				}
			}
			g.setColor(Color.BLACK);
			int y = getHeight() - 10;

			g.drawString("X: " + posX, 5, y);
			g.drawString("Y: " + posY, (int) (getWidth() * 0.45 - 20), y);
			g.drawString("Z: " + posZ, (int) (getWidth() * 0.75 + 20), y);
		}
	}

	private int getObjectY(GeneratedObject i) {
		int mapHeight = image.getHeight();
		return (int) ((mapHeight / 2 - (i.getBasic().getZ() * (mapHeight / Mediator.getMapHeight()))) * (1 + zoom)
				+ currentPoint.y);
	}

	private int getObjectX(GeneratedObject i) {
		int mapWidth = image.getWidth();
		return (int) (((i.getBasic().getX() * (mapWidth / Mediator.getMapWidth())) + mapWidth / 2) * (1 + zoom) + currentPoint.x);
	}

	public double getPointXAt(Point p) {
		int mapWidth = image.getWidth();
		return (Mediator.getMapWidth() * (-2 * p.getX() + zoom * mapWidth + 2 * currentPoint.x + mapWidth))
				/ (-2 * mapWidth * (zoom + 1));
	}

	public double getPointZAt(Point p) {
		int mapHeight = image.getHeight();

		return (Mediator.getMapHeight() * (-2 * p.getY() + zoom * mapHeight + 2 * currentPoint.y + mapHeight))
				/ (2 * mapHeight * (zoom + 1));
	}

	public void setPoint(Point p) {
		posX = getPointXAt(p);
		posZ = getPointZAt(p);

		int x = (int) (posX / (Mediator.getMapWidth() / image.getWidth())) + image.getWidth() / 2;
		int y = (int) -(posZ / (Mediator.getMapHeight() / image.getHeight())) + image.getHeight() / 2;
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
			posY = getColor(x, y);
		}
		repaint();
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