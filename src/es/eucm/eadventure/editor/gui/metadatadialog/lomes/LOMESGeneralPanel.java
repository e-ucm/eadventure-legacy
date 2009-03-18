package es.eucm.eadventure.editor.gui.metadatadialog.lomes;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMCreatePrimitiveContainerPanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;

public class LOMESGeneralPanel extends JPanel{

	private LOMESGeneralDataControl dataControl;
	
	private LOMESTextPanel titlePanel;
	
	private LOMCreatePrimitiveContainerPanel descriptionPanel;	
	
	public LOMESGeneralPanel (LOMESGeneralDataControl dControl){
		this.dataControl = dControl;
		
		//Layout
		setLayout(new GridBagLayout());
		
		// Create the panels
		
		titlePanel = new LOMESTextPanel(dataControl.getTitleController( ), TextConstants.getText("LOM.General.Title"), LOMESTextPanel.TYPE_FIELD);
		LOMCreatePrimitiveContainerPanel languagePanel = new LOMCreatePrimitiveContainerPanel(LOMCreatePrimitiveContainerPanel.STRING_TYPE,dataControl.getLanguages(),TextConstants.getText("LOM.General.Language"),LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD);
		descriptionPanel = new LOMCreatePrimitiveContainerPanel(LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE,dataControl.getDescriptions(),TextConstants.getText("LOM.General.Description"),LOMCreatePrimitiveContainerPanel.FIELD_TYPE_AREA);
		LOMESOptionsPanel aggregationLevel = new LOMESOptionsPanel(dataControl.getAggregationLevel(), TextConstants.getText("LOMES.General.AggregationLevel")) ;
		// there is necessary to 
		LOMESCreateContainerPanel identifierPanel = new LOMESCreateContainerPanel(dataControl.getIdentifier(), TextConstants.getText( "LOMES.General.Identifier" ),LOMContributeDialog.NONE);
		LOMCreatePrimitiveContainerPanel keywordPanel = new LOMCreatePrimitiveContainerPanel(LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE,dataControl.getKeywords(),TextConstants.getText("LOM.General.Keyword"),LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD);
		
		
		
		GridBagConstraints c = new GridBagConstraints();
		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());
		c.gridy=0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		// Add the panels
		container.add (titlePanel,c);
		c.gridy++;
		container.add (identifierPanel,c);
		c.gridy++;
		container.add (languagePanel,c);
		c.gridy++;
		container.add (descriptionPanel,c);
		c.gridy++;
		container.add (keywordPanel,c);
		c.gridy++;
		container.add (aggregationLevel,c);
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
		c.gridy++;
		container.add(buttonPanel,c);
				
		c.gridy=0;
		c.anchor = GridBagConstraints.NORTH;
		c.weighty=1.0;
		add(container,c);
	}
	
	
}
