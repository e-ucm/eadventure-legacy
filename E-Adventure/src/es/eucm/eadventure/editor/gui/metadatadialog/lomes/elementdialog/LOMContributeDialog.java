package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMLifeCycleDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESLifeCycleDataControl;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESGeneralId;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESCreateComposeTypePanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESOptionsPanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMVocabularyPanel;


public class LOMContributeDialog extends JDialog{

	public static final int NONE =0;
	
	public static final int LIFECYCLE=1;
	
	public static final int METAMETADATA=2;
	
	private LOMCreatePrimitiveContainerPanel entity;
	
	private LOMVocabularyPanel role;
	
	private LOMESCreateComposeTypePanel date;
	
	private Vocabulary roleValue;
	
	private ArrayList entityValue;
	
	private LOMESLifeCycleDate dateValue;
	
	private LangString descriptionValue;
	
	private LOMESContainer container;
	
	private int type;
	

	
	public LOMContributeDialog(LOMESContainer container, int selectedItem, int type){
		super( Controller.getInstance( ).peekWindow( ), container.getTitle(), Dialog.ModalityType.APPLICATION_MODAL );
		Controller.getInstance().pushWindow(this);
		this.container = container;
		this.type = type;
		
		if (selectedItem ==0){
			if (type==LIFECYCLE)
				roleValue=new Vocabulary(LOMESLifeCycleContribute.getRoleLifeCycleVocabularyType());
			if (type==METAMETADATA)
				roleValue=new Vocabulary(LOMESLifeCycleContribute.getRoleMetametaVocabularyType());
			entityValue=new ArrayList<String>();
			entityValue.add(new String("Empty"));
			dateValue = new LOMESLifeCycleDate();
		}else {
			roleValue=((LOMESLifeCycleContribute)container.get(selectedItem-1)).getRole();
			entityValue=((LOMESLifeCycleContribute)container.get(selectedItem-1)).getEntity();
			dateValue =((LOMESLifeCycleContribute)container.get(selectedItem-1)).getDate();
		}
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel rolePanel = new JPanel(new GridBagLayout());
		if (type == LIFECYCLE)
			role = new LOMVocabularyPanel(LOMESLifeCycleContribute.getRoleLifeCycleOptions(),roleValue.getValueIndex());
		if (type == METAMETADATA)
			role = new LOMVocabularyPanel(LOMESLifeCycleContribute.getRoleMetametaOptions(),roleValue.getValueIndex());
		rolePanel.add(role,c);
		rolePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.LifeCycle.RoleName" ) ) );
		
		JPanel datePanel = new JPanel(new GridBagLayout());
		date = new LOMESCreateComposeTypePanel(dateValue);
		datePanel.add(date,c);
		datePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.LifeCyle.Date" ) ) );
		
		
		entity = new LOMCreatePrimitiveContainerPanel(LOMCreatePrimitiveContainerPanel.STRING_TYPE,entityValue,TextConstants.getText("LOMES.LifeCycle.Entity"),LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD);
		
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		c =  new GridBagConstraints(); 
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		c.gridx=0;
		buttonPanel.add(ok,c);
		
		JButton info = new JButton("Info");
		info.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				showInfo();
				
			}
			
		});
		c.gridx=1;
		buttonPanel.add(info,c);
		
		
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints(); 
		c.insets = new Insets(5,5,5,5);c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		mainPanel.add(rolePanel,c);
		c.gridy=1;
		mainPanel.add(entity,c);
		c.gridy=2;
		mainPanel.add(datePanel,c);
		c.gridy=3;
		mainPanel.add(buttonPanel,c);
		
		
		this.getContentPane().add(mainPanel);
		
		
		addWindowListener( new WindowAdapter (){
			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getInstance().popWindow();
				
			}
			
		});
	
		this.setSize( new Dimension(250,300) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	
	}
	
	private void showInfo() {
	    
	    if (type == LIFECYCLE)
		JOptionPane.showMessageDialog(this,  TextConstants.getText("LOMES.LyfeCycle.ContributeInfo"), "Info",JOptionPane.INFORMATION_MESSAGE);
	    if (type == METAMETADATA)
		JOptionPane.showMessageDialog(this,  TextConstants.getText("LOMES.MetaMetaData.ContributeInfo"), "Info",JOptionPane.INFORMATION_MESSAGE);
		
	    
	}
	
	/**
	 * @return the entityValue
	 */
	public ArrayList getEntityValue() {
		return entityValue;
	}



	/**
	 * @return the dateValue
	 */
	public LOMESLifeCycleDate getDateValue() {
		return dateValue;
	}



	public Vocabulary getRoleValue(){
		roleValue.setValueIndex(role.getSelection());
		return roleValue;
	}
	
	
}
