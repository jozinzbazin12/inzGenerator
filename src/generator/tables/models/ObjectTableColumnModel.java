package generator.tables;

import java.awt.Color;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import generator.Mediator;
import generator.utils.PropertiesKeys;

public class ObjectTableColumnModel extends DefaultTableColumnModel {
	private static final Class<?>[] CLASSES = new Class[] { Color.class, String.class, String.class, String.class, String.class };

	private static final long serialVersionUID = -5443564851650290151L;

	private int index;

	public ObjectTableColumnModel() {
		index = 0;
		TableColumn color = new TableColumn(index++);
		color.setHeaderValue(Mediator.getMessage(PropertiesKeys.COLOR));
		color.setMinWidth(10);
		color.setMaxWidth(50);
		addColumn(color);

		createColumn(PropertiesKeys.OBJECT_NAME, 100, 300);
		createColumn(PropertiesKeys.COORDINATES, 200, 400);
		createColumn(PropertiesKeys.SCALES, 200, 400);
		createColumn(PropertiesKeys.ROTATIONS, 200, 400);

		color.setCellRenderer(new ColorCellRenderer());
		setColumnSelectionAllowed(false);
	}

	private void createColumn(String msgKey, int minWidth, int maxWidth) {
		TableColumn col = new TableColumn(index++);
		col.setHeaderValue(Mediator.getMessage(msgKey));
		col.setMinWidth(minWidth);
		col.setMaxWidth(maxWidth);
		col.setCellRenderer(new CellRenderer());
		addColumn(col);
	}

	public static Class<?>[] getColumnClasses() {
		return CLASSES;
	}
}
