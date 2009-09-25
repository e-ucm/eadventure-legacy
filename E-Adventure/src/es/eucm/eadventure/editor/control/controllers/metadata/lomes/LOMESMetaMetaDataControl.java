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
