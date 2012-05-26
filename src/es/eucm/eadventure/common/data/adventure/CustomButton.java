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

import es.eucm.eadventure.common.data.HasSound;

public class CustomButton implements Cloneable, HasSound {

    private String type;

    private String path;

    private String action;

    /**
     * @return the action
     */
    public String getAction( ) {

        return action;
    }

    /**
     * @param action
     *            the action to set
     */
    public void setAction( String action ) {

        this.action = action;
    }

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
    public CustomButton( String action, String type, String path ) {

        this.action = action;
        this.type = type;
        this.path = path;
    }

    @Override
    public boolean equals( Object o ) {

        if( o == null || !( o instanceof CustomButton ) )
            return false;
        CustomButton button = (CustomButton) o;
        if( button.action.equals( action ) && button.type.equals( type )){
                return true;
        }
            
        return false;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        CustomButton cb = (CustomButton) super.clone( );
        cb.action = ( action != null ? new String( action ) : null );
        cb.path = ( path != null ? new String( path ) : null );
        cb.type = ( type != null ? new String( type ) : null );
        return cb;
    }

    public String getSoundPath( ) {
        return path;
    }

    public void setSoundPath( String soundPath ) {
        this.path = soundPath;
    }
}
