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
