package generator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import generator.Mediator;
import generator.models.MyFile;
import generator.models.PathTypeSetting;
import generator.utils.PropertiesKeys;

public class ChangePathTypeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof PathTypeSetting) {
			PathTypeSetting obj = (PathTypeSetting) e.getSource();
			if (obj.getName().equals(Mediator.getMessage(PropertiesKeys.ABSOLUTE_PATH))) {
				MyFile.setAbsolute(true);
			} else {
				MyFile.setAbsolute(false);
			}
		}
	}
}
