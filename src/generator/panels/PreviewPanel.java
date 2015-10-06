package generator.panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import generator.listeners.ImageListener;

public class PreviewPanel extends JPanel {

	private static final long serialVersionUID = 5712708696866492460L;
	protected BufferedImage image;
	protected Point currentPoint;
	protected Point previousPoint = new Point();
	protected double zoom = 0;
	protected int width;
	protected int height;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			drawImage(g);
		}
	}

	protected void drawImage(Graphics g) {
		if (currentPoint == null) {
			setDefaultZoom();
		}
		width = getResizedWidth();
		height = getResizedHeight();
		if (width <= 0) {
			width = 1;
		}
		if (height <= 0) {
			height = 1;
		}
		Image scaledInstance = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
		g.drawImage(scaledInstance, (int) currentPoint.getX(), (int) currentPoint.getY(), null);
	}

	protected int getResizedHeight() {
		return (int) (image.getHeight() + image.getHeight() * zoom);
	}

	protected int getResizedWidth() {
		return (int) (image.getWidth() + image.getWidth() * zoom);
	}

	public void setDefaultZoom() {
		double dw = image.getWidth() - getWidth();
		double dh = image.getHeight() - getHeight();
		zoom = -Math.max(dw / image.getWidth(), dh / image.getHeight());
		currentPoint = new Point((getWidth() - getResizedWidth()) / 2, (getHeight() - getResizedHeight()) / 2);
	}

	public PreviewPanel(BufferedImage image) {
		this.image = image;
		ImageListener listener = new ImageListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		addMouseWheelListener(listener);
		revalidate();
	}

	public int getColor(int x, int y) {
		int rgb = image.getRGB(x, y);
		int r = (rgb >> 16) & 0x000000FF;
		int g = (rgb >> 8) & 0x000000FF;
		int b = (rgb) & 0x000000FF;
		return (r + g + b) / 3;
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

	public BufferedImage getImage() {
		return image;
	}
}