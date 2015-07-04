package generator.panels;

import generator.Mediator;
import generator.models.generation.ObjectListRow;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class SecondTabPanel extends JPanel {

	private static final long serialVersionUID = -2087487239161953473L;

	private PreviewPanel previewPanel;
	private Dimension imageSize;

	private JPanel objectsPanel;

	private JPanel view;

	public void deleteObject(int index) {
		view.remove(index);
		view.revalidate();
		int count = 0;
		for (Object i : view.getComponents()) {
			if (i instanceof ObjectListRow) {
				((ObjectListRow) i).setIndex(count++);
				((JComponent) i).revalidate();
			}

		}
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
		add(objectsPanel);
		previewPanel = new PreviewPanel();
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);

		Mediator.registerSecondTabPanel(this);
	}

	public JScrollPane createObjectListPanel() {
		JPanel view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(createTitle());

		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		return listScroller;
	}

	private Component createTitle() {
		JPanel title = new JPanel();
		title.setMaximumSize(new Dimension(2000,20));
		title.setLayout(new GridLayout(0, 6));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.OBJECT_NAME)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.COORDINATES)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.SCALES)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.ROTATIONS)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.DELETE_OBJECT)));
		for (Object i : title.getComponents()) {
			if (i instanceof JLabel) {
				((JComponent) i).setFont(new Font(((Component) i).getFont().getName(), Font.BOLD, 15));
				((JComponent) i).setBorder(new EmptyBorder(0, 10, 0, 10));
			}
		}
		title.setBackground(new Color(128, 128, 255));
		return title;
	}

	public void updateObjectListPanel(List<GeneratedObject> objects) {
		objectsPanel.removeAll();
		view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(createTitle());
		int count = 0;
		for (GeneratedObject i : objects) {
			view.add(new ObjectListRow(i, count++));
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
