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
package es.eucm.eadventure.editor.data.meta.ims;

import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class IMSRights {

    /*
     * cost and copyrightandotherrestrictions only can be "yes" or "no"
     */

    // 6.1
    private Vocabulary cost;

    // 6.2
    private Vocabulary copyrightandotherrestrictions;

    public IMSRights( ) {

        cost = new Vocabulary( Vocabulary.IMS_YES_NO );
        copyrightandotherrestrictions = new Vocabulary( Vocabulary.IMS_YES_NO );
        ;
    }

    public Vocabulary getCost( ) {

        return cost;
    }

    public void setCost( int index ) {

        this.cost.setValueIndex( index );
    }

    public Vocabulary getCopyrightandotherrestrictions( ) {

        return copyrightandotherrestrictions;
    }

    public void setCopyrightandotherrestrictions( int index ) {

        this.copyrightandotherrestrictions.setValueIndex( index );
    }

}
