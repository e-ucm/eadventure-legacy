package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.LangString;


public class LOMLangStringPanel extends JPanel{

    
    private JTextField value;
    
    private JTextField language;
    
    private LangString langstring;
    
    public LOMLangStringPanel(LangString langstring,String border){
	super();
	String valueData;
	String languageData;
	this.setLayout(new GridLayout(0,2));
	
	if (langstring!=null){
	    valueData = langstring.getValue(0);
	    languageData = langstring.getLanguage(0);
	}else {
	    valueData = new String("");
	    languageData = new String("");
	}
	JPanel valuePanel = new JPanel();
	value = new JTextField(valueData);
	value.getDocument().addDocumentListener(new TextFieldListener (value));
	valuePanel.add(value);
	
	JPanel languagePanel = new JPanel();
	language = new JTextField(languageData);
	language.getDocument().addDocumentListener(new TextFieldListener (language));
	languagePanel.add(language);
	
	this.add(valuePanel);
	this.add(languagePanel);
	
	this.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), border) );
	
	
    }
    
    private class TextFieldListener implements DocumentListener {

	private JTextField text;
	
	public TextFieldListener(JTextField text){
	    this.text = text;
	}
	
	public void changedUpdate( DocumentEvent e ) {
		//Do nothing
	}

	public void insertUpdate( DocumentEvent e ) {
		if (text==value){
		    langstring.setValue(value.getText(),0);
		} else if (text==language){
		    langstring.setLanguage(language.getText(), 0);
		}
		
	}
	public void removeUpdate( DocumentEvent e ) {
		insertUpdate(e);
	}
	
}
    
}
