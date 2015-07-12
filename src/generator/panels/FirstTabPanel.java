package generator.panels;

import generator.Mediator;
import generator.actions.LoadMapAction;
import generator.algorithms.Algorithm;
import generator.algorithms.FullRandomAlgorithm;
import generator.algorithms.RegularAlgorithm;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

import java.awt.GridLayout;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class FirstTabPanel extends JPanel {

	private static final long serialVersionUID = -2087487239161953473L;
	private Map<String, JSpinner> arguments;

	private JPanel options;
	private JLabel mapLabel;

	private JPanel options2;

	private JPanel options3;

	private JComboBox<Algorithm> algorithmList;
	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;

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

	private void createOptionsPanel() {
		options = new JPanel();
		options.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OPTIONS_BORDER)));
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
		JCheckBox box=new JCheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE));
		box.setSelected(true);
		controls.add(loadMapButton);
		controls.add(box);
		options.add(controls);
		
		
		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new GridLayout(0, 3));
		JLabel attributelabel = new JLabel(Mediator.getMessage(PropertiesKeys.ATTRIBUTE));
		legendPanel.add(attributelabel);
		JLabel minLabel = new JLabel(Mediator.getMessage(PropertiesKeys.MIN));
		legendPanel.add(attributelabel);
		JLabel maxLabel = new JLabel(Mediator.getMessage(PropertiesKeys.MAX));
		legendPanel.add(attributelabel);
		legendPanel.add(minLabel);
		legendPanel.add(maxLabel);

		options.add(legendPanel);
		options.add(createSpinner(-10000, 10000, Consts.MIN_X, Consts.MAX_X, MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X)));
		options.add(createSpinner(-10000, 10000, Consts.MIN_Y, Consts.MAX_Y, MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y)));
		options.add(createSpinner(-10000, 10000, Consts.MIN_Z, Consts.MAX_Z, MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z)));

		options.add(createSpinner(-180, 180, Consts.MIN_RX, Consts.MAX_RX, MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X)));
		options.add(createSpinner(-180, 180, Consts.MIN_RY, Consts.MAX_RY, MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y)));
		options.add(createSpinner(-180, 180, Consts.MIN_RZ, Consts.MAX_RZ, MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Z)));

		options.add(createSpinner(-1000, 1000, Consts.MIN_SX, Consts.MAX_SX, MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X)));
		options.add(createSpinner(-1000, 1000, Consts.MIN_SY, Consts.MAX_SY, MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y)));
		options.add(createSpinner(-1000, 1000, Consts.MIN_SZ, Consts.MAX_SZ, MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z)));
		

		add(options);
	}

	private JPanel createSpinner(double min, double max, String key1, String key2, String description) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		JSpinner minSpinner = new JSpinner(new SpinnerNumberModel(0.0, min, max, 1));
		panel.add(attributelabel);
		JSpinner maxSpinner = new JSpinner(new SpinnerNumberModel(0.0, min, max, 1));
		panel.add(attributelabel);
		panel.add(minSpinner);
		panel.add(maxSpinner);
		arguments.put(key1, minSpinner);
		arguments.put(key2, maxSpinner);
		return panel;
	}

	private void createOptionsPanel2() {
		options2 = new JPanel();
		options2.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ALGORITHM_OPTIONS)));
		options2.setLayout(new GridLayout(12, 2, 5, 5));
		algorithmList = new JComboBox<Algorithm>();
		algorithmList.addItem(new FullRandomAlgorithm(Mediator.getMessage(PropertiesKeys.FULL_RANDOM_ALGORITHM)));
		algorithmList.addItem(new RegularAlgorithm(Mediator.getMessage(PropertiesKeys.REGULAR_ALGORITHM)));
		options2.add(algorithmList);
		add(options2);
	}

	private void createOptionsPanel3() {
		options3 = new JPanel();
		options3.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OPTIONS_BORDER)));
		options3.setLayout(new GridLayout(2, 12, 5, 5));
		JPanel view=new JPanel();
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		JScrollPane list= new JScrollPane(view);
		list.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel row=new JPanel();
		//row.setLayout(new GridLayout(0, 3));
		JLabel icon=new JLabel();
		JLabel icon2=new JLabel();
		JButton delete=new JButton("TESTTESTTEST");
		row.add(icon);
		row.add(icon2);
		row.add(delete);
		view.add(row);
		options3.add(list);
		add(options3);
	}

	public FirstTabPanel() {
		arguments = new HashMap<String, JSpinner>();
		setLayout(new GridLayout(0, 3));
		createOptionsPanel();
		createOptionsPanel2();
		createOptionsPanel3();
		Mediator.registerFirstTabPanel(this);

	}

	public Map<String, JSpinner> getArguments() {
		return arguments;
	}
}