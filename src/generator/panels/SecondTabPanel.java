package generator.panels;

import generator.Mediator;
import generator.models.generation.ObjectListRow;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SecondTabPanel extends JPanel {

	private static final long serialVersionUID = -2087487239161953473L;

	private PreviewPanel previewPanel;
	private Dimension imageSize;

	private JPanel objectsPanel;

	private JPanel view;

	public void deleteObject(ObjectListRow objectListRow) {
		view.remove(objectListRow.getIndex()+1);
		ObjectListRow.setClicked(null);
		int count = 0;
		for (Object i : view.getComponents()) {
			if (i instanceof ObjectListRow) {
				((ObjectListRow) i).setIndex(count++);
				((JComponent) i).revalidate();
			}
		}
		objectsPanel.revalidate();
		objectsPanel.repaint();
	}

	public File addPreview(String imgName) throws IOException {
		remove(previewPanel);
		File file = new File(imgName);
		BufferedImage image = ImageIO.read(file);
		if (image == null)
			throw new IOException();
		imageSize = new Dimension(image.getWidth(), image.getHeight());
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
		objectsPanel = new JPanel();
		objectsPanel.setLayout(new GridLayout(0, 1));
		objectsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.GENERATED_OBJECTS)));
		objectsPanel.add(createObjectListPanel());
		add(objectsPanel);
		previewPanel = new PreviewPanel();
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);

		Mediator.registerSecondTabPanel(this);
	}

	public JScrollPane createObjectListPanel() {
		JPanel view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(ObjectListRow.createTitle());

		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return listScroller;
	}

	
	public void updateObjectListPanel(List<GeneratedObject> objects) {
		objectsPanel.removeAll();
		view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(ObjectListRow.createTitle());
		int count = 0;
		for (GeneratedObject i : objects) {
			view.add(new ObjectListRow(i, Color.blue,count++));
		}
		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		objectsPanel.add(listScroller);
	}

	public Dimension getImageSize() {
		return imageSize;
	}
}
