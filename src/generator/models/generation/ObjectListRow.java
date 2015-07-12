package generator.models.generation;

import generator.Mediator;
import generator.actions.DeleteObjectAction;
import generator.actions.EditObjectAction;
import generator.actions.NewObjectAction;
import generator.models.result.GeneratedObject;
import generator.utils.PropertiesKeys;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class ObjectListRow extends JPanel implements MouseListener, Comparable<ObjectListRow> {

	private static final long serialVersionUID = 5353030562828445813L;
	private static ObjectListRow clicked = null;
	private static ObjectListRow highlighted = null;
	private int index;
	private Color backgroundColor;
	private GeneratedObject object;
	private static JPopupMenu menu;
	private JLabel name;
	private JLabel position;
	private JLabel scale;
	private JLabel rotation;
	private JPanel color;

	public static Component createTitle() {
		JPanel title = new JPanel();
		title.setMaximumSize(new Dimension(2000, 20));
		title.setLayout(new GridLayout(0, 5));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.OBJECT_NAME)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.COORDINATES)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.SCALES)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.ROTATIONS)));
		title.add(new JLabel(Mediator.getMessage(PropertiesKeys.COLOR)));
		for (Object i : title.getComponents()) {
			if (i instanceof JLabel) {
				((JComponent) i).setFont(new Font(((Component) i).getFont().getName(), Font.BOLD, 15));
				((JComponent) i).setBorder(new EmptyBorder(0, 10, 0, 10));
			}
		}
		title.setBackground(new Color(128, 128, 255));
		return title;
	}

	public ObjectListRow(GeneratedObject obj, int index) {
		this.index = index;
		this.object = obj;
		setMaximumSize(new Dimension(2000, 20));
		setLayout(new GridLayout(0, 5));
		setbackground(index);
		name = new JLabel(obj.getObjectFile());
		position = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getX(), obj.getBasic().getY(), obj.getBasic().getZ()));
		scale = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getSx(), obj.getBasic().getSy(), obj.getBasic().getSz()));
		rotation = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getRx(), obj.getBasic().getRy(), obj.getBasic().getRz()));
		color = new JPanel() {
			private static final long serialVersionUID = 5649120561651341378L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.black);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
		};
		color.setBackground(obj.getModel().getColor());

		name.setBorder(new EmptyBorder(0, 10, 0, 10));
		position.setBorder(new EmptyBorder(0, 10, 0, 10));
		scale.setBorder(new EmptyBorder(0, 10, 0, 10));
		rotation.setBorder(new EmptyBorder(0, 10, 0, 10));
		color.setBorder(new EmptyBorder(0, 10, 0, 10));

		add(name);
		add(position);
		add(scale);
		add(rotation);
		add(color);

		menu = new JPopupMenu();
		DeleteObjectAction deleteAction = new DeleteObjectAction(Mediator.getMessage(PropertiesKeys.DELETE_OBJECT));
		EditObjectAction editAction = new EditObjectAction(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));
		NewObjectAction newAction = new NewObjectAction(Mediator.getMessage(PropertiesKeys.NEW_OBJECT));

		JMenuItem neww = new JMenuItem(newAction);
		neww.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		menu.add(neww);

		menu.add(new JSeparator());

		JMenuItem edit = new JMenuItem(editAction);
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		menu.add(edit);

		JMenuItem delete = new JMenuItem(deleteAction);
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		menu.add(delete);

		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "newAction");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK), "editAction");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "deleteAction");

		getActionMap().put("newAction", newAction);
		getActionMap().put("editAction", editAction);
		getActionMap().put("deleteAction", deleteAction);

		addMouseListener(this);
	}

	private void setbackground(int index) {
		if (index % 2 == 0)
			backgroundColor = new Color(200, 192, 255);
		else
			backgroundColor = new Color(191, 223, 255);
		setBackground(backgroundColor);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (clicked == this) {
			g.setColor(Color.black);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		setbackground(index);
		this.index = index;
	}

	public void highlight() {
		if (highlighted != null)
			unHighlight();
		highlighted = this;
		setBackground(new Color(backgroundColor.getRed() - 20, backgroundColor.getGreen() - 20, backgroundColor.getBlue() - 20));
		getObject().swapColors();
		Mediator.refreshPreview();
		repaint();
	}

	public static void unHighlight() {
		if (highlighted != null) {
			highlighted.setBackground(new Color(highlighted.getBackgroundColor().getRed(), highlighted.getBackgroundColor().getGreen(), highlighted
					.getBackgroundColor().getBlue()));
			highlighted.repaint();
			highlighted.getObject().swapColors();
			Mediator.refreshPreview();
			highlighted = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			menu.setInvoker(this);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		setClicked(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		highlight();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		unHighlight();
	}

	@Override
	public int compareTo(ObjectListRow o) {
		return object.getObjectFile().compareTo(o.getObject().getObjectFile());
	}

	public GeneratedObject getObject() {
		return object;
	}

	public static ObjectListRow getClicked() {
		return clicked;
	}

	public static void setClicked(ObjectListRow obj) {
		if (clicked != obj) {
			ObjectListRow prev = clicked;
			clicked = obj;
			if (prev != null) {
				prev.repaint();
			}
		} else {
			clicked = null;
		}
		clicked = obj;
		if (clicked != null)
			clicked.repaint();
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void refresh() {
		name.setText(object.getObjectFile());
		position.setText(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", object.getBasic().getX(), object.getBasic().getY(), object.getBasic().getZ()));
		scale.setText(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", object.getBasic().getSx(), object.getBasic().getSy(), object.getBasic().getSz()));
		rotation.setText(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", object.getBasic().getRx(), object.getBasic().getRy(), object.getBasic().getRz()));
		//color.setBackground(pointColor);
	}

}
