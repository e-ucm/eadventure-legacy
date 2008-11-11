package es.eucm.eadventure.editor.gui.lomdialog;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.lom.LOMGeneralDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;

public class LOMGeneralPanel extends JPanel{

	private LOMGeneralDataControl dataControl;
	
	private LOMTextPanel titlePanel;
	
	private LOMTextPanel descriptionPanel;
	
	public LOMGeneralPanel (LOMGeneralDataControl dControl){
		this.dataControl = dControl;
		
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create the panels
		
		titlePanel = new LOMTextPanel(dataControl.getTitleController( ), TextConstants.getText("LOM.General.Title"), LOMTextPanel.TYPE_FIELD);
		LOMOptionsPanel languagePanel = new LOMOptionsPanel(dataControl.getLanguageController( ), TextConstants.getText("LOM.General.Language"));
		descriptionPanel = new LOMTextPanel(dataControl.getDescriptionController( ), TextConstants.getText("LOM.General.Description"), LOMTextPanel.TYPE_AREA);
		LOMTextPanel keywordPanel = new LOMTextPanel(dataControl.getKeywordController( ), TextConstants.getText("LOM.General.Keyword"), LOMTextPanel.TYPE_FIELD);
		
		// Add the panels
		add ( Box.createVerticalStrut( 1 ));
		add (titlePanel);
		add ( Box.createVerticalStrut( 1 ));
		add (languagePanel);
		add ( Box.createVerticalStrut( 1 ));
		add (descriptionPanel);
		add ( Box.createVerticalStrut( 1 ));
		add (keywordPanel);
		// Add "set defaults" button: If you press here, the title and description fields will be filled with the title and description of the adventure 
		JButton setDefaults = new JButton (TextConstants.getText( "LOM.General.SetDefaults" ));
		setDefaults.setToolTipText( TextConstants.getText( "LOM.General.SetDefaultsTip" ) );
		setDefaults.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				String adventureDesc = Controller.getInstance().getAdventureDescription( );
				String adventureTitle = Controller.getInstance().getAdventureTitle( );
				dataControl.getTitleController( ).setText( adventureTitle );
				dataControl.getDescriptionController( ).setText( adventureDesc );
				titlePanel.updateText( );
				descriptionPanel.updateText( );
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( setDefaults );
		add ( Box.createVerticalStrut( 1 ));
		add(buttonPanel);
		add ( Box.createRigidArea( new Dimension (400,100) ));
	}
}
