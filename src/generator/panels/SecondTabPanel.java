package generator.panels;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import generator.Mediator;
import generator.actions.model.DeleteModelAction;
import generator.actions.model.EditModelAction;
import generator.actions.model.LoadModelAction;
import generator.algorithms.Algorithm;
import generator.algorithms.FullRandomAlgorithm;
import generator.algorithms.RegularAlgorithm;
import generator.models.generation.ObjectFileTable;
import generator.models.generation.ObjectFileTableColumnModel;
import generator.models.generation.ObjectFileTableModel;
import generator.models.generation.ObjectInfo;
import generator.utils.PropertiesKeys;

public class SecondTabPanel extends JPanel implements MouseListener {

	private static final DeleteModelAction deleteAction = new DeleteModelAction(
			Mediator.getMessage(PropertiesKeys.DELETE_OBJECT));
	private static final EditModelAction editAction = new EditModelAction(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));

	private static final String DELETE_ACTION = "deleteAction";
	private static final String EDIT_ACTION = "editAction";
	private static final String NEW_ACTION = "newAction";
	private static final long serialVersionUID = -2087487239161953473L;
	private Map<String, JSpinner> arguments;

	private JPanel options2;
	private JComboBox<Algorithm> algorithmList;
	private JPopupMenu menu;
	private JPopupMenu rowMenu;
	private ObjectFileTable table;

	private List<ObjectInfo> objectsInfo = new ArrayList<>();

	public Algorithm getAlgorithm() {
		return (Algorithm) algorithmList.getSelectedItem();
	}

	private void createAlgotithmsPanel() {
		options2 = new JPanel();
		options2.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_OPTIONS)));
		options2.setLayout(new GridLayout(12, 2, 5, 5));
		algorithmList = new JComboBox<>();
		algorithmList.addItem(new FullRandomAlgorithm(Mediator.getMessage(PropertiesKeys.FULL_RANDOM_ALGORITHM)));
		algorithmList.addItem(new RegularAlgorithm(Mediator.getMessage(PropertiesKeys.REGULAR_ALGORITHM)));
		options2.add(algorithmList);
		add(options2);
	}

	private void createObjectFilesPanel() {
		JPanel objectsPanel = new JPanel();
		objectsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OBJECTS)));
		objectsPanel.setLayout(new GridLayout(0, 1));

		menu = new JPopupMenu();
		LoadModelAction newAction = new LoadModelAction(Mediator.getMessage(PropertiesKeys.LOAD_OBJECT));
		JMenuItem neww = new JMenuItem(newAction);
		neww.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		menu.add(neww);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK),
				NEW_ACTION);
		getActionMap().put(NEW_ACTION, newAction);

		rowMenu = new JPopupMenu();
		JMenuItem edit = new JMenuItem(editAction);
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		rowMenu.add(edit);
		JMenuItem delete = new JMenuItem(deleteAction);
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		rowMenu.add(delete);

		JScrollPane tableScroller = new JScrollPane(createTable());
		tableScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroller.addMouseListener(this);
		createShorcruts();
		objectsPanel.add(tableScroller);
		add(objectsPanel);
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
		TableColumnModel columnModel = new ObjectFileTableColumnModel();
		DefaultTableModel model = new ObjectFileTableModel(ObjectFileTableColumnModel.getColumnClasses());
		table = new ObjectFileTable(model, columnModel);
		MouseListener rowListener = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					int r = table.rowAtPoint(e.getPoint());
					if (r >= 0 && r < table.getRowCount()) {
						table.setRowSelectionInterval(r, r);
					} else {
						table.clearSelection();
					}

					int rowindex = table.getSelectedRow();
					if (rowindex < 0) {
						menu.show(e.getComponent(), e.getX(), e.getY());
					} else if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
						rowMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		};
		table.addMouseListener(rowListener);
		MouseListener tableListener = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		};
		table.getTableHeader().addMouseListener(tableListener);
		return table;
	}

	public SecondTabPanel() {
		arguments = new HashMap<String, JSpinner>();
		setLayout(new GridLayout(0, 2));
		createObjectFilesPanel();
		createAlgotithmsPanel();
		Mediator.registerSecondTabPanel(this);
	}

	public Map<String, JSpinner> getArguments() {
		return arguments;
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

	public void updateObjectFiles(Collection<ObjectInfo> collection) {
		List<ObjectInfo> modelsList = new ArrayList<>(collection);
		Collections.sort(modelsList);
		objectsInfo.clear();
		objectsInfo.addAll(collection);
		TableModel model = table.getModel();
		((DefaultTableModel) model).addRow(collection.toArray(new ObjectInfo[0]));
	}

	public List<ObjectInfo> getSelectedRows() {
		int[] selectedRows = table.getSelectedRows();
		List<ObjectInfo> objects = new ArrayList<>();
		for (int i : selectedRows) {
			objects.add(objectsInfo.get(i));
		}
		return objects;
	}

}