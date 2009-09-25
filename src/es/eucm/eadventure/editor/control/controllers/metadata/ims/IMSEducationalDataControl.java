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
package es.eucm.eadventure.editor.control.controllers.metadata.ims;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.LangString;

public class IMSEducationalDataControl {

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    private IMSEducational data;

    public IMSEducationalDataControl( IMSEducational data ) {

        this.data = data;
    }

    /**
     * ***************** OPTIONS DATA CONTROLLERS
     * ******************************************
     */
    public IMSOptionsDataControl getInteractivityTypeController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getInteractivityType( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.InteractivityType" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getInteractivityType( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getInteractivityType( ).getValueIndex( );
            }

        };
    }

    public IMSOptionsDataControl getLearningResourceTypeController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ 14 ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.LearningResourceType" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getLearningResourceType( ).setValueIndex( /*mapIndex(*/option/*)*/);
            }

            public int getSelectedOption( ) {

                return /*mapIndexInverse(*/data.getLearningResourceType( ).getValueIndex( );//);
            }

            private int mapIndex( int index ) {

                switch( index ) {
                    case 0:
                        return 0;
                    case 1:
                        return 1;
                    case 2:
                        return 10;
                    case 3:
                        return 13;
                    case 4:
                        return 14;
                    default:
                        return 0;
                }
            }

            private int mapIndexInverse( int invIndex ) {

                switch( invIndex ) {
                    case 0:
                        return 0;
                    case 1:
                        return 1;
                    case 10:
                        return 2;
                    case 13:
                        return 3;
                    case 14:
                        return 4;
                    default:
                        return 0;
                }

            }

        };
    }

    public IMSOptionsDataControl getInteractivityLevelController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getInteractivityLevel( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.InteractivityLevel" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getInteractivityLevel( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getInteractivityLevel( ).getValueIndex( );
            }

        };
    }

    public IMSOptionsDataControl getSemanticDensityController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getSemanticDensity( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.SemanticDensity" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getSemanticDensity( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getSemanticDensity( ).getValueIndex( );
            }

        };
    }

    public IMSOptionsDataControl getIntendedEndUserRoleController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getIntendedEndUserRole( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.IntendedEndUserRole" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getIntendedEndUserRole( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getIntendedEndUserRole( ).getValueIndex( );
            }

        };
    }

    public IMSOptionsDataControl getContextController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getContext( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.Contex" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getContext( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getContext( ).getValueIndex( );
            }

        };
    }

    public IMSOptionsDataControl getDifficultyController( ) {

        return new IMSOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getDifficulty( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Educational.Difficulty" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getDifficulty( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getDifficulty( ).getValueIndex( );
            }

        };
    }

    /**
     * ***************** TEXT CONTROLLERS
     * ******************************************
     */
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

    public IMSTextDataControl getTypicalAgeRangeController( ) {

        return new IMSTextDataControl( ) {

            public String getText( ) {

                return data.getTypicalAgeRange( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setTypicalAgeRange( new LangString( text ) );
            }

        };
    }

    public IMSTypicalLearningTimeDataControl getTypicalLearningTime( ) {

        return new IMSTypicalLearningTimeDataControl( data );
    }

    /**
     * ***************** GETTER & SETTER
     * ******************************************
     */
    /**
     * @return the data
     */
    public IMSEducational getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( IMSEducational data ) {

        this.data = data;
    }

}
