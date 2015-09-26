package generator.tables.model;

import java.awt.Color;

import generator.Mediator;
import generator.tables.AbstractTableColumnModel;
import generator.utils.PropertiesKeys;

public class ObjectFileTableColumnModel extends AbstractTableColumnModel {
	private static final Class<?>[] CLASSES = new Class[] { Color.class, String.class, String.class, Integer.class, String.class,
			String.class, String.class, String.class, String.class, String.class, Boolean.class };

	private static final long serialVersionUID = -5443564851650290151L;

	public ObjectFileTableColumnModel() {
		createColorColumn();

		createColumn(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_NAME), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_LOCATION), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.COUNT), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.MIN_POSITION), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.MAX_POSITION), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.MIN_SCALE), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.MAX_SCALE), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.MIN_ROTATION), 100, 300);
		createColumn(Mediator.getMessage(PropertiesKeys.MAX_ROTATION), 100, 300);

		createRelativeColumn();
		setColumnSelectionAllowed(false);
	}

	public static Class<?>[] getColumnClasses() {
		return CLASSES;
	}
}
