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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
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


public class LOMLifeCycleContributeDialog extends JDialog{

	
	private LOMCreatePrimitiveContainerPanel entity;
	
	private LOMVocabularyPanel role;
	
	private LOMESCreateComposeTypePanel date;
	
	private Vocabulary roleValue;
	
	private ArrayList entityValue;
	
	private LOMESLifeCycleDate dateValue;
	
	private LOMESContainer container;
	
	public LOMLifeCycleContributeDialog(LOMESContainer container, int selectedItem){
		super( Controller.getInstance( ).peekWindow( ), container.getTitle(), Dialog.ModalityType.APPLICATION_MODAL );
		this.container = container;
	
		
		if (selectedItem ==0){
			roleValue=new Vocabulary(LOMESLifeCycleContribute.getRoleVocabularyType());
			entityValue=new ArrayList<String>();
			dateValue = new LOMESLifeCycleDate();
		}else {
			roleValue=((LOMESLifeCycleContribute)container.get(selectedItem)).getRole();
			entityValue=((LOMESLifeCycleContribute)container.get(selectedItem)).getEntity();
			dateValue =((LOMESLifeCycleContribute)container.get(selectedItem)).getDate();
		}
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel rolePanel = new JPanel(new GridBagLayout());
		role = new LOMVocabularyPanel(LOMESLifeCycleContribute.getRoleOptions(),roleValue.getValueIndex());
		rolePanel.add(role,c);
		rolePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.LifeCycle.RoleName" ) ) );
		
		JPanel datePanel = new JPanel(new GridBagLayout());
		date = new LOMESCreateComposeTypePanel(dateValue);
		datePanel.add(date,c);
		datePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.LifeCyle.Date" ) ) );
		
		
		entity = new LOMCreatePrimitiveContainerPanel(LOMCreatePrimitiveContainerPanel.STRING_TYPE,entityValue,TextConstants.getText("LOMES.LifeCycle.Entity"),LOMCreatePrimitiveContainerPanel.FIELD_TYPE_FIELD);
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints(); 
		c.insets = new Insets(5,5,5,5);c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		mainPanel.add(rolePanel,c);
		c.gridy=1;
		mainPanel.add(entity,c);
		c.gridy=2;
		mainPanel.add(datePanel,c);
		
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
		buttonPanel.add(ok,c);
		
		this.getContentPane().setLayout(new GridLayout(0,2));
		this.getContentPane().add(mainPanel);
		this.getContentPane().add(buttonPanel);
	
		this.setSize( new Dimension(250,200) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	
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
