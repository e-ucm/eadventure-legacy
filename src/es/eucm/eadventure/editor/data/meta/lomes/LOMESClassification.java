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
package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxonPath;

public class LOMESClassification {

    // 9.1
    private Vocabulary purpose;

    //9.2 Taxon  Path
    private LOMTaxonPath taxonPath;

    // 9.3
    private LangString description;

    // 9.4
    private ArrayList<LangString> keyword;

    public LOMESClassification( ) {

        purpose = new Vocabulary( Vocabulary.LOMES_CL_PURPOSE_9_1, Vocabulary.LOM_ES_SOURCE, 0 );
        description = null;
        keyword = new ArrayList<LangString>( );
        taxonPath = new LOMTaxonPath( );

    }

    public void addKeyword( LangString keyword ) {

        this.keyword.add( keyword );
    }

    public void setKeyword( LangString keyword ) {

        this.keyword = new ArrayList<LangString>( );
        this.keyword.add( keyword );
    }

    public LangString getKeyword( ) {

        return keyword.get( 0 );
    }

    public ArrayList<LangString> getKeywords( ) {

        return keyword;
    }

    public LangString getKeyword( int i ) {

        return keyword.get( i );
    }

    public int getNKeyword( ) {

        return keyword.size( );
    }

    public Vocabulary getPurpose( ) {

        return purpose;
    }

    public void setPurpose( int index ) {

        this.purpose.setValueIndex( index );

    }

    public LangString getDescription( ) {

        return description;
    }

    public void setDescription( LangString description ) {

        this.description = description;
    }

    public void setKeyword( ArrayList<LangString> keyword ) {

        this.keyword = keyword;
    }

    public void setPurpose( Vocabulary purpose ) {

        this.purpose = purpose;
    }

    /**
     * @return the taxonPath
     */
    public LOMTaxonPath getTaxonPath( ) {

        return taxonPath;
    }

    /**
     * @param taxonPath
     *            the taxonPath to set
     */
    public void setTaxonPath( LOMTaxonPath taxonPath ) {

        this.taxonPath = taxonPath;
    }

}
