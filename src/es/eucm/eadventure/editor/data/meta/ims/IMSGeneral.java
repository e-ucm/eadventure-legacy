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
package es.eucm.eadventure.editor.data.meta.ims;

import java.util.ArrayList;

import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSGeneral {

    //1.2
    private LangString title;

    //1.3.1	The catalogentry is the joint of next two attributes. 
    //		They must be taken at the same time.
    private ArrayList<String> catalog;

    //1.3.2
    private ArrayList<LangString> entry;

    //1.4
    private ArrayList<String> language;

    //1.5
    private ArrayList<LangString> description;

    //1.6
    private ArrayList<LangString> keyword;

    public IMSGeneral( ) {

        title = null;
        description = new ArrayList<LangString>( );
        keyword = new ArrayList<LangString>( );
        language = new ArrayList<String>( );
        catalog = new ArrayList<String>( );
        entry = new ArrayList<LangString>( );
    }

    /** *********************************ADD METHODS ************************* */
    public void addTitle( LangString title ) {

        this.title = title;
    }

    public void addCatalog( String catalog ) {

        this.catalog.add( catalog );

    }

    public void addEntry( LangString entry ) {

        this.entry.add( entry );
    }

    public void addDescription( LangString desc ) {

        this.description.add( desc );
    }

    public void addKeyword( LangString keyword ) {

        this.keyword.add( keyword );
    }

    public void addLanguage( String language ) {

        this.language.add( language );
    }

    /** ********************************* SETTERS ************************* */
    public void setTitle( LangString title ) {

        this.title = title;
    }

    public void setCalaog( String catalog ) {

        this.catalog = new ArrayList<String>( );

        this.catalog.add( catalog );

    }

    public void setEntry( LangString entry ) {

        this.entry = new ArrayList<LangString>( );
        this.entry.add( entry );
    }

    public void setDescription( LangString desc ) {

        description = new ArrayList<LangString>( );
        this.description.add( desc );
    }

    public void setKeyword( LangString keyword ) {

        this.keyword = new ArrayList<LangString>( );
        this.keyword.add( keyword );
    }

    public void setLanguage( String language ) {

        this.language = new ArrayList<String>( );
        this.language.add( language );
    }

    /** ********************************* GETTERS ************************* */
    //TITLE
    public LangString getTitle( ) {

        return title;
    }

    //CATALOGENTRY
    public String getCatalog( ) {

        return catalog.get( 0 );
    }

    public LangString getEntry( ) {

        return entry.get( 0 );
    }

    public String getCatalog( int i ) {

        return catalog.get( i );
    }

    public LangString getEntry( int i ) {

        return entry.get( i );
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

    //KEYWORD
    public LangString getKeyword( ) {

        return keyword.get( 0 );
    }

    public LangString getKeyword( int i ) {

        return keyword.get( i );
    }

    public int getNKeyword( ) {

        return keyword.size( );
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

}
