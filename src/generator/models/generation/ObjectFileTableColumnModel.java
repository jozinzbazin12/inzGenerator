package generator.models.generation;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import generator.Mediator;
import generator.utils.PropertiesKeys;

public class ObjectFileTableColumnModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = -5443564851650290151L;

	public ObjectFileTableColumnModel() {
		TableColumn color = new TableColumn(0);
		color.setHeaderValue(Mediator.getMessage(PropertiesKeys.COLOR));
		color.setMinWidth(10);
		color.setMaxWidth(50);

		TableColumn fileName = new TableColumn(1);
		fileName.setHeaderValue(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_NAME));
		fileName.setMinWidth(100);
		fileName.setMaxWidth(300);

		TableColumn path = new TableColumn(2);
		path.setHeaderValue(Mediator.getMessage(PropertiesKeys.OBJECT_FILE_LOCATION));
		path.setMinWidth(200);
		path.setMaxWidth(400);

		addColumn(color);
		addColumn(fileName);
		addColumn(path);

		color.setCellRenderer(new ColorCellRenderer());
		setColumnSelectionAllowed(false);
	}
}
