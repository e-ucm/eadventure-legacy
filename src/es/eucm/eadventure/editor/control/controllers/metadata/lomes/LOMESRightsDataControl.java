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
package es.eucm.eadventure.editor.control.controllers.metadata.lomes;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESRights;

public class LOMESRightsDataControl {

    public static final String[] AVAILABLE_OPTIONS = new String[] { "yes", "no" };

    private LOMESRights data;

    public LOMESRightsDataControl( LOMESRights data ) {

        this.data = data;
    }

    public LOMESOptionsDataControl getCost( ) {

        return new LOMESOptionsDataControl( ) {

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
                    if( AVAILABLE_OPTIONS[i].equals( data.getCost( ).getValue( ) ) )
                        return i;
                }
                return -1;
            }

        };
    }

    public LOMESOptionsDataControl getCopyrightandotherrestrictions( ) {

        return new LOMESOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getCopyrightandotherrestrictions( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOMES.Rights.CopyAndOthers" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setCopyrightandotherrestrictions( option );
                }

            }

            public int getSelectedOption( ) {

                for( int i = 0; i < AVAILABLE_OPTIONS.length; i++ ) {
                    if( AVAILABLE_OPTIONS[i].equals( data.getCopyrightandotherrestrictions( ).getValue( ) ) )
                        return i;
                }
                return -1;
            }

        };
    }

    public LOMESTextDataControl getDescriptionController( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getDescription( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setDescription( new LangString( text ) );
            }

        };
    }

    public LOMESTextDataControl getAccessDescriptionController( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getAccessDescription( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setAccessDescription( new LangString( text ) );
            }

        };
    }

    public LOMESOptionsDataControl getAccesType( ) {

        return new LOMESOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getAccessType( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOMES.Rights.AccesType" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                if( option != getSelectedOption( ) ) {
                    data.setCopyrightandotherrestrictions( option );
                }

            }

            public int getSelectedOption( ) {

                for( int i = 0; i < AVAILABLE_OPTIONS.length; i++ ) {
                    if( AVAILABLE_OPTIONS[i].equals( data.getAccessType( ).getValue( ) ) )
                        return i;
                }
                return -1;
            }

        };
    }

    public LOMESRights getData( ) {

        return data;
    }

    public void setData( LOMESRights data ) {

        this.data = data;
    }

}
