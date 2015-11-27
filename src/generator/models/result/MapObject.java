package generator.models.result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import generator.models.MyFile;
import generator.models.MyFileAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class MapObject {

	@XmlElement(name = "Settings", required = true)
	private BasicMapData basic;

	@XmlElement(name = "Light", required = true)
	private LightData lightData;

	@XmlJavaTypeAdapter(MyFileAdapter.class)
	@XmlAttribute(name = "mapFile", required = true)
	private MyFile mapFileName;

	@XmlElement(name = "Material", required = true)
	private Material material;

	public BasicMapData getBasic() {
		return basic;
	}

	public LightData getLightData() {
		return lightData;
	}

	public MyFile getMapFileName() {
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

	public void setMapFileName(MyFile mapFileName) {
		this.mapFileName = mapFileName;
	}

	public void setMapFileName(String mapFileName) {
		this.mapFileName = new MyFile(mapFileName);
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
