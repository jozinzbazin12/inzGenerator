package generator.models.result;


public class GeneratedObject {
	private String objectFile;
	private BasicModelData basic;
	public GeneratedObject(BasicModelData basic) {
		this.basic=basic;
	}
	public BasicModelData getBasic() {
		return basic;
	}

	public GeneratedObject(String name, BasicModelData data) {
		objectFile=name;
		basic=data;
	}
	public String getObjectFile() {
		return objectFile;
	}
	
}
