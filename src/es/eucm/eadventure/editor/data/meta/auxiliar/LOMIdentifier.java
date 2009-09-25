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
