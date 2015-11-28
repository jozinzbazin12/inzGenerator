package generator.windows;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
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
import generator.listeners.ChangePathTypeListener;
import generator.models.LangugeOption;
import generator.models.PathTypeSetting;
import generator.panels.FirstTabPanel;
import generator.panels.SecondTabPanel;
import generator.panels.ThirdTabPanel;
import generator.utils.PropertiesKeys;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 2924752215725936696L;
	private static final String WINDOWS_LOOK = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private static final String LINUX_LOOK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	private JTabbedPane tab;

	public MainWindow(String name) {
		super(name);
		try {
			if (Mediator.isLinux()) {
				UIManager.setLookAndFeel(LINUX_LOOK);
			} else {
				UIManager.setLookAndFeel(WINDOWS_LOOK);
			}
			JFrame.setDefaultLookAndFeelDecorated(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setVisible(true);
		setSize(1200, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	private JRadioButtonMenuItem createPathOption(ButtonGroup group, String name, boolean selected) {
		PathTypeSetting option = new PathTypeSetting(name);
		group.add(option);
		option.setSelected(selected);
		option.addActionListener(new ChangePathTypeListener());
		return option;
	}

	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(Mediator.getMessage(PropertiesKeys.FILE_MENU));

		JMenuItem openXmlOption = new JMenuItem(new LoadXMLAction(Mediator.getMessage(PropertiesKeys.LOAD_XML_OPTION)) {
			private static final long serialVersionUID = -6720737308249773635L;

			@Override
			protected void onSucess(File path) {
				Mediator.loadXMLFile(path);
			}
		});
		openXmlOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(openXmlOption);

		JMenuItem saveXmlOption = new JMenuItem(new SaveXMLAction(Mediator.getMessage(PropertiesKeys.SAVE_XML_OPTION)) {
			private static final long serialVersionUID = -2803868446968416714L;

			@Override
			protected void onSucess(File path) {
				Mediator.saveXMLFile(path.getAbsolutePath());
			}

		});
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

		JMenu pathMenu = new JMenu(Mediator.getMessage(PropertiesKeys.PATH_TYPE));
		ButtonGroup pathGroup = new ButtonGroup();
		pathMenu.add(createPathOption(pathGroup, Mediator.getMessage(PropertiesKeys.ABSOLUTE_PATH), true));
		pathMenu.add(createPathOption(pathGroup, Mediator.getMessage(PropertiesKeys.RELATIVE_PATH), false));

		optionsMenu.add(pathMenu);

		JMenuItem generateOption = new JMenuItem(new GenerateObjectsAction(Mediator.getMessage(PropertiesKeys.GENERATE_OPTION)));
		generateOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		optionsMenu.add(generateOption);

		menuBar.add(menu);
		menuBar.add(optionsMenu);
	}

	public void createWindow() {
		createMenu();
		JPanel first = new FirstTabPanel();
		JPanel second = new SecondTabPanel();
		JPanel third = new ThirdTabPanel();
		preparePanels(first, second, third);
		revalidate();
	}

	private void preparePanels(JPanel first, JPanel second, JPanel third) {
		tab = new JTabbedPane();
		tab.addTab(Mediator.getMessage(PropertiesKeys.FIRST_TAB_NAME), first);
		tab.addTab(Mediator.getMessage(PropertiesKeys.SECOND_TAB_NAME), second);
		tab.addTab(Mediator.getMessage(PropertiesKeys.THIRD_TAB_NAME), third);

		add(tab);
	}
}
