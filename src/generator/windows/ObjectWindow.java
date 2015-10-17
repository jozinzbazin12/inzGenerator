package generator.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import generator.Mediator;
import generator.models.generation.GenerationModel;
import generator.models.generation.ModelInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.CheckBox;
import generator.utils.Component;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.Label;
import generator.utils.PropertiesKeys;

public class ObjectWindow extends JFrame implements ActionListener {
	private Map<String, Component> arguments = new HashMap<>();
	private static final long serialVersionUID = 5328377975510513084L;
	private JButton cancel;
	private JButton ok;
	private boolean edit;
	private List<GeneratedObject> objects;
	private JComboBox<GenerationModel> models;
	private CheckBox relative;

	private void createWindow() {
		setSize(600, 600);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(14, 2, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));

		JPanel modelPanel = new JPanel(new GridLayout(0, 2, 0, 30));
		modelPanel.add(new Label(Mediator.getMessage(PropertiesKeys.MODEL), Mediator.getMessage(PropertiesKeys.MODEL_TOOLTIP)));
		Collection<ModelInfo> values = Mediator.getModels().values();
		List<GenerationModel> options = new ArrayList<>();
		for (ModelInfo i : values) {
			options.add(i.getModel());
		}
		GenerationModel[] array = options.toArray(new GenerationModel[options.size()]);
		Arrays.sort(array);
		models = new JComboBox<>(array);
		modelPanel.add(models);
		panel.add(modelPanel);
		relative = new CheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE),
				Mediator.getMessage(PropertiesKeys.RELATIVE_TOOLTIP), true);
		panel.add(relative);

		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), arguments, true));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.Y,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y), arguments, true));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), arguments, true));
		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.SX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X), arguments, true));
		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.SY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y), arguments, true));
		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.SZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z), arguments, true));
		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.RX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X), arguments, true));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.RY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y), arguments, true));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.RZ,
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
		Mediator.registerObjectWindow(this);
	}

	public ObjectWindow(String name) {
		super(name);
		createWindow();
		objects = new ArrayList<>();
		objects.add(new GeneratedObject());
		edit = false;
	}

	public ObjectWindow(String name, List<GeneratedObject> obj) {
		super(name);
		this.objects = obj;
		createWindow();
		edit = true;
		fillValues();
	}

	private void fillValues() {
		GeneratedObject generatedObject = objects.get(0);
		BasicModelData basic = generatedObject.getBasic();
		if (objects.size() == 1) {
			arguments.get(Consts.X).setValue(basic.getX());
			arguments.get(Consts.Y).setValue(basic.getY());
			arguments.get(Consts.Z).setValue(basic.getZ());

			arguments.get(Consts.RX).setValue(basic.getRx());
			arguments.get(Consts.RY).setValue(basic.getRy());
			arguments.get(Consts.RZ).setValue(basic.getRz());

			arguments.get(Consts.SX).setValue(basic.getSx());
			arguments.get(Consts.SY).setValue(basic.getSy());
			arguments.get(Consts.SZ).setValue(basic.getSz());
			relative.setSilent(true);
			relative.setSelected(basic.isRelative());
			models.setSelectedItem(generatedObject.getModel());
		} else {
			boolean last = basic.isRelative();
			boolean diff = false;
			for (GeneratedObject i : objects) {
				boolean actual = i.getBasic().isRelative();
				if (actual != last) {
					relative.setSelected(false);
					diff = true;
					break;
				}
				last = actual;
			}
			if (!diff) {
				relative.setSelected(basic.isRelative());
			}
			models.setSelectedIndex(-1);
			relative.setSilent(false);
		}
	}

	public Map<String, Component> getArguments() {
		return arguments;
	}

	private BasicModelData getData(BasicModelData data) {
		Component x = arguments.get(Consts.X);
		Component y = arguments.get(Consts.Y);
		Component z = arguments.get(Consts.Z);
		Component sx = arguments.get(Consts.SX);
		Component sy = arguments.get(Consts.SY);
		Component sz = arguments.get(Consts.SZ);
		Component rx = arguments.get(Consts.RX);
		Component ry = arguments.get(Consts.RY);
		Component rz = arguments.get(Consts.RZ);

		if (x.isModified()) {
			data.setX((double) x.value());
		}
		if (y.isModified()) {
			data.setY((double) y.value());
		}
		if (z.isModified()) {
			data.setZ((double) z.value());
		}
		if (sx.isModified()) {
			data.setSx((double) sx.value());
		}
		if (sy.isModified()) {
			data.setSy((double) sy.value());
		}
		if (sz.isModified()) {
			data.setSz((double) sz.value());
		}
		if (rx.isModified()) {
			data.setRx((double) rx.value());
		}
		if (ry.isModified()) {
			data.setRy((double) ry.value());
		}
		if (rz.isModified()) {
			data.setRz((double) rz.value());
		}

		if (relative.isModified()) {
			data.setRelative(relative.isSelected());
		}
		return data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)) {
			if (edit) {
				for (GeneratedObject i : objects) {
					i.setBasic(getData(i.getBasic()));
					if (models.getSelectedIndex() != -1) {
						i.setModel((GenerationModel) models.getSelectedItem());
					}
				}
			} else {
				objects.get(0).setBasic(getData(new BasicModelData()));
				objects.get(0).setModel((GenerationModel) models.getSelectedItem());
				List<GeneratedObject> generatedObjects = Mediator.getResultObject().getGeneratedObjects();
				generatedObjects.add(objects.get(0));
			}
			Mediator.refreshPreview();
			dispose();
		}
		if (e.getSource().equals(cancel)) {
			dispose();
		}
	}
}
