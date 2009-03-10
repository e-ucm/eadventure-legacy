package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMGeneralIdentifierDialog;

public class LOMESCreatePanel extends JPanel{

	private JButton add;
	
	private JButton delete;
	
	private JComboBox elements;
	
	private  LOMESContainer container;
	
	public LOMESCreatePanel(LOMESContainer container){
		this.container = container;
		this.setLayout(new GridLayout(0,3));
		
		String[] containerElements = container.elements();
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
		
		this.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.General.Identifier" ) )); 
		
		
		
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
			if (elements.getSelectedIndex()!=0)
			container.delete(elements.getSelectedIndex()-1);
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
			if (container instanceof LOMIdentifier){
				int selectedIndex = elements.getSelectedIndex();
				LOMGeneralIdentifierDialog idDialog = new LOMGeneralIdentifierDialog(container,selectedIndex);
				((LOMIdentifier)container).addIdentifier(idDialog.getCatalog(),idDialog.getEntry(),selectedIndex);
				if (selectedIndex==0)
					elements.addItem(idDialog.getEntry());
				else {
					elements.removeItemAt(selectedIndex);
					elements.insertItemAt(idDialog.getEntry(), selectedIndex);
				}
			}
		}
	}
	
	
}
