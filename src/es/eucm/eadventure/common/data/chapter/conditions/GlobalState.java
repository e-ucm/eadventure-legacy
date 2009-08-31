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
package es.eucm.eadventure.common.data.chapter.conditions;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.HasId;

/**
 * Group of conditions named with an Id, so it can be refered to in diverse
 * points of the chapter
 * 
 * @author Javier
 * 
 */
public class GlobalState extends Conditions implements Documented, HasId {

    /**
     * Id of the Conditions group
     */
    private String id;

    /**
     * Documentation (not used in game engine)
     */
    private String documentation;

    /**
     * Constructor
     */
    public GlobalState( String id ) {

        super( );
        this.id = id;
        this.documentation = new String( );
    }

    /**
     * @return the id
     */
    public String getId( ) {

        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId( String id ) {

        this.id = id;
    }

    /**
     * @return the documentation
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * @param documentation
     *            the documentation to set
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        GlobalState gs = (GlobalState) super.clone( );
        gs.documentation = ( documentation != null ? new String( documentation ) : null );
        gs.id = ( id != null ? new String( id ) : null );
        return gs;
    }
}
