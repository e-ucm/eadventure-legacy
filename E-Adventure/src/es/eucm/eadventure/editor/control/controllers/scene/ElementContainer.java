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
package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Image;

import es.eucm.eadventure.editor.control.Controller;

/**
 * This class can contain either an ElementReferenceDataControl or its
 * equivalent for the player
 */
public class ElementContainer {

    private ElementReferenceDataControl erdc;

    private int playerLayer;

    private Image image;

    /**
     * Constructor. When erdc has value, takes the element reference data
     * control for references to atrezzo, items or non-player characters,
     * putting player layer with its non-valid value. Takes playerLayer for
     * player, when erdc is null.
     * 
     * @param erdc
     *            the element reference data control
     * @param playerLayer
     *            the layer to show the player in the correct position
     * @param image
     *            the image of the player
     */
    public ElementContainer( ElementReferenceDataControl erdc, int playerLayer, Image image ) {

        if( erdc != null ) {
            this.erdc = erdc;
            this.playerLayer = -1;
            this.image = null;
        }
        else {
            this.playerLayer = playerLayer;
            this.image = image;
        }
    }

    /**
     * Change the image
     * 
     * @param image
     *            the new image
     */
    public void setImage( Image image ) {

        Controller.getInstance( ).dataModified( );
        this.image = image;
    }

    /**
     * Return the layer, checking if it is a player or not.
     * 
     * @return the layer.
     */
    public int getLayer( ) {

        if( erdc == null )
            return playerLayer;
        else
            return erdc.getElementReference( ).getLayer( );
    }

    /**
     * Return the y position, checking if it is a player or not.
     * 
     * @return the y position.
     */
    public int getY( ) {

        if( erdc == null )
            return playerLayer;
        else
            return erdc.getElementReference( ).getY( );
    }

    /**
     * Change the layer, checking if it is a player or not.
     * 
     * @param layer
     *            the new layer.
     */
    public void setLayer( int layer ) {

        Controller.getInstance( ).dataModified( );
        if( erdc == null )
            playerLayer = layer;
        else
            erdc.getElementReference( ).setLayer( layer );
    }

    /**
     * Check if contains a player
     * 
     * @return true, if contains a player.
     */
    public boolean isPlayer( ) {

        return erdc == null;
    }

    public int getPlayerLayer( ) {

        return playerLayer;
    }

    public ElementReferenceDataControl getErdc( ) {

        return erdc;
    }

    public Image getImage( ) {

        return image;
    }

    public boolean isVisible( ) {

        if( erdc == null ) {
            return true;
        }
        return this.erdc.isVisible( );
    }

    public void setVisible( boolean visible ) {

        if( erdc != null ) {
            Controller.getInstance( ).dataModified( );
            this.erdc.setVisible( visible );
        }
    }

}
