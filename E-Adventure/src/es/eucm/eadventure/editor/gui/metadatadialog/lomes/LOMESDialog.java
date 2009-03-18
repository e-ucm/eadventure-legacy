package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESDataControl;

public class LOMESDialog extends JDialog{

	private LOMESDataControl dataControl;
	
	private JTabbedPane tabs;
	
	private JButton ok;
	
	public LOMESDialog (LOMESDataControl dataControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "LOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl = dataControl;
		
		Controller.getInstance().pushWindow(this);
		
		tabs = new JTabbedPane();
		tabs.insertTab( TextConstants.getText("LOM.General.Tab"), null, new LOMESGeneralPanel(dataControl.getGeneral( )), TextConstants.getText("LOM.General.Tip"), 0 );
		tabs.insertTab( TextConstants.getText("LOM.LifeCycle.Tab")+" & "+TextConstants.getText("LOM.Technical.Tab"), null, new LOMESLifeCycleAndTechnicalPanel(dataControl.getLifeCycle( ), dataControl.getTechnical( )), TextConstants.getText("LOM.LifeCycle.Tip") +" & "+TextConstants.getText("LOM.Technical.Tip"), 1 );
		tabs.insertTab(TextConstants.getText("IMS.Meta.Tab"), null, new LOMESMetaMetaDataPanel(dataControl.getMetametadata()), TextConstants.getText("IMS.Meta.Tip"), 2 );
		tabs.insertTab( TextConstants.getText("LOM.Educational.Tab"), null, new LOMESEducationalPanel(dataControl.getEducational( )), TextConstants.getText("LOM.Educational.Tip"), 3 );
		tabs.insertTab(  TextConstants.getText("IMS.Rights.Tab")+" & "+TextConstants.getText("IMS.Classification.Tab"), null, new LOMESRightsAndClassificationPanel(dataControl.getRights(), dataControl.getClassification()), TextConstants.getText("IMS.Rights.Tip") + " & " + TextConstants.getText("IMS.Classification.Tip"), 4 );
		
		// create button to close the dialog
		ok = new JButton ("OK");
		ok.setToolTipText( "Close the window"  );
		ok.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				close();
			}
		});
		
		//dialogCont.add(ok,BorderLayout.SOUTH);
		
		JPanel cont = new JPanel();
		cont.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH; 
		c.weightx = 1;
		c.weighty = 1;
		c.gridy = 0;
		//c.ipady = 70;
		cont.add(tabs,c);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridy = 1;
		c.ipady = 0;
		cont.add(ok,c);
		// Set size and position and show the dialog
		this.getContentPane( ).add( cont);
		setMinimumSize( new Dimension( 450, 520) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
		
		// Pop that window when is closed
		addWindowListener( new WindowAdapter (){
			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getInstance().popWindow();
				
			}
			
		});
		
	}
	
	public void close(){
		this.dispose();
	}
}


