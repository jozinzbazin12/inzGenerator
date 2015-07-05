package generator.listeners;

import generator.Mediator;
import generator.models.LangugeOption;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class ChangeLanguageListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof LangugeOption) {
			LangugeOption obj = (LangugeOption) e.getSource();
			Locale locale = (Locale) obj.getLocale();
			if (!locale.equals(Mediator.getLocale()))
				Mediator.changeLocale(locale);
		}

	}
}
