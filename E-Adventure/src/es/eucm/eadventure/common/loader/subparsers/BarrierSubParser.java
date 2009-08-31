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

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

/**
 * Class to subparse items.
 */
public class BarrierSubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for reading nothing.
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for subparsing nothing.
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag.
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Store the current element being parsed.
     */
    private int reading = READING_NONE;

    /**
     * Stores the current element being subparsed.
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Barrier being parsed.
     */
    private Barrier barrier;

    /**
     * Current conditions being parsed.
     */
    private Conditions currentConditions;

    /**
     * Subparser for effects and conditions.
     */
    private SubParser subParser;

    /**
     * Stores the scene where the area should be attached
     */
    private Scene scene;

    private int nBarriers;

    /* Methods */

    /**
     * Constructor.
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public BarrierSubParser( Chapter chapter, Scene scene, int nBarriers ) {

        super( chapter );
        this.nBarriers = nBarriers;
        this.scene = scene;
    }

    private String generateId( ) {

        return Integer.toString( nBarriers + 1 );
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
            // If it is a object tag, create the new object (with its id)
            if( qName.equals( "barrier" ) ) {

                int x = 0, y = 0, width = 0, height = 0;

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "width" ) )
                        width = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "height" ) )
                        height = Integer.parseInt( attrs.getValue( i ) );
                }

                barrier = new Barrier( generateId( ), x, y, width, height );
            }

            // If it is a condition tag, create new conditions and switch the state
            else if( qName.equals( "condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

        }

        // If it is reading an effect or a condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, qName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is an object tag, store the object in the game data
            if( qName.equals( "barrier" ) ) {
                scene.addBarrier( barrier );
            }

            // If it is a name tag, store the name in the object
            else if( qName.equals( "name" ) ) {
                barrier.setName( currentString.toString( ).trim( ) );
            }

            // If it is a documentation tag, hold the documentation in the current element
            else if( qName.equals( "documentation" ) ) {
                if( reading == READING_NONE )
                    barrier.setDocumentation( currentString.toString( ).trim( ) );
            }

            // If it is a brief tag, store the brief description in the object
            else if( qName.equals( "brief" ) ) {
                barrier.setDescription( currentString.toString( ).trim( ) );
            }

            // If it is a detailed tag, store the detailed description in the object
            else if( qName.equals( "detailed" ) ) {
                barrier.setDetailedDescription( currentString.toString( ).trim( ) );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, qName );

            // If the condition tag is being closed
            if( qName.equals( "condition" ) ) {
                if( reading == READING_NONE ) {
                    this.barrier.setConditions( currentConditions );
                }
                // Switch state
                subParsing = SUBPARSING_NONE;
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If it is reading an effect or a condition, spread the call
        else
            subParser.characters( buf, offset, len );
    }
}
