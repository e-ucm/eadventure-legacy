package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMCreatePrimitiveContainerPanel;

public class LOMESRightsAndClassificationPanel extends JPanel{

	
	

		//private IMSRightsDataControl rightsController;
		
		//private IMSClassificationDataControl classificationController;
		
		public LOMESRightsAndClassificationPanel (LOMESRightsDataControl rightsController, LOMESClassificationDataControl classificationController){
			
			//Layout
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			
			//Create the panels
			//Rights Panels
			LOMESOptionsPanel cost = new LOMESOptionsPanel(rightsController.getCost(), TextConstants.getText("IMS.Rights.Cost"));
			LOMESOptionsPanel copyAndOth = new LOMESOptionsPanel(rightsController.getCopyrightandotherrestrictions(), TextConstants.getText("IMS.Rights.CopyAndOth"));
			LOMESTextPanel descriptionR = new LOMESTextPanel(rightsController.getDescriptionController(), TextConstants.getText("LOMES.Rights.Description"), LOMESTextPanel.TYPE_FIELD);
			LOMESOptionsPanel accessType = new LOMESOptionsPanel(rightsController.getAccesType(), TextConstants.getText("LOMES.Rights.AccessType"));
			LOMESTextPanel accessDescription= new LOMESTextPanel(rightsController.getAccessDescriptionController(), TextConstants.getText("LOMES.Rights.AccessDescription"), LOMESTextPanel.TYPE_FIELD);
	 
			//Classification Panels
			LOMESOptionsPanel purpose = new LOMESOptionsPanel(classificationController.getPurpose(), TextConstants.getText("IMS.Classification.Purpose"));
			LOMESCreateContainerPanel taxonPath = new LOMESCreateContainerPanel(classificationController.getTaxonPath(),TextConstants.getText("LOMES.TaxonPath.Name"),LOMContributeDialog.NONE);
			LOMESTextPanel description = new LOMESTextPanel(classificationController.getDescription(), TextConstants.getText("IMS.Classification.Description"), LOMESTextPanel.TYPE_AREA);
			LOMCreatePrimitiveContainerPanel keywordPanel = new LOMCreatePrimitiveContainerPanel(LOMCreatePrimitiveContainerPanel.LANGSTRING_TYPE,classificationController.getKeywords(),TextConstants.getText("LOM.General.Keyword"),LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD);
			
			//Add the panels
			
			JPanel costAndCopy = new JPanel();
			costAndCopy.setLayout(new GridLayout(0,2));
			costAndCopy.add(cost);
			costAndCopy.add(copyAndOth);
			
			JPanel access = new JPanel();
			access.setLayout(new GridLayout(0,2));
			access.add(accessType);
			access.add(accessDescription);
			access.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText("LOMES.Rights.Access") ) );
		
			
			JPanel container = new JPanel();
			container.setLayout(new GridBagLayout());
			c.gridy=0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			//Add the panels
			container.add (costAndCopy,c);
			c.gridy=1;
			container.add (descriptionR,c);
			c.gridy=2;
			container.add (access,c);
			c.gridy=3;
			container.add (purpose,c);
			c.gridy=4;
			container.add(taxonPath,c);
			c.gridy=5;
			c.ipady=30;
			container.add(description,c);
			c.gridy=6;
			c.ipady=0;
			container.add(keywordPanel,c);
			
			

			c.gridy=0;
			c.anchor = GridBagConstraints.NORTH;
			c.weighty=1.0;
			add(container,c);
		}
		
	
}
