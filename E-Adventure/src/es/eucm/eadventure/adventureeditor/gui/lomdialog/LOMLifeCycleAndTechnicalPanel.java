package es.eucm.eadventure.adventureeditor.gui.lomdialog;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import es.eucm.eadventure.adventureeditor.control.controllers.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.lom.LOMTechnicalDataControl;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

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
	
		//Add the panels
		add (versionPanel);
		add (Box.createVerticalStrut(5));
		add (minVersionPanel);
		add (Box.createVerticalStrut(5));
		add (maxVersionPanel);
		add ( Box.createRigidArea( new Dimension (400,250) ));
	}
	
	
}
