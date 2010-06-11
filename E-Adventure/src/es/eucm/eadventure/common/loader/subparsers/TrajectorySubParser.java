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
package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

/**
 * Class to subparse items.
 */
public class TrajectorySubParser extends SubParser {

    /* Attributes */

    /**
     * Constant for subparsing nothing.
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Stores the current element being subparsed.
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Trajectory being parsed.
     */
    private Trajectory trajectory;

    /**
     * Subparser for effects and conditions.
     */
    private SubParser subParser;

    /**
     * Scene to add the trajectory
     */
    private Scene scene;

    /* Methods */

    /**
     * Constructor.
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public TrajectorySubParser( Chapter chapter, Scene scene ) {

        super( chapter );
        this.trajectory = new Trajectory( );
        //scene.setTrajectory(trajectory);
        this.scene = scene;
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
            if( qName.equals( "node" ) ) {

                int x = 0, y = 0;
                float scale = 1.0f;

                String id = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "x" ) )
                        x = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "y" ) )
                        y = Integer.parseInt( attrs.getValue( i ) );
                    if( attrs.getQName( i ).equals( "id" ) )
                        id = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "scale" ) ) {
                        scale = Float.parseFloat( attrs.getValue( i ) );
                    }
                }

                trajectory.addNode( id, x, y, scale );

            }

            // If it is a condition tag, create new conditions and switch the state
            else if( qName.equals( "side" ) ) {
                String idStart = "";
                String idEnd = "";
                int length = -1;
                
                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "idStart" ) )
                        idStart = attrs.getValue( i );
                    if( attrs.getQName( i ).equals( "idEnd" ) )
                        idEnd = attrs.getValue( i );
                    if ( attrs.getQName( i ).equals( "length"))
                        length = Integer.parseInt( attrs.getValue( i ) );
                }

                trajectory.addSide( idStart, idEnd, length );
            }

            else if( qName.equals( "initialnode" ) ) {
                String id = "";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getQName( i ).equals( "id" ) )
                        id = attrs.getValue( i );
                }

                trajectory.setInitial( id );
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

            // Reset the current string
            currentString = new StringBuffer( );
        }

        if( qName.equals( "trajectory" ) ) {
            if( trajectory.getNodes( ).size( ) != 0 ) {
                trajectory.deleteUnconnectedNodes( );
                scene.setTrajectory( trajectory );

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
