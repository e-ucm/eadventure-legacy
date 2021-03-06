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
package es.eucm.eadventure.editor.data.meta.lomes;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESGeneralId;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;

public class LOMESGeneral {

    //1.1 Identifier
    private LOMIdentifier identifier;

    //1.2
    private LangString title;

    //1.3
    private ArrayList<String> language;

    //1.4
    private ArrayList<LangString> description;

    //1.5 
    private ArrayList<LangString> keyword;

    //1.8 
    private Vocabulary aggregationLevel;

    public LOMESGeneral( ) {

        title = null;
        language = new ArrayList<String>( );
        description = new ArrayList<LangString>( );
        keyword = new ArrayList<LangString>( );
        aggregationLevel = new Vocabulary( Vocabulary.GE_AGGREGATION_LEVEL_1_8, Vocabulary.LOM_ES_SOURCE, 0 );
        identifier = new LOMIdentifier( false );
    }

    /** *********************************ADD METHODS ************************* */
    public void addTitle( LangString title ) {

        this.title = title;
    }

    public void addLanguage( String language ) {

        this.language.add( language );
    }

    public void addDescription( LangString desc ) {

        this.description.add( desc );
    }

    public void addKeyword( LangString keyword ) {

        this.keyword.add( keyword );
    }

    public void addIdentifier( String catalog, String entry ) {

        identifier.addIdentifier( catalog, entry );
    }

    /** ********************************* SETTERS ************************* */
    public void setTitle( LangString title ) {

        this.title = title;
    }

    public void setLanguage( String language ) {

        this.language = new ArrayList<String>( );
        this.language.add( language );
    }

    public void setDescription( LangString desc ) {

        description = new ArrayList<LangString>( );
        this.description.add( desc );
    }

    public void setKeyword( LangString keyword ) {

        this.keyword = new ArrayList<LangString>( );
        this.keyword.add( keyword );
    }

    public void setAggregationLevel( int index ) {

        this.aggregationLevel.setValueIndex( index );
    }

    /** ********************************* GETTERS ************************* */

    //IDENTIFIER
    public LOMESGeneralId getIdentifier( int index ) {

        return (LOMESGeneralId) identifier.get( index );
    }

    public int getNIdentifier( ) {

        return identifier.getSize( );
    }

    /**
     * @return the identifier
     */
    public LOMIdentifier getIdentifier( ) {

        return identifier;
    }

    //TITLE
    public LangString getTitle( ) {

        return title;
    }

    //LANGUAGE
    public String getLanguage( ) {

        return language.get( 0 );
    }

    public String getLanguage( int i ) {

        return language.get( i );
    }

    public int getNLanguage( ) {

        return language.size( );
    }

    public ArrayList<String> getLanguages( ) {

        return language;
    }

    //DESCRIPTION
    public LangString getDescription( ) {

        return description.get( 0 );
    }

    public LangString getDescription( int i ) {

        return description.get( i );
    }

    public int getNDescription( ) {

        return description.size( );
    }

    public ArrayList<LangString> getDescriptions( ) {

        return description;
    }

    //KEYWORD
    public LangString getKeyword( ) {

        return keyword.get( 0 );
    }

    public LangString getKeyword( int i ) {

        return keyword.get( i );
    }

    public ArrayList<LangString> getKeywords( ) {

        return keyword;
    }

    public String[] keywordsToString( ) {

        String[] keyw = new String[ keyword.size( ) ];
        for( int i = 0; i < keyword.size( ); i++ )
            keyw[i] = keyword.get( i ).getValue( 0 );
        return keyw;
    }

    public int getNKeyword( ) {

        return keyword.size( );
    }

    public void deleteKey( int index ) {

    }

    public Vocabulary getAggregationLevel( ) {

        return this.aggregationLevel;
    }

}
