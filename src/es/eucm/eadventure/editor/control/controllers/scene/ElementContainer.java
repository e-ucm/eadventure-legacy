/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
