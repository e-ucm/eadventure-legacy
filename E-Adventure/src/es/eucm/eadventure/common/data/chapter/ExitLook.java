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
package es.eucm.eadventure.common.data.chapter;

public class ExitLook implements Cloneable {

    private String exitText;

    private String cursorPath;

    public ExitLook( ) {

        exitText = "";
        cursorPath = null;
    }

    /**
     * @return the exitText
     */
    public String getExitText( ) {

        return exitText;
    }

    /**
     * @param exitText
     *            the exitText to set
     */
    public void setExitText( String exitText ) {

        this.exitText = exitText;
    }

    /**
     * @return the cursorPath
     */
    public String getCursorPath( ) {

        return cursorPath;
    }

    /**
     * @param cursorPath
     *            the cursorPath to set
     */
    public void setCursorPath( String cursorPath ) {

        this.cursorPath = cursorPath;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ExitLook el = (ExitLook) super.clone( );
        el.cursorPath = ( cursorPath != null ? new String( cursorPath ) : null );
        el.exitText = ( exitText != null ? new String( exitText ) : null );
        return el;
    }

}
