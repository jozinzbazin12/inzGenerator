package generator.windows;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import generator.Mediator;
import generator.utils.PropertiesKeys;

public class HelpWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8660067461277447250L;
	private String help;
	private JButton ok;

	public HelpWindow(String name, String help) {
		super(name);
		this.help = help;
		createWindow();
	}

	private void createWindow() {
		setSize(600, 300);
		setLocation(400, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		JTextArea area = new JTextArea(help);
		area.setEditable(false);
		area.setFont(new Font("Arial", 0, 12));
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		panel.add(area);

		add(panel, BorderLayout.CENTER);
		JPanel bottom = new JPanel(new BorderLayout());
		ok = new JButton(Mediator.getMessage(PropertiesKeys.OK));
		ok.addActionListener(this);
		bottom.add(ok, BorderLayout.CENTER);

		add(bottom, BorderLayout.PAGE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			dispose();
		}

	}
}
