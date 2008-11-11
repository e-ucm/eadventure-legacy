package es.eucm.eadventure.editor.gui.lomdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.lom.LOMOptionsDataControl;

public class LOMOptionsPanel extends JPanel{
	
	private LOMOptionsDataControl dataControl;
	
	private JComboBox comboBox;
	
	public LOMOptionsPanel (LOMOptionsDataControl dControl, String title){
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
