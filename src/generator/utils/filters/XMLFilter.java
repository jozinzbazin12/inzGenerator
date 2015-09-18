package generator.utils.filters;

public class XMLFilter extends AbstractFileFilter {
	private static final String[] TYPE = { "xml" };

	public XMLFilter(String description) {
		super(TYPE, description);
	}
}
