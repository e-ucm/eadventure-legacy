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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.Dimension;
import javax.swing.BorderFactory;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class SceneLooksPanel extends LooksPanel {

    private SceneDataControl sceneDataControl;

    /**
     * Panel for the preview.
     */
    private ScenePreviewEditionPanel scenePreviewEditionPanel;

    public SceneLooksPanel( SceneDataControl control ) {

        super( control );
        // TODO Parche, arreglar
        lookPanel.setPreferredSize( new Dimension( 0, 600 ) );

    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void createPreview( ) {

        this.sceneDataControl = (SceneDataControl) this.dataControl;

        // Take the path of the background
        String scenePath = sceneDataControl.getPreviewBackground( );

        scenePreviewEditionPanel = new ScenePreviewEditionPanel( false, scenePath );
        scenePreviewEditionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Scene.Preview" ) ) );

        // Add the item references first
        for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList( ).getItemReferences( ) ) {
            scenePreviewEditionPanel.addElement( ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference );
        }

        // Add then the character references
        for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList( ).getNPCReferences( ) ) {
            scenePreviewEditionPanel.addElement( ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference );
        }

        // Add the atrezzo item references first
        for( ElementReferenceDataControl elementReference : sceneDataControl.getReferencesList( ).getAtrezzoReferences( ) ) {
            scenePreviewEditionPanel.addElement( ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference );
        }
        if( sceneDataControl.getTrajectory( ).hasTrajectory( ) ) {
            scenePreviewEditionPanel.setTrajectory( (Trajectory) sceneDataControl.getTrajectory( ).getContent( ) );
            for( NodeDataControl nodeDataControl : sceneDataControl.getTrajectory( ).getNodes( ) )
                scenePreviewEditionPanel.addNode( nodeDataControl );
            scenePreviewEditionPanel.setShowInfluenceArea( true );
        }
        else

        // Deleted the checking if player has layer
        if( !Controller.getInstance( ).isPlayTransparent( ) /*&& sceneDataControl.isAllowPlayer()*/)
            //scenePreviewEditionPanel.addPlayer(sceneDataControl, sceneDataControl.getReferencesList().getPlayerImage());
            scenePreviewEditionPanel.addPlayer( sceneDataControl, AssetsController.getImage( Controller.getInstance( ).getPlayerImagePath( ) ) );

        scenePreviewEditionPanel.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_OBJECT, false );
        scenePreviewEditionPanel.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_CHARACTER, false );
        scenePreviewEditionPanel.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_ATREZZO, false );
        scenePreviewEditionPanel.setMovableCategory( ScenePreviewEditionPanel.CATEGORY_PLAYER, false );
        lookPanel.add( scenePreviewEditionPanel, cLook );
        //resourcesPanel.setPreviewUpdater( this );

    }

    public void addPlayer( ) {

        // Deleted the checking if player has layer
        if( !Controller.getInstance( ).isPlayTransparent( )/* && sceneDataControl.isAllowPlayer()*/)
            scenePreviewEditionPanel.addPlayer( sceneDataControl, sceneDataControl.getReferencesList( ).getPlayerImage( ) );
        scenePreviewEditionPanel.repaint( );
    }

    @Override
    public void updatePreview( ) {

        if( scenePreviewEditionPanel != null ) {
            System.out.println( sceneDataControl.getPreviewBackground( ) );
            scenePreviewEditionPanel.loadBackground( sceneDataControl.getPreviewBackground( ) );
            System.out.println( "BG UPDATED!!" );
        }
        if( getParent( ) != null && getParent( ).getParent( ) != null )
            getParent( ).getParent( ).repaint( );
    }

    @Override
    public void updateResources( ) {

        super.updateResources( );
        if( getParent( ) != null && getParent( ).getParent( ) != null )
            getParent( ).getParent( ).repaint( );
    }

    public SceneDataControl getSceneDataControl( ) {

        return sceneDataControl;
    }

}
