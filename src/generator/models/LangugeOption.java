package generator.models;

import java.text.MessageFormat;
import java.util.Locale;

import javax.swing.JRadioButtonMenuItem;

public class LangugeOption extends JRadioButtonMenuItem {

	private static final long serialVersionUID = 1076446707858885610L;
	private String name;
	private Locale locale;

	public LangugeOption(String name, Locale locale) {
		super(MessageFormat.format("{0} ({1})", name, locale.getLanguage().toUpperCase()));
		this.name = name;
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
