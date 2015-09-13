package generator.panels;

import generator.Mediator;
import generator.actions.NewObjectAction;
import generator.models.generation.ObjectListRow;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class ThirdTabPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -2087487239161953473L;
	private static JPopupMenu menu;
	private PreviewPanel previewPanel;
	private Dimension imageSize;

	private JPanel objectsPanel;

	private JPanel view;
	private List<ObjectListRow> rows;

	public void refreshObjects() {
		for (ObjectListRow i : rows) {
			i.refresh();
		}
		objectsPanel.revalidate();
	}

	public GeneratedObject getGeneratedObject() {
		return ObjectListRow.getClicked().getObject();
	}

	public void highlight(GeneratedObject obj) {
		for (ObjectListRow i : rows) {
			if (i.getObject() == obj) {
				i.highlight();
				i.repaint();
				break;
			}
		}
	}

	public void setClicked(GeneratedObject obj) {
		for (ObjectListRow i : rows) {
			if (i.getObject() == obj) {
				ObjectListRow.setClicked(i);
				objectsPanel.revalidate();
				i.revalidate();
				break;
			}
		}
	}

	public void deleteObject(ObjectListRow objectListRow) {
		view.remove(objectListRow.getIndex() + 1);
		ObjectListRow.setClicked(null);
		int count = 0;
		for (Object i : view.getComponents()) {
			if (i instanceof ObjectListRow) {
				((ObjectListRow) i).setIndex(count++);
				((JComponent) i).revalidate();
			}
		}
		objectsPanel.repaint();
		previewPanel.repaint();
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
		previewPanel.repaint();
	}

	public ThirdTabPanel() {
		setLayout(new GridLayout(0, 2));
		objectsPanel = new JPanel();
		objectsPanel.setLayout(new GridLayout(0, 1));
		objectsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.GENERATED_OBJECTS)));
		objectsPanel.add(createObjectListPanel());
		add(objectsPanel);
		previewPanel = new PreviewPanel();
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);
		Mediator.registerThirdTabPanel(this);
	}

	public JScrollPane createObjectListPanel() {
		JPanel view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(ObjectListRow.createTitle());

		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		menu = new JPopupMenu();
		NewObjectAction newAction = new NewObjectAction(Mediator.getMessage(PropertiesKeys.NEW_OBJECT));
		JMenuItem neww = new JMenuItem(newAction);
		neww.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		menu.add(neww);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "newAction");
		getActionMap().put("newAction", newAction);
		listScroller.addMouseListener(this);

		return listScroller;
	}

	public void updateObjectListPanel(List<GeneratedObject> objects) {
		objectsPanel.removeAll();
		view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(ObjectListRow.createTitle());
		int count = 0;
		rows = new ArrayList<ObjectListRow>();
		Collections.sort(objects);
		for (GeneratedObject i : objects) {
			ObjectListRow objectListRow = new ObjectListRow(i, count++);
			rows.add(objectListRow);
			view.add(objectListRow);
		}
		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.addMouseListener(this);
		objectsPanel.add(listScroller);
		objectsPanel.revalidate();
	}

	public Dimension getImageSize() {
		return imageSize;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			menu.setInvoker(this);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void unHighlight() {
		ObjectListRow.unHighlight();
	}

	public void refreshPreview() {
		previewPanel.repaint();

	}

}
