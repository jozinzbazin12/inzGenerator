package generator.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import generator.Mediator;
import generator.actions.LoadImageAction;
import generator.components.Component;
import generator.components.Label;
import generator.models.MyFile;
import generator.models.result.BasicMapData;
import generator.models.result.LightData;
import generator.models.result.MapObject;
import generator.models.result.Material;
import generator.models.result.RGB;
import generator.models.result.RGBA;
import generator.models.result.Texture;
import generator.utils.ComponentUtil;
import generator.utils.Consts;
import generator.utils.PropertiesKeys;
import generator.utils.WindowUtil;

public class FirstTabPanel extends AbstractPanel implements MouseListener {
	private static final long serialVersionUID = -2087487239161953473L;
	private static final double DEFAULT_MAP_SIZE = 2000;
	private static final double DEFAULT_MAP_HEIGHT = 100;
	private Label mapHeightLabel;
	private Label mapLabel;
	private Label mapWidthLabel;
	private JPopupMenu menu;
	private JPanel texture;
	private Label texturePath;

	private JPanel preview;

	public FirstTabPanel() {
		arguments = new HashMap<>();
		setLayout(new GridLayout(0, 3));
		createMapOptionsPanel();
		createTextureOptionsPanel();
		createPreview();
		Mediator.registerFirstTabPanel(this);
	}

	private void addRGBLegend(JPanel lightLegend) {
		lightLegend.add(new Label());
		lightLegend.add(new Label("R", Mediator.getMessage(PropertiesKeys.COLOR_RED_TOOLTIP), SwingConstants.CENTER));
		lightLegend.add(new Label("G", Mediator.getMessage(PropertiesKeys.COLOR_GREEN_TOOLTIP), SwingConstants.CENTER));
		lightLegend.add(new Label("B", Mediator.getMessage(PropertiesKeys.COLOR_BLUE_TOOLTIP), SwingConstants.CENTER));
	}

	private JPanel createLightSettingsTitle() {
		JPanel lightLegend = new JPanel();
		lightLegend.setLayout(new GridLayout(0, 5));
		addRGBLegend(lightLegend);
		lightLegend.add(new Label("A", Mediator.getMessage(PropertiesKeys.COLOR_ALPHA_TOOLTIP), SwingConstants.CENTER));
		return lightLegend;
	}

