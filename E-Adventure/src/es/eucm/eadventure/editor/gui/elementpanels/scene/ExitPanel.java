package es.eucm.eadventure.editor.gui.elementpanels.scene;

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
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ExitLookPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.RectangleImagePanel;

public class ExitPanel extends JTabbedPane {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the exit.
	 */
	private ExitDataControl exitDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field containing the X position.
	 */
	private JTextField exitXTextField;

	/**
	 * Text field containing the Y position.
	 */
	private JTextField exitYTextField;

	/**
	 * Text field containing the width.
	 */
	private JTextField exitWidthTextField;

	/**
	 * Text field containing the height.
	 */
	private JTextField exitHeightTextField;

	/**
	 * Panel in which the exit area is painted.
	 */
	private RectangleImagePanel rectangleImagePanel;

	/**
	 * X coordinate of the corner of the exit.
	 */
	private int exitX;

	/**
	 * Y coordinate of the corner of the exit.
	 */
	private int exitY;

	/**
	 * Width of the exit.
	 */
	private int exitWidth;

	/**
	 * Height of the exit.
	 */
	private int exitHeight;

	/**
	 * Constructor.
	 * 
	 * @param exitDataControl
	 *            Exit controller
	 */
	public ExitPanel( ExitDataControl exitDataControl ) {

		JPanel mainPanel = new JPanel();
		// Set the controller
		this.exitDataControl = exitDataControl;

		// Set the values of the exit
		exitX = exitDataControl.getX( );
		exitY = exitDataControl.getY( );
		exitWidth = exitDataControl.getWidth( );
		exitHeight = exitDataControl.getHeight( );

		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( exitDataControl.getParentSceneId( ) );

		// Create the panel for the rectangle selection and the listener for the text fields
		JPanel rectangleImagePositionPanel = new JPanel( );
		rectangleImagePositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Preview" ) ) );
		rectangleImagePositionPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		TextFieldChangesListener textFieldChangesListener = new TextFieldChangesListener( );

		// Panel for the X coordinate
		JPanel xCoordinatePanel = new JPanel( );
		xCoordinatePanel.setLayout( new FlowLayout( ) );

		// Create and add the x position label
		xCoordinatePanel.add( new JLabel( TextConstants.getText( "SceneLocation.XCoordinate" ) ) );

		// Create and add the x position text field
		exitXTextField = new JTextField( String.valueOf( exitX ), 5 );
		exitXTextField.addActionListener( textFieldChangesListener );
		exitXTextField.addFocusListener( textFieldChangesListener );
		xCoordinatePanel.add( exitXTextField, c );

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
		exitYTextField = new JTextField( String.valueOf( exitY ), 5 );
		exitYTextField.addActionListener( textFieldChangesListener );
		exitYTextField.addFocusListener( textFieldChangesListener );
		yCoordinatePanel.add( exitYTextField, c );

		// Add the Y coordinate panel
		c.gridx = 1;
		rectangleImagePositionPanel.add( yCoordinatePanel, c );

		// Panel for the width
		JPanel widthPanel = new JPanel( );
		widthPanel.setLayout( new FlowLayout( ) );

		// Create and add the width label
		widthPanel.add( new JLabel( TextConstants.getText( "SceneLocation.Width" ) ) );

		// Create and add the x position text field
		exitWidthTextField = new JTextField( String.valueOf( exitWidth ), 5 );
		exitWidthTextField.addActionListener( textFieldChangesListener );
		exitWidthTextField.addFocusListener( textFieldChangesListener );
		widthPanel.add( exitWidthTextField, c );

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
		exitHeightTextField = new JTextField( String.valueOf( exitHeight ), 5 );
		exitHeightTextField.addActionListener( textFieldChangesListener );
		exitHeightTextField.addFocusListener( textFieldChangesListener );
		heightPanel.add( exitHeightTextField, c );

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
		rectangleImagePanel = new RectangleImagePanel( scenePath, exitX, exitY, exitWidth, exitHeight );
		rectangleImagePanel.addMouseListener( new ImagePanelMouseListener( ) );
		rectangleImagePanel.addMouseMotionListener( new ImagePanelMouseListener( ) );
		rectangleImagePositionPanel.add( rectangleImagePanel, c );

		// Set the layout of the principal panel
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Title" ) ) );
		c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create and add te documenation panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( exitDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Documentation" ) ) );
		mainPanel.add( documentationPanel, c );

		// Add the created rectangle position panel
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add( rectangleImagePositionPanel, c );
		
		this.insertTab(  TextConstants.getText( "Exit.Title" ), null, mainPanel,  TextConstants.getText( "Exit.Title" ), 0 );
		this.insertTab( TextConstants.getText( "Exit.AdvancedOptions" ), null, new ExitLookPanel(this.exitDataControl.getExitLookDataControl( )), TextConstants.getText( "Exit.AdvancedOptions" ), 1 );
	}

	/**
	 * Updates the values of the exit, extracting them from the text fields.
	 */
	private void checkExitValues( ) {

		try {
			// Try to parse the value from the text field
			exitX = Integer.parseInt( exitXTextField.getText( ) );
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			exitXTextField.setText( String.valueOf( exitX ) );
		}

		try {
			// Try to parse the value from the text field
			exitY = Integer.parseInt( exitYTextField.getText( ) );
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			exitYTextField.setText( String.valueOf( exitY ) );
		}

		try {
			// Try to parse the value from the text field
			int exitWidthValue = Integer.parseInt( exitWidthTextField.getText( ) );

			// If the value is negative, set the last valid value
			if( exitWidthValue < 0 )
				exitWidthTextField.setText( String.valueOf( exitWidth ) );
			else
				exitWidth = exitWidthValue;
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			exitWidthTextField.setText( String.valueOf( exitWidth ) );
		}

		try {
			// Try to parse the value from the text field
			int exitHeightValue = Integer.parseInt( exitHeightTextField.getText( ) );

			// If the value is negative, set the last valid value
			if( exitHeightValue < 0 )
				exitHeightTextField.setText( String.valueOf( exitHeight ) );
			else
				exitHeight = exitHeightValue;
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			exitHeightTextField.setText( String.valueOf( exitHeight ) );
		}

		// Set the values on the panel and the controller and repaint it
		exitDataControl.setExit( exitX, exitY, exitWidth, exitHeight );
		rectangleImagePanel.setFirstPoint( exitX, exitY );
		rectangleImagePanel.setRectangleSize( exitWidth, exitHeight );
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
			exitDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			exitDataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

	/**
	 * Listener for the text fields. It checks the values from the fields and updates the panel.
	 */
	private class TextFieldChangesListener extends FocusAdapter implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost( FocusEvent e ) {
			checkExitValues( );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			checkExitValues( );
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
				exitX = rectangleImagePanel.getRelativeX( e.getX( ) );
				exitY = rectangleImagePanel.getRelativeY( e.getY( ) );

				// Set the points on the panel
				rectangleImagePanel.setFirstPoint( exitX, exitY );
				rectangleImagePanel.setSecondPoint( exitX, exitY );
				rectangleImagePanel.repaint( );

				// Set the new values of the text fields
				exitXTextField.setText( String.valueOf( rectangleImagePanel.getRectangleX( ) ) );
				exitYTextField.setText( String.valueOf( rectangleImagePanel.getRectangleY( ) ) );
				exitWidthTextField.setText( String.valueOf( rectangleImagePanel.getRectangleWidth( ) ) );
				exitHeightTextField.setText( String.valueOf( rectangleImagePanel.getRectangleHeight( ) ) );
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
				exitWidthTextField.setText( String.valueOf( rectangleImagePanel.getRectangleWidth( ) ) );
				exitHeightTextField.setText( String.valueOf( rectangleImagePanel.getRectangleHeight( ) ) );
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
				exitX = rectangleImagePanel.getRectangleX( );
				exitY = rectangleImagePanel.getRectangleY( );
				exitWidth = rectangleImagePanel.getRectangleWidth( );
				exitHeight = rectangleImagePanel.getRectangleHeight( );
				exitDataControl.setExit( exitX, exitY, exitWidth, exitHeight );

				// Update the text fields
				exitXTextField.setText( String.valueOf( exitX ) );
				exitYTextField.setText( String.valueOf( exitY ) );
				exitWidthTextField.setText( String.valueOf( exitWidth ) );
				exitHeightTextField.setText( String.valueOf( exitHeight ) );
			}
		}

		// Not implemented
		public void mouseClicked( MouseEvent e ) {}

		public void mouseEntered( MouseEvent e ) {}

		public void mouseExited( MouseEvent e ) {}

		public void mouseMoved( MouseEvent e ) {}
	}
}
