package generator.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import generator.Mediator;
import generator.models.result.GeneratedObject;

public class ObjectsPreviewPanel extends PreviewPanel {
	private static final long serialVersionUID = -9061500843600921283L;
	private List<GeneratedObject> generatedObjects;

	public ObjectsPreviewPanel() {
		super();
	}

	public ObjectsPreviewPanel(BufferedImage image) {
		super(image);
		findMinMax();
	}

	public List<GeneratedObject> getGeneratedObjects() {
		return generatedObjects;
	}

	private int getObjectX(GeneratedObject i) {
		int mapWidth = (int) (image.getWidth() * Mediator.getMapWidth() / Mediator.getMapHeight());
		return (int) ((mapWidth / 2 - (i.getBasic().getX() * (mapWidth / Mediator.getMapWidth()))) * (1 + zoom) + currentPoint.x);
	}

	private int getObjectZ(GeneratedObject i) {
		int mapHeight = image.getHeight();
		return (int) ((mapHeight / 2 - (i.getBasic().getZ() * (mapHeight / Mediator.getMapHeight()))) * (1 + zoom)
				+ currentPoint.y);
	}

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
					g.fillRect(getObjectX(i), getObjectZ(i), 4, 4);
				}
			}
			drawPosition(g);
		}
	}

	public void setResultObject(List<GeneratedObject> list) {
		generatedObjects = list;
	}

}