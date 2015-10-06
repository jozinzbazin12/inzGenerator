package generator.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import generator.Mediator;
import generator.actions.LoadMaskAction;
import generator.actions.model.LoadSingleModelAction;
import generator.algorithms.Algorithm;
import generator.algorithms.panels.additional.AlgorithmAdditionalPanel;
import generator.models.generation.GenerationModel;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;
import generator.panels.MaskPreviewPanel;
import generator.utils.CheckBox;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.Label;
import generator.utils.PropertiesKeys;
import generator.utils.Spinner;

public class ModelWindow extends JFrame implements ActionListener {
	private Map<String, Spinner> arguments = new HashMap<>();
	private static final long serialVersionUID = 5328377975510513084L;
	private JButton cancel;
	private JButton ok;
	private List<ModelInfo> objects;
	private Label fileName;
	private JTextField pathField;
	private CheckBox equalScale;
	private JPanel spinnersZ;
	private JPanel spinnersY;
	private CheckBox relative;
	private AlgorithmAdditionalPanel additionalPanel;
	private MaskPreviewPanel preview;
	private Label maskName;
	private boolean maskChanged;

	private void createWindow() {
		setSize(1000, 600);
		setLocation(100, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel container = new JPanel(new GridLayout(1, 0));
		JPanel basic = createBasicSettingsPanel();
		JPanel additional = createAdditionalPanel();
		setVisible(true);
		container.add(basic);
		container.add(additional);
		add(container, BorderLayout.CENTER);
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

	private JPanel createAdditionalPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 0));
		preview = new MaskPreviewPanel(Mediator.getMapImage(), true);
		panel.add(preview);
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));
		additionalPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.ADDITIONAL)));
		panel.add(additionalPanel);
		return panel;
	}

	private void openMask(String path) {
		maskName.setText(path);
		try {
			preview.setMask(ImageIO.read(new File(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		preview.repaint();
		maskChanged = true;
	}

	private JPanel createBasicSettingsPanel() {
		JPanel panel = new JPanel(new GridLayout(17, 2, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));

		JPanel maskOptions = createMaskOptionsPanel();
		panel.add(maskOptions);

		JPanel fileOptions = createFileOptionsPanel();
		panel.add(fileOptions);

		JPanel additional = new JPanel(new GridLayout(0, 2));
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
		return panel;

	}

	private JPanel createMaskOptionsPanel() {
		JPanel maskOptions = new JPanel();
		maskName = new Label();
		maskOptions.setLayout(new GridLayout(0, 3, 5, 5));
		ModelInfo modelInfo = objects.get(0);
		String maskFile = modelInfo.getMaskFile();

		JButton open = new JButton(new LoadMaskAction(Mediator.getMessage(PropertiesKeys.LOAD_MASK)));
		JButton delete = new JButton(new AbstractAction(Mediator.getMessage(PropertiesKeys.DELETE_MASK)) {
			private static final long serialVersionUID = -7854144403814938858L;

			@Override
			public void actionPerformed(ActionEvent e) {
				preview.deleteMask();
				maskName.setText(null);
				((JComponent) e.getSource()).setEnabled(false);
			}
		});

		if (objects.size() == 1) {
			if (maskFile != null) {
				delete.setEnabled(true);
			} else {
				delete.setEnabled(false);
			}
		} else {
			delete.setEnabled(false);
			open.setEnabled(false);
		}
		maskName.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MASK_FILE_NAME)));
		maskOptions.add(maskName);

		maskOptions.add(open);
		maskOptions.add(delete);
		if (objects.size() > 1) {
			open.setEnabled(false);
			delete.setEnabled(false);
		}
		return maskOptions;
	}

	private JPanel createFileOptionsPanel() {
		JPanel fileOptions = new JPanel();
		fileOptions.setLayout(new GridLayout(0, 3, 5, 5));
		// fileOptions.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.FILE_MENU)));
		if (objects.size() == 1) {
			fileName = new Label(objects.get(0).getModel().getName());
			pathField = new JTextField(objects.get(0).getModel().getPath());
		} else {
			StringBuilder str = new StringBuilder();
			for (ModelInfo i : objects) {
				str.append(i.getModel().getName());
				str.append(", ");
			}
			str.deleteCharAt(str.length() - 2);
			fileName = new Label(str.toString());
			pathField = new JTextField();
			pathField.setEnabled(false);
		}
		fileName.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_NAME)));
		fileOptions.add(fileName);
		fileOptions.add(pathField);

		JButton openFile = new JButton(new LoadSingleModelAction(Mediator.getMessage(PropertiesKeys.LOAD_OBJECT)));
		fileOptions.add(openFile);
		if (objects.size() > 1) {
			openFile.setEnabled(false);
		}
		return fileOptions;
	}

	public ModelWindow(String name, List<ModelInfo> generationModel) {
		super(name);
		additionalPanel = Algorithm.getAdditionalPanels().get(Mediator.getAlgorithm());
		objects = generationModel;
		createWindow();
		fillValues();
		maskChanged = false;
	}

	private void setEqualScale(boolean b) {
		equalScale.setSelected(b);
		showScaleSpinners(!b);
	}

	private void showScaleSpinners(boolean b) {
		spinnersY.setVisible(b);
		spinnersZ.setVisible(b);
	}

	private void fillValues() {
		if (objects.size() == 1) {
			ModelInfo objectInfo = objects.get(0);
			setSilent(true);
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
			setEqualScale(scaleSettings.isEqual());
			setSilent(false);
			additionalPanel.setArgs(objectInfo.getArgs());

			BufferedImage mask = objectInfo.getMask();
			String maskFile = objectInfo.getMaskFile();
			if (mask != null) {
				preview.setMask(mask);
				maskName.setText(maskFile);
			} else if (maskFile != null) {
				openMask(maskFile);
			}
		} else {
			boolean lastEqual = objects.get(0).getScaleSettings().isEqual();
			boolean diff = false;
			for (ModelInfo i : objects) {
				boolean actual = i.getScaleSettings().isEqual();
				if (actual != lastEqual) {
					setEqualScale(false);
					diff = true;
					break;
				}
				lastEqual = actual;
			}
			if (!diff) {
				setEqualScale(objects.get(0).getScaleSettings().isEqual());
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

	private void setSilent(boolean value) {
		for (Map.Entry<String, Spinner> i : arguments.entrySet()) {
			i.getValue().setSilent(value);
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

		if (data.isEqual() || (equalScale.isModified())) {
			data.setMinX(minX);
			data.setMinY(minX);
			data.setMinZ(minX);

			data.setMaxX(maxX);
			data.setMaxY(maxX);
			data.setMaxZ(maxX);
			data.setEqual(equalScale.isSelected());
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
				additionalPanel.getArgs(i.getArgs(), true);

				if (maskChanged) {
					i.setMask(preview.getMask());
					i.setMaskFile(maskName.getText());
				}
			}
			dispose();
		} else if (e.getSource().equals(cancel)) {
			dispose();
		} else if (e.getSource().equals(equalScale)) {
			if (equalScale.isSelected()) {
				showScaleSpinners(false);
			} else {
				showScaleSpinners(true);
			}
		}
		additionalPanel.delete(Mediator.getAlgorithm());
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

	public void setMask(String path) {
		openMask(path);
	}
}
