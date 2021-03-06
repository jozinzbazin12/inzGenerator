package generator.listeners;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import generator.Mediator;
import generator.models.result.GeneratedObject;
import generator.panels.ObjectsPreviewPanel;
import generator.panels.PreviewPanel;

public class ImageListener extends MouseAdapter {
	private GeneratedObject currentObject;
	private boolean objectsPanel = false;
	private PreviewPanel panel;

	public ImageListener(PreviewPanel previewPanel) {
		this.panel = previewPanel;
		if (previewPanel instanceof ObjectsPreviewPanel) {
			objectsPanel = true;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point currentPoint = panel.getCurrentPoint();
		if (SwingUtilities.isRightMouseButton(e) && e.getX() > 0 && e.getY() > 0 && e.getX() < panel.getWidth()
				&& e.getY() < panel.getHeight()) {
			Point previousPoint = panel.getPreviousPoint();
			currentPoint.x += e.getPoint().x - previousPoint.x;
			currentPoint.y += e.getPoint().y - previousPoint.y;
			panel.setPreviousPoint(e.getPoint());
		}
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
		if (currentObject != null && SwingUtilities.isLeftMouseButton(e)) {
			((ObjectsPreviewPanel) panel).setPoint(e.getPoint());
			int mapWidth = (int) (Mediator.getMapDimensions().width * Mediator.getMapWidth() / Mediator.getMapHeight());
			int mapHeight = Mediator.getMapDimensions().height;
			currentObject.getBasic()
					.setX((Mediator.getMapWidth() * (-2 * e.getX() + panel.getZoom() * mapWidth + 2 * currentPoint.x + mapWidth))
							/ (2 * mapWidth * (panel.getZoom() + 1)));
			currentObject.getBasic()
					.setZ((Mediator.getMapHeight()
							* (-2 * e.getY() + panel.getZoom() * mapHeight + 2 * currentPoint.y + mapHeight))
							/ (2 * mapHeight * (panel.getZoom() + 1)));
			Mediator.setClicked(currentObject);
			panel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		panel.setPoint(e.getPoint());
		if (objectsPanel) {
			ObjectsPreviewPanel panel = (ObjectsPreviewPanel) this.panel;
			if (panel.getGeneratedObjects() != null) {
				for (GeneratedObject i : panel.getGeneratedObjects()) {
					int mapWidth = (int) (Mediator.getMapDimensions().width * Mediator.getMapWidth() / Mediator.getMapHeight());
					int mapHeight = Mediator.getMapDimensions().height;
					double x = (mapWidth / 2 - (i.getBasic().getX() * (mapWidth / Mediator.getMapWidth())))
							* (1 + panel.getZoom()) + panel.getCurrentPoint().x;
					double z = ((mapHeight / 2 - (i.getBasic().getZ() * (mapHeight / Mediator.getMapHeight())))
							* (1 + panel.getZoom()) + panel.getCurrentPoint().y);
					if (Math.abs(e.getX() - x) <= 5 && Math.abs(e.getY() - z) <= 5) {
						if (currentObject == i) {
							return;
						}
						Mediator.highlight(i);
						currentObject = i;
						return;
					}
				}
			}
		}
		currentObject = null;
		Mediator.unHighlight();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Point previousPoint = panel.getPreviousPoint();
			previousPoint.x = e.getX();
			previousPoint.y = e.getY();
		}
		if (SwingUtilities.isMiddleMouseButton(e)) {
			panel.setDefaultZoom();
			((JComponent) e.getSource()).repaint();
		}
		if (currentObject != null && SwingUtilities.isLeftMouseButton(e)) {
			Mediator.setClicked(currentObject);
			panel.repaint();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		panel.setZoom(panel.getZoom() - e.getUnitsToScroll() / 50d);
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
	}
}