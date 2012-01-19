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
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESLifeCycleDate implements LOMESComposeType {

    //2.3.3 Date; It has 2 values, dateTime and description
    private String dateTime;

    private LangString description;

    public LOMESLifeCycleDate( ) {

        dateTime = new String( "1970-12-02T05:01:14.338Z" );
        description = new LangString( "Empty" );
    }

    public LOMESLifeCycleDate( String dateTime, LangString description ) {

        this.dateTime = dateTime;
        this.description = description;
    }

    public String getDateTime( ) {

        return dateTime;
    }

    public void setDateTime( String dateTime ) {

        this.dateTime = dateTime;
    }

    public LangString getDescription( ) {

        return description;
    }

    public void setDescription( LangString description ) {

        this.description = description;
    }

    public static String[] attributes( ) {

        String[] attr = new String[ LOMESGeneralId.NUMBER_ATTR ];
        attr[0] = TC.get( "LOMES.Date.DateTimeName" ) + " " + ATTR_STRING;
        attr[0] = TC.get( "LOMES.Date.Description" ) + " " + ATTR_STRING;
        return attr;
    }

    public String getTitle( ) {

        return TC.get( "LOMES.LifeCycle.DateTitle" );
    }

}
