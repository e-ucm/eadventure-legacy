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
package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.editor.data.meta.ims.IMSRights;

public class IMSRightsDataControl {

    public static final String[] AVAILABLE_OPTIONS = new String[] { "yes", "no" };

    private IMSRights data;

    public IMSRightsDataControl( IMSRights data ) {

        this.data = data;
    }

    public IMSOptionsDataControl getCost( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                return new String[] { "Yes", "No" };
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setCost( option );
                }

            }

            public int getSelectedOption( ) {

                for( int i = 0; i < AVAILABLE_OPTIONS.length; i++ ) {
                    if( AVAILABLE_OPTIONS[i].equals( data.getCost( ) ) )
                        return i;
                }
                return -1;
            }

        };
    }

    public IMSOptionsDataControl getCopyrightandotherrestrictions( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                return new String[] { "Yes", "No" };
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setCopyrightandotherrestrictions( option );
                }

            }

            public int getSelectedOption( ) {

                for( int i = 0; i < AVAILABLE_OPTIONS.length; i++ ) {
                    if( AVAILABLE_OPTIONS[i].equals( data.getCopyrightandotherrestrictions( ) ) )
                        return i;
                }
                return -1;
            }

        };
    }

    public IMSRights getData( ) {

        return data;
    }

    public void setData( IMSRights data ) {

        this.data = data;
    }

}
