package generator.tables.object;

import java.awt.Color;
import java.text.MessageFormat;

import javax.swing.table.TableColumn;

import generator.Mediator;
import generator.tables.AbstractTableColumnModel;
import generator.tables.ColorCellRenderer;
import generator.utils.PropertiesKeys;

public class ObjectFileTableColumnModel extends AbstractTableColumnModel {
	private static final Class<?>[] CLASSES = new Class[] { Color.class, String.class, String.class, Integer.class, Double.class,
			Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class,
			Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class, Double.class,
			Double.class };
	private static final int SETTINGS_WIDTH = 120;
	private static final String X = "X";
	private static final long serialVersionUID = -5443564851650290151L;
	private static final String Y = "Y";
	private static final String Z = "Z";;

	public ObjectFileTableColumnModel() {
		TableColumn color = new TableColumn(index++);
		color.setHeaderValue(Mediator.getMessage(PropertiesKeys.COLOR));
		color.setMinWidth(10);
		color.setMaxWidth(50);

		TableColumn fileName = new TableColumn(index++);
		fileName.setHeaderValue(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_NAME));
		fileName.setMinWidth(100);
		fileName.setMaxWidth(300);

		TableColumn path = new TableColumn(index++);
		path.setHeaderValue(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_LOCATION));
		path.setMinWidth(200);
		path.setMaxWidth(400);

		TableColumn count = new TableColumn(index++);
		count.setHeaderValue("ilosc");
		count.setMaxWidth(100);

		String min = Mediator.getMessage(PropertiesKeys.MIN_ARG);
		String max = Mediator.getMessage(PropertiesKeys.MAX_ARG);
		String pos = Mediator.getMessage(PropertiesKeys.COORDINATE);
		String rot = Mediator.getMessage(PropertiesKeys.ROTATION);
		String sca = Mediator.getMessage(PropertiesKeys.SCALE);

		TableColumn minPosX = new TableColumn(index++);
		minPosX.setHeaderValue(MessageFormat.format(min, MessageFormat.format(pos, X)));
		minPosX.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxPosX = new TableColumn(index++);
		maxPosX.setHeaderValue(MessageFormat.format(max, MessageFormat.format(pos, X)));
		maxPosX.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minPosY = new TableColumn(index++);
		minPosY.setHeaderValue(MessageFormat.format(min, MessageFormat.format(pos, Y)));
		minPosY.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxPosY = new TableColumn(index++);
		maxPosY.setHeaderValue(MessageFormat.format(max, MessageFormat.format(pos, Y)));
		maxPosY.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minPosZ = new TableColumn(index++);
		minPosZ.setHeaderValue(MessageFormat.format(min, MessageFormat.format(pos, Z)));
		minPosZ.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxPosZ = new TableColumn(index++);
		maxPosZ.setHeaderValue(MessageFormat.format(max, MessageFormat.format(pos, Z)));
		maxPosZ.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minScaX = new TableColumn(index++);
		minScaX.setHeaderValue(MessageFormat.format(min, MessageFormat.format(sca, X)));
		minScaX.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxScaX = new TableColumn(index++);
		maxScaX.setHeaderValue(MessageFormat.format(max, MessageFormat.format(sca, X)));
		maxScaX.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minScaY = new TableColumn(index++);
		minScaY.setHeaderValue(MessageFormat.format(min, MessageFormat.format(sca, Y)));
		minScaY.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxScaY = new TableColumn(index++);
		maxScaY.setHeaderValue(MessageFormat.format(max, MessageFormat.format(sca, Y)));
		maxScaY.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minScaZ = new TableColumn(index++);
		minScaZ.setHeaderValue(MessageFormat.format(min, MessageFormat.format(sca, Z)));
		minScaZ.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxScaZ = new TableColumn(index++);
		maxScaZ.setHeaderValue(MessageFormat.format(max, MessageFormat.format(sca, Z)));
		maxScaZ.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minRotX = new TableColumn(index++);
		minRotX.setHeaderValue(MessageFormat.format(min, MessageFormat.format(rot, X)));
		minRotX.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxRotX = new TableColumn(index++);
		maxRotX.setHeaderValue(MessageFormat.format(max, MessageFormat.format(rot, X)));
		maxRotX.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minRotY = new TableColumn(index++);
		minRotY.setHeaderValue(MessageFormat.format(min, MessageFormat.format(rot, Y)));
		minRotY.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxRotY = new TableColumn(index++);
		maxRotY.setHeaderValue(MessageFormat.format(max, MessageFormat.format(rot, Y)));
		maxRotY.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn minRotZ = new TableColumn(index++);
		minRotZ.setHeaderValue(MessageFormat.format(min, MessageFormat.format(rot, Z)));
		minRotZ.setPreferredWidth(SETTINGS_WIDTH);

		TableColumn maxRotZ = new TableColumn(index++);
		maxRotZ.setHeaderValue(MessageFormat.format(max, MessageFormat.format(rot, Z)));
		maxRotZ.setPreferredWidth(SETTINGS_WIDTH);

		addColumn(color);
		addColumn(fileName);
		addColumn(path);
		addColumn(count);

		addColumn(minPosX);
		addColumn(maxPosX);
		addColumn(minPosY);
		addColumn(maxPosY);
		addColumn(minPosZ);
		addColumn(maxPosZ);

		addColumn(minScaX);
		addColumn(maxScaX);
		addColumn(minScaY);
		addColumn(maxScaY);
		addColumn(minScaZ);
		addColumn(maxScaZ);

		addColumn(minRotX);
		addColumn(maxRotX);
		addColumn(minRotY);
		addColumn(maxRotY);
		addColumn(minRotZ);
		addColumn(maxRotZ);

		color.setCellRenderer(new ColorCellRenderer());
		setColumnSelectionAllowed(false);
	}

	public static Class<?>[] getColumnClasses() {
		return CLASSES;
	}
}
