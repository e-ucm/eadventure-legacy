/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.general.ChangePositionTool;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.PositionImagePanel;

public class PositionPanel extends JPanel implements Positioned {

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
    
    private NoEditableNumberSpinner scaleTextField;

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

    private boolean withScale = false;
    
    private float scale;
    
    
    /**
     * Constructor.
     * 
     * @param positionImagePanel
     *            Position image panel
     */
    public PositionPanel( PositionImagePanel positionImagePanel, int initialX, int initialY ) {

        this( null, positionImagePanel, initialX, initialY );
    }

    /**
     * Constructor.
     * 
     * @param positionImagePanel
     *            Position image panel
     */
    public PositionPanel( PositionImagePanel positionImagePanel, int initialX, int initialY, float scale ) {

        this( null, positionImagePanel, initialX, initialY, scale );
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

        createPanel();
    }

    /**
     * Constructor
     * 
     * @param positionPanelListener
     *            Listener for the changes on the position panel
     * @param positionImagePanel
     *            Position image panel
     */
    public PositionPanel( PositionPanelListener positionPanelListener, PositionImagePanel positionImagePanel, int initialX, int initialY , float scale) {

        // Set the panel
        this.positionPanelListener = positionPanelListener;
        this.positionImagePanel = positionImagePanel;
        this.positionX = initialX;
        this.positionY = initialY;
        this.scale = scale;
        this.withScale = true;
        
        createPanel();
    }

    private void createPanel() {
        // Set the layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Panel for the X coordinate
        JPanel xCoordinatePanel = new JPanel( );
        xCoordinatePanel.setLayout( new FlowLayout( ) );

        // Create and add the x position label
        xCoordinatePanel.add( new JLabel( TC.get( "SceneLocation.XCoordinate" ) ) );

        // Create and add the x position text field
        positionXTextField = new NoEditableNumberSpinner( positionX, -2000, 5000, 1 );
        positionXTextField.addChangeListener( new ChangeValueListener( ) );
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
        yCoordinatePanel.add( new JLabel( TC.get( "SceneLocation.YCoordinate" ) ));

        // Create and add the y position text field
        positionYTextField = new NoEditableNumberSpinner( positionY, -2000, 5000, 1 );
        positionYTextField.addChangeListener( new ChangeValueListener( ) );

        yCoordinatePanel.add( positionYTextField);

        // Add the Y coordinate panel
        c.gridx = 1;
        add( yCoordinatePanel, c );

        if (withScale) {
            JPanel scalePanel = new JPanel();
            scalePanel.setLayout( new FlowLayout() );
            scalePanel.add( new JLabel(TC.get( "SceneLocation.Scale" )), c);
            scaleTextField = new NoEditableNumberSpinner( scale, 0.1f, 5.0f, 0.1f);
            scaleTextField.addChangeListener( new ChangeValueListener());
            scalePanel.add(scaleTextField);
            c.gridx = 2;
            add( scalePanel, c);
        }
        
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
        positionImagePanel.setScale(scale);
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

        Controller.getInstance( ).addTool( new ChangePositionTool( this, x, y ) );
    }

    public void setPositionX( int x ) {

        positionX = x;
        positionXTextField.setValue( positionX );
    }

    public void setPositionY( int y ) {

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

        public void stateChanged( ChangeEvent e ) {

            if( e.getSource( ) == positionXTextField )
                positionX = (Integer) positionXTextField.getValue( );
            if( e.getSource( ) == positionYTextField )
                positionY = (Integer) positionYTextField.getValue( );
            if (e.getSource( ) == scaleTextField)
                scale = (Float) scaleTextField.getValue( );
            positionImagePanel.setSelectedPoint( positionX, positionY );
            positionImagePanel.setScale(scale);
            positionImagePanel.repaint( );
        }
    }

    public float getScale( ) {
        return scale;
    }
    
    public void setPositionImage(PositionImagePanel positionImagePanel) {
        this.positionImagePanel = positionImagePanel;
    }
}
