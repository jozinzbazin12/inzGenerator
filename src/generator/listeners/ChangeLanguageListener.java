package generator.listeners;

import generator.Mediator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JComboBox;

public class ChangeLanguageListener implements ActionListener {

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JComboBox<?>) {
			JComboBox<Locale> obj = (JComboBox<Locale>) e.getSource();
			Locale locale = (Locale) obj.getSelectedItem();
			if (!locale.equals(Mediator.getLocale()))
				Mediator.changeLocale(locale);
		}

	}
}
