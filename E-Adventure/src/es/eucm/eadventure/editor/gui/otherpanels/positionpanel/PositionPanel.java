package es.eucm.eadventure.editor.gui.otherpanels.positionpanel;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.Positioned;
import es.eucm.eadventure.common.gui.NoEditableNumberSpinner;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.general.ChangePositionTool;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.PositionImagePanel;

public class PositionPanel extends JPanel implements Positioned{

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
	private NoEditableNumberSpinner positionXTextField;

	/**
	 * Text field containing the Y position.
	 */
	private NoEditableNumberSpinner positionYTextField;

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
	public PositionPanel( PositionImagePanel positionImagePanel, int initialX, int initialY  ) {
		this( null, positionImagePanel, initialX, initialY );
	}

	/**
	 * Constructor
	 * 
	 * @param positionPanelListener
	 *            Listener for the changes on the position panel
	 * @param positionImagePanel
	 *            Position image panel
	 */
	public PositionPanel( PositionPanelListener positionPanelListener, PositionImagePanel positionImagePanel, int initialX, int initialY ) {

		// Set the panel
		this.positionPanelListener = positionPanelListener;
		this.positionImagePanel = positionImagePanel;
		this.positionX = initialX;
		this.positionY = initialY;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );

		// Panel for the X coordinate
		JPanel xCoordinatePanel = new JPanel( );
		xCoordinatePanel.setLayout( new FlowLayout( ) );

		// Create and add the x position label
		xCoordinatePanel.add( new JLabel( TextConstants.getText( "SceneLocation.XCoordinate" ) ) );

		// Create and add the x position text field
		positionXTextField = new NoEditableNumberSpinner( positionX, 0, 1000000,1 );
		positionXTextField.addChangeListener(new ChangeValueListener());
		//positionXTextField.addActionListener( new TextFieldChangesListener( ) );
		//positionXTextField.addFocusListener( new TextFieldChangesListener( ) );
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
		positionYTextField = new NoEditableNumberSpinner( positionY, 0, 1000000,1 );
		//positionYTextField.setEnabled(false);
		positionYTextField.addChangeListener(new ChangeValueListener());
		
		//positionYTextField = new JTextField( String.valueOf( positionY ), 5 );
		//positionYTextField.addActionListener( new TextFieldChangesListener( ) );
		//positionYTextField.addFocusListener( new TextFieldChangesListener( ) );
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
		Controller.getInstance().addTool(new ChangePositionTool(this,x,y));
	}
	
	public void setPositionX( int x ){
		positionX = x;
		positionXTextField.setValue( positionX );
	}
	
	public void setPositionY( int y ){
		positionY = y;
		positionYTextField.setValue( positionY );
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
	
	public class ChangeValueListener implements ChangeListener {
		
			public void stateChanged(ChangeEvent e) {
				if (e.getSource()==positionXTextField)
					positionX = (Integer)positionXTextField.getValue( );
				if (e.getSource()==positionYTextField)
					positionY = (Integer)positionYTextField.getValue( );
				positionImagePanel.setSelectedPoint( positionX, positionY );
				positionImagePanel.repaint( );
		}
	}
}
