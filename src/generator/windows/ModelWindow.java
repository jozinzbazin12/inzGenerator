package generator.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import generator.Mediator;
import generator.actions.model.LoadSingleModelAction;
import generator.models.generation.GenerationModel;
import generator.models.generation.ObjectInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class ModelWindow extends JFrame implements ActionListener {
	private Map<String, JSpinner> arguments = new HashMap<String, JSpinner>();
	private static final long serialVersionUID = 5328377975510513084L;
	private JButton cancel;
	private JButton ok;
	private List<ObjectInfo> objects;
	private JLabel fileName;
	private JTextField pathField;
	private JCheckBox equalScale;
	private JPanel spinnersZ;
	private JPanel spinnersY;
	private JCheckBox relative;

	private void createWindow() {
		setSize(600, 600);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel fileOptions = new JPanel();
		fileOptions.setLayout(new GridLayout(0, 3));
		fileOptions.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.FILE_MENU)));
		if (objects.size() == 1) {
			fileName = new JLabel(objects.get(0).getModel().getName());
		} else {
			StringBuilder str = new StringBuilder();
			for (ObjectInfo i : objects) {
				str.append(i.getModel().getName());
				str.append(", ");
			}
			str.deleteCharAt(str.length() - 2);
			fileName = new JLabel(str.toString());
		}
		fileName.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_NAME)));
		fileOptions.add(fileName);
		if (objects.size() == 1) {
			pathField = new JTextField(objects.get(0).getModel().getPath());
		} else {
			pathField = new JTextField();
			pathField.setEnabled(false);
		}
		fileOptions.add(pathField);
		JButton openFile = new JButton(new LoadSingleModelAction(Mediator.getMessage(PropertiesKeys.LOAD_OBJECT)));
		fileOptions.add(openFile);
		if (objects.size() > 1) {
			openFile.setEnabled(false);
		}
		add(fileOptions, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(15, 2, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));

		JPanel additional = new JPanel(new GridLayout(0, 2));
		// additional.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ADDITIONAL)));
		relative = new JCheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE));
		additional.add(relative);
		equalScale = new JCheckBox(Mediator.getMessage(PropertiesKeys.EQUAL_SCALE));
		equalScale.addActionListener(this);
		equalScale.setSelected(objects.get(0).getScaleSettings().isEqual());
		additional.add(equalScale);
		panel.add(additional);

		panel.add(ComponentUtil.createAtrributeLegendPanel());

		panel.add(ComponentUtil.createSpinner(0, 10000, Consts.MIN_COUNT, Consts.MAX_COUNT,
				Mediator.getMessage(PropertiesKeys.COUNT), arguments));
		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_X, Consts.MAX_X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), arguments));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Y, Consts.MAX_Y,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y), arguments));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Z, Consts.MAX_Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), arguments));
		panel.add(new JSeparator());

		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SX, Consts.MAX_SX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X), arguments));
		spinnersY = ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SY, Consts.MAX_SY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y), arguments);
		spinnersZ = ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SZ, Consts.MAX_SZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z), arguments);
		panel.add(spinnersY);
		panel.add(spinnersZ);

		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RX, Consts.MAX_RX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X), arguments));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RY, Consts.MAX_RY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y), arguments));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RZ, Consts.MAX_RZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Z), arguments));

		setVisible(true);
		add(panel, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(0, 2, 20, 20));
		ok = new JButton(Mediator.getMessage(PropertiesKeys.OK));
		ok.addActionListener(this);
		bottom.add(ok);

		cancel = new JButton(Mediator.getMessage(PropertiesKeys.CANCEL));
		cancel.addActionListener(this);
		bottom.add(cancel);
		add(bottom, BorderLayout.PAGE_END);
		Mediator.registerModelWindow(this);
	}

	public ModelWindow(String name, List<ObjectInfo> generationModel) {
		super(name);
		objects = generationModel;
		createWindow();
		fillValues();
		relative.setSelected(objects.get(0).getPositionSettings().isRelative());
	}

	private void fillValues() {
		if (objects.size() == 1) {
			arguments.get(Consts.MIN_COUNT).setValue(objects.get(0).getMinCount());
			arguments.get(Consts.MAX_COUNT).setValue(objects.get(0).getMaxCount());

			arguments.get(Consts.MIN_X).setValue(objects.get(0).getPositionSettings().getMinX());
			arguments.get(Consts.MIN_Y).setValue(objects.get(0).getPositionSettings().getMinY());
			arguments.get(Consts.MIN_Z).setValue(objects.get(0).getPositionSettings().getMinZ());
			arguments.get(Consts.MAX_X).setValue(objects.get(0).getPositionSettings().getMaxX());
			arguments.get(Consts.MAX_Y).setValue(objects.get(0).getPositionSettings().getMaxY());
			arguments.get(Consts.MAX_Z).setValue(objects.get(0).getPositionSettings().getMaxZ());

			arguments.get(Consts.MIN_RX).setValue(objects.get(0).getRotationSettings().getMinX());
			arguments.get(Consts.MIN_RY).setValue(objects.get(0).getRotationSettings().getMinY());
			arguments.get(Consts.MIN_RZ).setValue(objects.get(0).getRotationSettings().getMinZ());
			arguments.get(Consts.MAX_RX).setValue(objects.get(0).getRotationSettings().getMaxX());
			arguments.get(Consts.MAX_RY).setValue(objects.get(0).getRotationSettings().getMaxY());
			arguments.get(Consts.MAX_RZ).setValue(objects.get(0).getRotationSettings().getMaxZ());

			arguments.get(Consts.MIN_SX).setValue(objects.get(0).getScaleSettings().getMinX());
			arguments.get(Consts.MIN_SY).setValue(objects.get(0).getScaleSettings().getMinY());
			arguments.get(Consts.MIN_SZ).setValue(objects.get(0).getScaleSettings().getMinZ());
			arguments.get(Consts.MAX_SX).setValue(objects.get(0).getScaleSettings().getMaxX());
			arguments.get(Consts.MAX_SY).setValue(objects.get(0).getScaleSettings().getMaxY());
			arguments.get(Consts.MAX_SZ).setValue(objects.get(0).getScaleSettings().getMaxZ());
		}
	}

	public Map<String, JSpinner> getArguments() {
		return arguments;
	}

	private PositionSettings getPosition() {
		PositionSettings data = new PositionSettings();
		data.setMinX((double) arguments.get(Consts.MIN_X).getValue());
		data.setMinY((double) arguments.get(Consts.MIN_Y).getValue());
		data.setMinZ((double) arguments.get(Consts.MIN_Z).getValue());

		data.setMaxX((double) arguments.get(Consts.MAX_X).getValue());
		data.setMaxY((double) arguments.get(Consts.MAX_Y).getValue());
		data.setMaxZ((double) arguments.get(Consts.MAX_Z).getValue());
		data.setRelative(relative.isSelected());

		return data;
	}

	private RotationSettings getRotation() {
		RotationSettings data = new RotationSettings();
		data.setMinX((double) arguments.get(Consts.MIN_RX).getValue());
		data.setMinY((double) arguments.get(Consts.MIN_RY).getValue());
		data.setMinZ((double) arguments.get(Consts.MIN_RZ).getValue());

		data.setMaxX((double) arguments.get(Consts.MAX_RX).getValue());
		data.setMaxY((double) arguments.get(Consts.MAX_RY).getValue());
		data.setMaxZ((double) arguments.get(Consts.MAX_RZ).getValue());
		return data;
	}

	private ScaleSettings getScale() {
		ScaleSettings data = new ScaleSettings();
		if (equalScale.isSelected()) {
			double minx = (double) arguments.get(Consts.MIN_SX).getValue();
			data.setMinX(minx);
			data.setMinY(minx);
			data.setMinZ(minx);

			double maxx = (double) arguments.get(Consts.MAX_SX).getValue();
			data.setMaxX(maxx);
			data.setMaxY(maxx);
			data.setMaxZ(maxx);
			data.setEqual(true);
		} else {
			data.setMinX((double) arguments.get(Consts.MIN_SX).getValue());
			data.setMinY((double) arguments.get(Consts.MIN_SY).getValue());
			data.setMinZ((double) arguments.get(Consts.MIN_SZ).getValue());

			data.setMaxX((double) arguments.get(Consts.MAX_SX).getValue());
			data.setMaxY((double) arguments.get(Consts.MAX_SY).getValue());
			data.setMaxZ((double) arguments.get(Consts.MAX_SZ).getValue());
		}
		return data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)) {
			for (ObjectInfo i : objects) {
				i.setPositionSettings(getPosition());
				i.setRotationSettings(getRotation());
				i.setScaleSettings(getScale());
				i.setMinCount(getIntValue(arguments.get(Consts.MIN_COUNT).getValue()));
				i.setMaxCount(getIntValue(arguments.get(Consts.MAX_COUNT).getValue()));
			}
			dispose();
		} else if (e.getSource().equals(cancel)) {
			dispose();
		} else if (e.getSource().equals(equalScale)) {
			if (equalScale.isSelected()) {
				spinnersY.setVisible(false);
				spinnersZ.setVisible(false);
			} else {
				spinnersY.setVisible(true);
				spinnersZ.setVisible(true);
			}
		}
	}

	private int getIntValue(Object value) {
		if (value instanceof Double) {
			return ((Double) value).intValue();
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		throw new IllegalArgumentException("Argument not a number");
	}

	public void changeFile(String path) {
		GenerationModel model = objects.get(0).getModel();
		model.setPath(path);
		fileName.setText(model.getName());
		pathField.setText(path);
	}
}
