package es.eucm.eadventure.editor.gui.lomdialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.lom.LOMDataControl;

public class LOMDialog extends JDialog{

	private LOMDataControl dataControl;
	
	private JTabbedPane tabs;
	
	private JButton ok;
	
	public LOMDialog (LOMDataControl dataControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "LOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl = dataControl;
		
		tabs = new JTabbedPane();
		tabs.insertTab( TextConstants.getText("LOM.General.Tab"), null, new LOMGeneralPanel(dataControl.getGeneral( )), TextConstants.getText("LOM.General.Tip"), 0 );
		tabs.insertTab( TextConstants.getText("LOM.LifeCycle.Tab")+" & "+TextConstants.getText("LOM.Technical.Tab"), null, new LOMLifeCycleAndTechnicalPanel(dataControl.getLifeCycle( ), dataControl.getTechnical( )), TextConstants.getText("LOM.LifeCycle.Tip") +" & "+TextConstants.getText("LOM.Technical.Tip"), 1 );
		tabs.insertTab( TextConstants.getText("LOM.Educational.Tab"), null, new LOMEducationalPanel(dataControl.getEducational( )), TextConstants.getText("LOM.Educational.Tip"), 2 );
		
		
		// create button to close the dialog
		ok = new JButton ("OK");
		ok.setToolTipText( "Close the window"  );
		ok.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				close();
			}
		});
		
		
		
		JPanel cont = new JPanel();
		cont.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; 
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridy = 0;
		cont.add(tabs,c);
		c.fill = GridBagConstraints.NONE;
		c.gridy = 1;
		c.ipady = 0;
		cont.add(ok,c);   
		
		// Set size and position and show the dialog
		this.getContentPane( ).setLayout( new BorderLayout() );
		this.getContentPane( ).add( cont, BorderLayout.CENTER );
		//setSize( new Dimension( 400, 200 ) );
		setMinimumSize( new Dimension( 450, 480 ) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );

	}
	
	public void close(){
		this.dispose();
	}
}
