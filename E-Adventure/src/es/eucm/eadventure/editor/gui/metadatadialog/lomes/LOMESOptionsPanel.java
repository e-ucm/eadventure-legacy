package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSOptionsDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMOptionsDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESOptionsDataControl;

public class LOMESOptionsPanel extends JPanel{
	
	private LOMESOptionsDataControl dataControl;
	
	private JComboBox comboBox;
	
	public LOMESOptionsPanel (LOMESOptionsDataControl dControl, String title){
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