	private void createMapOptionsPanel() {
		JPanel options = new JPanel(new GridLayout(3, 0, 5, 5));
		options.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_OPTIONS_BORDER)));

		JPanel mapPanel = new JPanel(new GridLayout(4, 0, 5, 5));
		mapPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_INFO_BORDER)));
		mapLabel = new Label();
		mapLabel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_BORDER)));
		mapPanel.add(mapLabel);
		texturePath = new Label();
		texturePath.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.TEXTURE_PATH)));
		mapPanel.add(texturePath);
		JPanel size = new JPanel();
		size.setLayout(new GridLayout(0, 2));
		mapWidthLabel = new Label();
		mapWidthLabel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.WIDTH)));
		size.add(mapWidthLabel);
		mapHeightLabel = new Label();
		mapHeightLabel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.HEIGHT)));
		size.add(mapHeightLabel);
		mapPanel.add(size);

		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(0, 2));
		JButton loadMapButton = new JButton(new LoadImageAction(Mediator.getMessage(PropertiesKeys.LOAD_MAP_BUTTON)) {
			private static final long serialVersionUID = 7367163999541143871L;

			@Override
			protected void onSucess(File path) {
				Dimension imageSize = Mediator.setMapFile(new MyFile(path));
				setMapProperties(path.getAbsolutePath(), imageSize);
			}

		});
		JButton texture = new JButton(new LoadImageAction(Mediator.getMessage(PropertiesKeys.OPEN_TEXTURE)) {
			private static final long serialVersionUID = -864076895016573176L;

			@Override
			protected void onSucess(File path) {
				String str = path.getAbsolutePath();
				setTexturePath(str);
				Mediator.setTextureFile(str);
			}
		});
		controls.add(loadMapButton);
		controls.add(texture);
		mapPanel.add(controls);
		options.add(mapPanel);

		JPanel mapPanel2 = new JPanel(new GridLayout(4, 0, 5, 5));
		mapPanel2.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MAP_SIZE_BORDER)));
		mapPanel2.add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.X,
				Mediator.getMessage(PropertiesKeys.MAP_LENGTH), DEFAULT_MAP_SIZE, arguments));
		mapPanel2.add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.Y,
				Mediator.getMessage(PropertiesKeys.MAP_HEIGHT), DEFAULT_MAP_HEIGHT, arguments));
		mapPanel2.add(ComponentUtil.createSpinner(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.Z,
				Mediator.getMessage(PropertiesKeys.MAP_WIDTH), DEFAULT_MAP_SIZE, arguments));

		options.add(mapPanel2);

		JPanel lightPanel = new JPanel(new GridLayout(5, 0, 5, 5));
		lightPanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.LIGHT_BORDER)));
		lightPanel.add(createLightSettingsTitle());
		lightPanel.add(ComponentUtil.createLightSpinners(0, 1, Consts.LIGHT_AMBIENT_R, Consts.LIGHT_AMBIENT_G,
				Consts.LIGHT_AMBIENT_B, Consts.LIGHT_AMBIENT_A, Mediator.getMessage(PropertiesKeys.AMBIENT),
				Mediator.getMessage(PropertiesKeys.AMBIENT_TOOLTIP), 0.5, arguments));
		lightPanel.add(ComponentUtil.createLightSpinners(0, 1, Consts.LIGHT_DIFFUSE_R, Consts.LIGHT_DIFFUSE_G,
				Consts.LIGHT_DIFFUSE_B, Consts.LIGHT_DIFFUSE_A, Mediator.getMessage(PropertiesKeys.DIFFUSE),
				Mediator.getMessage(PropertiesKeys.DIFFUSE_TOOLTIP), 0.8, arguments));
		lightPanel.add(ComponentUtil.createLightSpinners(0, 1, Consts.LIGHT_SPECULAR_R, Consts.LIGHT_SPECULAR_G,
				Consts.LIGHT_SPECULAR_B, Consts.LIGHT_SPECULAR_A, Mediator.getMessage(PropertiesKeys.SPECULAR),
				Mediator.getMessage(PropertiesKeys.SPECULAR_TOOLTIP), 0.5, arguments));
		lightPanel.add(ComponentUtil.createLightSpinners(-Consts.MAX_POSITION, Consts.MAX_POSITION, Consts.LIGHT_POSITION_X,
				Consts.LIGHT_POSITION_Y, Consts.LIGHT_POSITION_Z, Consts.LIGHT_POSITION_MODE,
				Mediator.getMessage(PropertiesKeys.LIGHT_POSITION), Mediator.getMessage(PropertiesKeys.LIGHT_POSITION_TOOLTIP), 1,
				arguments));
		options.add(lightPanel);
		add(options);
	}

	private JPanel createMaterialsSettingsTitle() {
		JPanel lightLegend = new JPanel();
		lightLegend.setLayout(new GridLayout(0, 4));
		addRGBLegend(lightLegend);
		return lightLegend;
	}

	private void createTextureOptionsPanel() {
		JPanel textureOptions = new JPanel(new GridLayout(2, 0, 5, 5));
		textureOptions.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.MATERIAL_OPTIONS)));

		JPanel settings = new JPanel(new GridLayout(7, 0, 5, 5));
		settings.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.SETTINGS)));
		settings.add(createMaterialsSettingsTitle());
		settings.add(ComponentUtil.createMaterialSpinners(0, 1, Consts.MATERIAL_AMBIENT_R, Consts.MATERIAL_AMBIENT_G,
				Consts.MATERIAL_AMBIENT_B, Mediator.getMessage(PropertiesKeys.AMBIENT),
				Mediator.getMessage(PropertiesKeys.MATERIAL_AMBIENT_TOOLTIP), 0.5, arguments));
		settings.add(ComponentUtil.createMaterialSpinners(0, 1, Consts.MATERIAL_DIFFUSE_R, Consts.MATERIAL_DIFFUSE_G,
				Consts.MATERIAL_DIFFUSE_B, Mediator.getMessage(PropertiesKeys.DIFFUSE),
				Mediator.getMessage(PropertiesKeys.MATERIAL_DIFFUSE_TOOLTIP), 0.8, arguments));
		settings.add(ComponentUtil.createMaterialSpinners(0, 1, Consts.MATERIAL_SPECULAR_R, Consts.MATERIAL_SPECULAR_G,
				Consts.MATERIAL_SPECULAR_B, Mediator.getMessage(PropertiesKeys.SPECULAR),
				Mediator.getMessage(PropertiesKeys.MATERIAL_SPECULAR_TOOLTIP), 0.5, arguments));

		settings.add(ComponentUtil.createSpinner(0, 1, Consts.MATERIAL_D, Mediator.getMessage(PropertiesKeys.OPACITY), 1,
				arguments, Mediator.getMessage(PropertiesKeys.MATERIAL_OPACITY_TOOLTIP)));
		settings.add(ComponentUtil.createSpinner(0, 1000, Consts.MATERIAL_NS, Mediator.getMessage(PropertiesKeys.SHINESS), 1,
				arguments, Mediator.getMessage(PropertiesKeys.MATERIAL_SHINESS_TOOLTIP)));

		settings.add(ComponentUtil.createSpinner(0.01, 9999, Consts.SCALE, Mediator.getMessage(PropertiesKeys.TEXTURE_SCALE), 1,
				arguments, Mediator.getMessage(PropertiesKeys.TEXTURE_SCALE_TOOLTIP)));
		textureOptions.add(settings);
		add(textureOptions);

		texture = new JPanel();
		WindowUtil.setBorder(texture, PropertiesKeys.TEXTURE);
		texture.setLayout(new GridLayout(1, 0));
		textureOptions.add(texture);
	}

	private void createPreview() {
		preview = new JPanel(new GridLayout(1, 0));
		WindowUtil.setBorder(preview, PropertiesKeys.MAP_PREVIEV_BORDER);
		add(preview);
	}

	public Map<String, Component> getArguments() {
		return arguments;
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

	public String getMapName() {
		return mapLabel.getText();
	}

	public BasicMapData getMapSettings() {
		BasicMapData data = new BasicMapData();
		data.setLength(getValue(Consts.X), getValue(Consts.Y), getValue(Consts.Z));
		return data;
	}

	public Material getMaterial() {
		Material mtl = new Material();
		RGB ambient = new RGB(getValue(Consts.MATERIAL_AMBIENT_R), getValue(Consts.MATERIAL_AMBIENT_G),
				getValue(Consts.MATERIAL_AMBIENT_B));
		RGB diffuse = new RGB(getValue(Consts.MATERIAL_DIFFUSE_R), getValue(Consts.MATERIAL_DIFFUSE_G),
				getValue(Consts.MATERIAL_DIFFUSE_B));
		RGB specular = new RGB(getValue(Consts.MATERIAL_SPECULAR_R), getValue(Consts.MATERIAL_SPECULAR_G),
				getValue(Consts.MATERIAL_SPECULAR_B));
		mtl.setAmbient(ambient);
		mtl.setDiffuse(diffuse);
		mtl.setSpecular(specular);
		mtl.setD(getValue(Consts.MATERIAL_D));
		mtl.setNs(getValue(Consts.MATERIAL_NS));
		if (!texturePath.getText().isEmpty()) {
			mtl.setTexture(new Texture(new MyFile(texturePath.getText()), getValue(Consts.SCALE)));
		}
		return mtl;
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

	public void setMapFileName(String name) {
		mapLabel.setText(name);
	}

	public void setMapHeight(String name) {
		mapHeightLabel.setText(name);
	}

	public void setMapObject(MapObject data) {
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
			RGB ambient = mtl.getAmbient();
			if (ambient != null) {
				arguments.get(Consts.MATERIAL_AMBIENT_R).setValue(ambient.getR());
				arguments.get(Consts.MATERIAL_AMBIENT_G).setValue(ambient.getG());
				arguments.get(Consts.MATERIAL_AMBIENT_B).setValue(ambient.getB());
			}

			RGB diffuse = mtl.getDiffuse();
			if (diffuse != null) {
				arguments.get(Consts.MATERIAL_DIFFUSE_R).setValue(diffuse.getR());
				arguments.get(Consts.MATERIAL_DIFFUSE_G).setValue(diffuse.getG());
				arguments.get(Consts.MATERIAL_DIFFUSE_B).setValue(diffuse.getB());
			}

			RGB specular = mtl.getSpecular();
			if (mtl != null) {
				arguments.get(Consts.MATERIAL_SPECULAR_R).setValue(specular.getR());
				arguments.get(Consts.MATERIAL_SPECULAR_G).setValue(specular.getG());
				arguments.get(Consts.MATERIAL_SPECULAR_B).setValue(specular.getB());
			}
			arguments.get(Consts.MATERIAL_D).setValue(mtl.getD());
			arguments.get(Consts.MATERIAL_NS).setValue(mtl.getNs());

			Texture txt = mtl.getTexture();
			if (txt != null) {
				setTextureScale(txt.getScale());
				setTexturePath(txt.getPath());
			}
		}
	}

	public void setMapProperties(String path, Dimension imageSize) {
		setMapFileName(path);
		setMapHeight(String.valueOf(imageSize.height) + " px");
		setMapWidth(String.valueOf(imageSize.width) + " px");
	}

	public void setMapWidth(String name) {
		mapWidthLabel.setText(name);
	}

	public void setTexturePath(String path) {
		texture.removeAll();
		BufferedImage pic;
		try {
			pic = ImageIO.read(new File(path));
			texture.add(new TexturePreveiwPanel(pic));
			texturePath.setText(path);
		} catch (IOException e) {
			WindowUtil.displayError(PropertiesKeys.FILE_NOT_IMAGE);
			e.printStackTrace();
		}

	}

	public void setTextureScale(double scale) {
		arguments.get(Consts.SCALE).setValue(scale);
	}

	public void addPreview(BufferedImage image) {
		preview.removeAll();
		preview.add(new ObjectsPreviewPanel(image));
	}
}