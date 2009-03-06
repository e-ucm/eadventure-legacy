package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSTextDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESTextDataControl;

public class LOMESTextWithOptionsPanel extends LOMESTextPanel{

	private JComboBox combo;
	
	//private IMSTextDataControl dataControl;
	
	public LOMESTextWithOptionsPanel(LOMESTextDataControl dControl, String title, String[] options) {
		super(dControl, title, LOMESTextPanel.TYPE_FIELD);
		combo = new JComboBox(options);
		combo.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				dataControl.setText(combo.getSelectedIndex( )+ textField.getText() );
			}
			
		});
		add(combo);
		
	}

	

}
