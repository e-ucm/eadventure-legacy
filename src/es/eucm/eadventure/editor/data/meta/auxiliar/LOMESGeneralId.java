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

import es.eucm.eadventure.common.gui.TC;

public class LOMESGeneralId implements LOMESComposeType {

    public static final int NUMBER_ATTR = 2;

    //1.1.1
    private String catalog;

    private String entry;

    public LOMESGeneralId( ) {

        this.catalog = null;
        this.entry = null;
    }

    public LOMESGeneralId( String catalog, String entry ) {

        this.catalog = catalog;
        this.entry = entry;
    }

    public String getCatalog( ) {

        return catalog;
    }

    public void setCatalog( String catalog ) {

        this.catalog = catalog;
    }

    public String getEntry( ) {

        return entry;
    }

    public void setEntry( String entry ) {

        this.entry = entry;
    }

    public static String[] attributes( ) {

        String[] attr = new String[ NUMBER_ATTR ];
        attr[0] = TC.get( "LOMES.GeneralId.CatalogName" ) + " " + ATTR_STRING;
        attr[0] = TC.get( "LOMES.GeneralId.EntryName" ) + " " + ATTR_STRING;
        return attr;
    }

    public String getTitle( ) {

        // TODO Auto-generated method stub
        return null;
    }

}
