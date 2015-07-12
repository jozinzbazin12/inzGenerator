package generator.listeners;

import generator.Mediator;
import generator.models.result.GeneratedObject;
import generator.panels.PreviewPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ImageListener extends MouseAdapter {
	private PreviewPanel panel;
	private GeneratedObject currentObject;

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
			Mediator.refreshObjects();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e) && e.getX() > 0 && e.getY() > 0 && e.getX() < panel.getWidth() && e.getY() < panel.getHeight()) {
			panel.getCurrentPoint().x += (e.getPoint().x - panel.getPreviousPoint().x) / 20;
			panel.getCurrentPoint().y += (e.getPoint().y - panel.getPreviousPoint().y) / 20;
		}
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
		if (currentObject != null && SwingUtilities.isLeftMouseButton(e)) {
			currentObject.getBasic().setX((e.getX()-panel.getCurrentPoint().x)/(1+panel.getZoom()));
			currentObject.getBasic().setZ((e.getY()-panel.getCurrentPoint().y)/(1+panel.getZoom()));
			Mediator.setClicked(currentObject);
			panel.repaint();
			Mediator.refreshObjects();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		panel.setZoom(panel.getZoom() - e.getUnitsToScroll() / 250d);
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
	}

	public ImageListener(PreviewPanel panel) {
		this.panel = panel;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentObject == null) {
			for (GeneratedObject i : panel.getGeneratedObjects()) {
				double x = i.getBasic().getX() + i.getBasic().getX() * panel.getZoom() + panel.getCurrentPoint().x;
				double z = i.getBasic().getZ() + i.getBasic().getZ() * panel.getZoom() + panel.getCurrentPoint().y;
				if (absolute(e.getX(), x) <= 4 && absolute(e.getY(), z) <= 4) {
					Mediator.highlight(i);
					currentObject = i;
					return;
				}
			}
		}
		currentObject = null;
		Mediator.unHighlight();
	}

	private double absolute(double a, double b) {
		if (a < 0)
			a *= -1;
		if (b < 0)
			b *= -1;
		return a - b > 0 ? a - b : b - a;
	}
}