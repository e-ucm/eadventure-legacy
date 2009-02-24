package es.eucm.eadventure.editor.gui.ims;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ims.IMSDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMDataControl;

public class IMSDialog extends JDialog{

	private IMSDataControl dataControl;
	
	private JTabbedPane tabs;
	
	public IMSDialog (IMSDataControl dataControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "LOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl = dataControl;
		
		tabs = new JTabbedPane();
		tabs.insertTab( TextConstants.getText("LOM.General.Tab"), null, new IMSGeneralPanel(dataControl.getGeneral( )), TextConstants.getText("LOM.General.Tip"), 0 );
		tabs.insertTab( TextConstants.getText("LOM.LifeCycle.Tab")+" & "+TextConstants.getText("LOM.Technical.Tab")+" & "+TextConstants.getText("IMS.Meta.Tab"), null, new IMSLifeCycleTechnicalAndMetaPanel(dataControl.getLifeCycle( ), dataControl.getTechnical( ), dataControl.getMetametadata()), TextConstants.getText("LOM.LifeCycle.Tip") +" & "+TextConstants.getText("LOM.Technical.Tip")+" & "+TextConstants.getText("IMS.Meta.Tip"), 1 );
		tabs.insertTab( TextConstants.getText("LOM.Educational.Tab"), null, new IMSEducationalPanel(dataControl.getEducational( )), TextConstants.getText("LOM.Educational.Tip"), 2 );
		tabs.insertTab(  TextConstants.getText("IMS.Rights.Tab")+" & "+TextConstants.getText("IMS.Classification.Tab"), null, new IMSRightsAndClassificationPanel(dataControl.getRights(), dataControl.getClassification()), TextConstants.getText("IMS.Rights.Tip") + " & " + TextConstants.getText("IMS.Classification.Tip"), 3 );
		
		
		
		// Set size and position and show the dialog
		this.getContentPane( ).setLayout( new BorderLayout() );
		this.getContentPane( ).add( tabs, BorderLayout.CENTER );
		setSize( new Dimension( 400, 200 ) );
		setMinimumSize( new Dimension( 450, 450 ) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );

	}
}
