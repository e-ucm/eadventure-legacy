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
package es.eucm.eadventure.editor.control.controllers.metadata.lom;

import es.eucm.eadventure.editor.data.meta.lom.LOMTechnical;
import es.eucm.eadventure.editor.control.config.LOMConfigData;

public class LOMTechnicalDataControl {

    public static final String GROUP = "technical";

    private LOMTechnical data;

    public LOMTechnicalDataControl( LOMTechnical data ) {

        this.data = data;
    }

    public LOMTextDataControl getMaximumVersionController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getMaximumVersion( );
            }

            public void setText( String text ) {

                data.setMaximumVersion( text );
                LOMConfigData.storeData( GROUP, "maximumVersion", text );
            }

        };
    }

    public LOMTextDataControl getMinimumVersionController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getMinimumVersion( );
            }

            public void setText( String text ) {

                data.setMinimumVersion( text );
                LOMConfigData.storeData( GROUP, "minimumVersion", text );
            }

        };
    }

    /**
     * @return the data
     */
    public LOMTechnical getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMTechnical data ) {

        this.data = data;
    }

}
