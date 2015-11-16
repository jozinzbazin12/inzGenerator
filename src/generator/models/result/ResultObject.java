package generator.models.result;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import generator.models.generation.GenerationInfo;

@XmlRootElement(name = "Objects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultObject {
	@XmlElement(name = "Object", required = true)
	private List<GeneratedObject> generatedObjects = new ArrayList<>();
	@XmlElement(name = "Settings")
	private GenerationInfo generationInfo = new GenerationInfo();
	@XmlElement(name = "Map", required = true)
	private MapObject mapObject = new MapObject();

	public ResultObject() {
	}

	public ResultObject(List<GeneratedObject> generatedObjects) {
		this.generatedObjects = generatedObjects;
	}

	public List<GeneratedObject> getGeneratedObjects() {
		return generatedObjects;
	}

	public GenerationInfo getGenerationInfo() {
		return generationInfo;
	}

	public MapObject getMapObject() {
		return mapObject;
	}

	public void setGeneratedObjects(List<GeneratedObject> generatedObjects) {
		this.generatedObjects = generatedObjects;

	}

	public void setGenerationInfo(GenerationInfo generationInfo) {
		this.generationInfo = generationInfo;
	}

	public void setMapObject(MapObject mapObject) {
		this.mapObject = mapObject;
	}

}
