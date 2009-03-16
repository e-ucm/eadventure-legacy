package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESMetaMetaDataControl;

public class LOMESMetaMetaDataPanel extends JPanel{

	
	public LOMESMetaMetaDataPanel(LOMESMetaMetaDataControl metaController){
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Meta panels
		LOMESTextPanel meta = new LOMESTextPanel(metaController.getMetadataschemeController(), TextConstants.getText("IMS.MetaMetaData.Metadatascheme"), LOMESTextPanel.TYPE_FIELD);
		LOMESCreateContainerPanel identifierPanel = new LOMESCreateContainerPanel(metaController.getIdentifier(), TextConstants.getText( "LOMES.General.Identifier" ),LOMContributeDialog.NONE);
		LOMESCreateContainerPanel contribute = new LOMESCreateContainerPanel(metaController.getContribute(),TextConstants.getText("LOMES.LifeCycle.Contribute"),LOMContributeDialog.METAMETADATA);
		LOMESOptionsPanel language = new LOMESOptionsPanel(metaController.getLanguageController(), TextConstants.getText("LOMES.MetaMetaData.Language"));
		//LOMESOptionsPanel catalog = new LOMESOptionsPanel(metaController.getCatalog(), TextConstants.getText("LOMES.LifeCycle.Role"));
		
		// add Panels
		
		add (identifierPanel);
		add (Box.createVerticalStrut(1));
		add (contribute);
		add (Box.createVerticalStrut(1));
		add (meta);
		add (Box.createVerticalStrut(1));
		add (language);
		add (Box.createVerticalStrut(1));
		add ( Box.createRigidArea( new Dimension (100,45) ));
	}
}
