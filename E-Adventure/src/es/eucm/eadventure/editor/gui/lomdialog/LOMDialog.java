package es.eucm.eadventure.editor.gui.lomdialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.lom.LOMDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;

public class LOMDialog extends JDialog{

	private LOMDataControl dataControl;
	
	private JTabbedPane tabs;
	
	public LOMDialog (LOMDataControl dataControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "LOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl = dataControl;
		
		tabs = new JTabbedPane();
		tabs.insertTab( TextConstants.getText("LOM.General.Tab"), null, new LOMGeneralPanel(dataControl.getGeneral( )), TextConstants.getText("LOM.General.Tip"), 0 );
		tabs.insertTab( TextConstants.getText("LOM.LifeCycle.Tab")+" & "+TextConstants.getText("LOM.Technical.Tab"), null, new LOMLifeCycleAndTechnicalPanel(dataControl.getLifeCycle( ), dataControl.getTechnical( )), TextConstants.getText("LOM.LifeCycle.Tip") +" & "+TextConstants.getText("LOM.Technical.Tip"), 1 );
		tabs.insertTab( TextConstants.getText("LOM.Educational.Tab"), null, new LOMEducationalPanel(dataControl.getEducational( )), TextConstants.getText("LOM.Educational.Tip"), 2 );
		
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
