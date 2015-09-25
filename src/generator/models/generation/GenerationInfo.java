package generator.models.generation;

import java.util.List;

public class GenerationInfo {

	private List<ObjectInfo> objects;
	private int count;

	public List<ObjectInfo> getObjects() {
		return objects;
	}

	public void setObjects(List<ObjectInfo> objects) {
		this.objects = objects;
	}

	@Deprecated
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
