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

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.lom.LOMGeneral;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.control.config.LOMConfigData;

public class LOMGeneralDataControl {

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    public static final String GROUP = "general";

    private LOMGeneral data;

    public LOMGeneralDataControl( LOMGeneral data ) {

        this.data = data;
    }

    public LOMTextDataControl getTitleController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getTitle( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setTitle( new LangString( text ) );
                LOMConfigData.storeData( GROUP, "title", text );
            }

        };
    }

    public LOMOptionsDataControl getLanguageController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                return new String[] { TC.get( "LOM.General.Language.English" ), TC.get( "LOM.General.Language.Spanish" ) };
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setLanguage( AVAILABLE_LANGS[option] );
                    Controller.getInstance( ).updateLOMLanguage( );
                    LOMConfigData.storeData( GROUP, "language", Integer.toString( option ) );
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

    public LOMTextDataControl getDescriptionController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getDescription( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setDescription( new LangString( text ) );
                LOMConfigData.storeData( GROUP, "description", text );
            }

        };
    }

    public LOMTextDataControl getKeywordController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getKeyword( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setKeyword( new LangString( text ) );
                LOMConfigData.storeData( GROUP, "keyword", text );
            }

        };
    }

    /**
     * @return the data
     */
    public LOMGeneral getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMGeneral data ) {

        this.data = data;
    }

}
