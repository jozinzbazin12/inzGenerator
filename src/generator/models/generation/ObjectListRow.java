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
	private int index;
	private Color backgroundColor;
	private GeneratedObject object;
	private static JPopupMenu menu;

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

	public ObjectListRow(GeneratedObject obj, Color pointColor, int index) {
		this.index = index;
		this.object = obj;
		setMaximumSize(new Dimension(2000, 20));
		setLayout(new GridLayout(0, 5));
		setbackground(index);
		JLabel name = new JLabel(obj.getObjectFile());
		JLabel position = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getX(), obj.getBasic().getY(), obj.getBasic().getZ()));
		JLabel scale = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getSx(), obj.getBasic().getSy(), obj.getBasic().getSz()));
		JLabel rotation = new JLabel(MessageFormat.format("X: {0}, Y: {1}, Z: {2}", obj.getBasic().getRx(), obj.getBasic().getRy(), obj.getBasic().getRz()));
		JPanel color = new JPanel() {
			private static final long serialVersionUID = 5649120561651341378L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.black);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			}
		};
		color.setBackground(pointColor);

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
		EditObjectAction editAction=new EditObjectAction(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));
		NewObjectAction newAction=new NewObjectAction(Mediator.getMessage(PropertiesKeys.NEW_OBJECT));
		
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			menu.setInvoker(this);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		if (clicked != this) {
			ObjectListRow prev = clicked;
			clicked = this;
			if (prev != null) {
				prev.repaint();
			}
		} else {
			clicked = null;
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setBackground(new Color(backgroundColor.getGreen() - 20, backgroundColor.getBlue() - 20, backgroundColor.getRed() - 20));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setBackground(backgroundColor);
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

	public static void setClicked(ObjectListRow clicked) {
		ObjectListRow.clicked = clicked;
	}
	
}
