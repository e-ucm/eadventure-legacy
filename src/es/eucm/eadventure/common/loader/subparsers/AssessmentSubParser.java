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

import org.xml.sax.Attributes;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;

public class AssessmentSubParser extends SubParser {

    /* Attributes */

    /**
     * String to store the current string in the XML file
     */
    private StringBuffer currentString;

    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading either tag
     */
    private static final int READING_EITHER = 1;

    /**
     * Stores the current element being read
     */
    private int reading = READING_NONE;

    /**
     * Assessment rule currently being read
     */
    private AssessmentRule currentAssessmentRule;

    /**
     * Set of conditions being read
     */
    private Conditions currentConditions;

    /**
     * Set of either conditions being read
     */
    private Conditions currentEitherCondition;

    /**
     * The assessment profile
     */
    private AssessmentProfile profile;

    /* Methods */

    /**
     * Constructor.
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public AssessmentSubParser( Chapter chapter ) {

        super( chapter );
        profile = new AssessmentProfile( );

    }

    /*
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        if( qName.equals( "assessment" ) ) {

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "show-report-at-end" ) ) {
                    profile.setShowReportAtEnd( attrs.getValue( i ).equals( "yes" ) );
                }
                if( attrs.getQName( i ).equals( "send-to-email" ) ) {
                    if( attrs.getValue( i ) == null || attrs.getValue( i ).length( ) < 1 ) {
                        profile.setEmail( "" );
                        profile.setSendByEmail( false );
                    }
                    else {
                        profile.setEmail( attrs.getValue( i ) );
                        profile.setSendByEmail( true );
                    }
                }
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
        else if( qName.equals( "smtp-config" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "smtp-ssl" ) )
                    profile.setSmtpSSL( attrs.getValue( i ).equals( "yes" ) );
                if( attrs.getQName( i ).equals( "smtp-server" ) )
                    profile.setSmtpServer( attrs.getValue( i ) );
                if( attrs.getQName( i ).equals( "smtp-port" ) )
                    profile.setSmtpPort( attrs.getValue( i ) );
                if( attrs.getQName( i ).equals( "smtp-user" ) )
                    profile.setSmtpUser( attrs.getValue( i ) );
                if( attrs.getQName( i ).equals( "smtp-pwd" ) )
                    profile.setSmtpPwd( attrs.getValue( i ) );
            }
        }

        else if( qName.equals( "assessment-rule" ) ) {

            String id = null;
            int importance = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) )
                    id = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "importance" ) ) {
                    for( int j = 0; j < AssessmentRule.N_IMPORTANCE_VALUES; j++ )
                        if( attrs.getValue( i ).equals( AssessmentRule.IMPORTANCE_VALUES[j] ) )
                            importance = j;
                }
            }

            currentAssessmentRule = new AssessmentRule( id, importance );
        }

        else if( qName.equals( "timed-assessment-rule" ) ) {

            String id = null;
            int importance = 0;
            boolean usesEndConditions = false;
            boolean has = false;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) )
                    id = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "importance" ) ) {
                    for( int j = 0; j < AssessmentRule.N_IMPORTANCE_VALUES; j++ )
                        if( attrs.getValue( i ).equals( AssessmentRule.IMPORTANCE_VALUES[j] ) )
                            importance = j;
                }
                if( attrs.getQName( i ).equals( "usesEndConditions" ) ) {
                    has = true;
                    usesEndConditions = attrs.getValue( i ).equals( "yes" );
                }
            }

            currentAssessmentRule = new TimedAssessmentRule( id, importance );
            if( has )
                ( (TimedAssessmentRule) currentAssessmentRule ).setUsesEndConditions( usesEndConditions );
        }

        else if( qName.equals( "condition" ) || qName.equals( "init-condition" ) || qName.equals( "end-condition" ) ) {
            currentConditions = new Conditions( );
        }

        // If it is an either tag, create a new either conditions and switch the state
        else if( qName.equals( "either" ) ) {
            currentEitherCondition = new Conditions( );
            reading = READING_EITHER;
        }

        // If it is an active tag
        else if( qName.equals( "active" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {

                    // Store the active flag in the conditions or either conditions
                    if( reading == READING_NONE )
                        currentConditions.add( new FlagCondition( attrs.getValue( i ), FlagCondition.FLAG_ACTIVE ) );
                    if( reading == READING_EITHER )
                        currentEitherCondition.add( new FlagCondition( attrs.getValue( i ), FlagCondition.FLAG_ACTIVE ) );
                    profile.addFlag( attrs.getValue( i ) );
                }
            }
        }

        // If it is an inactive tag
        else if( qName.equals( "inactive" ) ) {
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "flag" ) ) {

                    // Store the inactive flag in the conditions or either conditions
                    if( reading == READING_NONE )
                        currentConditions.add( new FlagCondition( attrs.getValue( i ), FlagCondition.FLAG_INACTIVE ) );
                    if( reading == READING_EITHER )
                        currentEitherCondition.add( new FlagCondition( attrs.getValue( i ), FlagCondition.FLAG_INACTIVE ) );
                    profile.addFlag( attrs.getValue( i ) );
                }
            }
        }

        // If it is a greater-than tag
        else if( qName.equals( "greater-than" ) ) {
            // The var
            String var = null;
            // The value
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                currentConditions.add( new VarCondition( var, VarCondition.VAR_GREATER_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.add( new VarCondition( var, VarCondition.VAR_GREATER_THAN, value ) );
            profile.addVar( var );
        }

        // If it is a greater-equals-than tag
        else if( qName.equals( "greater-equals-than" ) ) {
            // The var
            String var = null;
            // The value
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                currentConditions.add( new VarCondition( var, VarCondition.VAR_GREATER_EQUALS_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.add( new VarCondition( var, VarCondition.VAR_GREATER_EQUALS_THAN, value ) );
            profile.addVar( var );
        }

        // If it is a less-than tag
        else if( qName.equals( "less-than" ) ) {
            // The var
            String var = null;
            // The value
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                currentConditions.add( new VarCondition( var, VarCondition.VAR_LESS_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.add( new VarCondition( var, VarCondition.VAR_LESS_THAN, value ) );
            profile.addVar( var );
        }

        // If it is a less-equals-than tag
        else if( qName.equals( "less-equals-than" ) ) {
            // The var
            String var = null;
            // The value
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                currentConditions.add( new VarCondition( var, VarCondition.VAR_LESS_EQUALS_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.add( new VarCondition( var, VarCondition.VAR_LESS_EQUALS_THAN, value ) );
            profile.addVar( var );
        }

        // If it is a equals-than tag
        else if( qName.equals( "equals" ) ) {
            // The var
            String var = null;
            // The value
            int value = 0;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                    var = attrs.getValue( i );
                }
                else if( attrs.getQName( i ).equals( "value" ) ) {
                    value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                currentConditions.add( new VarCondition( var, VarCondition.VAR_EQUALS, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.add( new VarCondition( var, VarCondition.VAR_EQUALS, value ) );
            profile.addVar( var );
        }

        // If it is a global-state-reference tag
        else if( qName.equals( "global-state-ref" ) ) {
            // Id
            String id = null;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) ) {
                    id = attrs.getValue( i );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                currentConditions.add( new GlobalStateCondition( id ) );
            if( reading == READING_EITHER )
                currentEitherCondition.add( new GlobalStateCondition( id ) );
        }

        else if( qName.equals( "set-property" ) ) {
            String id = null;
            String value = null;

            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "id" ) )
                    id = attrs.getValue( i );
                if( attrs.getQName( i ).equals( "value" ) )
                    value = attrs.getValue( i );

            }

            currentAssessmentRule.addProperty( new AssessmentProperty( id, value ) );
        }

        else if( qName.equals( "assessEffect" ) ) {
            if( currentAssessmentRule instanceof TimedAssessmentRule ) {
                int timeMin = Integer.MIN_VALUE;
                int timeMax = Integer.MIN_VALUE;
                for( int i = 0; i < attrs.getLength( ); i++ ) {

                    if( attrs.getQName( i ).equals( "time-min" ) )
                        timeMin = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "time-max" ) )
                        timeMax = Integer.parseInt( attrs.getValue( i ) );
                }

                TimedAssessmentRule tRule = (TimedAssessmentRule) currentAssessmentRule;
                if( timeMin != Integer.MIN_VALUE && timeMax != Integer.MAX_VALUE ) {
                    tRule.addEffect( timeMin, timeMax );
                }
                else {
                    tRule.addEffect( );
                }
            }
        }
    }

    /*  
     *  (non-Javadoc)
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        if( qName.equals( "assessment" ) ) {
            chapter.addAssessmentProfile( profile );
        }
        else if( qName.equals( "assessment-rule" ) || qName.equals( "timed-assessment-rule" ) ) {
            //assessmentRules.add( currentAssessmentRule );
            profile.addRule( currentAssessmentRule );
        }

        else if( qName.equals( "concept" ) ) {
            currentAssessmentRule.setConcept( currentString.toString( ).trim( ) );
        }

        else if( qName.equals( "condition" ) ) {
            currentAssessmentRule.setConditions( currentConditions );
        }

        else if( qName.equals( "init-condition" ) ) {
            ( (TimedAssessmentRule) currentAssessmentRule ).setInitConditions( currentConditions );
        }

        else if( qName.equals( "end-condition" ) ) {
            ( (TimedAssessmentRule) currentAssessmentRule ).setEndConditions( currentConditions );
        }

        // If it is an either tag
        else if( qName.equals( "either" ) ) {
            // Store the either condition in the condition, and switch the state back to normal
            currentConditions.add( currentEitherCondition );
            reading = READING_NONE;
        }

        else if( qName.equals( "set-text" ) ) {
            currentAssessmentRule.setText( currentString.toString( ).trim( ) );
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
