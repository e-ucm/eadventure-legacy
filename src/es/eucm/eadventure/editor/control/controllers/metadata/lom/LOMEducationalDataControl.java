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

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.lom.LOMEducational;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.control.config.LOMConfigData;

public class LOMEducationalDataControl {

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    public static final String GROUP = "educational";

    private LOMEducational data;

    public LOMEducationalDataControl( LOMEducational data ) {

        this.data = data;
    }

    /**
     * ***************** OPTIONS DATA CONTROLLERS
     * ******************************************
     */
    public LOMOptionsDataControl getInteractivityTypeController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getInteractivityType( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.InteractivityType" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getInteractivityType( ).setValueIndex( option );
                LOMConfigData.storeData( GROUP, "interactivityType", Integer.toString( option ) );
            }

            public int getSelectedOption( ) {

                return data.getInteractivityType( ).getValueIndex( );
            }

        };
    }

    public LOMOptionsDataControl getLearningResourceTypeController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ 5 ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.LearningResourceType" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getLearningResourceType( ).setValueIndex( mapIndex( option ) );
                LOMConfigData.storeData( GROUP, "learningResourceType", Integer.toString( option ) );
            }

            public int getSelectedOption( ) {

                return mapIndexInverse( data.getLearningResourceType( ).getValueIndex( ) );
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

    public LOMOptionsDataControl getInteractivityLevelController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getInteractivityLevel( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.InteractivityLevel" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getInteractivityLevel( ).setValueIndex( option );
                LOMConfigData.storeData( GROUP, "interactivityLevel", Integer.toString( option ) );
            }

            public int getSelectedOption( ) {

                return data.getInteractivityLevel( ).getValueIndex( );
            }

        };
    }

    public LOMOptionsDataControl getSemanticDensityController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getSemanticDensity( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.SemanticDensity" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getSemanticDensity( ).setValueIndex( option );
                LOMConfigData.storeData( GROUP, "semanticDensity", Integer.toString( option ) );
            }

            public int getSelectedOption( ) {

                return data.getSemanticDensity( ).getValueIndex( );
            }

        };
    }

    public LOMOptionsDataControl getIntendedEndUserRoleController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getIntendedEndUserRole( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.IntendedEndUserRole" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getIntendedEndUserRole( ).setValueIndex( option );
                LOMConfigData.storeData( GROUP, "intendedEndUserRole", Integer.toString( option ) );
            }

            public int getSelectedOption( ) {

                return data.getIntendedEndUserRole( ).getValueIndex( );
            }

        };
    }

    public LOMOptionsDataControl getContextController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getContext( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.Context" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getContext( ).setValueIndex( option );
                LOMConfigData.storeData( GROUP, "context", Integer.toString( option ) );
            }

            public int getSelectedOption( ) {

                return data.getContext( ).getValueIndex( );
            }

        };
    }

    public LOMOptionsDataControl getDifficultyController( ) {

        return new LOMOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getDifficulty( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "LOM.Educational.Difficulty" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getDifficulty( ).setValueIndex( option );
                LOMConfigData.storeData( GROUP, "difficulty", Integer.toString( option ) );
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

    public LOMTextDataControl getTypicalAgeRangeController( ) {

        return new LOMTextDataControl( ) {

            public String getText( ) {

                return data.getTypicalAgeRange( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setTypicalAgeRange( new LangString( text ) );
                LOMConfigData.storeData( GROUP, "typicalAgeRange", text );
            }

        };
    }

    public LOMTypicalLearningTimeDataControl getTypicalLearningTime( ) {

        return new LOMTypicalLearningTimeDataControl( data );
    }

    /**
     * ***************** GETTER & SETTER
     * ******************************************
     */
    /**
     * @return the data
     */
    public LOMEducational getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMEducational data ) {

        this.data = data;
    }

}
