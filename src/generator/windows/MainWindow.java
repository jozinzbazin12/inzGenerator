package generator.windows;

import generator.Mediator;
import generator.panels.BottomPanel;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.utils.PropertiesKeys;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ComponentListener {
	private static final String WINDOWS_LOOK = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private static final long serialVersionUID = 2924752215725936696L;
	private JTabbedPane tab;
	private BottomPanel bottomPanel;
	public void createWindow() {
		FirstTabPanel first=new FirstTabPanel();
		SecondTabPanel secondTab = new SecondTabPanel();
		bottomPanel = new BottomPanel();
		preparePanels(first,secondTab);
		add(bottomPanel, BorderLayout.WEST);
		revalidate();
	}


	private void preparePanels(JPanel first, JPanel second) {
		tab = new JTabbedPane();
		tab.addTab(Mediator.getMessage(PropertiesKeys.FIRST_TAB_NAME), first);
		tab.addTab(Mediator.getMessage(PropertiesKeys.SECOND_TAB_NAME), second);
		tab.setPreferredSize(new Dimension(getWidth(), (int) (getHeight()*0.85)));
		add(tab,BorderLayout.NORTH);	
	}

	public MainWindow(String name) {
		super(name);
		try {
			UIManager.setLookAndFeel(WINDOWS_LOOK);
		} catch (Exception e) {
		}
		JFrame.setDefaultLookAndFeelDecorated(false);
		setVisible(true);
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		addComponentListener(this);
	}


	@Override
	public void componentHidden(ComponentEvent e) {	
	}


	@Override
	public void componentMoved(ComponentEvent e) {	
	}


	@Override
	public void componentResized(ComponentEvent e) {
		if(tab!=null) tab.setPreferredSize(new Dimension(getWidth(), (int) (getHeight()*0.85)));	
		getContentPane().revalidate();
	}


	@Override
	public void componentShown(ComponentEvent e) {
	}
}
