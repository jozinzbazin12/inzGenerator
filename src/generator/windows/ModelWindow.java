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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import generator.Mediator;
import generator.actions.model.LoadSingleModelAction;
import generator.models.generation.GenerationModel;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;
import generator.utils.CheckBox;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;
import generator.utils.Spinner;

public class ModelWindow extends JFrame implements ActionListener {
	private Map<String, Spinner> arguments = new HashMap<>();
	private static final long serialVersionUID = 5328377975510513084L;
	private JButton cancel;
	private JButton ok;
	private List<ModelInfo> objects;
	private JLabel fileName;
	private JTextField pathField;
	private CheckBox equalScale;
	private JPanel spinnersZ;
	private JPanel spinnersY;
	private CheckBox relative;

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
			for (ModelInfo i : objects) {
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
		relative = new CheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE));
		additional.add(relative);
		equalScale = new CheckBox(Mediator.getMessage(PropertiesKeys.EQUAL_SCALE));
		equalScale.addActionListener(this);

		additional.add(equalScale);
		panel.add(additional);

		panel.add(ComponentUtil.createAtrributeLegendPanel());

		panel.add(ComponentUtil.createSpinner(0, 10000, Consts.MIN_COUNT, Consts.MAX_COUNT,
				Mediator.getMessage(PropertiesKeys.COUNT), arguments, true));
		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_X, Consts.MAX_X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), arguments, true));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Y, Consts.MAX_Y,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y), arguments, true));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Z, Consts.MAX_Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), arguments, true));
		panel.add(new JSeparator());

		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SX, Consts.MAX_SX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X), arguments, true));
		spinnersY = ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SY, Consts.MAX_SY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y), arguments, true);
		spinnersZ = ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SZ, Consts.MAX_SZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z), arguments, true);
		panel.add(spinnersY);
		panel.add(spinnersZ);

		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RX, Consts.MAX_RX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X), arguments, true));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RY, Consts.MAX_RY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y), arguments, true));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RZ, Consts.MAX_RZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Z), arguments, true));

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

	public ModelWindow(String name, List<ModelInfo> generationModel) {
		super(name);
		objects = generationModel;
		createWindow();
		fillValues();
	}

	private void fillValues() {
		if (objects.size() == 1) {
			ModelInfo objectInfo = objects.get(0);
			arguments.get(Consts.MIN_COUNT).setValue(objectInfo.getMinCount());
			arguments.get(Consts.MAX_COUNT).setValue(objectInfo.getMaxCount());

			PositionSettings positionSettings = objectInfo.getPositionSettings();
			arguments.get(Consts.MIN_X).setValue(positionSettings.getMinX());
			arguments.get(Consts.MIN_Y).setValue(positionSettings.getMinY());
			arguments.get(Consts.MIN_Z).setValue(positionSettings.getMinZ());
			arguments.get(Consts.MAX_X).setValue(positionSettings.getMaxX());
			arguments.get(Consts.MAX_Y).setValue(positionSettings.getMaxY());
			arguments.get(Consts.MAX_Z).setValue(positionSettings.getMaxZ());

			RotationSettings rotationSettings = objectInfo.getRotationSettings();
			arguments.get(Consts.MIN_RX).setValue(rotationSettings.getMinX());
			arguments.get(Consts.MIN_RY).setValue(rotationSettings.getMinY());
			arguments.get(Consts.MIN_RZ).setValue(rotationSettings.getMinZ());
			arguments.get(Consts.MAX_RX).setValue(rotationSettings.getMaxX());
			arguments.get(Consts.MAX_RY).setValue(rotationSettings.getMaxY());
			arguments.get(Consts.MAX_RZ).setValue(rotationSettings.getMaxZ());

			ScaleSettings scaleSettings = objectInfo.getScaleSettings();
			arguments.get(Consts.MIN_SX).setValue(scaleSettings.getMinX());
			arguments.get(Consts.MIN_SY).setValue(scaleSettings.getMinY());
			arguments.get(Consts.MIN_SZ).setValue(scaleSettings.getMinZ());
			arguments.get(Consts.MAX_SX).setValue(scaleSettings.getMaxX());
			arguments.get(Consts.MAX_SY).setValue(scaleSettings.getMaxY());
			arguments.get(Consts.MAX_SZ).setValue(scaleSettings.getMaxZ());
			relative.setSelected(positionSettings.isRelative());
			equalScale.setSelected(scaleSettings.isEqual());
		} else {
			boolean lastEqual = objects.get(0).getScaleSettings().isEqual();
			boolean diff = false;
			for (ModelInfo i : objects) {
				boolean actual = i.getScaleSettings().isEqual();
				if (actual != lastEqual) {
					equalScale.setSelected(false);
					diff = true;
					break;
				}
				lastEqual = actual;
			}
			if (!diff) {
				equalScale.setSelected(objects.get(0).getScaleSettings().isEqual());
			}

			lastEqual = objects.get(0).getPositionSettings().isRelative();
			diff = false;
			for (ModelInfo i : objects) {
				boolean actual = i.getPositionSettings().isRelative();
				if (actual != lastEqual) {
					relative.setSelected(false);
					diff = true;
					break;
				}
				lastEqual = actual;
			}
			if (!diff) {
				relative.setSelected(objects.get(0).getPositionSettings().isRelative());
			}
		}
	}

	public Map<String, Spinner> getArguments() {
		return arguments;
	}

	private PositionSettings getPosition(PositionSettings data) {
		Spinner minX = arguments.get(Consts.MIN_X);
		Spinner minY = arguments.get(Consts.MIN_Y);
		Spinner minZ = arguments.get(Consts.MIN_Z);
		Spinner maxX = arguments.get(Consts.MAX_X);
		Spinner maxY = arguments.get(Consts.MAX_Y);
		Spinner maxZ = arguments.get(Consts.MAX_Z);

		if (minX.isModified()) {
			data.setMinX((double) minX.getValue());
		}
		if (minY.isModified()) {
			data.setMinY((double) minY.getValue());
		}
		if (minZ.isModified()) {
			data.setMinZ((double) minZ.getValue());
		}
		if (maxX.isModified()) {
			data.setMaxX((double) maxX.getValue());
		}
		if (maxY.isModified()) {
			data.setMaxY((double) maxY.getValue());
		}
		if (maxZ.isModified()) {
			data.setMaxZ((double) maxZ.getValue());
		}
		if (relative.isModified()) {
			data.setRelative(relative.isSelected());
		}

		return data;
	}

	private RotationSettings getRotation(RotationSettings data) {
		Spinner minX = arguments.get(Consts.MIN_RX);
		Spinner minY = arguments.get(Consts.MIN_RY);
		Spinner minZ = arguments.get(Consts.MIN_RZ);
		Spinner maxX = arguments.get(Consts.MAX_RX);
		Spinner maxY = arguments.get(Consts.MAX_RY);
		Spinner maxZ = arguments.get(Consts.MAX_RZ);

		if (minX.isModified()) {
			data.setMinX((double) minX.getValue());
		}
		if (minY.isModified()) {
			data.setMinY((double) minY.getValue());
		}
		if (minZ.isModified()) {
			data.setMinZ((double) minZ.getValue());
		}
		if (maxX.isModified()) {
			data.setMaxX((double) maxX.getValue());
		}
		if (maxY.isModified()) {
			data.setMaxY((double) maxY.getValue());
		}
		if (maxZ.isModified()) {
			data.setMaxZ((double) maxZ.getValue());
		}

		return data;
	}

	private ScaleSettings getScale(ScaleSettings data) {
		Spinner sminX = arguments.get(Consts.MIN_SX);
		Spinner sminY = arguments.get(Consts.MIN_SY);
		Spinner sminZ = arguments.get(Consts.MIN_SZ);
		Spinner smaxX = arguments.get(Consts.MAX_SX);
		Spinner smaxY = arguments.get(Consts.MAX_SY);
		Spinner smaxZ = arguments.get(Consts.MAX_SZ);

		double minX = sminX.isModified() ? (double) sminX.getValue() : data.getMinX();
		double minY = sminY.isModified() ? (double) sminY.getValue() : data.getMinY();
		double minZ = sminZ.isModified() ? (double) sminZ.getValue() : data.getMinZ();
		double maxX = smaxX.isModified() ? (double) smaxX.getValue() : data.getMaxX();
		double maxY = smaxY.isModified() ? (double) smaxY.getValue() : data.getMaxY();
		double maxZ = smaxZ.isModified() ? (double) smaxZ.getValue() : data.getMaxZ();

		if (data.isEqual() || (equalScale.isModified() && equalScale.isSelected())) {
			data.setMinX(minX);
			data.setMinY(minX);
			data.setMinZ(minX);

			data.setMaxX(maxX);
			data.setMaxY(maxX);
			data.setMaxZ(maxX);
			data.setEqual(true);
		} else {
			data.setMinX(minX);
			data.setMinY(minY);
			data.setMinZ(minZ);

			data.setMaxX(maxX);
			data.setMaxY(maxY);
			data.setMaxZ(maxZ);
		}
		return data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)) {
			for (ModelInfo i : objects) {
				i.setPositionSettings(getPosition(i.getPositionSettings()));
				i.setRotationSettings(getRotation(i.getRotationSettings()));
				i.setScaleSettings(getScale(i.getScaleSettings()));
				Spinner minC = arguments.get(Consts.MIN_COUNT);
				Spinner maxC = arguments.get(Consts.MAX_COUNT);
				if (minC.isModified()) {
					i.setMinCount(getIntValue(minC.getValue()));
				}
				if (maxC.isModified()) {
					i.setMaxCount(getIntValue(maxC.getValue()));
				}
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
