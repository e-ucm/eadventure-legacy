package es.eucm.eadventure.editor.gui.ims;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.ims.IMSOptionsDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMOptionsDataControl;

public class IMSOptionsPanel extends JPanel{
	
	private IMSOptionsDataControl dataControl;
	
	private JComboBox comboBox;
	
	public IMSOptionsPanel (IMSOptionsDataControl dControl, String title){
		this.dataControl = dControl;
		setLayout( new GridLayout( ) );
		
		comboBox= new JComboBox(dataControl.getOptions( ));
		if (dataControl.getSelectedOption( )!=-1)
			comboBox.setSelectedIndex( dataControl.getSelectedOption( ) );
		comboBox.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				dataControl.setOption( comboBox.getSelectedIndex( ) );
			}
			
		});
		add (comboBox);
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );
	}

}
