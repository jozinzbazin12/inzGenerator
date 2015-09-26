package generator.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import generator.Mediator;
import generator.actions.object.DeleteObjectAction;
import generator.actions.object.EditObjectAction;
import generator.actions.object.NewObjectAction;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.tables.Table;
import generator.tables.object.ObjectTableColumnModel;
import generator.tables.object.ObjectTableModel;
import generator.utils.PropertiesKeys;

public class ThirdTabPanel extends JPanel implements MouseListener {

	private static final String DELETE_ACTION = "deleteAction";
	private static final String EDIT_ACTION = "editAction";
	private static final String NEW_ACTION = "newAction";
	private final EditObjectAction editAction = new EditObjectAction(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));
	private final NewObjectAction newAction = new NewObjectAction(Mediator.getMessage(PropertiesKeys.NEW_OBJECT));
	private final DeleteObjectAction deleteAction = new DeleteObjectAction(Mediator.getMessage(PropertiesKeys.DELETE_OBJECT));

	private static final long serialVersionUID = -2087487239161953473L;
	private JPopupMenu menu;
	private JPopupMenu rowMenu;
	private ObjectsPreviewPanel previewPanel;
	private Dimension imageSize;

	private JPanel objectsPanel;
	private Table table;
	private List<GeneratedObject> objects = new ArrayList<>();

	private GeneratedObject last;

	public void unHighlight() {
		if (last != null) {
			last.swapColors();
			refreshPreview();
			repaint();
			last = null;
		}
		table.setHighlighted(-1);
	}

	public void highlight(GeneratedObject obj) {
		int index = objects.indexOf(obj);
		if (last != null) {
			last.swapColors();
		}
		if (index >= 0) {
			obj.swapColors();
			last = obj;
			table.setHighlighted(index);
		}

		refreshPreview();
		repaint();
	}

	public File addPreview(String imgName) throws IOException {
		remove(previewPanel);
		File file = new File(imgName);
		BufferedImage image = ImageIO.read(file);
		if (image == null) {
			throw new IOException();
		}
		imageSize = new Dimension(image.getWidth(), image.getHeight());
		previewPanel = new ObjectsPreviewPanel(image);
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);
		return file;
	}

	private void printOnPreview() {
		previewPanel.setResultObject(objects);
		previewPanel.repaint();
	}

	public ThirdTabPanel() {
		setLayout(new GridLayout(0, 2));
		objectsPanel = new JPanel();
		objectsPanel.setLayout(new GridLayout(0, 1));
		objectsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.GENERATED_OBJECTS)));
		objectsPanel.add(createObjectListPanel());
		add(objectsPanel);
		previewPanel = new ObjectsPreviewPanel();
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);
		Mediator.registerThirdTabPanel(this);
	}

	public JScrollPane createObjectListPanel() {
		menu = new JPopupMenu();
		JMenuItem neww = new JMenuItem(newAction);
		neww.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		menu.add(neww);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
				NEW_ACTION);
		getActionMap().put(NEW_ACTION, newAction);

		rowMenu = new JPopupMenu();
		JMenuItem edit = new JMenuItem(editAction);
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		rowMenu.add(edit);
		JMenuItem delete = new JMenuItem(deleteAction);
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		rowMenu.add(delete);

		JScrollPane tabScroller = new JScrollPane(createTable());
		tabScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tabScroller.addMouseListener(this);
		createShorcruts();
		return tabScroller;
	}

	private void createShorcruts() {
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK),
				EDIT_ACTION);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK),
				DELETE_ACTION);

		getActionMap().put(EDIT_ACTION, editAction);
		getActionMap().put(DELETE_ACTION, deleteAction);
	}

	private Component createTable() {
		TableColumnModel columnModel = new ObjectTableColumnModel();
		DefaultTableModel model = new ObjectTableModel(ObjectTableColumnModel.getColumnClasses());
		table = new Table(model, columnModel, false);
		MouseAdapter rowListener = new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (row >= 0) {
					table.setHighlighted(row);
					highlight(objects.get(row));
					table.repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				int rowindex = table.rowAtPoint(e.getPoint());
				if (rowindex >= 0) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						if (table.getSelectedRowCount() <= 1) {
							table.setRowSelectionInterval(rowindex, rowindex);
						}
						rowMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		};
		table.addMouseListener(rowListener);
		table.addMouseMotionListener(rowListener);
		MouseListener tableListener = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		};
		table.getTableHeader().addMouseListener(tableListener);
		GeneratedObject generatedObject = new GeneratedObject();
		generatedObject.setBasic(new BasicModelData());

		return table;
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

	public void refreshPreview() {
		previewPanel.repaint();
	}

	public void updateObjectFiles(Collection<GeneratedObject> collection) {
		List<GeneratedObject> objectsList = new ArrayList<>(collection);
		Collections.sort(objectsList);
		objects.clear();
		objects.addAll(objectsList);
		TableModel model = table.getModel();
		((DefaultTableModel) model).addRow(objectsList.toArray(new GeneratedObject[0]));
		printOnPreview();
	}

	public GeneratedObject getSelectedRow() {
		int row = table.getSelectedRow();
		if (row < 0) {
			return null;
		}
		return objects.get(row);
	}

	public void click(GeneratedObject obj) {
		int index = objects.indexOf(obj);
		table.setRowSelectionInterval(index, index);
		if (index != table.getHighlighted()) {
			unHighlight();
		}
	}
}
