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

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that activates a flag
 */
public class ActivateEffect extends AbstractEffect implements HasTargetId {

    /**
     * Name of the flag to be activated
     */
    private String idFlag;

    /**
     * Creates a new Activate effect.
     * 
     * @param idFlag
     *            the id of the flag to be activated
     */
    public ActivateEffect( String idFlag ) {

        super( );
        this.idFlag = idFlag;
    }

    @Override
    public int getType( ) {

        return ACTIVATE;
    }

    /**
     * Returns the idFlag
     * 
     * @return String containing the idFlag
     */
    public String getTargetId( ) {

        return idFlag;
    }

    /**
     * Sets the new idFlag
     * 
     * @param idFlag
     *            New idFlag
     */
    public void setTargetId( String idFlag ) {

        this.idFlag = idFlag;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ActivateEffect ae = (ActivateEffect) super.clone( );
        ae.idFlag = ( idFlag != null ? new String( idFlag ) : null );
        return ae;
    }
}
