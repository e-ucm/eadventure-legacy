package es.eucm.eadventure.editor.gui.metadatadialog.lomes;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JComboBox;
import javax.swing.JPanel;



public class LOMVocabularyPanel extends JPanel{

	private int selection;
	
	private JComboBox elements;
	
	public LOMVocabularyPanel(String[] values,int selection){
		//super( Controller.getInstance( ).peekWindow( ), TextConstants.getText("LOMES.Value"), Dialog.ModalityType.APPLICATION_MODAL );
		super();
	    	elements = new JComboBox(values);
		this.selection=selection;
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel textPanel = new JPanel(new GridBagLayout());
		
		elements.addActionListener(new VocabularySelectionListener ());
		elements.setSelectedIndex(selection);
		textPanel.add(elements,c);

		this.add(textPanel);
	
		
	}

	
	
	private class VocabularySelectionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			
			selection = elements.getSelectedIndex();
		}      
    }

	/**
	 * @return the selection
	 */
	public int getSelection() {
		return selection;
	}
	
}
