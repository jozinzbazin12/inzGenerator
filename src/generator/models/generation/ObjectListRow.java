package generator.models.generation;

import generator.Mediator;
import generator.actions.DeleteObjectAction;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.MessageFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ObjectListRow extends JPanel {

	private static final long serialVersionUID = 5353030562828445813L;
	private int index;

	public ObjectListRow(GeneratedObject obj, int index) {
		this.index = index;
		setMaximumSize(new Dimension(2000,20));
		Color color = null;
		if (index % 2 == 0)
			color = new Color(200, 192, 255);
		else
			color = new Color(191, 223, 255);
		setLayout(new GridLayout(0, 6));
		setBackground(color);
		JLabel name = new JLabel(obj.getObjectFile());
		JLabel position = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getX(), obj.getBasic().getY(), obj.getBasic().getZ()));
		JLabel scale = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getSx(), obj.getBasic().getSy(), obj.getBasic().getSz()));
		JLabel rotation = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getRx(), obj.getBasic().getRy(), obj.getBasic().getRz()));

		name.setBorder(new EmptyBorder(0, 10, 0, 10));
		position.setBorder(new EmptyBorder(0, 10, 0, 10));
		scale.setBorder(new EmptyBorder(0, 10, 0, 10));
		rotation.setBorder(new EmptyBorder(0, 10, 0, 10));

		JButton edit = new JButton(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));
		JButton delete = new JButton(new DeleteObjectAction(Mediator.getMessage(PropertiesKeys.DELETE_OBJECT)));

		edit.setBackground(color);
		delete.setBackground(color);
		add(name);
		add(position);
		add(scale);
		add(rotation);
		add(edit);
		add(delete);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
