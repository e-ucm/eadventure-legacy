package es.eucm.eadventure.editor.gui.ims;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ims.IMSClassificationDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSMetaMetaDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSRightsDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSTechnicalDataControl;

public class IMSRightsAndClassificationPanel extends JPanel{

	
	

		//private IMSRightsDataControl rightsController;
		
		//private IMSClassificationDataControl classificationController;
		
		public IMSRightsAndClassificationPanel (IMSRightsDataControl rightsController, IMSClassificationDataControl classificationController){
			
			//Layout
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			//Create the panels
			IMSOptionsPanel cost = new IMSOptionsPanel(rightsController.getCost(), TextConstants.getText("IMS.Rights.Cost"));
			IMSOptionsPanel copyAndOth = new IMSOptionsPanel(rightsController.getCost(), TextConstants.getText("IMS.Rights.CopyAndOth"));
			IMSOptionsPanel purpose = new IMSOptionsPanel(classificationController.getPurpose(), TextConstants.getText("IMS.Classification.Purpose"));
			IMSTextPanel description = new IMSTextPanel(classificationController.getDescription(), TextConstants.getText("IMS.Classification.Description"), IMSTextPanel.TYPE_AREA);
			IMSTextPanel keywordPanel = new IMSTextPanel(classificationController.getKeywordController( ), TextConstants.getText("IMS.Classification.Keyword"), IMSTextPanel.TYPE_FIELD);
			
			//Add the panels
			add (cost);
			add (Box.createVerticalStrut(1));
			add (copyAndOth);
			add (Box.createVerticalStrut(1));
			add (purpose);
			add (Box.createVerticalStrut(1));
			add (description);
			add (Box.createVerticalStrut(1));
			add (keywordPanel);
			add ( Box.createRigidArea( new Dimension (400,350) ));
		}
		
	
}
