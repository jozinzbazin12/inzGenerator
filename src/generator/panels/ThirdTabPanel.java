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
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import generator.actions.Action;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.models.result.ResultObject;
import generator.tables.Table;
import generator.tables.object.ObjectTableColumnModel;
import generator.tables.object.ObjectTableModel;
import generator.utils.PropertiesKeys;
import generator.windows.ObjectWindow;

public class ThirdTabPanel extends JPanel implements MouseListener {

	private static final String DELETE_ACTION = "deleteAction";
	private static final String EDIT_ACTION = "editAction";
	private static final String NEW_ACTION = "newAction";
	private static final long serialVersionUID = -2087487239161953473L;
	private final Action deleteAction = new Action(Mediator.getMessage(PropertiesKeys.DELETE_OBJECT)) {
		private static final long serialVersionUID = -3567790752381825146L;

		@Override
		protected void additionalAction() {
			List<GeneratedObject> rows = getSelectedRows();
			if (rows != null) {
				ResultObject resultObject = Mediator.getResultObject();
				List<GeneratedObject> objects = resultObject.getGeneratedObjects();
				objects.removeAll(rows);
				updateObjects(objects);
			}
		};
	};
	private final Action editAction = new Action(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT)) {
		private static final long serialVersionUID = -3567790752381825146L;

		@Override
		protected void additionalAction() {
			List<GeneratedObject> generatedObjects = Mediator.getGeneratedObjects();
			if (!generatedObjects.isEmpty()) {
				String objectName = generatedObjects.size() > 1 ? "" : generatedObjects.get(0).getObjectName();
				new ObjectWindow(MessageFormat.format(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT), objectName),
						generatedObjects);
			}
		}
	};

	private Dimension imageSize;
	private GeneratedObject last;
	private JPopupMenu menu;
	private final Action newAction = new Action(Mediator.getMessage(PropertiesKeys.NEW_OBJECT)) {
		private static final long serialVersionUID = 298336929138538316L;

		@Override
		protected void additionalAction() {
			new ObjectWindow(Mediator.getMessage(PropertiesKeys.NEW_OBJECT));
		};
	};
	private List<GeneratedObject> objects = new ArrayList<>();

	private JPanel objectsPanel;
	private ObjectsPreviewPanel previewPanel;
	private JPopupMenu rowMenu;

	private Table table;

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

	public void addPreview(BufferedImage image) throws IOException {
		remove(previewPanel);
		imageSize = new Dimension(image.getWidth(), image.getHeight());
		previewPanel = new ObjectsPreviewPanel(image);
		previewPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.PREVIEW_BORDER)));
		add(previewPanel);
	}

	public void click(GeneratedObject obj) {
		int index = objects.indexOf(obj);
		table.setRowSelectionInterval(index, index);
		if (index != table.getHighlighted()) {
			unHighlight();
		}
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
		table = new Table(model, columnModel, true);
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
						if (Mediator.find(table.getSelectedRows(), rowindex) == -1) {
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

	public BufferedImage getMap() {
		return previewPanel.getImage();
	}

	public double getMapHeight(int x, int y) {
		return previewPanel.getColor(x, y);
	}

	public double getMapMaxY() {
		return previewPanel.getMaxY();
	}

	public List<GeneratedObject> getSelectedRows() {
		int[] selectedRows = table.getSelectedRows();
		List<GeneratedObject> rows = new ArrayList<>();
		for (int i : selectedRows) {
			rows.add(objects.get(i));
		}
		return rows;
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			menu.setInvoker(this);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	private void printOnPreview() {
		previewPanel.setResultObject(objects);
		previewPanel.repaint();
	}

	public void refreshPreview() {
		previewPanel.repaint();
	}

	public void unHighlight() {
		if (last != null) {
			last.swapColors();
			refreshPreview();
			repaint();
			last = null;
		}
		table.setHighlighted(-1);
	}

	public void updateObjects(Collection<GeneratedObject> collection) {
		List<GeneratedObject> objectsList = new ArrayList<>(collection);
		Collections.sort(objectsList);
		objects.clear();
		objects.addAll(objectsList);
		TableModel model = table.getModel();
		((DefaultTableModel) model).addRow(objectsList.toArray(new GeneratedObject[0]));
		revalidate();
		printOnPreview();
	}
}
