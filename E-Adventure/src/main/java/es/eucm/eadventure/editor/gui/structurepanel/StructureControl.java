/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.structurepanel;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;

/**
 * This class is a Singleton that is used to control the flow of the "Structure
 * Panel" at the right of the editor.
 * <p>
 * 
 * 
 * @author Eugenio Marchiori
 * 
 */
public class StructureControl {

    /**
     * The instance of the Singleton class
     */
    static private StructureControl instance;

    /**
     * Get the instance of the class
     * 
     * @return
     */
    static public StructureControl getInstance( ) {

        if( instance == null )
            instance = new StructureControl( );
        return instance;
    }

    /**
     * Stores the present structure panel of the project
     */
    private StructurePanel structurePanel;

    /**
     * Stores the previous structure panel of the project
     */
    private StructurePanel pastStructure;

    /**
     * Stores the back list of visited dataControls
     */
    private List<DataControl> backList;

    /**
     * Stores the forward list of visited dataControls
     */
    private List<DataControl> forwardList;

    /**
     * Default constructor, initializes the lists
     */
    private StructureControl( ) {

        backList = new ArrayList<DataControl>( );
        forwardList = new ArrayList<DataControl>( );
    }

    /**
     * Set the current StructurePanel of the project
     * 
     * @param structurePanel
     *            The new StructurePanel
     */
    public void setStructurePanel( StructurePanel structurePanel ) {

        if( structurePanel instanceof EffectsStructurePanel )
            pastStructure = structurePanel;
        this.structurePanel = structurePanel;
    }

    /**
     * Resets the StructurePanel to the previous one
     */
    public void resetStructurePanel( ) {

        structurePanel = pastStructure;
    }

    /**
     * Change the current element shown in the editor to a new Searchable
     * element.
     * <p>
     * 
     * @param dataControl
     */
    public void changeDataControl( Searchable dataControl ) {

        if( dataControl != null ) {
            List<Searchable> path = Controller.getInstance( ).getSelectedChapterDataControl( ).getPath( dataControl );
            if( path != null )
                structurePanel.setSelectedItem( path );
        }
    }

    /**
     * Add the dataControl to the list of visits as needed.
     * 
     * @param dataControl
     *            The visted dataControl
     */
    public void visitDataControl( DataControl dataControl ) {

        if( backList.size( ) > 0 ) {
            if( dataControl != backList.get( backList.size( ) - 1 ) )
                backList.add( dataControl );
        }
        else
            backList.add( dataControl );
        forwardList.clear( );
    }

    /**
     * Go back in the list of visited tree nodes (if possible)
     */
    public void goBack( ) {

        if( backList.size( ) > 1 ) {
            DataControl temp = backList.remove( backList.size( ) - 1 );
            DataControl temp2 = backList.get( backList.size( ) - 1 );
            List<DataControl> tempBack = new ArrayList<DataControl>( );
            tempBack.addAll( backList );
            List<DataControl> tempFwrd = new ArrayList<DataControl>( );
            tempFwrd.addAll( forwardList );
            changeDataControl( temp2 );
            backList = tempBack;
            forwardList = tempFwrd;
            forwardList.add( temp );
        }
    }

    /**
     * Go forward in the list of visited tree nodes (if possible)
     */
    public void goForward( ) {

        if( forwardList.size( ) > 0 ) {
            DataControl temp = forwardList.get( forwardList.size( ) - 1 );
            forwardList.remove( temp );
            List<DataControl> tempBack = new ArrayList<DataControl>( );
            tempBack.addAll( backList );
            List<DataControl> tempFwrd = new ArrayList<DataControl>( );
            tempFwrd.addAll( forwardList );
            changeDataControl( temp );
            backList = tempBack;
            backList.add( temp );
            forwardList = tempFwrd;
        }
    }

}
