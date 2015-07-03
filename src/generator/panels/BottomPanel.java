package generator.panels;

import generator.Mediator;
import generator.actions.GenerateObjectsAction;
import generator.actions.LoadXMLAction;
import generator.actions.SaveXMLAction;
import generator.utils.PropertiesKeys;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
		setLayout(new GridLayout(0,3));
		
		
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
		
		add(xmlPanel);
		add(savePanel);
		JButton generateButton=new JButton(new GenerateObjectsAction(Mediator.getMessage(PropertiesKeys.GENERATE_BUTTON)));
		add(generateButton);
		Mediator.registerBottomPanel(this);
}
}
