package generator.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import generator.Mediator;
import generator.utils.PropertiesKeys;

public class HelpWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8660067461277447250L;
	private String help;
	private JButton ok;

	public HelpWindow(String name, String help, int width, int height) {
		super(name);
		this.help = help;
		createWindow(width, height);
	}

	public HelpWindow(String name, String help) {
		this(name, help, 400, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			dispose();
		}

	}

	private void createWindow(int width, int height) {
		setSize(width, height);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new GridLayout(0, 1));
		JTextArea area = new JTextArea(help);
		area.setEditable(false);
		area.setFont(new Font("Arial", 0, 12));
		area.setLineWrap(true);
		area.setOpaque(false);
		area.setWrapStyleWord(true);
		panel.add(area);

		add(panel, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		ok = new JButton(Mediator.getMessage(PropertiesKeys.OK));
		ok.addActionListener(this);
		ok.setPreferredSize(new Dimension(120, 30));
		bottom.add(ok, BorderLayout.CENTER);

		add(bottom, BorderLayout.PAGE_END);
	}
}
