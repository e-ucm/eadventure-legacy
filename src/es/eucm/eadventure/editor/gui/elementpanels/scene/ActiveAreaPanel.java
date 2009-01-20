package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ActiveAreaPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the activeArea.
	 */
	private ActiveAreaDataControl activeAreaDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	private ScenePreviewEditionPanel spep;
	
	private JTabbedPane tabPanel;

	private JPanel docPanel;

	private JTextField nameTextField;

	private JTextField descriptionTextField;

	private JTextField detailedDescriptionTextField;

	/**
	 * Constructor.
	 * 
	 * @param activeAreaDataControl
	 *            ActiveArea controller
	 */
	public ActiveAreaPanel( ActiveAreaDataControl activeAreaDataControl ) {
		// Set the controller
		this.activeAreaDataControl = activeAreaDataControl;
		
		// Create the panels and layouts
		tabPanel = new JTabbedPane( );
		docPanel = new JPanel( );
		docPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );

		// Set the layout
		setLayout( new BorderLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Title" ) ) );
		//GridBagConstraints c = new GridBagConstraints( );
		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		cDoc.weighty = 0.3;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( activeAreaDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Documentation" ) ) );
		docPanel.add( documentationPanel, cDoc );

		// Create the field for the name
		cDoc.gridy = 1;
		cDoc.weighty = 0;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( this.activeAreaDataControl.getName( ) );
		nameTextField.addActionListener( new TextFieldChangesListener( ) );
		nameTextField.addFocusListener( new TextFieldChangesListener( ) );
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Name" ) ) );
		docPanel.add( namePanel, cDoc );

		// Create the field for the brief description
		cDoc.gridy = 2;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		descriptionTextField = new JTextField( activeAreaDataControl.getBriefDescription( ) );
		descriptionTextField.addActionListener( new TextFieldChangesListener( ) );
		descriptionTextField.addFocusListener( new TextFieldChangesListener( ) );
		descriptionPanel.add( descriptionTextField );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Description" ) ) );
		docPanel.add( descriptionPanel, cDoc );

		// Create the field for the detailed description
		cDoc.gridy = 3;
		JPanel detailedDescriptionPanel = new JPanel( );
		detailedDescriptionPanel.setLayout( new GridLayout( ) );
		detailedDescriptionTextField = new JTextField( activeAreaDataControl.getDetailedDescription( ) );
		detailedDescriptionTextField.addActionListener( new TextFieldChangesListener( ) );
		detailedDescriptionTextField.addFocusListener( new TextFieldChangesListener( ) );
		detailedDescriptionPanel.add( detailedDescriptionTextField );
		detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.DetailedDescription" ) ) );
		docPanel.add( detailedDescriptionPanel, cDoc );
		
		cDoc.gridy = 4;
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		docPanel.add( new JFiller(),cDoc );

		// Add the tabs
		//Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel

		tabPanel.insertTab( TextConstants.getText( "ActiveArea.MainPanelTitle" ), null, createMainPanel(), TextConstants.getText( "ActiveArea.MainPanelTip" ), 0 );
		tabPanel.insertTab( TextConstants.getText( "ActiveArea.DocPanelTitle" ), null, docPanel, TextConstants.getText( "ActiveArea.DocPanelTip" ), 1 );
		setLayout( new BorderLayout( ) );
		add( tabPanel, BorderLayout.CENTER );
		

	
		
	}

	private JPanel createMainPanel (){
		JPanel mainPanel = new JPanel();
		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( activeAreaDataControl.getParentSceneId( ) );
				
		spep = new ScenePreviewEditionPanel(scenePath);
		spep.setShowTextEdition(true);
		spep.setFixedSelectedElement(true);
		spep.setSelectedElement(activeAreaDataControl);
		
		// Set the layout of the principal panel
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the button for the conditions
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Conditions" ) ) );
		mainPanel.add( conditionsPanel, c );
		
		// Add the created rectangle position panel
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;		
		c.weighty = 1;
		mainPanel.add( spep, c );
		
		return mainPanel;
	}

	/**
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class DocumentationTextAreaChangesListener implements DocumentListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		public void changedUpdate( DocumentEvent arg0 ) {
		// Do nothing
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		public void insertUpdate( DocumentEvent arg0 ) {
			// Set the new content
			activeAreaDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			activeAreaDataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

	
	/**
	 * Called when a text field has changed, so that we can set the new values.
	 * 
	 * @param source
	 *            Source of the event
	 */
	private void valueChanged( Object source ) {
		// Check the name field
		if( source == nameTextField )
			this.activeAreaDataControl.setName( nameTextField.getText( ) );

		// Check the brief description field
		else if( source == descriptionTextField )
			this.activeAreaDataControl.setBriefDescription( descriptionTextField.getText( ) );

		// Check the detailed description field
		else if( source == detailedDescriptionTextField )
			this.activeAreaDataControl.setDetailedDescription( detailedDescriptionTextField.getText( ) );
	}

	
	/**
	 * Listener for the text fields. It checks the values from the fields and updates the data.
	 */
	private class TextFieldChangesListener extends FocusAdapter implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost( FocusEvent e ) {
			valueChanged( e.getSource( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			valueChanged( e.getSource( ) );
		}
	}

	/**
	 * Listener for the edit conditions button.
	 */
	private class ConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( activeAreaDataControl.getConditions( ) );
		}
	}
}
