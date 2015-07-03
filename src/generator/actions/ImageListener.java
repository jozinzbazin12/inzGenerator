package generator.actions;

import generator.panels.PreviewPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class ImageListener extends MouseAdapter {
	private PreviewPanel panel;

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			panel.getPreviousPoint().x = e.getX();
			panel.getPreviousPoint().y = e.getY();
		}
		if (SwingUtilities.isRightMouseButton(e)) {
			panel.setDefaultZoom();
			((JComponent) e.getSource()).repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && e.getX() > 0 && e.getY() > 0 && e.getX() < panel.getWidth() && e.getY() < panel.getHeight()) {
			panel.getCurrentPoint().x += (e.getPoint().x - panel.getPreviousPoint().x) / 20;
			panel.getCurrentPoint().y += (e.getPoint().y - panel.getPreviousPoint().y) / 20;
		}
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		panel.setZoom(panel.getZoom()- e.getUnitsToScroll() / 250d);
		if (e.getSource() instanceof JComponent) {
			((JComponent) e.getSource()).repaint();
		}
	}

	public ImageListener(PreviewPanel panel) {
		this.panel = panel;
	}
}