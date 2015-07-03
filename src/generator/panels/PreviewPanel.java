package generator.panels;

import generator.actions.ImageListener;
import generator.models.result.GeneratedObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

public class PreviewPanel extends JPanel {
	private static final long serialVersionUID = -9061500843600921283L;
	// private static final int BORDER = 10;
	private BufferedImage image;
	private Point currentPoint;
	private Point previousPoint = new Point();
	private double zoom = 0;
	private List<GeneratedObject> generatedObjects;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			if (currentPoint == null)
				setDefaultZoom();
			int width = getResizedWidth();
			int height = getResizedHeight();
			if (width <= 0)
				width = 1;
			if (height <= 0)
				height = 1;
			Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			g.drawImage(scaledInstance, (int) currentPoint.getX(), (int) currentPoint.getY(), null);
			g.setColor(Color.red);
			g.drawLine(width / 2 + currentPoint.x, currentPoint.y - 1000, width / 2 + currentPoint.x, height + currentPoint.y + 1000);
			g.drawLine(currentPoint.x - 1000, height / 2 + currentPoint.y, width + currentPoint.x + 1000, height / 2 + currentPoint.y);
			if (generatedObjects != null) {
				for (GeneratedObject i : generatedObjects) {
					g.fillRect((int) (i.getBasic().getX() + i.getBasic().getX() * zoom + currentPoint.x), (int) (i.getBasic().getZ() + i.getBasic().getZ() * zoom + currentPoint.y), 4, 4);
				}
			}
		}

	}

	private int getResizedHeight() {
		return (int) (image.getHeight() + image.getHeight() * zoom);
	}

	private int getResizedWidth() {
		return (int) (image.getWidth() + image.getWidth() * zoom);
	}

	public void setDefaultZoom() {
		double dw = image.getWidth() - getWidth();
		double dh = image.getHeight() - getHeight();
		zoom = -Math.max(dw / image.getWidth(), dh / image.getHeight());
		currentPoint = new Point((getWidth() - getResizedWidth()) / 2, (getHeight() - getResizedHeight()) / 2);
	}

	// @Override
	// public int getHeight() {
	// return super.getHeight() - BORDER;
	// }
	//
	// @Override
	// public int getWidth() {
	// return super.getWidth() - BORDER;
	// }

	public PreviewPanel(BufferedImage image) throws IOException {	
		this.image=image;
		ImageListener listener = new ImageListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		addMouseWheelListener(listener);
		revalidate();
	}

	public PreviewPanel() {
		super();
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	public Point getPreviousPoint() {
		return previousPoint;
	}

	public void setPreviousPoint(Point previousPoint) {
		this.previousPoint = previousPoint;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

	public void setResultObject(List<GeneratedObject> list) {
		generatedObjects = list;
	}

}