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
package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESMetaMetaData;

public class LOMESMetaMetaDataControl {

    private LOMESMetaMetaData data;

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    public LOMESMetaMetaDataControl( LOMESMetaMetaData data ) {

        this.data = data;
    }

    public LOMIdentifier getIdentifier( ) {

        return data.getIdentifier( );
    }

    public LOMESContainer getContribute( ) {

        return data.getContribute( );
    }

    public LOMESTextDataControl getDescription( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getDescription( );
            }

            public void setText( String text ) {

                data.setDescription( text );
            }

        };
    }

    public LOMESOptionsDataControl getLanguageController( ) {

        return new LOMESOptionsDataControl( ) {

            public String[] getOptions( ) {

                return new String[] { TC.get( "LOM.General.Language.English" ), TC.get( "LOM.General.Language.Spanish" ) };
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setLanguage( AVAILABLE_LANGS[option] );
                }

            }

            public int getSelectedOption( ) {

                for( int i = 0; i < AVAILABLE_LANGS.length; i++ ) {
                    if( AVAILABLE_LANGS[i].equals( data.getLanguage( ) ) )
                        return i;
                }
                return -1;
            }
        };
    }

    public LOMESTextDataControl getMetadataschemeController( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getMetadatascheme( );
            }

            public void setText( String text ) {

                data.setMetadatascheme( text );
            }

        };
    }

    public LOMESMetaMetaData getData( ) {

        return data;
    }

    public void setData( LOMESMetaMetaData data ) {

        this.data = data;
    }

}
