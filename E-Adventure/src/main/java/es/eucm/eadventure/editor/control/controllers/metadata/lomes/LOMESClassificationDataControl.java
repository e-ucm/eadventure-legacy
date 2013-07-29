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
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxonPath;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESClassification;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESClassificationDataControl {

    private LOMESClassification data;

    public LOMESClassificationDataControl( LOMESClassification data ) {

        this.data = data;
    }

    public LOMESOptionsDataControl getPurpose( ) {

        return new LOMESOptionsDataControl( ) {

            public String[] getOptions( ) {

                String[] options = new String[ data.getPurpose( ).getValues( ).length ];
                for( int i = 0; i < options.length; i++ ) {
                    options[i] = TC.get( "IMS.Classification.Purpose" + i );
                }
                return options;
            }

            public void setOption( int option ) {

                data.setPurpose( option );
            }

            public int getSelectedOption( ) {

                return data.getPurpose( ).getValueIndex( );
            }

        };
    }

    public LOMESTextDataControl getDescription( ) {

        return new LOMESTextDataControl( ) {

            public String getText( ) {

                return data.getDescription( ).getValue( 0 );
            }

            public void setText( String text ) {

                data.setDescription( new LangString( text ) );
            }

        };
    }

    public LOMTaxonPath getTaxonPath( ) {

        return data.getTaxonPath( );
    }

    /*public LOMESTextDataControl getSource(){
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getSource().getValue( 0 );
    		}

    		public void setText( String text ) {
    			data.setSource( new LangString(text) );
    		}
    		
    	};
    }
    
    public LOMESTextDataControl getIdentifier(){
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getIdentifier();
    		}

    		public void setText( String text ) {
    			data.setIdentifier(text);
    		}
    		
    	};
    }
    
    public LOMESTextDataControl getEntry(){
    	return new LOMESTextDataControl (){

    		public String getText( ) {
    			return data.getEntry().getValue(0);
    		}

    		public void setText( String text ) {
    			data.setEntry(new LangString(text));
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

    public ArrayList<LangString> getKeywords( ) {

        return data.getKeywords( );
    }

    public LOMESClassification getData( ) {

        return data;
    }

    public void setData( LOMESClassification data ) {

        this.data = data;
    }

}
