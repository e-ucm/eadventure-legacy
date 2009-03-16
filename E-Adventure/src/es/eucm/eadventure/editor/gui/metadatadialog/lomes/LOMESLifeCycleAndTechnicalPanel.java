package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSMetaMetaDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSTechnicalDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTechnicalDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESMetaMetaDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESTechnicalDataControl;

public class LOMESLifeCycleAndTechnicalPanel extends JPanel{

	
	public LOMESLifeCycleAndTechnicalPanel (LOMESLifeCycleDataControl lifeCycleController, LOMESTechnicalDataControl technicalController){
		
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create the panels
		//Lyfecyle panels
		LOMESTextPanel versionPanel = new LOMESTextPanel(lifeCycleController.getVersionController( ), TextConstants.getText("LOM.LifeCycle.Version"), LOMESTextPanel.TYPE_FIELD);
		LOMESOptionsPanel status = new LOMESOptionsPanel(lifeCycleController.getStatusController(), TextConstants.getText("IMS.LifeCycle.Status"));
		LOMESCreateContainerPanel contribute = new LOMESCreateContainerPanel(lifeCycleController.getContribute(),TextConstants.getText("LOMES.LifeCycle.Contribute"),LOMContributeDialog.LIFECYCLE);
		
		//Technical panels
		LOMESCreateContainerPanel requirements = new LOMESCreateContainerPanel(technicalController.getRequirement(),TextConstants.getText("LOMES.Technical.Requirement"),LOMContributeDialog.NONE);
		
		
		
		//Add the panels
		add (versionPanel);
		add (Box.createVerticalStrut(1));
		add (status);
		add (Box.createVerticalStrut(1));
		add (contribute);
		add (Box.createVerticalStrut(1));
		add (Box.createVerticalStrut(1));
		add (requirements);
		add ( Box.createRigidArea( new Dimension (400,45) ));
		//setSize(400, 200);
	}
	
	
	
	
}
