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
package es.eucm.eadventure.editor.data.meta.auxiliar;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMTaxonPath extends LOMESContainer {

    public LOMTaxonPath( ) {

        super( );
    }

    public LOMTaxonPath( LangString source, LOMTaxon taxon ) {

        this( );
        add( new LOMClassificationTaxonPath( source, taxon ) );
    }

    public void addTaxonPath( LangString source, LOMTaxon taxon ) {

        add( new LOMClassificationTaxonPath( source, taxon ) );
    }

    public void addTaxonPath( LangString source, LOMTaxon taxon, int index ) {

        if( index == 0 ) {
            add( new LOMClassificationTaxonPath( source, taxon ) );
        }
        else {
            delete( index - 1 );
            add( new LOMClassificationTaxonPath( source, taxon ), index - 1 );

        }
    }

    @Override
    public String[] elements( ) {

        String[] elements = new String[ container.size( ) ];
        for( int i = 0; i < container.size( ); i++ )
            elements[i] = ( (LOMClassificationTaxonPath) container.get( i ) ).getSource( ).getValue( 0 );
        return elements;
    }

    @Override
    public String getTitle( ) {

        return TC.get( "LOMES.ClassificationTaxonPath.DialogTitle" );
    }

    @Override
    public String[] attributes( ) {

        LOMESGeneralId generalId = new LOMESGeneralId( );
        return LOMESGeneralId.attributes( );
    }

}
