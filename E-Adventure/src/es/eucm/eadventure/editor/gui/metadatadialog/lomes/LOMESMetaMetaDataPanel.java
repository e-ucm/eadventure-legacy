package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESMetaMetaDataControl;

public class LOMESMetaMetaDataPanel extends JPanel{

	
	public LOMESMetaMetaDataPanel(LOMESMetaMetaDataControl metaController){
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Meta panels
		LOMESTextPanel meta = new LOMESTextPanel(metaController.getMetadataschemeController(), TextConstants.getText("IMS.MetaMetaData.Metadatascheme"), LOMESTextPanel.TYPE_FIELD);
		LOMESTextPanel catalog =new LOMESTextPanel(metaController.getCatalog(), TextConstants.getText("LOMES.MetaMetaData.Catalog"), LOMESTextPanel.TYPE_FIELD);
		LOMESTextPanel entry =new LOMESTextPanel(metaController.getEntry(), TextConstants.getText("LOMES.MetaMetaData.Entry"), LOMESTextPanel.TYPE_FIELD);
		LOMESOptionsPanel role = new LOMESOptionsPanel(metaController.getRoleController(), TextConstants.getText("LOMES.LifeCycle.Role"));
		LOMESTextPanel entity =new LOMESTextPanel(metaController.getEntry(), TextConstants.getText("LOMES.LifeCycle.Entity"), LOMESTextPanel.TYPE_FIELD);
		LOMESTextPanel description=new LOMESTextPanel(metaController.getDescription(), TextConstants.getText("LOMES.LifeCycle.Description"), LOMESTextPanel.TYPE_FIELD);
		LOMESOptionsPanel language = new LOMESOptionsPanel(metaController.getLanguageController(), TextConstants.getText("LOMES.MetaMetaData.Language"));
		//LOMESOptionsPanel catalog = new LOMESOptionsPanel(metaController.getCatalog(), TextConstants.getText("LOMES.LifeCycle.Role"));
		
		// add Panels
		
		add (catalog);
		add (Box.createVerticalStrut(1));
		add (entry);
		add (Box.createVerticalStrut(1));
		add (role);
		add (Box.createVerticalStrut(1));
		add (entity);
		add (Box.createVerticalStrut(1));
		add (meta);
		add (Box.createVerticalStrut(1));
		add (meta);
		add (Box.createVerticalStrut(1));
		add (language);
		add (Box.createVerticalStrut(1));
		add ( Box.createRigidArea( new Dimension (100,45) ));
	}
}
