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
import java.io.File;
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
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import generator.Mediator;
import generator.actions.Action;
import generator.actions.model.LoadModelAction;
import generator.algorithms.Algorithm;
import generator.algorithms.panels.main.AlgorithmMainPanel;
import generator.models.generation.ModelInfo;
import generator.tables.Table;
import generator.tables.model.ObjectFileTableColumnModel;
import generator.tables.model.ObjectFileTableModel;
import generator.utils.PropertiesKeys;
import generator.windows.HelpWindow;
import generator.windows.ModelWindow;

public class SecondTabPanel extends AbstractPanel implements MouseListener {

	private static final String DELETE_ACTION = "deleteAction";

	private static final String EDIT_ACTION = "editAction";
	private static final String NEW_ACTION = "newAction";
	private static final long serialVersionUID = -2087487239161953473L;
	private JComboBox<Algorithm> algorithmList;
	private final Action deleteAction = new Action(Mediator.getMessage(PropertiesKeys.DELETE_OBJECT)) {
		private static final long serialVersionUID = 8236759084604074679L;

		@Override
		protected void additionalAction() {
			Map<String, ModelInfo> models = Mediator.getModels();
			List<ModelInfo> selectedRows = getSelectedRows();
			for (ModelInfo i : selectedRows) {
				models.remove(i.getModel().getPath().getKey());
			}

			updateModels(models.values());
		}
	};
	private final Action editAction = new Action(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT)) {
		private static final long serialVersionUID = -1373378695620341768L;

		@Override
		protected void additionalAction() {
			List<ModelInfo> selectedRows = getSelectedRows();
			if (!selectedRows.isEmpty()) {
				new ModelWindow(Mediator.getMessage(PropertiesKeys.EDIT_OBJECT_SETTINGS), selectedRows);
			}
		}
	};
	private JPopupMenu menu;
	private List<ModelInfo> modelsInfo = new ArrayList<>();
	private final LoadModelAction newAction = new LoadModelAction(Mediator.getMessage(PropertiesKeys.LOAD_OBJECT)) {
		private static final long serialVersionUID = 5761150114867749004L;

		@Override
		protected void onSucess(File s) {
			Mediator.loadModel(s);
			updateModels(Mediator.getModels().values());
		}

	};
	private JPanel options;

	private JPopupMenu rowMenu;
	private Table table;

	public SecondTabPanel() {
		setLayout(new GridLayout(0, 2));
		createModelsPanel();
		createAlgotithmsPanel();
		Mediator.registerSecondTabPanel(this);
	}

	private void createAlgotithmsPanel() {
		JPanel optionsPanel = new JPanel(new BorderLayout());
		options = new JPanel(new GridLayout(0, 1));
		optionsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_OPTIONS)));
		JPanel algorithmPanel = new JPanel(new GridLayout(2, 0, 0, 10));
		JPanel selectionPanel = new JPanel(new GridLayout(0, 2, 50, 0));
		algorithmList = new JComboBox<>();
		List<Algorithm> keySet = new ArrayList<>(Algorithm.getMainPanels().keySet());
		Collections.sort(keySet);
		for (Algorithm i : keySet) {
			algorithmList.addItem(i);
		}
		algorithmList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				options.removeAll();
				JPanel panel = Algorithm.getMainPanels().get(getAlgorithm());
				if (panel != null) {
					options.add(panel);
				}
				options.revalidate();
				options.repaint();
			}
		});
		options.add(Algorithm.getMainPanels().get(getAlgorithm()));
		selectionPanel.add(algorithmList);
		selectionPanel.add(new JButton(new Action(Mediator.getMessage(PropertiesKeys.ALGORITHM_HELP)) {
			private static final long serialVersionUID = -7408409695654287430L;

			@Override
			protected void additionalAction() {
				new HelpWindow(Mediator.getMessage(PropertiesKeys.ALGORITHM_HELP), getAlgorithm().getHelp());
			}

		}));
		algorithmPanel.add(selectionPanel);
		algorithmPanel.add(new JSeparator());
		optionsPanel.add(algorithmPanel, BorderLayout.NORTH);

		optionsPanel.add(options, BorderLayout.CENTER);
		add(optionsPanel);
	}

	private void createModelsPanel() {
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
		return table;
	}

	public Algorithm getAlgorithm() {
		return (Algorithm) algorithmList.getSelectedItem();
	}

	@Override
	public Map<String, Double> getArgs() {
		return getArgs(new HashMap<>(), false);
	}

	@Override
	public Map<String, Double> getArgs(Map<String, Double> args, boolean checkModified) {
		return AlgorithmMainPanel.getArgs(args);
	}

	public List<ModelInfo> getSelectedRows() {
		int[] selectedRows = table.getSelectedRows();
		List<ModelInfo> objects = new ArrayList<>();
		for (int i : selectedRows) {
			objects.add(modelsInfo.get(i));
		}
		return objects;
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

	@Override
	public void setArgs(Map<String, Double> args) {
		AlgorithmMainPanel.setArgs(args);
	}

	public void updateModels(Collection<ModelInfo> collection) {
		List<ModelInfo> modelsList = new ArrayList<>(collection);
		Collections.sort(modelsList);
		modelsInfo.clear();
		modelsInfo.addAll(modelsList);
		TableModel model = table.getModel();
		((DefaultTableModel) model).addRow(modelsList.toArray(new ModelInfo[0]));
	}

}