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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

public abstract class LOMESContainer {

    protected int currentElement;

    protected ArrayList<LOMESComposeType> container;

    protected LOMESContainer( ) {

        container = new ArrayList<LOMESComposeType>( );
    }

    /**
     * The title for create element dialog
     * 
     * @return
     */
    public abstract String getTitle( );

    /**
     * Returns an String representation of each element in container
     * 
     * @return
     */
    public abstract String[] elements( );

    /**
     * Returns each attributes type.
     * 
     * @return
     */
    public abstract String[] attributes( );

    public void add( LOMESComposeType newComponent ) {

        container.add( newComponent );
    }

    public void add( LOMESComposeType newComponent, int index ) {

        container.add( index, newComponent );
    }

    public void delete( int index ) {

        container.remove( index );
    }

    public LOMESComposeType get( int index ) {

        return container.get( index );
    }

    public int getSize( ) {

        return container.size( );
    }

    public LOMESComposeType getCurrentElement( ) {

        return container.get( currentElement );
    }

    public void setCurrentElement( int currentElement ) {

        this.currentElement = currentElement;
    }

}
