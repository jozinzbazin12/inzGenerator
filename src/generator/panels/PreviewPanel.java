package generator.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import generator.Mediator;
import generator.listeners.ImageListener;

public class PreviewPanel extends JPanel {

	private static final long serialVersionUID = 5712708696866492460L;
	protected BufferedImage image;
	protected Point currentPoint;
	protected Point previousPoint = new Point();
	protected double zoom = 0;
	protected int width;
	protected int height;
	protected boolean positionEnabled = false;
	protected double posX, posY, posZ;
	protected int maxY;
	protected int minY;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			drawImage(g);
		}
		if (positionEnabled) {
			drawPosition(g);
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

	public PreviewPanel(BufferedImage image, boolean showPosition) {
		this.image = image;
		this.positionEnabled = showPosition;
		ImageListener listener = new ImageListener(this);
		addMouseMotionListener(listener);
		addMouseListener(listener);
		addMouseWheelListener(listener);
		if (showPosition) {
			findMinMax();
		}
		revalidate();
	}

	public PreviewPanel(BufferedImage image) {
		this(image, false);
	}

	protected void drawPosition(Graphics g) {
		g.setColor(Color.BLACK);
		int y = getHeight() - 10;

		g.drawString("X: " + posX, 5, y);
		g.drawString("Y: " + posY, (int) (getWidth() * 0.45 - 20), y);
		g.drawString("Z: " + posZ, (int) (getWidth() * 0.75 + 20), y);
	}

	public void setPoint(Point p) {
		posX = getPointXAt(p);
		posZ = getPointZAt(p);

		int x = (int) (posX / (Mediator.getMapWidth() / image.getWidth())) + image.getWidth() / 2;
		int y = (int) -(posZ / (Mediator.getMapHeight() / image.getHeight())) + image.getHeight() / 2;
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
			posY = getColor(x, y) * Mediator.getMapMaxYSetting() / maxY;
		}
		repaint();
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

	public int getColor(int x, int y) {
		return getColor(image, x, y);
	}

	protected void findMinMax() {
		int w = image.getWidth();
		int h = image.getHeight();
		minY = Integer.MAX_VALUE;
		maxY = Integer.MIN_VALUE;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int value = getColor(i, j);
				if (value < minY) {
					minY = value;
				}
				if (value > maxY) {
					maxY = value;
				}
			}
		}
	}

	public static int getColor(BufferedImage img, int x, int y) {
		int rgb = img.getRGB(x, y);
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

	public int getMaxY() {
		return maxY;
	}

	public int getMinY() {
		return minY;
	}
}