package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESComposeType;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMlIdentifierDialog;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMLifeCycleDateDialog;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESCreateComposeTypePanel extends JPanel{

	private JButton edit;
	
	//private JLabel name;
	
	private  LOMESComposeType compose;
	
	public LOMESCreateComposeTypePanel(LOMESComposeType compose){
		this.compose = compose;
		this.setLayout(new GridLayout(0,1));
		
		edit = new JButton(TextConstants.getText("LOMES.Edit"));
		edit.addActionListener( new EditButtonListener());
		
		this.add(edit);
		
		this.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.General.Identifier" ) )); 
		
		
		
	}
	

	/**
	 * Listener for the "Add" button
	 */
	private class EditButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent arg0 ) {
			if (compose instanceof LOMESLifeCycleDate){
				LOMLifeCycleDateDialog idDialog = new LOMLifeCycleDateDialog((LOMESLifeCycleDate)compose);
				((LOMESLifeCycleDate)compose).setDateTime(idDialog.getDateTimeValue());
				((LOMESLifeCycleDate)compose).setDescription(new LangString(idDialog.getDescriptionValue()));
				
			}
		}
	}
}
