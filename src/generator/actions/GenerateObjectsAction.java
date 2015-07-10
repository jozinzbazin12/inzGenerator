package generator.actions;

import generator.Mediator;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ObjectInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;
import generator.utils.Consts;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

public class GenerateObjectsAction extends AbstractAction {

	private static final long serialVersionUID = -2015637726992639128L;

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		GenerationInfo info = new GenerationInfo();
		List<ObjectInfo> objects = new ArrayList<ObjectInfo>();
		info.setCount(30);
		PositionSettings pos = new PositionSettings(Mediator.getGenerationInfoArguments(Consts.MIN_X), Mediator.getGenerationInfoArguments(Consts.MAX_X), Mediator.getGenerationInfoArguments(Consts.MIN_Y),
				Mediator.getGenerationInfoArguments(Consts.MAX_Y), Mediator.getGenerationInfoArguments(Consts.MIN_Z), Mediator.getGenerationInfoArguments(Consts.MAX_Z), true);

		ScaleSettings scale = new ScaleSettings(Mediator.getGenerationInfoArguments(Consts.MIN_SX), Mediator.getGenerationInfoArguments(Consts.MAX_SX), Mediator.getGenerationInfoArguments(Consts.MIN_SY),
				Mediator.getGenerationInfoArguments(Consts.MAX_SY), Mediator.getGenerationInfoArguments(Consts.MIN_SZ), Mediator.getGenerationInfoArguments(Consts.MAX_SZ));

		RotationSettings rotation = new RotationSettings(Mediator.getGenerationInfoArguments(Consts.MIN_RX), Mediator.getGenerationInfoArguments(Consts.MAX_RX),
				Mediator.getGenerationInfoArguments(Consts.MIN_RY), Mediator.getGenerationInfoArguments(Consts.MAX_RY), Mediator.getGenerationInfoArguments(Consts.MIN_RZ),
				Mediator.getGenerationInfoArguments(Consts.MAX_RZ));

		ObjectInfo obj = new ObjectInfo(0, pos, rotation, scale);
		objects.add(obj);
		info.setObjects(objects);
		Mediator.getResultObject().setGeneratedObjects(Mediator.getAlgorithm().generate(info));;
		Mediator.printOnPreview();
	}

	public GenerateObjectsAction(String name) {
		super(name);
	}
}
