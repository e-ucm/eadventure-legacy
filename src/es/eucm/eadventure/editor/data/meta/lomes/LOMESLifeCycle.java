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
package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMContribute;

public class LOMESLifeCycle {

    public static final String VERSION_DEFAULT = "V1.0";

    //2.1
    private LangString version;

    //2.2 
    private ArrayList<Vocabulary> status;

    //2.3 Contribute
    private LOMContribute contribute;

    public LOMESLifeCycle( ) {

        version = new LangString( VERSION_DEFAULT );
        contribute = null;
        status = new ArrayList<Vocabulary>( );

    }

    /** *********************************ADD METHODS ************************* */
    public void addVersion( LangString version ) {

        this.version = version;
    }

    public void addStatus( int index ) {

        this.status.add( new Vocabulary( Vocabulary.LC_STAUS_VALUE_2_2, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    /** ********************************* SETTERS ************************* */

    public void setStatus( int index ) {

        this.status = new ArrayList<Vocabulary>( );
        this.status.add( new Vocabulary( Vocabulary.LC_STAUS_VALUE_2_2, Vocabulary.LOM_ES_SOURCE, index ) );
    }

    public void setVersion( LangString version ) {

        this.version = version;
    }

    /** ********************************* GETTERS ************************* */
    //VERSION
    public LangString getVersion( ) {

        return version;
    }

    public Vocabulary getStatus( ) {

        return status.get( 0 );
    }

    //CONTRIBUTION
    /**
     * @return the contribute
     */
    public LOMContribute getContribute( ) {

        return contribute;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( ArrayList<Vocabulary> status ) {

        this.status = status;
    }

    /**
     * @param contribute
     *            the contribute to set
     */
    public void setContribute( LOMContribute contribute ) {

        this.contribute = contribute;
    }

}
