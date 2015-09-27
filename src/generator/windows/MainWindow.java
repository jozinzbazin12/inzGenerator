package generator.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import generator.Mediator;
import generator.actions.ExitAction;
import generator.actions.LoadXMLAction;
import generator.actions.SaveXMLAction;
import generator.actions.object.GenerateObjectsAction;
import generator.listeners.ChangeLanguageListener;
import generator.models.LangugeOption;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.panels.ThirdTabPanel;
import generator.utils.PropertiesKeys;

public class MainWindow extends JFrame {
	private static final String WINDOWS_LOOK = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private static final long serialVersionUID = 2924752215725936696L;
	private JTabbedPane tab;

	public void createWindow() {
		createMenu();
		JPanel first = new FirstTabPanel();
		JPanel second = new SecondTabPanel();
		JPanel third = new ThirdTabPanel();
		preparePanels(first, second, third);
		revalidate();
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(Mediator.getMessage(PropertiesKeys.FILE_MENU));

		JMenuItem openXmlOption = new JMenuItem(new LoadXMLAction(Mediator.getMessage(PropertiesKeys.LOAD_XML_OPTION)));
		openXmlOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(openXmlOption);

		JMenuItem saveXmlOption = new JMenuItem(new SaveXMLAction(Mediator.getMessage(PropertiesKeys.SAVE_XML_OPTION)));
		saveXmlOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menu.add(saveXmlOption);

		menu.add(new JSeparator());

		JMenuItem exitAction = new JMenuItem(new ExitAction(Mediator.getMessage(PropertiesKeys.EXIT)));

		menu.add(exitAction);

		JMenu optionsMenu = new JMenu(Mediator.getMessage(PropertiesKeys.OPTIONS_MENU));

		JMenu languageMenu = new JMenu(Mediator.getMessage(PropertiesKeys.LANGUAGE_MENU));
		ButtonGroup group = new ButtonGroup();
		languageMenu.add(createLanguage(group, new Locale("PL"), "Polski"));
		languageMenu.add(createLanguage(group, Locale.ENGLISH, "English"));
		optionsMenu.add(languageMenu);

		JMenuItem generateOption = new JMenuItem(new GenerateObjectsAction(Mediator.getMessage(PropertiesKeys.GENERATE_OPTION)));
		generateOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		optionsMenu.add(generateOption);

		menuBar.add(menu);
		menuBar.add(optionsMenu);
	}

	private JRadioButtonMenuItem createLanguage(ButtonGroup group, Locale locale, String name) {
		LangugeOption languageOption = new LangugeOption(name, locale);
		group.add(languageOption);
		languageOption.addActionListener(new ChangeLanguageListener());
		if (languageOption.getLocale().equals(Mediator.getLocale())) {
			languageOption.setSelected(true);
		}
		return languageOption;
	}

	private void preparePanels(JPanel first, JPanel second, JPanel third) {
		tab = new JTabbedPane();
		tab.addTab(Mediator.getMessage(PropertiesKeys.FIRST_TAB_NAME), first);
		tab.addTab(Mediator.getMessage(PropertiesKeys.SECOND_TAB_NAME), second);
		tab.addTab(Mediator.getMessage(PropertiesKeys.THIRD_TAB_NAME), third);

		add(tab, BorderLayout.NORTH);
	}

	public MainWindow(String name) {
		super(name);
		try {
			UIManager.setLookAndFeel(WINDOWS_LOOK);
			JFrame.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
		}

		setVisible(true);
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
