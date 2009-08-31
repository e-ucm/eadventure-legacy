/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.data.meta;

import java.util.ArrayList;

public class LangString {

    private static final ArrayList<LangString> summary = new ArrayList<LangString>( );

    public static void updateLanguage( String lang ) {

        for( LangString ls : summary ) {
            ls.languages = new ArrayList<String>( );
            for( int i = 0; i < ls.getNValues( ); i++ ) {
                ls.languages.add( lang );
            }
        }
    }

    public static final String XXLAN = "XXlan";

    private ArrayList<String> languages;

    private ArrayList<String> values;

    public LangString( String lang, String value ) {

        languages = new ArrayList<String>( );
        values = new ArrayList<String>( );

        languages.add( lang );
        values.add( value );
    }

    public LangString( String value ) {

        languages = new ArrayList<String>( );
        values = new ArrayList<String>( );

        languages.add( XXLAN );
        values.add( value );
        summary.add( this );
    }

    /*public LangString(){
    	languages = new ArrayList<String>();
    	values = new ArrayList<String>();
    	summary.add( this );
    }*/

    public void addValue( String language, String value ) {

        languages.add( language );
        values.add( value );
    }

    public void addValue( String value ) {

        addValue( XXLAN, value );
    }

    public int getNValues( ) {

        return values.size( );
    }

    public int getNLanguages( ) {

        return languages.size( );
    }

    public String getValue( int i ) {

        return values.get( i );
    }

    public String getLanguage( int i ) {

        return languages.get( i );
    }

    public String getValue( String lang ) {

        for( int i = 0; i < languages.size( ); i++ ) {
            if( languages.get( i ).equals( lang ) )
                return values.get( i );
        }
        return null;
    }

    public void deleteValue( int index ) {

        values.remove( index );
    }

    /**
     * Changes the value for specified "index" value, keeping the previous
     * language
     * 
     * @param value
     * @param index
     */
    public void setValue( String value, int index ) {

        values.remove( index );
        values.add( index, value );

    }

    public void setLanguage( String lang, int index ) {

        languages.remove( index );
        languages.add( index, lang );

    }

}
