package es.eucm.eadventure.adventureeditor.gui.otherpanels.positionpanel;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.adventureeditor.gui.TextConstants;
import es.eucm.eadventure.adventureeditor.gui.otherpanels.positionimagepanels.PositionImagePanel;

public class PositionPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Listener of the panel.
	 */
	private PositionPanelListener positionPanelListener;

	/**
	 * Text field containing the X position.
	 */
	private JTextField positionXTextField;

	/**
	 * Text field containing the Y position.
	 */
	private JTextField positionYTextField;

	/**
	 * Panel with the image.
	 */
	private PositionImagePanel positionImagePanel;

	/**
	 * Last valid X position.
	 */
	private int positionX;

	/**
	 * Last valid Y position.
	 */
	private int positionY;

	/**
	 * Constructor.
	 * 
	 * @param positionImagePanel
	 *            Position image panel
	 */
	public PositionPanel( PositionImagePanel positionImagePanel ) {
		this( null, positionImagePanel );
	}

	/**
	 * Constructor
	 * 
	 * @param positionPanelListener
	 *            Listener for the changes on the position panel
	 * @param positionImagePanel
	 *            Position image panel
	 */
	public PositionPanel( PositionPanelListener positionPanelListener, PositionImagePanel positionImagePanel ) {

		// Set the panel
		this.positionPanelListener = positionPanelListener;
		this.positionImagePanel = positionImagePanel;

		// Set the default values
		positionX = 0;
		positionY = 0;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );

		// Panel for the X coordinate
		JPanel xCoordinatePanel = new JPanel( );
		xCoordinatePanel.setLayout( new FlowLayout( ) );

		// Create and add the x position label
		xCoordinatePanel.add( new JLabel( TextConstants.getText( "SceneLocation.XCoordinate" ) ) );

		// Create and add the x position text field
		positionXTextField = new JTextField( String.valueOf( positionX ), 5 );
		positionXTextField.addActionListener( new TextFieldChangesListener( ) );
		positionXTextField.addFocusListener( new TextFieldChangesListener( ) );
		xCoordinatePanel.add( positionXTextField, c );

		// Add the X coordinate panel
		c.insets = new Insets( 4, 4, 4, 4 );
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		add( xCoordinatePanel, c );

		// Panel for the Y coordinate
		JPanel yCoordinatePanel = new JPanel( );
		yCoordinatePanel.setLayout( new FlowLayout( ) );

		// Create and add the y position label
		yCoordinatePanel.add( new JLabel( TextConstants.getText( "SceneLocation.YCoordinate" ) ), c );

		// Create and add the y position text field
		positionYTextField = new JTextField( String.valueOf( positionY ), 5 );
		positionYTextField.addActionListener( new TextFieldChangesListener( ) );
		positionYTextField.addFocusListener( new TextFieldChangesListener( ) );
		yCoordinatePanel.add( positionYTextField, c );

		// Add the Y coordinate panel
		c.gridx = 1;
		add( yCoordinatePanel, c );

		// Create and add the panel
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		positionImagePanel.addMouseListener( new ImagePanelMouseListener( ) );
		add( positionImagePanel, c );

		// Set the actual position in the panel and repaint it
		positionImagePanel.setSelectedPoint( positionX, positionY );
		positionImagePanel.repaint( );
	}

	/**
	 * Loads a new background image.
	 * 
	 * @param imagePath
	 *            Path for the background image
	 */
	public void loadImage( String imagePath ) {
		positionImagePanel.loadImage( imagePath );
	}

	/**
	 * Removes the background image.
	 */
	public void removeImage( ) {
		positionImagePanel.removeImage( );
	}

	/**
	 * Returns the X coordinate of the selected position.
	 * 
	 * @return X coordinate of the selected position
	 */
	public int getPositionX( ) {
		return positionX;
	}

	/**
	 * Returns the Y coordinate of the selected position.
	 * 
	 * @return Y coordinate of the selected position
	 */
	public int getPositionY( ) {
		return positionY;
	}

	/**
	 * Sets the new selected position.
	 * 
	 * @param x
	 *            X coordinate of the selected position
	 * @param y
	 *            Y coordinate of the selected position
	 */
	public void setPosition( int x, int y ) {
		// Set the new values
		positionX = x;
		positionY = y;

		// Set the values on the fields
		positionXTextField.setText( String.valueOf( positionX ) );
		positionYTextField.setText( String.valueOf( positionY ) );

		// Set the point in the panel and repaint it
		positionImagePanel.setSelectedPoint( positionX, positionY );
		positionImagePanel.repaint( );
	}

	/**
	 * Updates the values of the position, extracting them from the text fields.
	 */
	private void checkPositionValues( ) {

		try {
			// Try to parse the value from the text field
			positionX = Integer.parseInt( positionXTextField.getText( ) );
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			positionXTextField.setText( String.valueOf( positionX ) );
		}

		try {
			// Try to parse the value from the text field
			positionY = Integer.parseInt( positionYTextField.getText( ) );
		} catch( NumberFormatException e ) {
			// If it failed, set the last valid value
			positionYTextField.setText( String.valueOf( positionY ) );
		}

		// Call the listener
		if( positionPanelListener != null )
			positionPanelListener.updatePositionValues( positionX, positionY );

		// Set the values on the panel and repaint it
		positionImagePanel.setSelectedPoint( positionX, positionY );
		positionImagePanel.repaint( );
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
			checkPositionValues( );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			checkPositionValues( );
		}
	}

	/**
	 * Listener for the image panel.
	 */
	private class ImagePanelMouseListener extends MouseAdapter {

		@Override
		public void mousePressed( MouseEvent mouseEvent ) {
			// If the panel has a loaded image
			if( positionImagePanel.isImageLoaded( ) ) {

				// Calculate and set the new values
				int x = positionImagePanel.getRelativeX( mouseEvent.getX( ) );
				int y = positionImagePanel.getRelativeY( mouseEvent.getY( ) );
				setPosition( x, y );

				// Call the listener
				if( positionPanelListener != null )
					positionPanelListener.updatePositionValues( positionX, positionY );
			}
		}
	}
}
