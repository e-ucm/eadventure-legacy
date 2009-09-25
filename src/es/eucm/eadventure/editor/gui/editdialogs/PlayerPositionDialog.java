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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.ElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;

/**
 * This class is the dialog to edit the insertion position for the scenes and
 * the next scene structures. It displays the scene graphically, so the user can
 * select a insertion point.
 * 
 * @author Bruno Torijano Bueno
 */
public class PlayerPositionDialog extends ToolManagableDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Panel to select and show the position.
     */
    private PositionPanel playerPositionPanel;

    /**
     * Constructor.
     * 
     * @param sceneId
     *            Identifier of the scene to display
     * @param positionX
     *            Initial X coordinate
     * @param positionY
     *            Initial Y coordinate
     */
    public PlayerPositionDialog( String sceneId, int positionX, int positionY ) {

        // Call the super method
        super( Controller.getInstance( ).peekWindow( ), TC.get( "PlayerPosition.Title" ) );//, Dialog.ModalityType.APPLICATION_MODAL );

        // Get the path to the scene image and the player
        String scenePath = Controller.getInstance( ).getSceneImagePath( sceneId );
        Trajectory trajectory = Controller.getInstance( ).getSceneTrajectory( sceneId );
        String playerPath = Controller.getInstance( ).getPlayerImagePath( );

        // Set the layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        // Create and add the panel
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;

        if( positionX > 5000 )
            positionX = 5000;
        if( positionX < -2000 )
            positionX = -2000;
        if( positionY > 5000 )
            positionY = 5000;
        if( positionY < -2000 )
            positionY = -2000;

        playerPositionPanel = new PositionPanel( new ElementImagePanel( scenePath, playerPath, trajectory ), positionX, positionY );
        playerPositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "PlayerPosition.PositionPanel" ) ) );
        //playerPositionPanel.setPosition( positionX, positionY );
        add( playerPositionPanel, c );

        // Set the dialog
        setResizable( false );
        setSize( 640, 480 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        setVisible( true );
    }

    /**
     * Returns X coordinate of the selected position
     * 
     * @return X coordinate of the selected position
     */
    public int getPositionX( ) {

        return playerPositionPanel.getPositionX( );
    }

    /**
     * Returns Y coordinate of the selected position
     * 
     * @return Y coordinate of the selected position
     */
    public int getPositionY( ) {

        return playerPositionPanel.getPositionY( );
    }

}
