package generator.actions.object;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.models.generation.GenerationInfo;

public class GenerateObjectsAction extends AbstractAction {

	private static final long serialVersionUID = -2015637726992639128L;

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		GenerationInfo info = new GenerationInfo();
		info.setModels(new ArrayList<>(Mediator.getModels().values()));
		info.setArgs(Mediator.getAlgorithmArgs());
		Mediator.getResultObject().setGeneratedObjects(Mediator.getAlgorithm().generate(info));
		Mediator.updateObjects();
	}

	public GenerateObjectsAction(String name) {
		super(name);
	}
}
