package generator.models.result;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Objects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultObject {
	@XmlElement(name = "Map", required = true)
	private MapObject mapObject = new MapObject();
	@XmlElement(name = "Object", required = true)
	private List<GeneratedObject> generatedObjects;

	public ResultObject(List<GeneratedObject> generatedObjects) {
		this.generatedObjects = generatedObjects;
	}

	public List<GeneratedObject> getGeneratedObjects() {
		return generatedObjects;
	}

	public void setMapObject(MapObject mapObject) {
		this.mapObject = mapObject;
	}

	public MapObject getMapObject() {
		return mapObject;
	}

	public ResultObject() {
	}

	public void setGeneratedObjects(List<GeneratedObject> generatedObjects) {
		this.generatedObjects = generatedObjects;
		
	}
}
