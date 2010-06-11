/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;

/**
 * Summary of the items in the adventure
 */
public class AtrezzoSummary implements Serializable {

    /**
     * Required by Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of normal atrezzo items
     */
    private ArrayList<String> normalAtrezzoItems;

    /**
     * Default constructor
     * 
     * @param items
     *            List of items, which will be stored as normal items
     */
    public AtrezzoSummary( List<Atrezzo> atrezzoItems ) {

        normalAtrezzoItems = new ArrayList<String>( );

        for( Atrezzo atrezzo : atrezzoItems ) {
            normalAtrezzoItems.add( atrezzo.getId( ) );
        }
    }

}
