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

import java.util.ArrayList;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMIdentifier;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESGeneral;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESGeneralDataControl {

    public static final String[] AVAILABLE_LANGS = new String[] { "en", "es" };

    private LOMESGeneral data;

    public LOMESGeneralDataControl( LOMESGeneral data ) {

        this.data = data;
    }

    public LOMIdentifier getIdentifier( ) {

        return data.getIdentifier( );
    }

    public LOMESOptionsDataControl getAggregationLevel( ) {

        return new LOMESOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getAggregationLevel( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = Integer.toString( i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.getAggregationLevel( ).setValueIndex( option );
            }

            public int getSelectedOption( ) {

                return data.getAggregationLevel( ).getValueIndex( );
            }

        };
    }

    public LOMESTextDataControl getTitleController( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getTitle( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setTitle( new LangString( text ) );
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

    /*public LOMESTextDataControl getCatalogController() {
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getIdentifier(data.getCurrentId()).getCatalog();
    		}

    		public void setText( String text ) {
    			data.getIdentifier(data.getCurrentId()).setCatalog(text );
    		}
    		
    	};
    }
    
    public LOMESTextDataControl getEntryController() {
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getIdentifier(data.getCurrentId()).getEntry();
    		}

    		public void setText( String text ) {
    			data.getIdentifier(data.getCurrentId()).setEntry(text );
    		}
    		
    	};
    }*/

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

    /*public LOMESTextDataControl getKeywordController (){
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getKeyword( ).getValue( 0 );
    		}

    		public void setText( String text ) {
    			data.setKeyword( new LangString(text) );
    		}
    		
    	};
    }*/

    public String[] getKeywordsToString( ) {

        return data.keywordsToString( );
    }

    public ArrayList<LangString> getKeywords( ) {

        return data.getKeywords( );
    }

    public ArrayList<LangString> getDescriptions( ) {

        return data.getDescriptions( );
    }

    public ArrayList<String> getLanguages( ) {

        return data.getLanguages( );
    }

    /**
     * @return the data
     */
    public LOMESGeneral getData( ) {

        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( LOMESGeneral data ) {

        this.data = data;
    }

}
