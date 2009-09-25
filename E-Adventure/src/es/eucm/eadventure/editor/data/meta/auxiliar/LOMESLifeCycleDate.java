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
