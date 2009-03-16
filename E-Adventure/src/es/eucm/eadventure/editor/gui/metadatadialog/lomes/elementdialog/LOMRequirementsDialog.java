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

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleContribute;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMOrComposite;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESCreateComposeTypePanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMVocabularyPanel;


public class LOMRequirementsDialog extends JDialog{

	
	private LOMVocabularyPanel type;
    
	private LOMVocabularyPanel name;
	
	private JTextField minimumVersion;
    
	private JTextField maximumVersion;
	
	private Vocabulary typeValue;

	private Vocabulary nameValue;
	
	private String maxValue;
	
	private String minValue;
	
	private LOMESContainer container;
	

	
	public LOMRequirementsDialog(LOMESContainer container, int selectedItem){
		super( Controller.getInstance( ).peekWindow( ), container.getTitle(), Dialog.ModalityType.APPLICATION_MODAL );
		this.container = container;
		
		if (selectedItem ==0){
			
		    typeValue=new Vocabulary(Vocabulary.TE_TYPE_4_4_1_1);
		    nameValue= new Vocabulary(Vocabulary.TE_NAME_4_4_1_2);
		    maxValue = Controller.getInstance().getEditorMinVersion();
		    minValue = Controller.getInstance().getEditorVersion();
		}else {
		    
		    typeValue=((LOMOrComposite)container.get(selectedItem)).getType();
		    nameValue= ((LOMOrComposite)container.get(selectedItem)).getName();
		    maxValue = ((LOMOrComposite)container.get(selectedItem)).getMaximumVersion();
		    minValue = ((LOMOrComposite)container.get(selectedItem)).getMinimumVersion();
		}
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel typePanel = new JPanel(new GridBagLayout());
		type = new LOMVocabularyPanel(LOMOrComposite.getTypeOptions(),typeValue.getValueIndex());
		typePanel.add(type,c);
		typePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.Technical.Type" ) ) );
		
		JPanel namePanel = new JPanel(new GridBagLayout());
		name = new LOMVocabularyPanel(LOMOrComposite.getNameOptions(),nameValue.getValueIndex());
		namePanel.add(name,c);
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOMES.Technical.Name" ) ) );
		
		
		JPanel maxPanel = new JPanel(new GridBagLayout());
		maximumVersion = new JTextField(maxValue);
		maximumVersion.setEditable(false);
		//maximumVersion.getDocument().addDocumentListener(new TextFieldListener (maximumVersion));
		maxPanel.add(maximumVersion,c);
		maxPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOM.Technical.MaximumVersion" ) ) );
		
		JPanel minPanel = new JPanel(new GridBagLayout());
		minimumVersion = new JTextField(minValue);
		minimumVersion.setEditable(false);
		//minimumVersion.getDocument().addDocumentListener(new TextFieldListener minimumVersion));
		minPanel.add(minimumVersion,c);
		minPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "LOM.Technical.MinimumVersion" ) ) );
		
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints(); 
		c.insets = new Insets(5,5,5,5);c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		mainPanel.add(typePanel,c);
		c.gridy=1;
		mainPanel.add(namePanel,c);
		c.gridy=2;
		mainPanel.add(maxPanel,c);
		c.gridy=3;
		mainPanel.add(minPanel,c);
		
		
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
	 * @return the maxValue
	 */
	public String getMaxValue() {
	    return maxValue;
	}

	/**
	 * @return the typeValue
	 */
	public Vocabulary getTypeValue() {
	    return typeValue;
	}



	/**
	 * @return the nameValue
	 */
	public Vocabulary getNameValue() {
	    return nameValue;
	}



	/**
	 * @return the minValue
	 */
	public String getMinValue() {
	    return minValue;
	}
	

	
	
	
}
