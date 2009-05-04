package es.eucm.eadventure.editor.gui.metadatadialog.ims;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonUI;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

public class IMSDialog extends JDialog{
    
    	private static final String helpPath="metadata/IMSLOM.html";

	private IMSDataControl dataControl;
	
	private JTabbedPane tabs;
	
	private JButton ok;
	
	private int currentTab;
	
	public IMSDialog (IMSDataControl dataControl){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "LOM.Title" ), Dialog.ModalityType.TOOLKIT_MODAL );
		this.dataControl = dataControl;
		
		tabs = new JTabbedPane();
		
		JButton infoButton = new JButton(new ImageIcon("img/icons/information.png"));
		infoButton.setContentAreaFilled( false );
		infoButton.setMargin( new Insets(0,0,0,0) );
		infoButton.setFocusable(false);
		infoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpDialog(helpPath);
			}
		});

		tabs.insertTab( TextConstants.getText("LOM.General.Tab"), null, new IMSGeneralPanel(dataControl.getGeneral( )), TextConstants.getText("LOM.General.Tip"), 0);
		tabs.insertTab( TextConstants.getText("LOM.LifeCycle.Tab")+" & "+TextConstants.getText("LOM.Technical.Tab")+" & "+TextConstants.getText("IMS.Meta.Tab"), null, new IMSLifeCycleTechnicalAndMetaPanel(dataControl.getLifeCycle( ), dataControl.getTechnical( ), dataControl.getMetametadata()), TextConstants.getText("LOM.LifeCycle.Tip") +" & "+TextConstants.getText("LOM.Technical.Tip")+" & "+TextConstants.getText("IMS.Meta.Tip"), 1 );
		tabs.insertTab( TextConstants.getText("LOM.Educational.Tab"), null, new IMSEducationalPanel(dataControl.getEducational( )), TextConstants.getText("LOM.Educational.Tip"), 2);
		tabs.insertTab(  TextConstants.getText("IMS.Rights.Tab")+" & "+TextConstants.getText("IMS.Classification.Tab"), null, new IMSRightsAndClassificationPanel(dataControl.getRights(), dataControl.getClassification()), TextConstants.getText("IMS.Rights.Tip") + " & " + TextConstants.getText("IMS.Classification.Tip"), 3);
		tabs.add(new JPanel(),4);
		tabs.setTabComponentAt(4,infoButton );
		tabs.addChangeListener(new ChangeListener(){
		    @Override
		    public void stateChanged(ChangeEvent e) {
			 if (tabs.getSelectedIndex()==4){
			       tabs.setSelectedIndex(currentTab);
			   }else 
			       currentTab=tabs.getSelectedIndex();
			  }
			
		    });
		tabs.setSelectedIndex(1);
		currentTab=1;
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
		this.getContentPane( ).add( cont);
		setMinimumSize( new Dimension( 490, 450) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
		
	}
	
	public void close(){
		this.dispose();
	}
	
}



