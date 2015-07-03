package generator.actions;

import generator.Mediator;
import generator.models.generation.GenerationInfo;
import generator.models.generation.ObjectInfo;
import generator.models.generation.PositionSettings;
import generator.models.generation.RotationSettings;
import generator.models.generation.ScaleSettings;
import generator.models.result.ResultObject;
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
		PositionSettings pos = new PositionSettings(Mediator.getArgument(Consts.MIN_X), Mediator.getArgument(Consts.MAX_X), Mediator.getArgument(Consts.MIN_Y),
				Mediator.getArgument(Consts.MAX_Y), Mediator.getArgument(Consts.MIN_Z), Mediator.getArgument(Consts.MAX_Z), true);

		ScaleSettings scale = new ScaleSettings(Mediator.getArgument(Consts.MIN_SX), Mediator.getArgument(Consts.MAX_SX), Mediator.getArgument(Consts.MIN_SY),
				Mediator.getArgument(Consts.MAX_SY), Mediator.getArgument(Consts.MIN_SZ), Mediator.getArgument(Consts.MAX_SZ));

		RotationSettings rotation = new RotationSettings(Mediator.getArgument(Consts.MIN_RX), Mediator.getArgument(Consts.MAX_RX),
				Mediator.getArgument(Consts.MIN_RY), Mediator.getArgument(Consts.MAX_RY), Mediator.getArgument(Consts.MIN_RZ),
				Mediator.getArgument(Consts.MAX_RZ));

		ObjectInfo obj = new ObjectInfo(0, pos, rotation, scale);
		objects.add(obj);
		info.setObjects(objects);
		Mediator.setResultObject(new ResultObject(Mediator.getAlgorithm().generate(info)));
		Mediator.printOnPreview();
	}

	public GenerateObjectsAction(String name) {
		super(name);
	}
}
