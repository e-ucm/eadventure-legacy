package es.eucm.eadventure.editor.gui.startdialog;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.editor.gui.TextConstants;

public class DescriptorDataPanel extends JPanel {

	private DescriptorData currentDescriptor = null;

	private JTextField titleTextField;

	private JTextArea descriptionTextArea;

	private JTextArea playerModeDescription;

	private JTextField playerMode;

	private JTextField pathTextField;

	private String absoultePath;

	public DescriptorDataPanel( DescriptorData descriptor, String absolutePath ) {
		super( );
		this.currentDescriptor = descriptor;
		this.absoultePath = absolutePath;
		// Panel with the options
		JPanel guiStylesPanel = new JPanel( );
		guiStylesPanel.setLayout( new GridBagLayout( ) );
		guiStylesPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.gridy = 0;
		c.gridx = 0;
		c.gridheight = 1;
		// Create text field for the title
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel titlePanel = new JPanel( );
		titlePanel.setLayout( new GridLayout( ) );
		if( currentDescriptor != null )
			titleTextField = new JTextField( currentDescriptor.getTitle( ) );
		else
			titleTextField = new JTextField( "" );
		titleTextField.setEditable( false );
		titlePanel.add( titleTextField );
		titlePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.AdventureTitle" ) ) );
		guiStylesPanel.add( titlePanel, c );
		// Create the absolutePath field
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 1;
		if( absolutePath != null ) {
			pathTextField = new JTextField( absoultePath );
		} else {
			pathTextField = new JTextField( "" );
		}
		pathTextField.setEditable( false );
		JPanel pathTextPanel = new JPanel( );
		pathTextPanel.setLayout( new GridLayout( ) );
		pathTextPanel.add( pathTextField );
		pathTextPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.CompletePath" ) ) );
		guiStylesPanel.add( pathTextPanel, c );

		// Create the text area for the description
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.gridy = 2;
		c.gridx = 0;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		if( currentDescriptor != null )
			descriptionTextArea = new JTextArea( descriptor.getDescription( ), 4, 0 );
		else
			descriptionTextArea = new JTextArea( "", 4, 0 );
		descriptionTextArea.setLineWrap( true );
		descriptionTextArea.setWrapStyleWord( true );
		descriptionPanel.add( new JScrollPane( descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.AdventureDescription" ) ) );
		descriptionTextArea.setEditable( false );
		guiStylesPanel.add( descriptionPanel, c );

		//Create the info panel for the mode of the player
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.gridy = 3;
		JPanel playerModePanel = new JPanel( );
		playerModePanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c1 = new GridBagConstraints( );
		c1.anchor = GridBagConstraints.LINE_END;
		c1.fill = GridBagConstraints.HORIZONTAL;
		c1.weightx = 0.5;
		c1.gridx = 0;
		c1.weighty = 0.35;
		c1.gridy = 0;
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.AdventureDescription" ) ) );
		playerMode = null;
		playerModeDescription = new JTextArea( );
		playerModeDescription.setEditable( false );
		playerModeDescription.setWrapStyleWord( true );
		playerModeDescription.setBackground( playerModePanel.getBackground( ) );
		playerModeDescription.setBorder( BorderFactory.createEtchedBorder( ) );
		playerModeDescription.setLineWrap( true );
		if( descriptor == null ) {
			playerMode = new JTextField( "" );
			playerModeDescription.setText( "" );
		} else if( descriptor.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON ) {
			playerMode = new JTextField( TextConstants.getText( "Adventure.ModePlayerTransparent.Name" ) );
			playerModeDescription.setText( TextConstants.getText( "Adventure.ModePlayerTransparent.Description" ) );
		} else {
			playerMode = new JTextField( TextConstants.getText( "Adventure.ModePlayerVisible.Name" ) );
			playerModeDescription.setText( TextConstants.getText( "Adventure.ModePlayerVisible.Description" ) );
		}
		playerMode.setEditable( false );
		playerMode.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.CurrentPlayerMode" ) ) );
		c1.gridx = 0;
		c1.anchor = GridBagConstraints.LINE_START;
		playerModePanel.add( playerMode, c1 );
		c1.gridwidth = 1;
		c1.gridy = 1;
		c1.weightx = 1;
		c1.weighty = 0.65;
		c1.anchor = GridBagConstraints.CENTER;
		playerModePanel.add( playerModeDescription, c1 );
		playerModePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Adventure.PlayerMode" ) ) );
		guiStylesPanel.add( playerModePanel, c );

		setLayout( new BorderLayout( ) );
		add( guiStylesPanel, BorderLayout.CENTER );
	}

	/**
	 * @return the currentDescriptor
	 */
	public DescriptorData getCurrentDescriptor( ) {
		return currentDescriptor;
	}

	/**
	 * @param currentDescriptor the currentDescriptor to set
	 */
	public void update( DescriptorData currentDescriptor, String absolutePath ) {
		this.currentDescriptor = currentDescriptor;
		this.absoultePath = absolutePath;
		//Update the text fields
		if( currentDescriptor == null ) {
			playerMode.setText( "" );
			playerModeDescription.setText( "" );
		} else if( currentDescriptor.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON ) {
			playerMode.setText( TextConstants.getText( "Adventure.ModePlayerTransparent.Name" ) );
			playerModeDescription.setText( TextConstants.getText( "Adventure.ModePlayerTransparent.Description" ) );
		} else {
			playerMode.setText( TextConstants.getText( "Adventure.ModePlayerVisible.Name" ) );
			playerModeDescription.setText( TextConstants.getText( "Adventure.ModePlayerVisible.Description" ) );
		}

		if( currentDescriptor != null )
			descriptionTextArea.setText( currentDescriptor.getDescription( ) );
		else
			descriptionTextArea.setText( "" );

		if( currentDescriptor != null )
			titleTextField.setText( currentDescriptor.getTitle( ) );
		else
			titleTextField.setText( "" );

		if( absolutePath != null ) {
			pathTextField.setText( absoultePath );
		} else {
			pathTextField.setText( "" );
		}

	}
}
