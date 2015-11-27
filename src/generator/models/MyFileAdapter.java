package generator.models;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MyFileAdapter extends XmlAdapter<String, MyFile> {
	@Override
	public MyFile unmarshal(String v) throws Exception {
		return new MyFile(v);
	}

	@Override
	public String marshal(MyFile v) throws Exception {
		return v.getAbsolutePath();
	}

}
