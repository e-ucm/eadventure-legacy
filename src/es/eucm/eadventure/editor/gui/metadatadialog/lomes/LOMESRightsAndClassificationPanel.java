package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSClassificationDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSMetaMetaDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSRightsDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSTechnicalDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESClassificationDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESRightsDataControl;

public class LOMESRightsAndClassificationPanel extends JPanel{

	
	

		//private IMSRightsDataControl rightsController;
		
		//private IMSClassificationDataControl classificationController;
		
		public LOMESRightsAndClassificationPanel (LOMESRightsDataControl rightsController, LOMESClassificationDataControl classificationController){
			
			//Layout
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Create the panels
			//Rights Panels
			LOMESOptionsPanel cost = new LOMESOptionsPanel(rightsController.getCost(), TextConstants.getText("IMS.Rights.Cost"));
			LOMESOptionsPanel copyAndOth = new LOMESOptionsPanel(rightsController.getCost(), TextConstants.getText("IMS.Rights.CopyAndOth"));
			LOMESTextPanel descriptionR = new LOMESTextPanel(rightsController.getDescriptionController(), TextConstants.getText("LOMES.Rights.Description"), LOMESTextPanel.TYPE_FIELD);
			LOMESOptionsPanel accessType = new LOMESOptionsPanel(rightsController.getAccesType(), TextConstants.getText("LOMES.Rights.AccessType"));
			LOMESTextPanel accessDescription= new LOMESTextPanel(rightsController.getAccessDescriptionController(), TextConstants.getText("LOMES.Rights.AccessDescription"), LOMESTextPanel.TYPE_FIELD);
	 
			//Classification Panels
			LOMESOptionsPanel purpose = new LOMESOptionsPanel(classificationController.getPurpose(), TextConstants.getText("IMS.Classification.Purpose"));
			LOMESTextPanel description = new LOMESTextPanel(classificationController.getDescription(), TextConstants.getText("IMS.Classification.Description"), LOMESTextPanel.TYPE_AREA);
			LOMESTextPanel keywordPanel = new LOMESTextPanel(classificationController.getKeywordController( ), TextConstants.getText("IMS.Classification.Keyword"), LOMESTextPanel.TYPE_FIELD);
			LOMESTextPanel source = new LOMESTextPanel(classificationController.getSource(), TextConstants.getText("LOMES.Classification.Source"), LOMESTextPanel.TYPE_FIELD);
			LOMESTextPanel identifier = new LOMESTextPanel(classificationController.getIdentifier(), TextConstants.getText("LOMES.Classification.Identifier"), LOMESTextPanel.TYPE_FIELD);
			LOMESTextPanel entry = new LOMESTextPanel(classificationController.getEntry(), TextConstants.getText("LOMES.Classification.Entry"), LOMESTextPanel.TYPE_FIELD);
			//Add the panels
			add (cost);
			add (Box.createVerticalStrut(1));
			add (copyAndOth);
			add (Box.createVerticalStrut(1));
			add (descriptionR);
			add (Box.createVerticalStrut(1));
			add (accessType);
			add (Box.createVerticalStrut(1));
			add (accessDescription);
			add (Box.createVerticalStrut(1));
		
			add (purpose);
			add (Box.createVerticalStrut(1));
			add (source);
			add (Box.createVerticalStrut(1));
			add (identifier);
			add (source);
			add (Box.createVerticalStrut(1));
			add (identifier);
			add (Box.createVerticalStrut(1));
			add (entry);
			add (Box.createVerticalStrut(1));
			add (description);
			add (Box.createVerticalStrut(1));
			add (keywordPanel);
			add ( Box.createRigidArea( new Dimension (400,45) ));
			//setSize(400, 200);
		}
		
	
}
