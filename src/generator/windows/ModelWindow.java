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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import generator.Mediator;
import generator.actions.Action;
import generator.actions.LoadImageAction;
import generator.actions.model.LoadSingleModelAction;
import generator.algorithms.Algorithm;
import generator.algorithms.MaskAlgorithm;
import generator.algorithms.panels.additional.AlgorithmAdditionalPanel;
import generator.models.MyFile;
import generator.models.generation.GenerationModel;
import generator.models.generation.ModelInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;
import generator.panels.MaskPreviewPanel;
import generator.utils.CheckBox;
import generator.utils.Component;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.Label;
import generator.utils.PropertiesKeys;

public class ModelWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5328377975510513084L;
	private AlgorithmAdditionalPanel additionalPanel;
	private Algorithm algorithm;
	private Map<String, Component> arguments = new HashMap<>();
	private JButton cancel;
	private JButton delete;
	private CheckBox equalScale;
	private Label fileName;
	private boolean maskChanged;
	private Label maskName;
	private List<ModelInfo> objects;
	private JButton ok;
	private JTextField pathField;
	private MaskPreviewPanel preview;
	private CheckBox relative;
	private JPanel spinnersSY;
	private JPanel spinnersSZ;
	private JPanel spinnersX;
	private JPanel spinnersZ;

	public ModelWindow(String name, List<ModelInfo> generationModel) {
		super(name);
		algorithm = Mediator.getAlgorithm();
		additionalPanel = Algorithm.getAdditionalPanels().get(algorithm);
		objects = generationModel;
		createWindow();
		fillValues();
		maskChanged = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)) {
			for (ModelInfo i : objects) {
				i.setPositionSettings(getPosition(i.getPositionSettings()));
				i.setRotationSettings(getRotation(i.getRotationSettings()));
				i.setScaleSettings(getScale(i.getScaleSettings()));
				Component minC = arguments.get(Consts.MIN_COUNT);
				Component maxC = arguments.get(Consts.MAX_COUNT);
				if (minC.isModified()) {
					i.setMinCount(getIntValue(minC.value()));
				}
				if (maxC.isModified()) {
					i.setMaxCount(getIntValue(maxC.value()));
				}
				additionalPanel.getArgs(i.getArgs(), true);

				if (maskChanged) {
					i.setMask(preview.getMask());
					i.setMaskFile(new MyFile(maskName.getText()));
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

	public ModelInfo getCurrentModel() {
		return objects.get(0);
	}

	public ModelInfo changeFile(MyFile path) {
		ModelInfo modelInfo = objects.get(0);
		GenerationModel model = modelInfo.getModel();
		model.setPath(path);
		fileName.setText(model.getName());
		pathField.setText(path.getAbsolutePath());
		return modelInfo;
	}

	private JPanel createAdditionalPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 0));
		boolean bnw = algorithm instanceof MaskAlgorithm ? false : true;
		preview = new MaskPreviewPanel(Mediator.getMapImage(), true, bnw);
		panel.add(preview);
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));
		panel.add(additionalPanel);
		return panel;
	}

	private JPanel createBasicSettingsPanel() {
		JPanel panel = new JPanel(new GridLayout(17, 2, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));

		JPanel maskOptions = createMaskOptionsPanel();
		panel.add(maskOptions);

		JPanel fileOptions = createFileOptionsPanel();
		panel.add(fileOptions);

		JPanel additional = new JPanel(new GridLayout(0, 2));
		relative = new CheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE),
				Mediator.getMessage(PropertiesKeys.RELATIVE_TOOLTIP), true);
		additional.add(relative);
		equalScale = new CheckBox(Mediator.getMessage(PropertiesKeys.EQUAL_SCALE),
				Mediator.getMessage(PropertiesKeys.EQUAL_SCALE_TOOLTIP), true);
		equalScale.addActionListener(this);

		additional.add(equalScale);
		panel.add(additional);

		panel.add(ComponentUtil.createAtrributeLegendPanel());

		panel.add(ComponentUtil.createSpinner(0, 10000, Consts.MIN_COUNT, Consts.MAX_COUNT,
				Mediator.getMessage(PropertiesKeys.COUNT), arguments, true));
		panel.add(new JSeparator());
		spinnersX = ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_X, Consts.MAX_X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), arguments, true);
		panel.add(spinnersX);
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Y, Consts.MAX_Y,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y), arguments, true));
		spinnersZ = ComponentUtil.createSpinner(-10000, 10000, Consts.MIN_Z, Consts.MAX_Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), arguments, true);
		panel.add(spinnersZ);

		panel.add(new JSeparator());

		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SX, Consts.MAX_SX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X), arguments, true));
		spinnersSY = ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SY, Consts.MAX_SY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y), arguments, true);
		spinnersSZ = ComponentUtil.createSpinner(-1000, 1000, Consts.MIN_SZ, Consts.MAX_SZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z), arguments, true);
		panel.add(spinnersSY);
		panel.add(spinnersSZ);

		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RX, Consts.MAX_RX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X), arguments, true));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RY, Consts.MAX_RY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y), arguments, true));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.MIN_RZ, Consts.MAX_RZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Z), arguments, true));
		return panel;

	}

	private JPanel createFileOptionsPanel() {
		JPanel fileOptions = new JPanel();
		fileOptions.setLayout(new GridLayout(0, 3, 5, 5));
		if (objects.size() == 1) {
			fileName = new Label(objects.get(0).getModel().getName());
			pathField = new JTextField(objects.get(0).getModel().getPath().getAbsolutePath());
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

	private JPanel createMaskOptionsPanel() {
		JPanel maskOptions = new JPanel();
		maskName = new Label();
		maskOptions.setLayout(new GridLayout(0, 3, 5, 5));
		ModelInfo modelInfo = objects.get(0);
		File maskFile = modelInfo.getMaskFile();

		JButton open = new JButton(new LoadImageAction(Mediator.getMessage(PropertiesKeys.LOAD_MASK)) {
			private static final long serialVersionUID = -2212330075704003715L;

			@Override
			protected void onSucess(File path) {
				setMask(path);
				delete.setEnabled(true);
				maskChanged = true;
			}

		});
		delete = new JButton(new Action(Mediator.getMessage(PropertiesKeys.DELETE_MASK)) {
			private static final long serialVersionUID = -7854144403814938858L;

			@Override
			protected void additionalAction() {
				preview.deleteMask();
				maskName.setText(null);
				delete.setEnabled(false);
				maskChanged = true;
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
			open.setEnabled(true);
			delete.setEnabled(false);
		}
		return maskOptions;
	}

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

	private void fillValues() {
		relative.setSilent(true);
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
			setMask(objectInfo, mask);
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
			relative.setSilent(false);
		}
	}

	public Map<String, Component> getArguments() {
		return arguments;
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

	private PositionSettings getPosition(PositionSettings data) {
		Component minX = arguments.get(Consts.MIN_X);
		Component minY = arguments.get(Consts.MIN_Y);
		Component minZ = arguments.get(Consts.MIN_Z);
		Component maxX = arguments.get(Consts.MAX_X);
		Component maxY = arguments.get(Consts.MAX_Y);
		Component maxZ = arguments.get(Consts.MAX_Z);

		if (minX.isModified()) {
			data.setMinX((double) minX.value());
		}
		if (minY.isModified()) {
			data.setMinY((double) minY.value());
		}
		if (minZ.isModified()) {
			data.setMinZ((double) minZ.value());
		}
		if (maxX.isModified()) {
			data.setMaxX((double) maxX.value());
		}
		if (maxY.isModified()) {
			data.setMaxY((double) maxY.value());
		}
		if (maxZ.isModified()) {
			data.setMaxZ((double) maxZ.value());
		}
		if (relative.isModified()) {
			data.setRelative(relative.isSelected());
		}

		return data;
	}

	private RotationSettings getRotation(RotationSettings data) {
		Component minX = arguments.get(Consts.MIN_RX);
		Component minY = arguments.get(Consts.MIN_RY);
		Component minZ = arguments.get(Consts.MIN_RZ);
		Component maxX = arguments.get(Consts.MAX_RX);
		Component maxY = arguments.get(Consts.MAX_RY);
		Component maxZ = arguments.get(Consts.MAX_RZ);

		if (minX.isModified()) {
			data.setMinX((double) minX.value());
		}
		if (minY.isModified()) {
			data.setMinY((double) minY.value());
		}
		if (minZ.isModified()) {
			data.setMinZ((double) minZ.value());
		}
		if (maxX.isModified()) {
			data.setMaxX((double) maxX.value());
		}
		if (maxY.isModified()) {
			data.setMaxY((double) maxY.value());
		}
		if (maxZ.isModified()) {
			data.setMaxZ((double) maxZ.value());
		}

		return data;
	}

	private ScaleSettings getScale(ScaleSettings data) {
		Component sminX = arguments.get(Consts.MIN_SX);
		Component sminY = arguments.get(Consts.MIN_SY);
		Component sminZ = arguments.get(Consts.MIN_SZ);
		Component smaxX = arguments.get(Consts.MAX_SX);
		Component smaxY = arguments.get(Consts.MAX_SY);
		Component smaxZ = arguments.get(Consts.MAX_SZ);

		double minX = sminX.isModified() ? (double) sminX.value() : data.getMinX();
		double minY = sminY.isModified() ? (double) sminY.value() : data.getMinY();
		double minZ = sminZ.isModified() ? (double) sminZ.value() : data.getMinZ();
		double maxX = smaxX.isModified() ? (double) smaxX.value() : data.getMaxX();
		double maxY = smaxY.isModified() ? (double) smaxY.value() : data.getMaxY();
		double maxZ = smaxZ.isModified() ? (double) smaxZ.value() : data.getMaxZ();

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

	private void openMask(File path) {
		maskName.setText(path.getAbsolutePath());
		try {
			preview.setMask(ImageIO.read(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		preview.repaint();
		maskChanged = true;
	}

	private void setEqualScale(boolean b) {
		equalScale.setSilent(true);
		equalScale.setSelected(b);
		equalScale.setSilent(false);
		showScaleSpinners(!b);
	}

	private void setMask(ModelInfo objectInfo, BufferedImage mask) {
		MyFile maskFile = objectInfo.getMaskFile();
		if (mask != null) {
			preview.setMask(mask);
			maskName.setText(maskFile.getKey());
		} else if (maskFile != null) {
			openMask(maskFile);
		}
	}

	public void setMask(File path) {
		openMask(path);
	}

	private void setSilent(boolean value) {
		for (Map.Entry<String, Component> i : arguments.entrySet()) {
			i.getValue().setSilent(value);
		}
	}

	private void showScaleSpinners(boolean b) {
		spinnersSY.setVisible(b);
		spinnersSZ.setVisible(b);
	}
}
