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
package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.gui.TC;

public abstract class Searchable {

    protected static HashMap<Searchable, List<String>> resultSet = new HashMap<Searchable, List<String>>( );

    protected static String searchedText;

    private static boolean caseSensitive;

    private static boolean fullMatch;

    public HashMap<Searchable, List<String>> search( String text, boolean caseSensitive, boolean fullMatch ) {

        resultSet.clear( );
        if( caseSensitive )
            Searchable.searchedText = text;
        else
            Searchable.searchedText = text.toLowerCase( );
        Searchable.caseSensitive = caseSensitive;
        Searchable.fullMatch = fullMatch;
        this.recursiveSearch( );
        return Searchable.resultSet;
    }

    public abstract void recursiveSearch( );

    protected void addResult( String where ) {

        List<String> places = resultSet.get( this );
        if( places == null ) {
            places = new ArrayList<String>( );
            resultSet.put( this, places );
        }
        if( !places.contains( where ) )
            places.add( where );
    }

    protected void check( String value, String desc ) {

        if( value != null ) {
            if( !fullMatch ) {
                if( !caseSensitive && value.toLowerCase( ).contains( searchedText ) )
                    addResult( desc );
                else if( caseSensitive && value.contains( searchedText ) )
                    addResult( desc );
            }
            else {
                if( !caseSensitive && value.toLowerCase( ).equals( searchedText ) )
                    addResult( desc );
                else if( caseSensitive && value.equals( searchedText ) )
                    ;
            }
        }
    }

    protected void check( ConditionsController conditions, String desc ) {

        for( int i = 0; i < conditions.getBlocksCount( ); i++ ) {
            for( int j = 0; j < conditions.getConditionCount( i ); j++ ) {
                HashMap<String, String> properties = conditions.getCondition( i, j );
                if( properties.containsKey( ConditionsController.CONDITION_ID ) )
                    check( properties.get( ConditionsController.CONDITION_ID ), desc + " (ID)" );
                if( properties.containsKey( ConditionsController.CONDITION_STATE ) )
                    check( properties.get( ConditionsController.CONDITION_STATE ), desc + " (" + TC.get( "Search.State" ) + ")" );
                if( properties.containsKey( ConditionsController.CONDITION_VALUE ) )
                    check( properties.get( ConditionsController.CONDITION_VALUE ), desc + " (" + TC.get( "Search.Value" ) + ")" );
            }
        }
    }

    protected abstract List<Searchable> getPath( Searchable dataControl );

    protected List<Searchable> getPathFromChild( Searchable dataControl, DataControl child ) {

        if( child != null ) {
            List<Searchable> path = child.getPath( dataControl );
            if( path != null ) {
                path.add( this );
                return path;
            }
        }
        return null;
    }

    @SuppressWarnings ( "unchecked")
    protected List<Searchable> getPathFromChild( Searchable dataControl, List list ) {

        for( Object temp : list ) {
            List<Searchable> path = ( (Searchable) temp ).getPath( dataControl );
            if( path != null ) {
                path.add( this );
                return path;
            }
        }
        return null;
    }

    protected List<Searchable> getPathFromSearchableChild( Searchable dataControl, Searchable child ) {

        if( child != null ) {
            List<Searchable> path = child.getPath( dataControl );
            if( path != null ) {
                path.add( this );
                return path;
            }
        }
        return null;
    }

}
