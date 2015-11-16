package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MapObject {

	@XmlElement(name = "Settings", required = true)
	private BasicMapData basic;

	@XmlElement(name = "Light", required = true)
	private LightData lightData;

	@XmlAttribute(name = "mapFile", required = true)
	private String mapFileName;

	@XmlElement(name = "Material", required = true)
	private Material material;

	public BasicMapData getBasic() {
		return basic;
	}

	public LightData getLightData() {
		return lightData;
	}

	public String getMapFileName() {
		return mapFileName;
	}

	public Material getMaterial() {
		return material;
	}

	public void setBasic(BasicMapData basic) {
		this.basic = basic;
	}

	public void setLightData(LightData lightData) {
		this.lightData = lightData;
	}

	public void setMapFileName(String mapFileName) {
		this.mapFileName = mapFileName;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
