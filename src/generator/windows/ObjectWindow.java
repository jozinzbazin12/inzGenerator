package generator.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import generator.Mediator;
import generator.models.generation.ObjectInfo;
import generator.models.result.BasicModelData;
import generator.models.result.GeneratedObject;
import generator.utils.CheckBox;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;
import generator.utils.Spinner;

public class ObjectWindow extends JFrame implements ActionListener {
	Map<String, Spinner> arguments = new HashMap<>();
	private static final long serialVersionUID = 5328377975510513084L;
	private JButton cancel;
	private JButton ok;
	private boolean edit;
	private GeneratedObject object;
	private JComboBox<ObjectInfo> models;
	private JCheckBox relative;

	private void createWindow() {
		setSize(600, 600);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(14, 2, 5, 5));
		panel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));

		JPanel modelPanel = new JPanel(new GridLayout(0, 2, 0, 30));
		modelPanel.add(new JLabel(Mediator.getMessage(PropertiesKeys.MODEL)));
		Collection<ObjectInfo> values = Mediator.getModels().values();
		ObjectInfo[] array = values.toArray(new ObjectInfo[values.size()]);
		Arrays.sort(array);
		models = new JComboBox<>(array);
		modelPanel.add(models);
		panel.add(modelPanel);
		relative = new CheckBox(Mediator.getMessage(PropertiesKeys.RELATIVE));
		panel.add(relative);
		relative.setSelected(object.getBasic().isRelative());

		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.X), arguments));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.Y,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Y), arguments));
		panel.add(ComponentUtil.createSpinner(-10000, 10000, Consts.Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.COORDINATE), Consts.Z), arguments));
		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.RX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.X), arguments));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.RY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Y), arguments));
		panel.add(ComponentUtil.createSpinner(-180, 180, Consts.RZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.ROTATION), Consts.Z), arguments));
		panel.add(new JSeparator());
		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.SX,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.X), arguments));
		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.SY,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Y), arguments));
		panel.add(ComponentUtil.createSpinner(-1000, 1000, Consts.SZ,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.SCALE), Consts.Z), arguments));

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
		object = new GeneratedObject();
		edit = false;
	}

	public ObjectWindow(String name, GeneratedObject obj) {
		super(name);
		this.object = obj;
		createWindow();
		models.setSelectedItem(Mediator.getModels().get(obj.getModel().getPath()));
		edit = true;
		fillValues();
	}

	private void fillValues() {
		arguments.get(Consts.X).setValue(object.getBasic().getX());
		arguments.get(Consts.Y).setValue(object.getBasic().getY());
		arguments.get(Consts.Z).setValue(object.getBasic().getZ());

		arguments.get(Consts.RX).setValue(object.getBasic().getRx());
		arguments.get(Consts.RY).setValue(object.getBasic().getRy());
		arguments.get(Consts.RZ).setValue(object.getBasic().getRz());

		arguments.get(Consts.SX).setValue(object.getBasic().getSx());
		arguments.get(Consts.SY).setValue(object.getBasic().getSy());
		arguments.get(Consts.SZ).setValue(object.getBasic().getSz());
	}

	public Map<String, Spinner> getArguments() {
		return arguments;
	}

	private BasicModelData getData() {
		BasicModelData data = new BasicModelData();
		data.setX((double) arguments.get(Consts.X).getValue());
		data.setY((double) arguments.get(Consts.Y).getValue());
		data.setZ((double) arguments.get(Consts.Z).getValue());

		data.setSx((double) arguments.get(Consts.SX).getValue());
		data.setSy((double) arguments.get(Consts.SY).getValue());
		data.setSz((double) arguments.get(Consts.SZ).getValue());

		data.setRx((double) arguments.get(Consts.RX).getValue());
		data.setRy((double) arguments.get(Consts.RY).getValue());
		data.setRz((double) arguments.get(Consts.RZ).getValue());

		data.setRelative(relative.isSelected());
		return data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ok)) {
			if (edit) {
				object.setBasic(getData());
				object.setModel(((ObjectInfo) models.getSelectedItem()).getModel());
			} else {
				object.setBasic(getData());
				object.setModel(((ObjectInfo) models.getSelectedItem()).getModel());
				List<GeneratedObject> generatedObjects = Mediator.getResultObject().getGeneratedObjects();
				generatedObjects.add(object);
			}
			Mediator.refreshPreview();
			dispose();
		}
		if (e.getSource().equals(cancel)) {
			dispose();
		}
	}
}
