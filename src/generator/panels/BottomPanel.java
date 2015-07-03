package generator.panels;

import generator.Mediator;
import generator.actions.GenerateObjectsAction;
import generator.actions.LoadXMLAction;
import generator.actions.SaveXMLAction;
import generator.listeners.ChangeLanguageListener;
import generator.utils.PropertiesKeys;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BottomPanel extends JPanel {
	
	private static final long serialVersionUID = 2979767106989887354L;
	private JLabel xmlLabel;
	private JLabel saveLabel;

	public void setXmlName(String name)
	{
		xmlLabel.setText(name);
	}
	public void setXmlToSaveName(String name)
	{
		saveLabel.setText(name);
	}
	

	public String getXmlName()
	{
		return xmlLabel.getText();
	}
	public String getXmlToSaveName()
	{
		return saveLabel.getText();
	}
	
	public BottomPanel() {
		setLayout(new GridLayout(0,4));
		
		
		JPanel xmlPanel=new JPanel();
		xmlPanel.setLayout(new GridLayout(0,2));
		xmlPanel.setBorder(BorderFactory.createTitledBorder("Plik xml"));
		JButton loadXMLButton=new JButton(new LoadXMLAction(Mediator.getMessage(PropertiesKeys.LOAD_XML_BUTTON)));
		xmlPanel.add(loadXMLButton);
		xmlLabel = new JLabel();
		xmlPanel.add(xmlLabel);
		
		JPanel savePanel=new JPanel();
		savePanel.setLayout(new GridLayout(0,2));
		savePanel.setBorder(BorderFactory.createTitledBorder("Plik xml do zapisu"));
		JButton saveButton=new JButton(new SaveXMLAction(Mediator.getMessage(PropertiesKeys.SAVE_XML_BUTTON)));
		savePanel.add(saveButton);
		saveLabel = new JLabel();
		savePanel.add(saveLabel);
		
		JPanel languagePanel=new JPanel();
		languagePanel.setBorder(BorderFactory.createTitledBorder(Mediator.getMessage(PropertiesKeys.LANGUAGE)));
		JComboBox<Locale> languages=new JComboBox<Locale>();
		addLocales(languages);
		languages.addActionListener(new ChangeLanguageListener());
		languagePanel.add(languages);
		
		add(xmlPanel);
		add(savePanel);
		add(languagePanel);
		JButton generateButton=new JButton(new GenerateObjectsAction(Mediator.getMessage(PropertiesKeys.GENERATE_BUTTON)));
		add(generateButton);
		Mediator.registerBottomPanel(this);
}
	

	private void addLocales(JComboBox<Locale> locales){
		List <Locale> list= new ArrayList<>();
		list.add(new Locale("PL"));
		list.add(Locale.ENGLISH);
		Locale current=Mediator.getLocale();
		int index=0;
		for(int i=0; i<list.size();i++){
			locales.addItem(list.get(i));
			if(list.get(i).equals(current)){
				index=i;
			}
		}
		locales.setSelectedIndex(index);
	}
}
