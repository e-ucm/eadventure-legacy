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
package es.eucm.eadventure.common.data.adventure;

public class CustomCursor implements Cloneable {

    private String type;

    private String path;

    /**
     * @return the type
     */
    public String getType( ) {

        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( String type ) {

        this.type = type;
    }

    /**
     * @return the path
     */
    public String getPath( ) {

        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath( String path ) {

        this.path = path;
    }

    /**
     * @param type
     * @param path
     */
    public CustomCursor( String type, String path ) {

        this.type = type;
        this.path = path;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        CustomCursor cc = (CustomCursor) super.clone( );
        cc.path = ( path != null ? new String( path ) : null );
        cc.type = ( type != null ? new String( type ) : null );
        return cc;
    }

}
