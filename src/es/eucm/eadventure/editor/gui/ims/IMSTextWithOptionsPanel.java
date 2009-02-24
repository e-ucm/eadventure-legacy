package es.eucm.eadventure.editor.gui.ims;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import es.eucm.eadventure.editor.control.controllers.ims.IMSTextDataControl;

public class IMSTextWithOptionsPanel extends IMSTextPanel{

	private JComboBox combo;
	
	//private IMSTextDataControl dataControl;
	
	public IMSTextWithOptionsPanel(IMSTextDataControl dControl, String title, String[] options) {
		super(dControl, title, IMSTextPanel.TYPE_FIELD);
		combo = new JComboBox(options);
		combo.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				dataControl.setText(combo.getSelectedIndex( )+ textField.getText() );
			}
			
		});
		add(combo);
		
	}

	

}
