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
package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.ims.IMSGeneral;
import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSGeneralDataControl {

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    private IMSGeneral data;

    public IMSGeneralDataControl( IMSGeneral data ) {

        this.data = data;
    }

    public IMSTextDataControl getTitleController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getTitle( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setTitle( new LangString( text ) );
            }

        };
    }

    public IMSOptionsDataControl getLanguageController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                return new String[] { TC.get( "LOM.General.Language.English" ), TC.get( "LOM.General.Language.Spanish" ) };
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setLanguage( AVAILABLE_LANGS[option] );
                    // TODO ver que pasa con esto!!
                    //Controller.getInstance().updateLOMLanguage();
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

    public IMSTextDataControl getCatalogController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getCatalog( );
            }

            public void setText( String text ) {

                data.setCalaog( text );
            }

        };
    }

    public IMSTextDataControl getEntryController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getEntry( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setEntry( new LangString( text ) );
            }

        };
    }

    public IMSTextDataControl getDescriptionController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getDescription( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setDescription( new LangString( text ) );
            }

        };
    }

    public IMSTextDataControl getKeywordController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getKeyword( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setKeyword( new LangString( text ) );
            }

        };
    }

    /**
     * @return the data
     */
    public IMSGeneral getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( IMSGeneral data ) {

        this.data = data;
    }

}
