package generator.models.generation;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import generator.Mediator;
import generator.actions.model.DeleteModelAction;
import generator.actions.model.EditModelAction;
import generator.utils.PropertiesKeys;

public class ObjectFileListRow extends JPanel implements MouseListener, ActionListener {

	private static final String DELETE_ACTION = "deleteAction";
	private static final String EDIT_ACTION = "editAction";
	private JLabel name;
	private JLabel path;
	private JPanel color;
	private ObjectInfo object;
	private static final long serialVersionUID = 4928053334292626614L;
	private static JPopupMenu menu = new JPopupMenu();
	private static ObjectFileListRow highlighted = null;
	private Color backgroundColor;
	private static final DeleteModelAction deleteAction = new DeleteModelAction(
			Mediator.getMessage(PropertiesKeys.DELETE_OBJECT));
	private static final EditModelAction editAction = new EditModelAction(Mediator.getMessage(PropertiesKeys.MODIFY_OBJECT));
	private static Set<ObjectFileListRow> selectedRows = new HashSet<>();
	private static List<ObjectFileListRow> allRows = new ArrayList<>();
	private static ObjectFileListRow clicked = null;
	private static JCheckBox masterCheckbox;
	private JCheckBox checkbox;

	static {
		JMenuItem edit = new JMenuItem(editAction);
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
		menu.add(edit);

		JMenuItem delete = new JMenuItem(deleteAction);
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, KeyEvent.CTRL_DOWN_MASK));
		menu.add(delete);
	}

	public ObjectFileListRow(ObjectInfo obj, int index) {
		this.object = obj;
		setMaximumSize(new Dimension(2000, 20));
		setLayout(new GridLayout(0, 4));
		setbackground(index);
		name = new JLabel(obj.getModel().getName());
		path = new JLabel(obj.getModel().getPath());
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
		path.setBorder(new EmptyBorder(0, 10, 0, 10));
		color.setBorder(new EmptyBorder(0, 10, 0, 10));

		checkbox = new JCheckBox();
		checkbox.setOpaque(false);
		checkbox.addActionListener(this);

		add(checkbox);
		add(name);
		add(color);
		add(path);

		createShorcruts();
		addMouseListener(this);
		allRows.add(this);
	}

	public static Component createTitle() {
		TableColumnModel columnModel = new ObjectFileTableColumnModel();
		DefaultTableModel model = new ObjectFileTableModel(new Class[] { Color.class, String.class, String.class });
		JTable tab = new ObjectFileTable(model, columnModel);
		GenerationModel generationModel = new GenerationModel("dupa", "kutas");
		ObjectInfo objectInfo = new ObjectInfo(generationModel);
		GenerationModel generationModel2 = new GenerationModel("chuj", "iksde");
		ObjectInfo objectInfo2 = new ObjectInfo(generationModel2);
		model.addRow(new ObjectInfo[] { objectInfo, objectInfo2 });
		return tab;
	}

	private void setbackground(int index) {
		if (index % 2 == 0) {
			backgroundColor = new Color(200, 192, 255);
		} else {
			backgroundColor = new Color(191, 223, 255);
		}
		setBackground(backgroundColor);
	}

	private void createShorcruts() {
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK),
				EDIT_ACTION);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK),
				DELETE_ACTION);

		getActionMap().put(EDIT_ACTION, editAction);
		getActionMap().put(DELETE_ACTION, deleteAction);
	}

	public void highlight() {
		if (highlighted != null) {
			unHighlight();
		}
		highlighted = this;
		setBackground(new Color(backgroundColor.getRed() - 20, backgroundColor.getGreen() - 20, backgroundColor.getBlue() - 20));
		object.getModel().swapColors();
		Mediator.refreshPreview();
		repaint();
	}

	public static void unHighlight() {
		if (highlighted != null) {
			highlighted.setBackground(new Color(highlighted.getBackgroundColor().getRed(),
					highlighted.getBackgroundColor().getGreen(), highlighted.getBackgroundColor().getBlue()));
			highlighted.repaint();
			highlighted.getObject().getModel().swapColors();
			Mediator.refreshPreview();
			highlighted = null;
		}
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			menu.setInvoker(this);
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
		if (selectedRows.size() == 0) {
			clicked = this;
		}
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

	public ObjectInfo getObject() {
		return object;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(checkbox)) {
			setClicked(null);
			if (checkbox.isSelected() == true) {
				selectedRows.add(this);
			} else {
				selectedRows.remove(this);
			}
		}
	}

	public boolean isSelected() {
		return checkbox.isSelected();
	}

	public void setSelected(boolean b) {
		checkbox.setSelected(b);
	}

	public static void toggleAll(boolean selected) {
		setClicked(null);
		if (selected) {
			selectedRows.addAll(allRows);
			for (ObjectFileListRow i : allRows) {
				i.setSelected(true);
			}
		} else {
			selectedRows.clear();
			for (ObjectFileListRow i : allRows) {
				i.setSelected(false);
			}
		}
	}

	public static Set<ObjectFileListRow> getSelectedRows() {
		if (clicked != null) {
			Set<ObjectFileListRow> set = new HashSet<>();
			set.add(clicked);
			return set;
		}
		return selectedRows;
	}

	public static ObjectFileListRow getClicked() {
		return clicked;
	}

	public static void setClicked(ObjectFileListRow clicked) {
		ObjectFileListRow.clicked = clicked;
	}

}
