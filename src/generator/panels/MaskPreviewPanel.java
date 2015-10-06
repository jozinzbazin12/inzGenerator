package generator.panels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;

public class MaskPreviewPanel extends PreviewPanel {

	private static final long serialVersionUID = 2455148254602790811L;
	private BufferedImage originalMask;
	private boolean bnw;
	private JSlider transparency;

	public MaskPreviewPanel() {
	}

	public MaskPreviewPanel(BufferedImage img, boolean bnw) {
		super(img);
		this.bnw = bnw;
		setLayout(new BorderLayout());
		transparency = new JSlider(JSlider.VERTICAL, 0, 100, 50);
		transparency.setMaximumSize(new Dimension(50, 500));
		add(transparency, BorderLayout.WEST);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (originalMask != null) {
			int w = getResizedWidth();
			int h = getResizedHeight();
			Image mask = originalMask.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
			BufferedImage mask2;
			if (bnw) {
				mask2 = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
			} else {
				mask2 = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
			}
			Graphics2D g2d = mask2.createGraphics();
			g2d.drawImage(mask, 0, 0, this);
			g2d.dispose();
			mask = mask2;

			float opacity = 1 - transparency.getValue() / 100f;
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
			g.drawImage(mask, (int) currentPoint.getX(), (int) currentPoint.getY(), null);
		}
	}

	public void setMask(BufferedImage img) {
		originalMask = img;
	}

	public void deleteMask() {
		originalMask = null;
	}

	public BufferedImage getMask() {
		return originalMask;
	}
}
