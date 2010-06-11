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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMContribute extends LOMESContainer {

    public LOMContribute( String[] roleValues ) {

        super( );
        add( new LOMESLifeCycleContribute( roleValues ) );

    }

    public LOMContribute( Vocabulary role, ArrayList<String> entity, LOMESLifeCycleDate date ) {

        super( );
        add( new LOMESLifeCycleContribute( role, entity, date ) );
    }

    public void addContribute( Vocabulary role, ArrayList<String> entity, LOMESLifeCycleDate date ) {

        add( new LOMESLifeCycleContribute( role, entity, date ) );
    }

    public void addContribute( Vocabulary role, ArrayList<String> entity, LOMESLifeCycleDate date, int index ) {

        if( index == 0 ) {
            add( new LOMESLifeCycleContribute( role, entity, date ) );
        }
        else {
            delete( index - 1 );
            add( new LOMESLifeCycleContribute( role, entity, date ), index - 1 );

        }
    }

    @Override
    public String[] elements( ) {

        String[] elements = new String[ container.size( ) ];
        for( int i = 0; i < container.size( ); i++ )
            elements[i] = ( (LOMESLifeCycleContribute) container.get( i ) ).getRole( ).getValue( );
        return elements;
    }

    @Override
    public String getTitle( ) {

        return TC.get( "LOMES.LCContribute.DialogTitle" );
    }

    @Override
    public String[] attributes( ) {

        return LOMESLifeCycleContribute.attributes( );
    }

}
