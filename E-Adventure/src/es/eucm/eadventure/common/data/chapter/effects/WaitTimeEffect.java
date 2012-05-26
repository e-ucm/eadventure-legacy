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
package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect to wait some time without do nothing
 */
public class WaitTimeEffect extends AbstractEffect {

    /**
     * The time to wait without do nothing
     */
    private int time;

    /**
     * Constructor
     * 
     * @param time
     */
    public WaitTimeEffect( int time ) {

        super( );
        this.time = time;
    }

    /**
     * @return the time
     */
    public int getTime( ) {

        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime( int time ) {

        this.time = time;
    }

    /**
     * Return the effect type
     */
    @Override
    public int getType( ) {

        return WAIT_TIME;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        WaitTimeEffect wte = (WaitTimeEffect) super.clone( );
        wte.time = time;
        return wte;
    }

}
