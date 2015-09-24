package generator.listeners;

import generator.Mediator;
import generator.models.result.GeneratedObject;
import generator.panels.ObjectsPreviewPanel;
import generator.panels.PreviewPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ImageListener extends MouseAdapter {
	private PreviewPanel panel;
	private GeneratedObject currentObject;
	private boolean objectsPanel = false;

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			panel.getPreviousPoint().x = e.getX();
			panel.getPreviousPoint().y = e.getY();
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
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e) && e.getX() > 0 && e.getY() > 0 && e.getX() < panel.getWidth()
				&& e.getY() < panel.getHeight()) {
			panel.getCurrentPoint().x += (e.getPoint().x - panel.getPreviousPoint().x) / 20;
			panel.getCurrentPoint().y += (e.getPoint().y - panel.getPreviousPoint().y) / 20;
		}
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
		if (currentObject != null && SwingUtilities.isLeftMouseButton(e)) {
			int mapWidth = Mediator.getMapDimensions().width;
			int mapHeight = Mediator.getMapDimensions().height;
			currentObject.getBasic().setX((Mediator.getMapWidth()
					* (-2 * e.getX() + panel.getZoom() * mapWidth + 2 * panel.getCurrentPoint().x + mapWidth))
					/ (-2 * mapWidth * (panel.getZoom() + 1)));
			currentObject.getBasic().setZ((Mediator.getMapHeight()
					* (-2 * e.getY() + panel.getZoom() * mapHeight + 2 * panel.getCurrentPoint().y + mapHeight))
					/ (2 * mapHeight * (panel.getZoom() + 1)));
			Mediator.setClicked(currentObject);
			panel.repaint();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		panel.setZoom(panel.getZoom() - e.getUnitsToScroll() / 250d);
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
	}

	public ImageListener(PreviewPanel previewPanel) {
		this.panel = previewPanel;
		if (previewPanel instanceof ObjectsPreviewPanel) {
			objectsPanel = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (objectsPanel) {
			ObjectsPreviewPanel panel = (ObjectsPreviewPanel) this.panel;
			if (panel.getGeneratedObjects() != null) {
				for (GeneratedObject i : panel.getGeneratedObjects()) {
					int mapWidth = Mediator.getMapDimensions().width;
					int mapHeight = Mediator.getMapDimensions().height;
					double x = ((i.getBasic().getX() * (mapWidth / Mediator.getMapWidth())) + mapWidth / 2)
							* (1 + panel.getZoom()) + panel.getCurrentPoint().x;
					double z = ((mapHeight / 2 - (i.getBasic().getZ() * (mapHeight / Mediator.getMapHeight())))
							* (1 + panel.getZoom()) + panel.getCurrentPoint().y);
					if (absolute(e.getX(), x) <= 5 && absolute(e.getY(), z) <= 5) {
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

	private double absolute(double a, double b) {
		if (a < 0) {
			a *= -1;
		}
		if (b < 0) {
			b *= -1;
		}
		return a - b > 0 ? a - b : b - a;
	}
}