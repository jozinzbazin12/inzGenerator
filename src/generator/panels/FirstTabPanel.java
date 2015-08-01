package generator.panels;

import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

import generator.Mediator;
import generator.actions.LoadMapAction;
import generator.actions.LoadModelAction;
import generator.algorithms.Algorithm;
import generator.algorithms.FullRandomAlgorithm;
import generator.algorithms.RegularAlgorithm;
import generator.models.generation.ObjectFileListRow;
import generator.models.generation.ObjectInfo;
import generator.models.result.BasicModelData;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class FirstTabPanel extends JPanel implements MouseListener {

	private static final String NEW_ACTION = "newAction";
	private static final long serialVersionUID = -2087487239161953473L;
	private Map<String, JSpinner> arguments;

	private JPanel options;
	private JLabel mapLabel;

	private JPanel options2;
	private JComboBox<Algorithm> algorithmList;
	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;
	private JPopupMenu menu;
	private JPanel view;
	private JPanel objectsPanel;

	public Algorithm getAlgorithm() {
		return (Algorithm) algorithmList.getSelectedItem();
	}

	public String getMapName() {
		return mapLabel.getText();
	}

	public void setMapFileName(String name) {
		mapLabel.setText(name);
	}

	public void setMapHeight(String name) {
		mapHeightLabel.setText(name);
	}

	public void setMapWidth(String name) {
		mapWidthLabel.setText(name);
	}

	private void createMapOptionsPanel() {
		options = new JPanel();
		options.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_OPTIONS_BORDER)));
		options.setLayout(new GridLayout(13, 0, 5, 5));
		add(options);
		mapLabel = new JLabel();
		mapLabel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_BORDER)));
		options.add(mapLabel);
		JPanel size = new JPanel();
		size.setLayout(new GridLayout(0, 2));
		mapWidthLabel = new JLabel();
		mapWidthLabel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.WIDTH)));
		size.add(mapWidthLabel);
		mapHeightLabel = new JLabel();
		mapHeightLabel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.HEIGHT)));
		size.add(mapHeightLabel);
		options.add(size);

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(0, 2));
		JButton loadMapButton = new JButton(new LoadMapAction(Mediator.getMessage(PropertiesKeys.LOAD_MAP_BUTTON)));
		JCheckBox box = new JCheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE));
		box.setSelected(true);
		controls.add(loadMapButton);
		controls.add(box);
		options.add(controls);

		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new GridLayout(0, 2));
		JLabel attributelabel = new JLabel(Mediator.getMessage(PropertiesKeys.ATTRIBUTE));
		legendPanel.add(attributelabel);
		JLabel valueLabel = new JLabel(Mediator.getMessage(PropertiesKeys.VALUE));
		legendPanel.add(attributelabel);
		legendPanel.add(valueLabel);

		options.add(legendPanel);
		options.add(createSpinner(-10000, 10000, Consts.X, MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X)));
		options.add(createSpinner(-10000, 10000, Consts.Y, MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y)));
		options.add(createSpinner(-10000, 10000, Consts.Z, MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z)));

		options.add(createSpinner(-180, 180, Consts.RX, MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X)));
		options.add(createSpinner(-180, 180, Consts.RY, MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y)));
		options.add(createSpinner(-180, 180, Consts.RZ, MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Z)));

		options.add(createSpinner(-1000, 1000, Consts.SX, MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X)));
		options.add(createSpinner(-1000, 1000, Consts.SY, MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y)));
		options.add(createSpinner(-1000, 1000, Consts.SZ, MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z)));

		add(options);
	}

	private JPanel createSpinner(double min, double max, String key, String description) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(0.0, min, max, 1));
		panel.add(attributelabel);
		panel.add(spinner);
		arguments.put(key, spinner);
		return panel;
	}

	private void createOptionsPanel2() {
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
		objectsPanel = new JPanel();
		objectsPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OBJECTS)));
		objectsPanel.setLayout(new GridLayout(0, 1));
		view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(ObjectFileListRow.createTitle());

		menu = new JPopupMenu();
		LoadModelAction newAction = new LoadModelAction(Mediator.getMessage(PropertiesKeys.LOAD_OBJECT));
		JMenuItem neww = new JMenuItem(newAction);
		neww.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		menu.add(neww);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK), NEW_ACTION);
		getActionMap().put(NEW_ACTION, newAction);

		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listScroller.addMouseListener(this);
		objectsPanel.add(listScroller);
		add(objectsPanel);
	}

	public FirstTabPanel() {
		arguments = new HashMap<String, JSpinner>();
		setLayout(new GridLayout(0, 3));
		createMapOptionsPanel();
		createOptionsPanel2();
		createObjectFilesPanel();
		Mediator.registerFirstTabPanel(this);

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
		objectsPanel.removeAll();
		view = new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		view.add(ObjectFileListRow.createTitle());

		List<ObjectInfo> modelsList = new ArrayList<>(collection);
		Collections.sort(modelsList);
		int count = 0;
		for (ObjectInfo i : modelsList) {
			view.add(new ObjectFileListRow(i, count++));
		}
		JScrollPane listScroller = new JScrollPane(view);
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScroller.addMouseListener(this);
		objectsPanel.add(listScroller);
		objectsPanel.revalidate();
	}

	private double getValue(String key) {
		return (double) arguments.get(key).getValue();
	}

	public BasicModelData getMapSettings() {
		BasicModelData data = new BasicModelData();
		data.setPosition(getValue(Consts.X), getValue(Consts.Y), getValue(Consts.Z));
		data.setRotation(getValue(Consts.RX), getValue(Consts.RY), getValue(Consts.RZ));
		data.setScale(getValue(Consts.SX), getValue(Consts.SY), getValue(Consts.SZ));
		return data;
	}
	
	public void setArgumentValue(BasicModelData data){
		arguments.get(Consts.X).setValue(data.getX());
		arguments.get(Consts.Y).setValue(data.getY());
		arguments.get(Consts.Z).setValue(data.getZ());
		
		arguments.get(Consts.RX).setValue(data.getRx());
		arguments.get(Consts.RY).setValue(data.getRy());
		arguments.get(Consts.RZ).setValue(data.getRz());
		
		arguments.get(Consts.SX).setValue(data.getSx());
		arguments.get(Consts.SY).setValue(data.getSy());
		arguments.get(Consts.SZ).setValue(data.getSz());
	}
}