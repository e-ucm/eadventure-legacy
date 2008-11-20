package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.RectangleImagePanel;

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

	/**
	 * Text field containing the X position.
	 */
	private JTextField activeAreaXTextField;

	/**
	 * Text field containing the Y position.
	 */
	private JTextField activeAreaYTextField;

	/**
	 * Text field containing the width.
	 */
	private JTextField activeAreaWidthTextField;

	/**
	 * Text field containing the height.
	 */
	private JTextField activeAreaHeightTextField;

	/**
	 * Panel in which the activeArea area is painted.
	 */
	private RectangleImagePanel rectangleImagePanel;

	/**
	 * X coordinate of the corner of the activeArea.
	 */
	private int activeAreaX;

	/**
	 * Y coordinate of the corner of the activeArea.
	 */
	private int activeAreaY;

	/**
	 * Width of the activeArea.
	 */
	private int activeAreaWidth;

	/**
	 * Height of the activeArea.
	 */
	private int activeAreaHeight;

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

		// Set the values of the activeArea
		activeAreaX = activeAreaDataControl.getX( );
		activeAreaY = activeAreaDataControl.getY( );
		activeAreaWidth = activeAreaDataControl.getWidth( );
		activeAreaHeight = activeAreaDataControl.getHeight( );

		
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
		
		
		// Create the panel for the rectangle selection and the listener for the text fields
		JPanel rectangleImagePositionPanel = new JPanel( );
		rectangleImagePositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Preview" ) ) );
		rectangleImagePositionPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		ActiveAreaTextFieldChangesListener textFieldChangesListener = new ActiveAreaTextFieldChangesListener( );

		// Panel for the X coordinate
		JPanel xCoordinatePanel = new JPanel( );
		xCoordinatePanel.setLayout( new FlowLayout( ) );

		// Create and add the x position label
		xCoordinatePanel.add( new JLabel( TextConstants.getText( "SceneLocation.XCoordinate" ) ) );

		// Create and add the x position text field
		activeAreaXTextField = new JTextField( String.valueOf( activeAreaX ), 5 );
		activeAreaXTextField.addActionListener( textFieldChangesListener );
		activeAreaXTextField.addFocusListener( textFieldChangesListener );
		xCoordinatePanel.add( activeAreaXTextField, c );

		// Add the X coordinate panel
		c.insets = new Insets( 4, 4, 4, 4 );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		rectangleImagePositionPanel.add( xCoordinatePanel, c );

		// Panel for the Y coordinate
		JPanel yCoordinatePanel = new JPanel( );
		yCoordinatePanel.setLayout( new FlowLayout( ) );

		// Create and add the y position label
		yCoordinatePanel.add( new JLabel( TextConstants.getText( "SceneLocation.YCoordinate" ) ), c );

		// Create and add the y position text field
		activeAreaYTextField = new JTextField( String.valueOf( activeAreaY ), 5 );
		activeAreaYTextField.addActionListener( textFieldChangesListener );
		activeAreaYTextField.addFocusListener( textFieldChangesListener );
		yCoordinatePanel.add( activeAreaYTextField, c );

		// Add the Y coordinate panel
		c.gridx = 1;
		rectangleImagePositionPanel.add( yCoordinatePanel, c );

		// Panel for the width
		JPanel widthPanel = new JPanel( );
		widthPanel.setLayout( new FlowLayout( ) );

		// Create and add the width label
		widthPanel.add( new JLabel( TextConstants.getText( "SceneLocation.Width" ) ) );

		// Create and add the x position text field
		activeAreaWidthTextField = new JTextField( String.valueOf( activeAreaWidth ), 5 );
		activeAreaWidthTextField.addActionListener( textFieldChangesListener );
		activeAreaWidthTextField.addFocusListener( textFieldChangesListener );
		widthPanel.add( activeAreaWidthTextField, c );

		// Add the width panel
		c.gridx = 0;
		c.gridy = 1;
		rectangleImagePositionPanel.add( widthPanel, c );

		// Panel for the height
		JPanel heightPanel = new JPanel( );
		heightPanel.setLayout( new FlowLayout( ) );

		// Create and add the height label
		heightPanel.add( new JLabel( TextConstants.getText( "SceneLocation.Height" ) ), c );

		// Create and add the height text field
		activeAreaHeightTextField = new JTextField( String.valueOf( activeAreaHeight ), 5 );
		activeAreaHeightTextField.addActionListener( textFieldChangesListener );
		activeAreaHeightTextField.addFocusListener( textFieldChangesListener );
		heightPanel.add( activeAreaHeightTextField, c );

		// Add the height panel
		c.gridx = 1;
		rectangleImagePositionPanel.add( heightPanel, c );

		// Create and set the panel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		rectangleImagePanel = new RectangleImagePanel( scenePath, activeAreaX, activeAreaY, activeAreaWidth, activeAreaHeight, Color.GREEN );
		rectangleImagePanel.addMouseListener( new ImagePanelMouseListener( ) );
		rectangleImagePanel.addMouseMotionListener( new ImagePanelMouseListener( ) );
		rectangleImagePositionPanel.add( rectangleImagePanel, c );

		// Set the layout of the principal panel
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Title" ) ) );
		c = new GridBagConstraints( );
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
		mainPanel.add( rectangleImagePositionPanel, c );
		
		return mainPanel;
	}
	
	/**
	 * Updates the values of the activeArea, extracting them from the text fields.
	 */
	private void checkActiveAreaValues( ) {

		try {
			// Try to parse the value from the text field
			activeAreaX = Integer.parseInt( activeAreaXTextField.getText( ) );
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			activeAreaXTextField.setText( String.valueOf( activeAreaX ) );
		}

		try {
			// Try to parse the value from the text field
			activeAreaY = Integer.parseInt( activeAreaYTextField.getText( ) );
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			activeAreaYTextField.setText( String.valueOf( activeAreaY ) );
		}

		try {
			// Try to parse the value from the text field
			int activeAreaWidthValue = Integer.parseInt( activeAreaWidthTextField.getText( ) );

			// If the value is negative, set the last valid value
			if( activeAreaWidthValue < 0 )
				activeAreaWidthTextField.setText( String.valueOf( activeAreaWidth ) );
			else
				activeAreaWidth = activeAreaWidthValue;
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			activeAreaWidthTextField.setText( String.valueOf( activeAreaWidth ) );
		}

		try {
			// Try to parse the value from the text field
			int activeAreaHeightValue = Integer.parseInt( activeAreaHeightTextField.getText( ) );

			// If the value is negative, set the last valid value
			if( activeAreaHeightValue < 0 )
				activeAreaHeightTextField.setText( String.valueOf( activeAreaHeight ) );
			else
				activeAreaHeight = activeAreaHeightValue;
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			activeAreaHeightTextField.setText( String.valueOf( activeAreaHeight ) );
		}

		// Set the values on the panel and the controller and repaint it
		activeAreaDataControl.setActiveArea( activeAreaX, activeAreaY, activeAreaWidth, activeAreaHeight );
		rectangleImagePanel.setFirstPoint( activeAreaX, activeAreaY );
		rectangleImagePanel.setRectangleSize( activeAreaWidth, activeAreaHeight );
		rectangleImagePanel.repaint( );
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
	 * Listener for the text fields. It checks the values from the fields and updates the panel.
	 */
	private class ActiveAreaTextFieldChangesListener extends FocusAdapter implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost( FocusEvent e ) {
			checkActiveAreaValues( );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			checkActiveAreaValues( );
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
	 * Listener for the image panel.
	 */
	private class ImagePanelMouseListener implements MouseListener, MouseMotionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed( MouseEvent e ) {
			// If the panel has a loaded image
			if( rectangleImagePanel.isImageLoaded( ) ) {

				// Calculate and set the new values
				activeAreaX = rectangleImagePanel.getRelativeX( e.getX( ) );
				activeAreaY = rectangleImagePanel.getRelativeY( e.getY( ) );

				// Set the points on the panel
				rectangleImagePanel.setFirstPoint( activeAreaX, activeAreaY );
				rectangleImagePanel.setSecondPoint( activeAreaX, activeAreaY );
				rectangleImagePanel.repaint( );

				// Set the new values of the text fields
				activeAreaXTextField.setText( String.valueOf( rectangleImagePanel.getRectangleX( ) ) );
				activeAreaYTextField.setText( String.valueOf( rectangleImagePanel.getRectangleY( ) ) );
				activeAreaWidthTextField.setText( String.valueOf( rectangleImagePanel.getRectangleWidth( ) ) );
				activeAreaHeightTextField.setText( String.valueOf( rectangleImagePanel.getRectangleHeight( ) ) );
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
		 */
		public void mouseDragged( MouseEvent e ) {
			// If the panel has a loaded image
			if( rectangleImagePanel.isImageLoaded( ) ) {

				// Calculate and set the second point
				int secondPointX = rectangleImagePanel.getRelativeX( e.getX( ) );
				int secondPointY = rectangleImagePanel.getRelativeY( e.getY( ) );

				// Set the second point on the panel
				rectangleImagePanel.setSecondPoint( secondPointX, secondPointY );
				rectangleImagePanel.repaint( );

				// Set the new values of the text fields
				activeAreaWidthTextField.setText( String.valueOf( rectangleImagePanel.getRectangleWidth( ) ) );
				activeAreaHeightTextField.setText( String.valueOf( rectangleImagePanel.getRectangleHeight( ) ) );
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased( MouseEvent e ) {
			// If the panel has a loaded image
			if( rectangleImagePanel.isImageLoaded( ) ) {

				// Calculate and set the last point values
				int lastPointX = rectangleImagePanel.getRelativeX( e.getX( ) );
				int lastPointY = rectangleImagePanel.getRelativeY( e.getY( ) );

				// Set the last point on the panel (and validate the data)
				rectangleImagePanel.setSecondPoint( lastPointX, lastPointY );
				rectangleImagePanel.validateRectangle( );
				rectangleImagePanel.repaint( );

				// Set the new values (in the panel and in the controller)
				activeAreaX = rectangleImagePanel.getRectangleX( );
				activeAreaY = rectangleImagePanel.getRectangleY( );
				activeAreaWidth = rectangleImagePanel.getRectangleWidth( );
				activeAreaHeight = rectangleImagePanel.getRectangleHeight( );
				activeAreaDataControl.setActiveArea( activeAreaX, activeAreaY, activeAreaWidth, activeAreaHeight );

				// Update the text fields
				activeAreaXTextField.setText( String.valueOf( activeAreaX ) );
				activeAreaYTextField.setText( String.valueOf( activeAreaY ) );
				activeAreaWidthTextField.setText( String.valueOf( activeAreaWidth ) );
				activeAreaHeightTextField.setText( String.valueOf( activeAreaHeight ) );
			}
		}

		// Not implemented
		public void mouseClicked( MouseEvent e ) {}

		public void mouseEntered( MouseEvent e ) {}

		public void mouseActiveAreaed( MouseEvent e ) {}

		public void mouseMoved( MouseEvent e ) {}

		public void mouseExited( MouseEvent e ) {}
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
