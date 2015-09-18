package generator.utils.filters;

public class ObjFilter extends AbstractFileFilter {
	private static final String TYPE[] = { "obj" };

	public ObjFilter(String description) {
		super(TYPE, description);
	}
}
