package generator.actions.object;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.AbstractAction;

import generator.Mediator;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ModelInfo;
import generator.utils.PropertiesKeys;
import generator.utils.WindowUtil;

public class GenerateObjectsAction extends AbstractAction {

	private static final long serialVersionUID = -2015637726992639128L;

	public GenerateObjectsAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		GenerationInfo info = new GenerationInfo();
		Map<String, ModelInfo> models = Mediator.getModels();
		if (models.isEmpty()) {
			WindowUtil.displayError(PropertiesKeys.NO_RESULT);
			return;
		}
		info.setModels(new ArrayList<>(models.values()));
		info.setArgs(Mediator.getAlgorithmArgs());
		Mediator.getResultObject().setGeneratedObjects(Mediator.getAlgorithm().generate(info));
		Mediator.updateObjects();
	}
}
