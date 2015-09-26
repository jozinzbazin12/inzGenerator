package generator.tables.object;

import java.awt.Color;

import generator.Mediator;
import generator.tables.AbstractTableColumnModel;
import generator.utils.PropertiesKeys;

public class ObjectTableColumnModel extends AbstractTableColumnModel {
	private static final Class<?>[] CLASSES = new Class[] { Color.class, String.class, String.class, String.class, String.class,
			Boolean.class };

	private static final long serialVersionUID = -5443564851650290151L;

	public ObjectTableColumnModel() {
		createColorColumn();

		createColumn(Mediator.getMessage(PropertiesKeys.OBJECT_NAME), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.COORDINATES), 200, 400);
		createColumn(Mediator.getMessage(PropertiesKeys.SCALES), 200, 400);
		createColumn(Mediator.getMessage(PropertiesKeys.ROTATIONS), 200, 400);

		createRelativeColumn();

		setColumnSelectionAllowed(false);
	}

	public static Class<?>[] getColumnClasses() {
		return CLASSES;
	}
}
