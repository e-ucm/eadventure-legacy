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

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.Vocabulary;

public class LOMESEducationalDataControl {

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    private LOMESEducational data;

    public LOMESEducationalDataControl( LOMESEducational data ) {

        this.data = data;
    }

    /**
     * ***************** OPTIONS DATA CONTROLLERS
     * ******************************************
     */
    public LOMESOptionsDataControl getInteractivityTypeController( ) {

        return new LOMESOptionsDataControl( ) {

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

    public ArrayList<Vocabulary> getLearningResourceType( ) {

        return data.getLearningResourceType( );
    }

    public String[] getLearningResourceTypeTextOptions( ) {

        String[] options = new String[ data.getLearningResourceType( ).get( 0 ).getValues( ).length ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Educational.LearningResourceType" + i );
        }
        return options;
    }

    public String[] getLearningResourceTypeOptions( ) {

        return data.getLearningResourceType( ).get( 0 ).getValues( );
    }

    public LOMESOptionsDataControl getInteractivityLevelController( ) {

        return new LOMESOptionsDataControl( ) {

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

    public LOMESOptionsDataControl getSemanticDensityController( ) {

        return new LOMESOptionsDataControl( ) {

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

    public ArrayList<Vocabulary> getIntendedEndUserRole( ) {

        return data.getIntendedEndUserRole( );
    }

    public String[] getIntendedEndUserRoleTextOptions( ) {

        String[] options = new String[ data.getIntendedEndUserRole( ).get( 0 ).getValues( ).length ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Educational.IntendedEndUserRole" + i );
        }
        return options;
    }

    public String[] getIntendedEndUserRoleOptions( ) {

        return data.getIntendedEndUserRole( ).get( 0 ).getValues( );
    }

    /*public LOMESOptionsDataControl getIntendedEndUserRoleController() {
    	return new LOMESOptionsDataControl (){

    		public String[] getOptions( ) {
    			String[] options = new String[data.getIntendedEndUserRole( ).getValues( ).length];
    			for (int i=0; i<options.length; i++){
    				options[i]=TextConstants.getText( "LOMES.Educational.IntendedEndUserRole"+i );
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
    }*/

    public ArrayList<Vocabulary> getContext( ) {

        return data.getContext( );
    }

    public String[] getContextTextOptions( ) {

        String[] options = new String[ data.getContext( ).get( 0 ).getValues( ).length ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Educational.Context" + i );
        }
        return options;
    }

    public String[] getContextOptions( ) {

        return data.getContext( ).get( 0 ).getValues( );
    }

    /*public LOMESOptionsDataControl getContextController() {
    	return new LOMESOptionsDataControl (){

    		public String[] getOptions( ) {
    			String[] options = new String[data.getContext( ).getValues( ).length];
    			for (int i=0; i<options.length; i++){
    				options[i]=TextConstants.getText( "LOMES.Educational.Context"+i );
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
    }*/

    public LOMESOptionsDataControl getDifficultyController( ) {

        return new LOMESOptionsDataControl( ) {

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

    public ArrayList<Vocabulary> getCognitiveProcess( ) {

        return data.getCognitiveProcess( );
    }

    public String[] getCognitiveProcessTextOptions( ) {

        String[] options = new String[ data.getCognitiveProcess( ).get( 0 ).getValues( ).length ];
        for( int i = 0; i < options.length; i++ ) {
            options[i] = TC.get( "LOMES.Educational.CognitiveProcess" + i );
        }
        return options;
    }

    public String[] getCognitiveProcessOptions( ) {

        return data.getCognitiveProcess( ).get( 0 ).getValues( );
    }

    /*public LOMESOptionsDataControl getCognitiveProcessController() {
    	return new LOMESOptionsDataControl (){

    		public String[] getOptions( ) {
    			String[] options = new String[data.getCognitiveProcess().getValues( ).length];
    			for (int i=0; i<options.length; i++){
    				options[i]=TextConstants.getText( "LOMES.Educational.CognitiveProcess"+i );
    			}
    			return options;
    		}

    		public void setOption( int option ) {
    			data.getCognitiveProcess().setValueIndex( option );
    		}

    		public int getSelectedOption( ) {
    			return data.getCognitiveProcess().getValueIndex( );
    		}
    		
    	};
    }*/

    /**
     * ***************** TEXT CONTROLLERS
     * ******************************************
     */
    /*public LOMESTextDataControl getDescriptionController (){
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getDescription( ).getValue( 0 );
    		}

    		public void setText( String text ) {
    			data.setDescription( new LangString(text) );
    		}
    		
    	};
    }*/
    public ArrayList<LangString> getDescription( ) {

        return data.getDescription( );
    }

    /*public LOMESTextDataControl getTypicalAgeRangeController (){
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getTypicalAgeRange( ).getValue( 0 );
    		}

    		public void setText( String text ) {
    			data.setTypicalAgeRange( new LangString(text) );
    		}
    		
    	};
    }*/
    public ArrayList<LangString> getTypicalAgeRange( ) {

        return data.getTypicalAgeRange( );
    }

    public LOMESTypicalLearningTimeDataControl getTypicalLearningTime( ) {

        return new LOMESTypicalLearningTimeDataControl( data );
    }

    /**
     * ***************** GETTER & SETTER
     * ******************************************
     */
    /**
     * @return the data
     */
    public LOMESEducational getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMESEducational data ) {

        this.data = data;
    }

}
