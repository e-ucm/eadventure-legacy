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

import es.eucm.eadventure.common.gui.TC;

public class LOMIdentifier extends LOMESContainer {

    //public static final String 

    public static final String CATALOG_DEFAULT = "Catálogo unificado mec-red.es-ccaa de identificación de ODE";

    //take care when this class is call from MetaMetaData: it must be added suffix "-meta" 
    public static final String ENTRY_DEFAULT = "es-ma_20090317_2_1300009";

    public boolean isFromMeta;

    public LOMIdentifier( boolean isFromMeta ) {

        super( );
        this.isFromMeta = isFromMeta;
        if( isFromMeta )
            add( new LOMESGeneralId( CATALOG_DEFAULT, ENTRY_DEFAULT + "-meta" ) );
        else
            add( new LOMESGeneralId( CATALOG_DEFAULT, ENTRY_DEFAULT ) );
    }

    public LOMIdentifier( String catalog, String entry, boolean isFromMeta ) {

        super( );
        this.isFromMeta = isFromMeta;
        add( new LOMESGeneralId( catalog, entry ) );
    }

    public void addIdentifier( String catalog, String entry ) {

        add( new LOMESGeneralId( catalog, entry ) );
    }

    public void addIdentifier( String catalog, String entry, int index ) {

        // index is related with the set of options in container dialog. The index 0 means add new element,
        // and the others index are index-1 because the selected index == 0 is reserved for "add new element" 
        if( index == 0 ) {
            add( new LOMESGeneralId( catalog, entry ) );
        }
        else {
            delete( index - 1 );
            add( new LOMESGeneralId( catalog, entry ), index - 1 );

        }
    }

    @Override
    public String[] elements( ) {

        String[] elements = new String[ container.size( ) ];
        for( int i = 0; i < container.size( ); i++ )
            elements[i] = ( (LOMESGeneralId) container.get( i ) ).getEntry( );
        return elements;
    }

    @Override
    public String getTitle( ) {

        return TC.get( "LOMES.GeneralIdentifier.DialogTitle" );
    }

    @Override
    public String[] attributes( ) {

        LOMESGeneralId generalId = new LOMESGeneralId( );
        return LOMESGeneralId.attributes( );
    }

}
