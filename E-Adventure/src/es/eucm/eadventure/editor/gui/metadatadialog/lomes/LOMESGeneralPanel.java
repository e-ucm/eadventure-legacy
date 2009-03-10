package es.eucm.eadventure.editor.gui.metadatadialog.lomes;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSGeneralDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMGeneralDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESGeneralDataControl;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMCreatePrimitivePanel;

public class LOMESGeneralPanel extends JPanel{

	private LOMESGeneralDataControl dataControl;
	
	private LOMESTextPanel titlePanel;
	
	private LOMCreatePrimitivePanel descriptionPanel;	
	
	public LOMESGeneralPanel (LOMESGeneralDataControl dControl){
		this.dataControl = dControl;
		
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Create the panels
		
		titlePanel = new LOMESTextPanel(dataControl.getTitleController( ), TextConstants.getText("LOM.General.Title"), LOMESTextPanel.TYPE_FIELD);
		LOMCreatePrimitivePanel languagePanel = new LOMCreatePrimitivePanel(LOMCreatePrimitivePanel.STRING_TYPE,dataControl.getLanguages(),TextConstants.getText("LOM.General.Language"),LOMCreatePrimitivePanel.FIELD_TYPE_FIELD);
		descriptionPanel = new LOMCreatePrimitivePanel(LOMCreatePrimitivePanel.LANGSTRING_TYPE,dataControl.getDescriptions(),TextConstants.getText("LOM.General.Description"),LOMCreatePrimitivePanel.FIELD_TYPE_AREA);
		LOMESOptionsPanel aggregationLevel = new LOMESOptionsPanel(dataControl.getAggregationLevel(), TextConstants.getText("LOMES.General.AggregationLevel")) ;
		LOMESCreatePanel identifierPanel = new LOMESCreatePanel(dataControl.getIdentifier());
		LOMCreatePrimitivePanel keywordPanel = new LOMCreatePrimitivePanel(LOMCreatePrimitivePanel.LANGSTRING_TYPE,dataControl.getKeywords(),TextConstants.getText("LOM.General.Keyword"),LOMCreatePrimitivePanel.FIELD_TYPE_FIELD);
		
		//LOMESOptionsPanel languagePanel = new LOMESOptionsPanel(dataControl.getLanguageController( ), TextConstants.getText("LOM.General.Language"));
		// descriptionPanel = new LOMESTextPanel(dataControl.getDescriptionController( ), TextConstants.getText("LOM.General.Description"), LOMESTextPanel.TYPE_AREA);
		//LOMESTextPanel keywordPanel = new LOMESTextPanel(dataControl.getKeywordController( ), TextConstants.getText("LOM.General.Keyword"), LOMESTextPanel.TYPE_FIELD);
		//LOMESTextPanel catalog = new LOMESTextPanel(dataControl.getCatalogController(), TextConstants.getText("IMS.General.Catalog"), LOMESTextPanel.TYPE_FIELD);
		//LOMESTextPanel entry = new LOMESTextPanel(dataControl.getEntryController(), TextConstants.getText("IMS.General.Entry"), LOMESTextPanel.TYPE_FIELD);
		
		// Add the panels
		add ( Box.createVerticalStrut( 1 ));
		add (titlePanel);
		add ( Box.createVerticalStrut( 1 ));
		add (identifierPanel);
		add ( Box.createVerticalStrut( 1 ));
		add (languagePanel);
		add ( Box.createVerticalStrut( 1 ));
		add (descriptionPanel);
		add ( Box.createVerticalStrut( 1 ));
		add (keywordPanel);
		add ( Box.createVerticalStrut( 1 ));
		add (aggregationLevel);
		// Add "set defaults" button: If you press here, the title and description fields will be filled with the title and description of the adventure 
		JButton setDefaults = new JButton (TextConstants.getText( "LOM.General.SetDefaults" ));
		setDefaults.setToolTipText( TextConstants.getText( "LOM.General.SetDefaultsTip" ) );
		setDefaults.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				String adventureDesc = Controller.getInstance().getAdventureDescription( );
				String adventureTitle = Controller.getInstance().getAdventureTitle( );
				dataControl.getTitleController( ).setText( adventureTitle );
				//dataControl.getDescriptionController( ).setText( adventureDesc );
				titlePanel.updateText( );
				//descriptionPanel.updateText( );
				descriptionPanel.addLangstring(adventureDesc);
			
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add( setDefaults );
		add ( Box.createVerticalStrut( 1 ));
		add(buttonPanel);
		add ( Box.createRigidArea( new Dimension (400,45) ));
		//setSize(400, 100);
	}
	
	
}
