package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;

/**
 *	Panel for String or LangString elements with SPM more than 1
 *
 */

public class LOMCreatePrimitiveContainerPanel extends JPanel{
	
	public static final int STRING_TYPE = 0;
	
	public static final int LANGSTRING_TYPE = 1;
	
	public static final int VOCABULARY_TYPE = 2;
	
	public static final int FIELD_TYPE_NONE=0;
	public static final int FIELD_TYPE_AREA=1;
	public static final int FIELD_TYPE_FIELD=2;
	
	private JButton add;
	
	private JButton delete;
	
	private JComboBox elements;
	
	private  ArrayList<String> stringContainer;
	
	private ArrayList<LangString> langstringContainer;
	
	private ArrayList<Vocabulary> vocabularyContainer;
	
	private String[] vocabularyType;
	
	private int type;
	
	/**
	 * Store if the field of LOMStringDialog is a field or area
	 */
	private int fieldType;
	
	public LOMCreatePrimitiveContainerPanel(int type,ArrayList container,String title,int fieldType){
		this.setLayout(new GridLayout(0,3));
		this.fieldType = fieldType;
		this.type = type;
		if (type == STRING_TYPE){
			this.stringContainer = container;
		}else if (type == LANGSTRING_TYPE){
			this.langstringContainer = container;
		}if (type == VOCABULARY_TYPE){
			this.vocabularyContainer = container;
		}
		
		
		String[] containerElements = getElements();
		String[] ele = new String[containerElements.length+1];
		ele[0] = TextConstants.getText("LOMES.AddElement");
		for (int i=0; i<containerElements.length;i++){
			ele[i+1] = containerElements[i];
		}
		
		elements = new JComboBox(ele);
		
		this.add(elements);
		
		add = new JButton(TextConstants.getText("LOMES.Add"));
		add.addActionListener( new AddButtonListener());
		
		this.add(add);
		
		delete = new JButton(TextConstants.getText("LOMES.Delete"));
		delete.addActionListener( new DeleteButtonListener ());
		
		this.add(delete);
		
		this.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title )); 
		
		
		
	}
	
	
	public LOMCreatePrimitiveContainerPanel(int type,ArrayList container,String title,String[] vocabularyType){
		this(type,container,title,0);
		this.vocabularyType = vocabularyType;
	}
	
	
	public String[] getElements(){
		
		ArrayList aux=new ArrayList(); 
		if (type==STRING_TYPE)
			aux=stringContainer;
		else if (type==LANGSTRING_TYPE)
			aux=langstringContainer;
		else if (type==VOCABULARY_TYPE)
			aux = vocabularyContainer;
		
		String[] elem = new String[aux.size()];
		for (int i =0; i<aux.size();i++){
			if (type==STRING_TYPE){
				elem[i]=stringContainer.get(i);
			}else if (type==LANGSTRING_TYPE){
				elem[i]=langstringContainer.get(i).getValue(0);
			} if (type==VOCABULARY_TYPE){
				elem[i]=vocabularyContainer.get(i).getValue();
			}
		}
		return elem;
	}
	
	/**
	 * Add a new langstring outside the button "Add"
	 * @param value
	 */
	public void addLangstring(String value){
		
			elements.addItem(value);
			langstringContainer.add(new LangString(value));
	}
	
	
	/**
	 * Listener for the "Delete" button
	 */
	private class DeleteButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			int index=elements.getSelectedIndex();
			if (type==STRING_TYPE){
				if (index!=0){
				stringContainer.remove(index-1);
				elements.removeItemAt(index);
				}
			}else if (type==LANGSTRING_TYPE){
				if (index!=0){
					langstringContainer.get(index-1).deleteValue(0);
					elements.removeItemAt(index);
				}
			}if (type==VOCABULARY_TYPE){
				if (index!=0){
					vocabularyContainer.remove(index-1);
					elements.removeItemAt(index);
				}
			}
		}
	}
	
	/**
	 * Listener for the "Add" button
	 */
	private class AddButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			if (type == STRING_TYPE){
				int selectedIndex = elements.getSelectedIndex();
				String previousValue="";
				if (selectedIndex!=0)
					previousValue = stringContainer.get(selectedIndex-1);
				LOMStringDialog idDialog = new LOMStringDialog(previousValue,fieldType);
				
				if (!idDialog.getTextValue().equals("")){
				if (selectedIndex==0){
					elements.addItem(idDialog.getTextValue());
					stringContainer.add(idDialog.getTextValue());
				}else {
					stringContainer.remove(selectedIndex-1);
					stringContainer.add(selectedIndex-1,idDialog.getTextValue());
					elements.removeItemAt(selectedIndex);
					elements.insertItemAt(idDialog.getTextValue(), selectedIndex);
				}
				}
			}
		else if (type == LANGSTRING_TYPE){
			int selectedIndex = elements.getSelectedIndex();
			LangString previousValue=new LangString("");
			if (selectedIndex!=0)
				previousValue = langstringContainer.get(selectedIndex-1);
			LOMStringDialog idDialog = new LOMStringDialog(previousValue.getValue(0),fieldType);
			
			if (!idDialog.getTextValue().equals("")){
			if (selectedIndex==0){
				elements.addItem(idDialog.getTextValue());
				langstringContainer.add(new LangString(idDialog.getTextValue()));
			}else {
				langstringContainer.remove(selectedIndex-1);
				langstringContainer.add(selectedIndex-1,new LangString(idDialog.getTextValue()));
				elements.removeItemAt(selectedIndex);
				elements.insertItemAt(idDialog.getTextValue(), selectedIndex);
			}
			}
		}if (type == VOCABULARY_TYPE){
			int selectedIndex = elements.getSelectedIndex();
			int previousValue=0;
			if (selectedIndex!=0)
				previousValue = vocabularyContainer.get(selectedIndex-1).getValueIndex();
			LOMVocabularyDialog idDialog = new LOMVocabularyDialog(vocabularyType,selectedIndex==0?0:previousValue);
			
			
			if (selectedIndex==0){
				elements.addItem(vocabularyType[idDialog.getSelection()]);
				vocabularyContainer.add(new Vocabulary(vocabularyType,idDialog.getSelection()));
			}else {
				vocabularyContainer.remove(selectedIndex-1);
				vocabularyContainer.add(selectedIndex-1,new Vocabulary(vocabularyType,idDialog.getSelection()));
				elements.removeItemAt(selectedIndex);
				elements.insertItemAt(vocabularyType[idDialog.getSelection()], selectedIndex);
			}
			
		}
	}
		

	}
	
}
