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

import java.util.List;

import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.ChangeRectangleValueTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class InfluenceAreaDataControl extends DataControl {

    /**
     * Scene controller that contains this element reference (used to extract
     * the id of the scene).
     */
    private SceneDataControl sceneDataControl;

    /**
     * Contained influenceArea.
     */
    private InfluenceArea influenceArea;

    private DataControl dataControl;

    /**
     * Constructor.
     * 
     * @param sceneDataControl
     *            Parent scene controller
     * @param dataControl
     * @param activeArea
     *            Exit of the data control structure
     */
    public InfluenceAreaDataControl( SceneDataControl sceneDataControl, InfluenceArea influenceArea, DataControl dataControl ) {

        this.sceneDataControl = sceneDataControl;
        this.influenceArea = influenceArea;
        this.dataControl = dataControl;
    }

    /**
     * Returns the id of the scene that contains this element reference.
     * 
     * @return Parent scene id
     */
    public String getParentSceneId( ) {

        return sceneDataControl.getId( );
    }

    /**
     * Returns the X coordinate of the upper left position of the influenceArea.
     * 
     * @return X coordinate of the upper left point
     */
    public int getX( ) {

        return influenceArea.getX( );
    }

    /**
     * Returns the Y coordinate of the upper left position of the influenceArea.
     * 
     * @return Y coordinate of the upper left point
     */
    public int getY( ) {

        return influenceArea.getY( );
    }

    /**
     * Returns the width of the influenceArea.
     * 
     * @return Width of the influenceArea
     */
    public int getWidth( ) {

        return influenceArea.getWidth( );
    }

    /**
     * Returns the height of the influenceArea.
     * 
     * @return Height of the influenceArea
     */
    public int getHeight( ) {

        return influenceArea.getHeight( );
    }

    /**
     * Sets the new values for the influenceArea.
     * 
     * @param x
     *            X coordinate of the upper left point
     * @param y
     *            Y coordinate of the upper left point
     * @param width
     *            Width of the influenceArea area
     * @param height
     *            Height of the influenceArea area
     */
    public void setInfluenceArea( int x, int y, int width, int height ) {

        influenceArea.setExists( true );
        controller.addTool( new ChangeRectangleValueTool( influenceArea, x, y, width, height ) );
    }

    public void setInfluenceArea( InfluenceArea influenceArea ) {

        this.influenceArea = influenceArea;
        this.influenceArea.setExists( true );
    }

    @Override
    public Object getContent( ) {

        return influenceArea;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;
        return elementAdded;
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;
        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

    }

    @Override
    public int countIdentifierReferences( String id ) {

        return 0;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

    }

    @Override
    public void deleteIdentifierReferences( String id ) {

    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    public boolean hasInfluenceArea( ) {

        return influenceArea.isExists( );
    }

    public DataControl getDataControl( ) {

        return dataControl;
    }

    public void referenceScaleChanged( int incrementX, int incrementY ) {

        if( influenceArea.isExists( ) ) {
            controller.dataModified( );
            influenceArea.setWidth( influenceArea.getWidth( ) + incrementX );
            influenceArea.setHeight( influenceArea.getHeight( ) + incrementY );
        }
    }

    @Override
    public void recursiveSearch( ) {

    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }

}
