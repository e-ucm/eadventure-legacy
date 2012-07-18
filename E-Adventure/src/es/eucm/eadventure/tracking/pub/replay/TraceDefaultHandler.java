/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 * research group.
 * 
 * Copyright 2005-2012 <e-UCM> research group.
 * 
 * <e-UCM> is a research group of the Department of Software Engineering and
 * Artificial Intelligence at the Complutense University of Madrid (School of
 * Computer Science).
 * 
 * C Profesor Jose Garcia Santesmases sn, 28040 Madrid (Madrid), Spain.
 * 
 * For more info please visit: <http://e-adventure.e-ucm.es> or
 * <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 * You can access a list of all the contributors to <e-Adventure> at:
 * http://e-adventure.e-ucm.es/contributors
 * 
 * ****************************************************************************
 * <e-Adventure> is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with <e-Adventure>. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.tracking.pub.replay;

import java.awt.Point;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TraceDefaultHandler extends DefaultHandler {

    public enum Actions {
        MOVE, PRESS, RELEASE, SCENE
    }

    private ArrayList<Actions> actions;

    private ArrayList<Point> points;

    private ArrayList<Integer> delays;

    private ArrayList<Integer> buttons;

    private ArrayList<Integer> opts;

    private ArrayList<String> lines;

    private ArrayList<String> scenes;
    
    private ArrayList<Integer> ms;

    private long currentms = 0;

    public TraceDefaultHandler( ) {

        actions = new ArrayList<Actions>( );
        points = new ArrayList<Point>( );
        delays = new ArrayList<Integer>( );
        buttons = new ArrayList<Integer>( );
        opts = new ArrayList<Integer>( );
        lines = new ArrayList<String>( );
        scenes = new ArrayList<String>( );
        ms = new ArrayList<Integer>();
    }

    @Override
    public void startElement( String uri, String localName, String qName, Attributes attributes ) throws SAXException {

        if( qName.equals( "l" ) || qName.equals( "h" ) ) {
            long timeStamp = Long.parseLong( attributes.getValue( "ms" ) );
            ms.add( (int) timeStamp );
            Integer delay = (int) ( timeStamp - currentms );

            // Low level traces
            if( qName.equals( "l" ) ) {
                String button = attributes.getValue( "b" );
                String x = attributes.getValue( "x" );
                String y = attributes.getValue( "y" );
                if( attributes.getValue( "i" ).equals( "m" ) && x != null && y != null ) {
                    buttons.add( 0 );
                    delays.add( delay );
                    actions.add( Actions.MOVE );
                    int xp = Integer.parseInt( x );
                    int yp = Integer.parseInt( y );
                    points.add( new Point( xp, yp ) );
                    currentms = timeStamp;
                }
                else if( attributes.getValue( "i" ).equals( "p" ) && button != null && x != null && y != null ) {
                    buttons.add( Integer.parseInt( button ) );
                    delays.add( delay );
                    actions.add( Actions.PRESS );
                    int xp = Integer.parseInt( x );
                    int yp = Integer.parseInt( y );
                    points.add( new Point( xp, yp ) );
                    currentms = timeStamp;
                }
                else if( attributes.getValue( "i" ).equals( "r" ) && button != null && x != null && y != null ) {
                    buttons.add( Integer.parseInt( attributes.getValue( "b" ) ) );
                    delays.add( delay );
                    actions.add( Actions.RELEASE );
                    int xp = Integer.parseInt( x );
                    int yp = Integer.parseInt( y );
                    points.add( new Point( xp, yp ) );
                    currentms = timeStamp;
                }
            }
            else if( qName.equals( "h" ) ) {
                String type = attributes.getValue( "a" );
                String line = attributes.getValue( "l" );
                String opt = attributes.getValue( "t" );
                if( type != null && type.equals( "copt" ) && line != null && opt != null ) {
                    opts.add( Integer.parseInt( opt ) );
                    lines.add( line );
                }
                else if( type != null && type.equals( "scn" ) ) {
                    String scene = attributes.getValue( "o" );
                    scenes.add( scene );
                    buttons.add( 0 );
                    delays.add( delay );
                    actions.add( Actions.SCENE );
                    points.add( new Point( 0, 0 ) );
                    currentms = timeStamp;
                }
            }
        }
    }

    public ArrayList<String> getScenes( ) {

        return scenes;
    }

    public ArrayList<Integer> getOpts( ) {

        return opts;
    }

    public ArrayList<String> getLines( ) {

        return lines;
    }

    public ArrayList<Actions> getActions( ) {

        return actions;
    }

    public ArrayList<Point> getPoints( ) {

        return points;
    }

    public ArrayList<Integer> getDelays( ) {

        return delays;
    }

    public ArrayList<Integer> getButtons( ) {

        return buttons;
    }

    public long getCurrentms( ) {

        return currentms;
    }
    
    public ArrayList<Integer> getMs( ) {
        
        return ms;
    }

}
