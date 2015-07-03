package generator.panels;

import generator.Mediator;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SecondTabPanel extends JPanel {

	private static final long serialVersionUID = -2087487239161953473L;

	private PreviewPanel previewPanel;
	private Dimension imageSize;

	public File addPreview(String imgName) throws IOException {
		remove(previewPanel);
		File file = new File(imgName);
		BufferedImage image = ImageIO.read(file);
		if (image == null)
			throw new IOException();
		imageSize=new Dimension(image.getWidth(), image.getHeight());
		previewPanel = new PreviewPanel(image);
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);
		return file;
	}

	public void printOnPreview(List<GeneratedObject> list) {
		previewPanel.setResultObject(list);
	}

	public SecondTabPanel() {
		setLayout(new GridLayout(0, 2));
		JPanel options = new JPanel();
		options.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OPTIONS_BORDER)));
		previewPanel = new PreviewPanel();
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(options);
		add(previewPanel);
		Mediator.registerSecondTabPanel(this);
	}

	public Dimension getImageSize() {
		return imageSize;
	}
}
