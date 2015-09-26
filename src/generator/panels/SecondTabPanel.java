package generator.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import generator.Mediator;
import generator.actions.HelpAction;
import generator.actions.model.DeleteModelAction;
import generator.actions.model.EditModelAction;
import generator.actions.model.LoadModelAction;
import generator.algorithms.Algorithm;
import generator.algorithms.FullRandomAlgorithm;
import generator.algorithms.RegularAlgorithm;
import generator.models.generation.ObjectInfo;
import generator.tables.Table;
import generator.tables.model.ObjectFileTableColumnModel;
import generator.tables.model.ObjectFileTableModel;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class SecondTabPanel extends AbstractPanel implements MouseListener {

	private static final DeleteModelAction deleteAction = new DeleteModelAction(
			Mediator.getMessage(PropertiesKeys.DELETE_OBJECT));
	private static final EditModelAction editAction = new EditModelAction(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));
	private static final LoadModelAction newAction = new LoadModelAction(Mediator.getMessage(PropertiesKeys.LOAD_OBJECT));
	private static final String DELETE_ACTION = "deleteAction";
	private static final String EDIT_ACTION = "editAction";
	private static final String NEW_ACTION = "newAction";
	private static final long serialVersionUID = -2087487239161953473L;
	private Map<Algorithm, JPanel> panels = new HashMap<>();
	private JComboBox<Algorithm> algorithmList;
	private JPopupMenu menu;
	private JPopupMenu rowMenu;
	private Table table;
	private Map<String, JSpinner> algorithmArgs = new HashMap<>();

	private List<ObjectInfo> objectsInfo = new ArrayList<>();
	private JPanel options;

	public Algorithm getAlgorithm() {
		return (Algorithm) algorithmList.getSelectedItem();
	}

	private void createAlgotithmsPanel() {
		JPanel algorithmOptionsPanel = new JPanel(new BorderLayout());
		options = new JPanel(new GridLayout(0, 1));
		algorithmOptionsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_OPTIONS)));
		JPanel algorithmPanel = new JPanel(new GridLayout(2, 0, 0, 10));
		JPanel selectionPanel = new JPanel(new GridLayout(0, 2, 50, 0));
		algorithmList = new JComboBox<>();
		FullRandomAlgorithm fullRandomAlgorithm = new FullRandomAlgorithm(
				Mediator.getMessage(PropertiesKeys.FULL_RANDOM_ALGORITHM));
		RegularAlgorithm regularAlgorithm = new RegularAlgorithm(Mediator.getMessage(PropertiesKeys.REGULAR_ALGORITHM));
		algorithmList.addItem(fullRandomAlgorithm);
		algorithmList.addItem(regularAlgorithm);
		algorithmList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				options.removeAll();
				JPanel comp = panels.get(getAlgorithm());
				options.add(comp);
				options.revalidate();
			}
		});
		selectionPanel.add(algorithmList);
		selectionPanel.add(new JButton(new HelpAction(Mediator.getMessage(PropertiesKeys.HELP))));
		algorithmPanel.add(selectionPanel);
		algorithmPanel.add(new JSeparator());
		algorithmOptionsPanel.add(algorithmPanel, BorderLayout.NORTH);

		panels.put(fullRandomAlgorithm, new JPanel());

		createRegularPanel(regularAlgorithm);
		algorithmOptionsPanel.add(options, BorderLayout.CENTER);
		add(algorithmOptionsPanel);
	}

	private void createRegularPanel(Algorithm a) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(12, 1, 5, 5));
		panel.add(ComponentUtil.createAtrributeLegendPanel());
		panel.add(ComponentUtil.createSpinner(-MAX_POSITION, MAX_POSITION, Consts.MIN_X, Consts.MAX_X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), algorithmArgs));
		panel.add(ComponentUtil.createSpinner(-MAX_POSITION, MAX_POSITION, Consts.MIN_Z, Consts.MAX_Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), algorithmArgs));
		panels.put(a, panel);
	}

	private void createObjectFilesPanel() {
		menu = new JPopupMenu();
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
		add(tableScroller);
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
		table = new Table(model, columnModel, true);
		MouseAdapter rowListener = new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (row >= 0) {
					table.setHighlighted(row);
					table.repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
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
		return table;
	}

	public SecondTabPanel() {
		arguments = new HashMap<String, JSpinner>();
		setLayout(new GridLayout(0, 2));
		createObjectFilesPanel();
		createAlgotithmsPanel();
		Mediator.registerSecondTabPanel(this);
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

	public Map<String, Number> getAlgorithmArgs() {
		Map<String, Number> map = new HashMap<>();
		for (Map.Entry<String, JSpinner> i : algorithmArgs.entrySet()) {
			map.put(i.getKey(), (Number) i.getValue().getValue());
		}
		return map;
	}

}