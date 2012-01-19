/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.Documented;

/**
 * Group of effects named with an Id, so it can be refered to in diverse points
 * of the chapter
 * 
 * @author Javier
 * 
 */
public class Macro extends Effects implements Documented {

    /**
     * Id of the Effects group
     */
    private String id;

    /**
     * Documentation (not used in game engine)
     */
    private String documentation;

    /**
     * Constructor
     */
    public Macro( String id ) {

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

        Macro m = (Macro) super.clone( );
        m.documentation = ( documentation != null ? new String( documentation ) : null );
        m.id = ( id != null ? new String( id ) : null );
        return m;
    }
}
