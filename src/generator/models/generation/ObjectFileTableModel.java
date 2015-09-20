package generator.models.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class ObjectFileTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 4537045252888015583L;

	private List<ObjectInfo> data;
	private Class<?>[] classes;

	@Override
	public void addRow(Object[] rowData) {
		int oldCount = getRowCount();
		ObjectInfo[] objectInfoData = (ObjectInfo[]) rowData;
		data = Arrays.asList(objectInfoData);
		fireTableRowsDeleted(0, oldCount);
		fireTableRowsInserted(0, data.size());
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public int getRowCount() {
		if (data == null) {
			return 0;
		}
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return classes[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getValue(rowIndex, columnIndex);
	}

	private Object getValue(int row, int index) {
		if (data != null && data.size() > row) {
			ObjectInfo objectInfo = data.get(row);
			PositionSettings positionSettings = objectInfo.getPositionSettings();
			ScaleSettings scaleSettings = objectInfo.getScaleSettings();
			RotationSettings rotationSettings = objectInfo.getRotationSettings();
			GenerationModel model = objectInfo.getModel();
			switch (index) {
			case 0:
				return model.getColor();
			case 1:
				return model.getName();
			case 2:
				return model.getPath();
			case 3:
				return objectInfo.getCount();

			case 4:
				return positionSettings.getMinX();
			case 5:
				return positionSettings.getMaxX();
			case 6:
				return positionSettings.getMinY();
			case 7:
				return positionSettings.getMaxY();
			case 8:
				return positionSettings.getMinZ();
			case 9:
				return positionSettings.getMaxZ();

			case 10:
				return scaleSettings.getMinX();
			case 11:
				return scaleSettings.getMaxX();
			case 12:
				return scaleSettings.getMinY();
			case 13:
				return scaleSettings.getMaxY();
			case 14:
				return scaleSettings.getMinZ();
			case 15:
				return scaleSettings.getMaxZ();

			case 16:
				return rotationSettings.getMinX();
			case 17:
				return rotationSettings.getMaxX();
			case 18:
				return rotationSettings.getMinY();
			case 19:
				return rotationSettings.getMaxY();
			case 20:
				return rotationSettings.getMinZ();
			case 21:
				return rotationSettings.getMaxZ();
			}
			throw new RuntimeException("No such column");
		} else {
			return null;
		}
	}

	public ObjectFileTableModel(Class<?>[] classes) {
		data = new ArrayList<>();
		this.classes = classes;
	}

}
