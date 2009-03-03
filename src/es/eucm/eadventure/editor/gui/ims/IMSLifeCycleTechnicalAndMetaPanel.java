package es.eucm.eadventure.editor.gui.ims;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ims.IMSLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSMetaMetaDataControl;
import es.eucm.eadventure.editor.control.controllers.ims.IMSTechnicalDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMTechnicalDataControl;

public class IMSLifeCycleTechnicalAndMetaPanel extends JPanel{

	private IMSLifeCycleDataControl lifeCycleController;
	
	private IMSTechnicalDataControl technicalController;
	
	public IMSLifeCycleTechnicalAndMetaPanel (IMSLifeCycleDataControl lifeCycleController, IMSTechnicalDataControl technicalController, IMSMetaMetaDataControl metaController){
		this.lifeCycleController = lifeCycleController;
		this.technicalController = technicalController;
		
		//Layout
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//Create the panels
		IMSTextPanel versionPanel = new IMSTextPanel(lifeCycleController.getVersionController( ), TextConstants.getText("LOM.LifeCycle.Version"), IMSTextPanel.TYPE_FIELD);
		IMSTextPanel minVersionPanel = new IMSTextPanel(technicalController.getMinimumVersionController( ), TextConstants.getText("LOM.Technical.MinimumVersion"), IMSTextPanel.TYPE_FIELD, false);
		IMSTextPanel maxVersionPanel = new IMSTextPanel(technicalController.getMaximumVersionController( ), TextConstants.getText("LOM.Technical.MaximumVersion"), IMSTextPanel.TYPE_FIELD, false);
		IMSOptionsPanel status = new IMSOptionsPanel(lifeCycleController.getStatusController(), TextConstants.getText("IMS.LifeCycle.Status"));
		IMSTextPanel format = new IMSTextPanel(technicalController.getFormatController(), TextConstants.getText("IMS.Technical.Format"), IMSTextPanel.TYPE_FIELD);
		IMSTextWithOptionsPanel location = new IMSTextWithOptionsPanel(technicalController.getLocation(), TextConstants.getText("IMS.Technical.Location"),new String[]{"URI","TEXT"});
		IMSTextPanel meta = new IMSTextPanel(metaController.getMetadataschemeController(), TextConstants.getText("IMS.MetaMetaData.Metadatascheme"), IMSTextPanel.TYPE_FIELD);
		
		//Add the panels
		add (versionPanel);
		add (Box.createVerticalStrut(1));
		add (status);
		add (Box.createVerticalStrut(1));
		add (meta);
		add (Box.createVerticalStrut(1));
		add (format);
		add (Box.createVerticalStrut(1));
		add (location);
		add (Box.createVerticalStrut(1));
		add (minVersionPanel);
		add (Box.createVerticalStrut(1));
		add (maxVersionPanel);
		add ( Box.createRigidArea( new Dimension (400,45) ));
		//setSize(400, 200);
	}
	
	
	
	
}
