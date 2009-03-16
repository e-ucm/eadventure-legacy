package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMRequirement;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMRequirementsDialog;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMlIdentifierDialog;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;


/**
 * Panel for container of compose types. 
 *
 */
public class LOMESCreateContainerPanel extends JPanel{

	private JButton add;
	
	private JButton delete;
	
	private JComboBox elements;
	
	private  LOMESContainer container;
	
	private int type;
	
	public LOMESCreateContainerPanel(LOMESContainer container,String title,int type){
		this.container = container;
		this.type = type;
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
		
		this.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title )); 
		
		
		
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
			if (elements.getSelectedIndex()!=0){
				container.delete(elements.getSelectedIndex()-1);
				elements.removeItemAt(elements.getSelectedIndex());		
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
			if (container instanceof LOMIdentifier){
				int selectedIndex = elements.getSelectedIndex();
				LOMlIdentifierDialog idDialog = new LOMlIdentifierDialog(container,selectedIndex);
				//only add new element if it in not empty
				if (!idDialog.getCatalog().equals("")&&!idDialog.getEntry().equals("")){
					((LOMIdentifier)container).addIdentifier(idDialog.getCatalog(),idDialog.getEntry(),selectedIndex);
				if (selectedIndex==0)
					elements.addItem(idDialog.getEntry());
				else {
					elements.removeItemAt(selectedIndex);
					elements.insertItemAt(idDialog.getEntry(), selectedIndex);
				}
				}
			}else if (container instanceof LOMContribute){
				int selectedIndex = elements.getSelectedIndex();
				LOMContributeDialog idDialog = new LOMContributeDialog(container,selectedIndex,type);
				
				if (idDialog.getEntityValue().size()!=0){
					((LOMContribute)container).addContribute(idDialog.getRoleValue(),idDialog.getEntityValue(),idDialog.getDateValue(),selectedIndex);
				if (selectedIndex==0)
					elements.addItem(idDialog.getRoleValue().getValue());
				else {
					elements.removeItemAt(selectedIndex);
					elements.insertItemAt(idDialog.getRoleValue().getValue(), selectedIndex);
				}
				}
			}else if (container instanceof LOMRequirement){
				int selectedIndex = elements.getSelectedIndex();
				LOMRequirementsDialog idDialog = new LOMRequirementsDialog(container,selectedIndex);
				
				
				((LOMRequirement)container).addOrComposite(idDialog.getTypeValue(),idDialog.getNameValue(),idDialog.getMaxValue(),idDialog.getMinValue(),selectedIndex);
				if (selectedIndex==0)
					elements.addItem(idDialog.getTypeValue().getValue());
				else {
					elements.removeItemAt(selectedIndex);
					elements.insertItemAt(idDialog.getTypeValue(), selectedIndex);
				}
				
			}
		}
	}
	
	
}
