package generator.panels;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import generator.Mediator;
import generator.actions.LoadMapAction;
import generator.actions.LoadTextureAction;
import generator.models.result.RGBA;
import generator.models.result.BasicMapData;
import generator.models.result.LightData;
import generator.models.result.MapObject;
import generator.models.result.Material;
import generator.models.result.Texture;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;

public class FirstTabPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -2087487239161953473L;
	private Map<String, JSpinner> arguments;

	private JLabel mapLabel;

	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;
	private JPopupMenu menu;
	private JLabel texturePath;
	private JPanel texture;

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
		JPanel options = new JPanel();
		options.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_OPTIONS_BORDER)));
		options.setLayout(new GridLayout(15, 0, 5, 5));
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
		options.add(new JLabel(Mediator.getMessage(PropertiesKeys.SIZE), SwingConstants.CENTER));

		JPanel legendPanel = new JPanel();
		legendPanel.setLayout(new GridLayout(0, 2));
		JLabel attributelabel = new JLabel(Mediator.getMessage(PropertiesKeys.ATTRIBUTE));
		legendPanel.add(attributelabel);
		JLabel valueLabel = new JLabel(Mediator.getMessage(PropertiesKeys.VALUE), SwingConstants.CENTER);
		legendPanel.add(attributelabel);
		legendPanel.add(valueLabel);

		options.add(legendPanel);
		options.add(createSpinner(-10000, 10000, Consts.X,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.LENGTH), Consts.X)));
		options.add(createSpinner(-10000, 10000, Consts.Y,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.LENGTH), Consts.Y)));
		options.add(createSpinner(-10000, 10000, Consts.Z,
				MessageFormat.format(Mediator.getMessage(PropertiesKeys.LENGTH), Consts.Z)));

		options.add(new JLabel(Mediator.getMessage(PropertiesKeys.LIGHT), SwingConstants.CENTER));

		options.add(createLightSettingsTitle());

		options.add(createLightSpinners(0, 1, Consts.LIGHT_AMBIENT_R, Consts.LIGHT_AMBIENT_G, Consts.LIGHT_AMBIENT_B,
				Consts.LIGHT_AMBIENT_A, Mediator.getMessage(PropertiesKeys.AMBIENT), 0.5));
		options.add(createLightSpinners(0, 1, Consts.LIGHT_DIFFUSE_R, Consts.LIGHT_DIFFUSE_G, Consts.LIGHT_DIFFUSE_B,
				Consts.LIGHT_DIFFUSE_A, Mediator.getMessage(PropertiesKeys.DIFFUSE), 0.8));
		options.add(createLightSpinners(0, 1, Consts.LIGHT_SPECULAR_R, Consts.LIGHT_SPECULAR_G, Consts.LIGHT_SPECULAR_B,
				Consts.LIGHT_SPECULAR_A, Mediator.getMessage(PropertiesKeys.SPECULAR), 0.5));
		options.add(createLightSpinners(-10000, 10000, Consts.LIGHT_POSITION_X, Consts.LIGHT_POSITION_Y, Consts.LIGHT_POSITION_Z,
				Consts.LIGHT_POSITION_MODE, Mediator.getMessage(PropertiesKeys.LIGHT_POSITION), 1));

		add(options);
	}

	private JPanel createLightSettingsTitle() {
		JPanel lightLegend = new JPanel();
		lightLegend.setLayout(new GridLayout(0, 5));
		lightLegend.add(new JLabel());
		lightLegend.add(new JLabel("R", SwingConstants.CENTER));
		lightLegend.add(new JLabel("G", SwingConstants.CENTER));
		lightLegend.add(new JLabel("B", SwingConstants.CENTER));
		lightLegend.add(new JLabel("A", SwingConstants.CENTER));
		return lightLegend;
	}

	private void createTextureOptionsPanel() {
		JPanel textureOptions = new JPanel();
		textureOptions.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.TEXTURE_OPTIONS)));

		textureOptions.setLayout(new GridLayout(15, 0, 5, 5));

		texturePath = new JLabel();
		texturePath.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.TEXTURE_PATH)));
		textureOptions.add(texturePath);

		JPanel buttonHolder = new JPanel();
		buttonHolder.setLayout(new GridLayout(0, 2));
		JButton open = new JButton(new LoadTextureAction(Mediator.getMessage(PropertiesKeys.OPEN_TEXTURE)));
		buttonHolder.add(open);
		textureOptions.add(buttonHolder);
		textureOptions.add(createSpinner(0.01, 9999, Consts.SCALE, Mediator.getMessage(PropertiesKeys.TEXTURE_SCALE), 1));

		textureOptions.add(new JLabel(Mediator.getMessage(PropertiesKeys.MATERIAL), SwingConstants.CENTER));
		textureOptions.add(createLightSettingsTitle());
		textureOptions.add(createLightSpinners(0, 1, Consts.MATERIAL_AMBIENT_R, Consts.MATERIAL_AMBIENT_G,
				Consts.MATERIAL_AMBIENT_B, Consts.MATERIAL_AMBIENT_A, Mediator.getMessage(PropertiesKeys.AMBIENT), 0.5));
		textureOptions.add(createLightSpinners(0, 1, Consts.MATERIAL_DIFFUSE_R, Consts.MATERIAL_DIFFUSE_G,
				Consts.MATERIAL_DIFFUSE_B, Consts.MATERIAL_DIFFUSE_A, Mediator.getMessage(PropertiesKeys.DIFFUSE), 0.8));
		textureOptions.add(createLightSpinners(0, 1, Consts.MATERIAL_SPECULAR_R, Consts.MATERIAL_SPECULAR_G,
				Consts.MATERIAL_SPECULAR_B, Consts.MATERIAL_SPECULAR_A, Mediator.getMessage(PropertiesKeys.SPECULAR), 0.5));

		textureOptions.add(createSpinner(0, 1, Consts.MATERIAL_D, Mediator.getMessage(PropertiesKeys.OPACITY), 1));
		textureOptions.add(createSpinner(0, 1000, Consts.MATERIAL_NS, Mediator.getMessage(PropertiesKeys.SHINESS), 1));

		add(textureOptions);
	}

	private void createTextureOptionsPanel2() {
		JPanel texturePanel = new JPanel();
		texturePanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.TEXTURE_OPTIONS)));
		texturePanel.setLayout(new GridLayout(2, 0));

		texture = new JPanel();
		texture.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.TEXTURE)));
		texture.setLayout(new GridLayout(1, 0));
		texturePanel.add(texture);

		add(texturePanel);
	}

	private JPanel createSpinner(double min, double max, String key, String description, double defValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(defValue, min, max, 1));
		panel.add(attributelabel);
		panel.add(spinner);
		arguments.put(key, spinner);
		return panel;
	}

	private JPanel createSpinner(double min, double max, String key, String description) {
		return createSpinner(min, max, key, description, 0.0);
	}

	private JPanel createLightSpinners(double min, double max, String keyR, String keyG, String keyB, String keyA,
			String description, double defValue) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 5));
		JLabel attributelabel = new JLabel(description);
		panel.add(attributelabel);
		JSpinner spinnerR = new JSpinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		JSpinner spinnerG = new JSpinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		JSpinner spinnerB = new JSpinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		JSpinner spinnerA = new JSpinner(new SpinnerNumberModel(defValue, min, max, 0.01));
		panel.add(attributelabel);
		panel.add(spinnerR);
		panel.add(spinnerG);
		panel.add(spinnerB);
		panel.add(spinnerA);
		arguments.put(keyR, spinnerR);
		arguments.put(keyG, spinnerG);
		arguments.put(keyB, spinnerB);
		arguments.put(keyA, spinnerA);
		return panel;
	}

	public FirstTabPanel() {
		arguments = new HashMap<String, JSpinner>();
		setLayout(new GridLayout(0, 3));
		createMapOptionsPanel();
		createTextureOptionsPanel();
		createTextureOptionsPanel2();
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

	private double getValue(String key) {
		return (double) arguments.get(key).getValue();
	}

	public BasicMapData getMapSettings() {
		BasicMapData data = new BasicMapData();
		data.setLength(getValue(Consts.X), getValue(Consts.Y), getValue(Consts.Z));
		return data;
	}

	public LightData getLightSettings() {
		LightData light = new LightData();
		RGBA ambient = new RGBA(getValue(Consts.LIGHT_AMBIENT_R), getValue(Consts.LIGHT_AMBIENT_G),
				getValue(Consts.LIGHT_AMBIENT_B), getValue(Consts.LIGHT_AMBIENT_A));
		RGBA diffuse = new RGBA(getValue(Consts.LIGHT_DIFFUSE_R), getValue(Consts.LIGHT_DIFFUSE_G),
				getValue(Consts.LIGHT_DIFFUSE_B), getValue(Consts.LIGHT_DIFFUSE_A));
		RGBA specular = new RGBA(getValue(Consts.LIGHT_SPECULAR_R), getValue(Consts.LIGHT_SPECULAR_G),
				getValue(Consts.LIGHT_SPECULAR_B), getValue(Consts.LIGHT_SPECULAR_A));
		light.setLight(ambient, diffuse, specular);
		light.setPosition(getValue(Consts.LIGHT_POSITION_X), getValue(Consts.LIGHT_POSITION_Y), getValue(Consts.LIGHT_POSITION_Z),
				getValue(Consts.LIGHT_POSITION_MODE));
		return light;
	}

	public Material getMaterial() {
		Material mtl = new Material();
		RGBA ambient = new RGBA(getValue(Consts.MATERIAL_AMBIENT_R), getValue(Consts.MATERIAL_AMBIENT_G),
				getValue(Consts.MATERIAL_AMBIENT_B), getValue(Consts.MATERIAL_AMBIENT_A));
		RGBA diffuse = new RGBA(getValue(Consts.MATERIAL_DIFFUSE_R), getValue(Consts.MATERIAL_DIFFUSE_G),
				getValue(Consts.MATERIAL_DIFFUSE_B), getValue(Consts.MATERIAL_DIFFUSE_A));
		RGBA specular = new RGBA(getValue(Consts.MATERIAL_SPECULAR_R), getValue(Consts.MATERIAL_SPECULAR_G),
				getValue(Consts.MATERIAL_SPECULAR_B), getValue(Consts.MATERIAL_SPECULAR_A));
		mtl.setAmbient(ambient);
		mtl.setDiffuse(diffuse);
		mtl.setSpecular(specular);
		mtl.setD(getValue(Consts.MATERIAL_D));
		mtl.setNs(getValue(Consts.MATERIAL_NS));
		mtl.setTexture(new Texture(texturePath.getText(), getValue(Consts.SCALE)));
		return mtl;
	}

	public void setArgumentValue(MapObject data) {
		BasicMapData basic = data.getBasic();
		arguments.get(Consts.X).setValue(basic.getLengthX());
		arguments.get(Consts.Y).setValue(basic.getLengthY());
		arguments.get(Consts.Z).setValue(basic.getLengthZ());

		LightData light = data.getLightData();
		if (light != null) {
			RGBA ambient = light.getAmbient();
			if (ambient != null) {
				arguments.get(Consts.LIGHT_AMBIENT_R).setValue(ambient.getR());
				arguments.get(Consts.LIGHT_AMBIENT_G).setValue(ambient.getG());
				arguments.get(Consts.LIGHT_AMBIENT_B).setValue(ambient.getB());
				arguments.get(Consts.LIGHT_AMBIENT_A).setValue(ambient.getA());
			}

			RGBA diffuse = light.getDiffuse();
			if (diffuse != null) {
				arguments.get(Consts.LIGHT_DIFFUSE_R).setValue(diffuse.getR());
				arguments.get(Consts.LIGHT_DIFFUSE_G).setValue(diffuse.getG());
				arguments.get(Consts.LIGHT_DIFFUSE_B).setValue(diffuse.getB());
				arguments.get(Consts.LIGHT_DIFFUSE_A).setValue(diffuse.getA());
			}

			RGBA specular = light.getSpecular();
			if (specular != null) {
				arguments.get(Consts.LIGHT_SPECULAR_R).setValue(specular.getR());
				arguments.get(Consts.LIGHT_SPECULAR_G).setValue(specular.getG());
				arguments.get(Consts.LIGHT_SPECULAR_B).setValue(specular.getB());
				arguments.get(Consts.LIGHT_SPECULAR_A).setValue(specular.getA());
			}

			arguments.get(Consts.LIGHT_POSITION_X).setValue(light.getX());
			arguments.get(Consts.LIGHT_POSITION_Y).setValue(light.getY());
			arguments.get(Consts.LIGHT_POSITION_Z).setValue(light.getZ());
			arguments.get(Consts.LIGHT_POSITION_MODE).setValue(light.getMode());
		}

		Material mtl = data.getMaterial();
		if (mtl != null) {
			RGBA ambient = mtl.getAmbient();
			if (ambient != null) {
				arguments.get(Consts.MATERIAL_AMBIENT_R).setValue(ambient.getR());
				arguments.get(Consts.MATERIAL_AMBIENT_G).setValue(ambient.getG());
				arguments.get(Consts.MATERIAL_AMBIENT_B).setValue(ambient.getB());
				arguments.get(Consts.MATERIAL_AMBIENT_A).setValue(ambient.getA());
			}

			RGBA diffuse = mtl.getDiffuse();
			if (diffuse != null) {
				arguments.get(Consts.MATERIAL_DIFFUSE_R).setValue(diffuse.getR());
				arguments.get(Consts.MATERIAL_DIFFUSE_G).setValue(diffuse.getG());
				arguments.get(Consts.MATERIAL_DIFFUSE_B).setValue(diffuse.getB());
				arguments.get(Consts.MATERIAL_DIFFUSE_A).setValue(diffuse.getA());
			}

			RGBA specular = mtl.getSpecular();
			if (mtl != null) {
				arguments.get(Consts.MATERIAL_SPECULAR_R).setValue(specular.getR());
				arguments.get(Consts.MATERIAL_SPECULAR_G).setValue(specular.getG());
				arguments.get(Consts.MATERIAL_SPECULAR_B).setValue(specular.getB());
				arguments.get(Consts.MATERIAL_SPECULAR_A).setValue(specular.getA());
			}
			arguments.get(Consts.MATERIAL_D).setValue(mtl.getD());
			arguments.get(Consts.MATERIAL_NS).setValue(mtl.getNs());

			Texture txt = mtl.getTexture();
			if (txt != null) {
				setTextureScale(txt.getScale());
				try {
					setTexturePath(txt.getPath());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), Mediator.getMessage(PropertiesKeys.FILE_NOT_IMAGE),
							Mediator.getMessage(PropertiesKeys.ERROR_WINDOW_TITLE), JOptionPane.ERROR_MESSAGE, null);
				}
			}
		}
	}

	public void setTexturePath(String path) throws IOException {
		texture.removeAll();
		BufferedImage pic = ImageIO.read(new File(path));
		if (pic == null) {
			throw new IOException("Not an image");
		}
		texture.add(new PreviewPanel(pic));
		texturePath.setText(path);
	}

	public void setTextureScale(double scale) {
		arguments.get(Consts.SCALE).setValue(scale);
	}

}