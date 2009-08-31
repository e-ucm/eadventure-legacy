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
package es.eucm.eadventure.common.loader.subparsers;

import java.util.List;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;
import es.eucm.eadventure.common.data.chapter.Chapter;

public class AdaptationSubParser extends SubParser {

    /* Constants */
    private static final int NONE = 0;

    private static final int INITIAL_STATE = 1;

    private static final int ADAPTATION_RULE = 2;

    private int parsing = NONE;

    /**
     * Adaptation data
     */
    private AdaptedState initialState;

    private List<AdaptationRule> externalRules;

    private AdaptationRule rule_temp;

    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;

    /**
     * The adaptation profile to fill
     */
    private AdaptationProfile profile;

    /**
     * Default constructor
     */
    public AdaptationSubParser( Chapter chapter ) {

        super( chapter );
        profile = new AdaptationProfile( );
        currentString = new StringBuffer( );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // Check if it is an scorm adaptation profile
        if( qName.equals( "adaptation" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "scorm12" ) ) {
                    profile.setScorm12( attrs.getValue( i ).equals( "yes" ) );
                }
                if( attrs.getQName( i ).equals( "scorm2004" ) ) {
                    profile.setScorm2004( attrs.getValue( i ).equals( "yes" ) );
                }
                if( attrs.getQName( i ).equals( "name" ) ) {
                    profile.setName( attrs.getValue( i ) );
                }
            }
        }

        //Start parsing the initial state
        if( qName.equals( "initial-state" ) ) {
            parsing = INITIAL_STATE;
            initialState = new AdaptedState( );
        }

        //Start parsing an adaptation rule
        else if( qName.equals( "adaptation-rule" ) ) {
            parsing = ADAPTATION_RULE;
            rule_temp = new AdaptationRule( );
        }

        //Initial scene
        else if( qName.equals( "initial-scene" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "idTarget" ) ) {
                    if( parsing == INITIAL_STATE ) {
                        initialState.setTargetId( attrs.getValue( i ) );
                    }
                    else {
                        rule_temp.setInitialScene( attrs.getValue( i ) );
                    }
                }
            }
        }

        // If the tag activates a flag
        else if( qName.equals( "activate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {
                    if( parsing == INITIAL_STATE ) {
                        initialState.addActivatedFlag( attrs.getValue( i ) );
                    }
                    else {
                        rule_temp.addActivatedFlag( attrs.getValue( i ) );
                    }
                    profile.addFlag( attrs.getValue( i ) );
                }
            }
        }

        // If the tag deactivates a flag
        else if( qName.equals( "deactivate" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {
                    if( parsing == INITIAL_STATE ) {
                        initialState.addDeactivatedFlag( attrs.getValue( i ) );
                    }
                    else {
                        rule_temp.addDeactivatedFlag( attrs.getValue( i ) );
                    }
                    profile.addFlag( attrs.getValue( i ) );
                }
            }
        }

        // If the tag set-value a var
        else if( qName.equals( "set-value" ) ) {
            String var = null;
            String value = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i );
                }
            }

            if( parsing == INITIAL_STATE ) {
                initialState.addVarValue( var, AdaptedState.VALUE + " " + value );
            }
            else {
                rule_temp.addVarValue( var, AdaptedState.VALUE + " " + value );
            }
            profile.addVar( var );

        }

        // If the tag increment a var
        else if( qName.equals( "increment" ) ) {
            String var = null;
            String value = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i );
                }

            }

            if( parsing == INITIAL_STATE ) {
                initialState.addVarValue( var, AdaptedState.INCREMENT + " " + value );
            }
            else {
                rule_temp.addVarValue( var, AdaptedState.INCREMENT + " " + value );
            }
            profile.addVar( var );

        }

        // If the tag decrement a var
        else if( qName.equals( "decrement" ) ) {
            String var = null;
            String value = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i );
                }

            }

            if( parsing == INITIAL_STATE ) {
                initialState.addVarValue( var, AdaptedState.DECREMENT + " " + value );
            }
            else {
                rule_temp.addVarValue( var, AdaptedState.DECREMENT + " " + value );
            }
            profile.addVar( var );

        }

        //Property from the UoL
        else if( qName.equals( "property" ) ) {
            String id = null;
            String value = null;
            String op = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) ) {
                    id = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "operation" ) ) {
                    op = attrs.getValue( i );
                }
            }
            rule_temp.addUOLProperty( new UOLProperty( id, value, op ) );
        }

    }

    @Override
    public void endElement( String namespaceURI, String localName, String qName ) {

        //Finish parsing the initial state
        if( qName.equals( "adaptation" ) ) {
            chapter.addAdaptationProfile( profile );
        }
        else if( qName.equals( "initial-state" ) ) {
            parsing = NONE;
            profile.setInitialState( initialState );
        }

        else if( qName.equals( "ruleDescription" ) ) {
            this.rule_temp.setDescription( currentString.toString( ).trim( ) );
        }

        //Finish parsing an adaptation rule
        else if( qName.equals( "adaptation-rule" ) ) {
            parsing = NONE;
            profile.addRule( rule_temp );
        }

        // Reset the current string
        currentString = new StringBuffer( );
    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // Append the new characters
        currentString.append( new String( buf, offset, len ) );

    }
}
