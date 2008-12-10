package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class ExportToLOMDialog extends JDialog{

	private boolean validated;
	
	private String lomName;
	
	private String authorName;
	
	private String organizationName;
	
	private JTextField lomNameTextField;
	
	private JTextField authorNameTextField;
	
	private JTextField organizationTextField;
	
	private JCheckBox windowedCheckBox;
	
	private boolean windowed = false;
	
	public ExportToLOMDialog(String defaultLomName){
		// Set the values
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Operation.ExportToLOM.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		
		this.lomName = defaultLomName;
		
		this.getContentPane( ).setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(5,5,5,5);c.fill = GridBagConstraints.BOTH;c.weightx=1;
		
		//LOM NAME PANEL
		JPanel lomNamePanel = new JPanel();
		lomNamePanel.setLayout( new GridBagLayout() );
		lomNamePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Operation.ExportToLOM.LOMName" ) ) );
		lomNameTextField = new JTextField(defaultLomName);
		lomNameTextField.getDocument( ).addDocumentListener( new TextFieldListener(lomNameTextField) );
		JTextPane lomNameDescription = new JTextPane( );
		lomNameDescription.setEditable( false );
		lomNameDescription.setBackground( getContentPane( ).getBackground( ) );
		lomNameDescription.setText( TextConstants.getText( "Operation.ExportToLOM.LOMName.Description" ) );
		GridBagConstraints c2 = new GridBagConstraints();
		c2.insets = new Insets(5,5,5,5);c.gridy=0;c2.gridy = 0;
		c2.fill = GridBagConstraints.BOTH;c2.weightx=1;
		lomNamePanel.add( lomNameDescription, c2 );
		c2.gridy=1;
		lomNamePanel.add( lomNameTextField, c2 );
		
		//Credentials panel
		JPanel credentialsPanel = new JPanel();
		credentialsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Operation.ExportToLOM.Credentials" ) ) );
		credentialsPanel.setLayout( new GridBagLayout() );
		c2 = new GridBagConstraints();
		c2.insets = new Insets(5,5,5,5);
		c2.fill = GridBagConstraints.BOTH;c2.weightx=1;c2.gridy=0;
		JTextPane credentialsDescription = new JTextPane( );
		credentialsDescription.setEditable( false );
		credentialsDescription.setBackground( getContentPane( ).getBackground( ) );
		credentialsDescription.setText( TextConstants.getText( "Operation.ExportToLOM.Credentials.Description" ) );
		credentialsPanel.add( credentialsDescription, c2 );

		//Author name
		JPanel authorNamePanel = new JPanel();
		authorNamePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Operation.ExportToLOM.AuthorName" ) ) );
		authorNamePanel.setLayout( new GridBagLayout() );
		GridBagConstraints c3 = new GridBagConstraints();
		c3.insets = new Insets(2,2,2,2);c3.weightx=1;c3.fill = GridBagConstraints.BOTH;
		authorNameTextField = new JTextField("");
		authorNameTextField.getDocument( ).addDocumentListener( new TextFieldListener(authorNameTextField) );
		authorNamePanel.add( authorNameTextField, c3 );
		c2.gridy=1;
		credentialsPanel.add( authorNamePanel,c2 );
		
		//Organization name
		JPanel organizationNamePanel = new JPanel();
		organizationNamePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Operation.ExportToLOM.OrganizationName" ) ) );
		organizationNamePanel.setLayout( new GridBagLayout() );
		c3 = new GridBagConstraints();
		c3.insets = new Insets(2,2,2,2);c3.weightx=1;c3.fill = GridBagConstraints.BOTH;
		organizationTextField = new JTextField("");
		organizationTextField.getDocument( ).addDocumentListener( new TextFieldListener(organizationTextField) );
		organizationNamePanel.add( organizationTextField, c3 );
		c2.gridy=2;
		credentialsPanel.add( organizationNamePanel,c2 );
		

		//Applet properties panel
		JPanel lomAppletPanel = new JPanel();
		lomAppletPanel.setLayout( new GridBagLayout() );
		lomAppletPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Operation.ExportToLOM.LOMAppletProperties" ) ) );
		//lomNameTextField = new JTextField(defaultLomName);
		windowedCheckBox = new JCheckBox(TextConstants.getText("Operation.ExportToLOM.LOMAppletRunInsideBrowser"));
		windowedCheckBox.setSelected(true);
		windowedCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeWindowedMode();
			}});
		c2 = new GridBagConstraints();
		c2.insets = new Insets(5,5,5,5);c.gridy=0;c2.gridy = 0;
		c2.fill = GridBagConstraints.BOTH;c2.weightx=1;
		lomAppletPanel.add( windowedCheckBox, c2 );
		
		//Button panel
		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton(TextConstants.getText( "Operation.ExportToLOM.OK" ));
		okButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				validated = true;
				setVisible(false);
				dispose();
			}
		});
		buttonPanel.add( okButton );
		
		//Add all panels
		this.getContentPane( ).add( lomNamePanel, c );
		c.gridy=1;
		this.getContentPane( ).add( credentialsPanel, c );
		c.gridy=2;
		this.getContentPane( ).add( lomAppletPanel, c);
		c.gridy=3;c.anchor = GridBagConstraints.CENTER;
		this.getContentPane( ).add( buttonPanel, c );
		
		// Add window listener
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				validated=false;
				setVisible( false );
				dispose( );
			}
		} );
		
		this.setSize( new Dimension(400,470) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	}
	
	protected void changeWindowedMode() {
		windowed = !windowedCheckBox.isSelected();
	}

	private class TextFieldListener implements DocumentListener {

		private JTextField textField;
		
		public TextFieldListener(JTextField textField){
			this.textField = textField;
		}
		
		public void changedUpdate( DocumentEvent e ) {
			//Do nothing
		}

		public void insertUpdate( DocumentEvent e ) {
			if (textField == lomNameTextField){
				lomName = textField.getText( );
			}
			else if (textField == authorNameTextField){
				authorName = textField.getText( );
			}
			else if (textField == organizationTextField){
				organizationName = textField.getText( );
			}
		}

		public void removeUpdate( DocumentEvent e ) {
			insertUpdate(e);
		}
		
	}

	/**
	 * @return the lomName
	 */	public String getLomName( ) {
		return lomName;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName( ) {
		return authorName;
	}

	/**
	 * @return the organizationName
	 */
	public String getOrganizationName( ) {
		return organizationName;
	}

	/**
	 * @return the validated
	 */
	public boolean isValidated( ) {
		return validated;
	}

	/**
	 * @param validated the validated to set
	 */
	public void setValidated( boolean validated ) {
		this.validated = validated;
	}

	public boolean getWindowed() {
		return windowed;
	}
}
