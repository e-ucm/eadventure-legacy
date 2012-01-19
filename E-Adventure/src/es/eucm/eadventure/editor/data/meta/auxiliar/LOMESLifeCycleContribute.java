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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESLifeCycleContribute implements LOMESComposeType {

    public static final int NUMBER_ATTR = 3;

    private static final int numberRoleLifecycleValues = 14;

    private static final int numberRoleMetametaValues = 2;

    //2.3.1
    private Vocabulary role;

    //2.3.2 
    private ArrayList<String> entity;

    private LOMESLifeCycleDate date;

    public LOMESLifeCycleContribute( String[] roleValues ) {

        role = new Vocabulary( roleValues, Vocabulary.LOM_ES_SOURCE, 0 );
        entity = new ArrayList<String>( );
        entity.add( new String( "Empty" ) );
        date = new LOMESLifeCycleDate( );
    }

    public LOMESLifeCycleContribute( Vocabulary role, ArrayList<String> entity, LOMESLifeCycleDate date ) {

        this.role = role;
        this.entity = entity;
        this.date = date;
    }

    public Vocabulary getRole( ) {

        return role;
    }

    public void setRole( Vocabulary role ) {

        this.role = role;
    }

    public ArrayList<String> getEntity( ) {

        return entity;
    }

    public void setEntity( ArrayList<String> entity ) {

        this.entity = entity;
    }

    public static String[] attributes( ) {

        String[] attr = new String[ NUMBER_ATTR ];
        attr[0] = TC.get( "LOMES.GeneralId.CatalogName" ) + " " + ATTR_STRING;
        attr[0] = TC.get( "LOMES.GeneralId.EntryName" ) + " " + ATTR_STRING;
        return attr;
    }

    /**
     * @return the date
     */
    public LOMESLifeCycleDate getDate( ) {

        return date;
    }

    /**
     * @param date
     *            the date to set
     */
    public void setDate( LOMESLifeCycleDate date ) {

        this.date = date;
    }

    public String getTitle( ) {

        // TODO Auto-generated method stub
        return null;
    }

    public static String[] getRoleLifeCycleVocabularyType( ) {

        return Vocabulary.LC_CONTRIBUTION_TYPE_2_3_1;
    }

    public static String[] getRoleMetametaVocabularyType( ) {

        return Vocabulary.MD_CONTRIBUTION_TYPE_2_3_1;
    }

    public static String[] getRoleLifeCycleOptions( ) {

        String[] options = new String[ numberRoleLifecycleValues ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.LifeCycle.Role" + i );
        }
        return options;
    }

    public static String[] getRoleMetametaOptions( ) {

        String[] options = new String[ numberRoleMetametaValues ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Metameta.Role" + i );
        }
        return options;
    }

}
