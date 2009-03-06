package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.config.LOMConfigData;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMGeneralDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMTechnicalDataControl;

public class LOMLifeCycleAndTechnicalPanel extends JPanel{

	private LOMLifeCycleDataControl lifeCycleController;
	
	private LOMTechnicalDataControl technicalController;
	
	public LOMLifeCycleAndTechnicalPanel (LOMLifeCycleDataControl lifeCycleController, LOMTechnicalDataControl technicalController){
		this.lifeCycleController = lifeCycleController;
		this.technicalController = technicalController;
		
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create the panels
		LOMTextPanel versionPanel = new LOMTextPanel(lifeCycleController.getVersionController( ), TextConstants.getText("LOM.LifeCycle.Version"), LOMTextPanel.TYPE_FIELD);
		LOMTextPanel minVersionPanel = new LOMTextPanel(technicalController.getMinimumVersionController( ), TextConstants.getText("LOM.Technical.MinimumVersion"), LOMTextPanel.TYPE_FIELD, false);
		LOMTextPanel maxVersionPanel = new LOMTextPanel(technicalController.getMaximumVersionController( ), TextConstants.getText("LOM.Technical.MaximumVersion"), LOMTextPanel.TYPE_FIELD, false);
	
		// check if there are related stored data in config file
		/*if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "version")){
			versionPanel.setValue(LOMConfigData.getProperty(LOMLifeCycleDataControl.GROUP, "version"));
		}
		if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "minimumVersion")){
			minVersionPanel.setValue(LOMConfigData.getProperty(LOMTechnicalDataControl.GROUP, "minimumVersion"));
		}
		if (LOMConfigData.isStored(LOMLifeCycleDataControl.GROUP, "maximumVersion")){
			maxVersionPanel.setValue(LOMConfigData.getProperty(LOMTechnicalDataControl.GROUP, "maximumVersion"));
		}*/
		
		//Add the panels
		add (versionPanel);
		add (Box.createVerticalStrut(5));
		add (minVersionPanel);
		add (Box.createVerticalStrut(5));
		add (maxVersionPanel);
		add ( Box.createRigidArea( new Dimension (400,250) ));
	}
	
	
}
