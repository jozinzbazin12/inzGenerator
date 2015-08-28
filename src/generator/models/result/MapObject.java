package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MapObject {

	@XmlAttribute(name = "mapFile", required = true)
	private String mapFileName;

	@XmlAttribute(name = "textureFile", required = true)
	private String textureFileName;

	@XmlElement(name = "Settings", required = true)
	private BasicMapData basic;

	@XmlElement(name = "Light", required = true)
	private LightData lightData;

	public String getMapFileName() {
		return mapFileName;
	}

	public void setMapFileName(String mapFileName) {
		this.mapFileName = mapFileName;
	}

	public String getTextureFileName() {
		return textureFileName;
	}

	public void setTextureFileName(String textureFileName) {
		this.textureFileName = textureFileName;
	}

	public void setBasic(BasicMapData basic) {
		this.basic = basic;
	}

	public BasicMapData getBasic() {
		return basic;
	}

	public LightData getLightData() {
		return lightData;
	}

	public void setLightData(LightData lightData) {
		this.lightData = lightData;
	}
}
