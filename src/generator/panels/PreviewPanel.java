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
	protected Point currentPoint;
	protected int height;
	protected BufferedImage image;
	protected int maxY;
	protected int minY;
	protected boolean positionEnabled = false;
	protected double posX, posY, posZ;
	protected Point previousPoint = new Point();
	protected int width;
	protected double zoom = 0;

	public PreviewPanel() {
		super();
	}

	public PreviewPanel(BufferedImage image) {
		this(image, false);
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

	public static int getColor(BufferedImage img, int x, int y) {
		int rgb = img.getRGB(x, y);
		int r = (rgb >> 16) & 0x000000FF;
		int g = (rgb >> 8) & 0x000000FF;
		int b = (rgb) & 0x000000FF;
		return (r + g + b) / 3;
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

	protected void drawPosition(Graphics g) {
		g.setColor(Color.BLACK);
		int y = getHeight() - 10;

		g.drawString("X: " + posX, 5, y);
		g.drawString("Y: " + posY, (int) (getWidth() * 0.45 - 20), y);
		g.drawString("Z: " + posZ, (int) (getWidth() * 0.75 + 20), y);
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

	public int getColor(int x, int y) {
		return getColor(image, x, y);
	}

	public Point getCurrentPoint() {
		return currentPoint;
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

	public double getPointXAt(Point p) {
		if (p == null) {
			return 0;
		}
		double ratio = Mediator.getMapHeight() / Mediator.getMapWidth();
		int mapWidth = image.getWidth();
		return (Mediator.getMapWidth() * (-2 * p.getX() * ratio + zoom * mapWidth + 2 * currentPoint.x * ratio + mapWidth))
				/ (2 * mapWidth * (zoom + 1));
	}

	public double getPointZAt(Point p) {
		int mapHeight = image.getHeight();
		return (Mediator.getMapHeight() * (-2 * p.getY() + zoom * mapHeight + 2 * currentPoint.y + mapHeight))
				/ (2 * mapHeight * (zoom + 1));
	}

	public Point getPreviousPoint() {
		return previousPoint;
	}

	protected int getResizedHeight() {
		int i = (int) (image.getHeight() + image.getHeight() * zoom);
		return i > 0 ? i : 1;
	}

	protected int getResizedWidth() {
		int i = (int) ((int) (image.getWidth() + image.getWidth() * zoom) * Mediator.getMapWidth() / Mediator.getMapHeight());
		return i > 0 ? i : 1;
	}

	public double getZoom() {
		return zoom;
	}

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

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	public void setDefaultZoom() {
		double h = image.getWidth() * Mediator.getMapWidth() / Mediator.getMapHeight();
		double dw = h - getWidth();
		double dh = image.getHeight() - getHeight();
		zoom = -Math.max(dw / h, dh / image.getHeight());
		currentPoint = new Point((getWidth() - getResizedWidth()) / 2, (getHeight() - getResizedHeight()) / 2);
	}

	public void setPoint(Point p) {
		posX = getPointXAt(p);
		posZ = getPointZAt(p);

		int x = (int) -(posX / (Mediator.getMapWidth() / image.getWidth())) + image.getWidth() / 2;
		int y = (int) -(posZ / (Mediator.getMapHeight() / image.getHeight())) + image.getHeight() / 2;
		if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
			posY = (getColor(x, y) - 128) * (Mediator.getMapMaxYSetting() / (maxY - 128));
		}
		repaint();
	}

	public void setPreviousPoint(Point previousPoint) {
		this.previousPoint = previousPoint;
	}

	public void setZoom(double zoom) {
		if (zoom < -1) {
			this.zoom = -1;
			return;
		}
		this.zoom = zoom;
	}
}