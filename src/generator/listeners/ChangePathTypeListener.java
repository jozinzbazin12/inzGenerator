package generator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import generator.Mediator;
import generator.models.PathTypeSetting;
import generator.models.generation.GenerationModel;
import generator.utils.PropertiesKeys;

public class ChangePathTypeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof PathTypeSetting) {
			PathTypeSetting obj = (PathTypeSetting) e.getSource();
			if (obj.getName().equals(Mediator.getMessage(PropertiesKeys.ABSOLUTE_PATH))) {
				GenerationModel.setAbsolute(true);
			} else {
				GenerationModel.setAbsolute(false);
			}
		}
	}
}
