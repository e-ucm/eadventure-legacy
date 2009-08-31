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
package es.eucm.eadventure.editor.control.controllers.metadata.lom;

import es.eucm.eadventure.editor.data.meta.lom.LOMLifeCycle;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.control.config.LOMConfigData;

public class LOMLifeCycleDataControl {

    public static final String GROUP = "lifeCicle";

    private LOMLifeCycle data;

    public LOMLifeCycleDataControl( LOMLifeCycle data ) {

        this.data = data;
    }

    public LOMTextDataControl getVersionController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getVersion( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setVersion( new LangString( text ) );
                LOMConfigData.storeData( GROUP, "version", text );
            }

        };
    }

    /**
     * @return the data
     */
    public LOMLifeCycle getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMLifeCycle data ) {

        this.data = data;
    }

}
